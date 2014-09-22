/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;


// imports:
import error.*;
import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;
import java.math.BigDecimal;
import javax.swing.*;
import terminalinterface.ChartFormat.DisplayData;



/**
 * Visual representation of financial data in the form of a chart.
 *
 * @author Alexandar Atanasov
 */
public class Chart extends JPanel {
    // declare member variables:

    /** The default lenght of one bar. */
    private static final int DEFAULT_BAR_LENGH = 10;

    /** The default lenght of the text area. */
    private static final int DEFAULT_TEXT_AREA_LENGHT = 80;

    /** The default gap between the borders and the edge of the chart. */
    private static final int DEFAULT_BORDER_GAP = 5;

    /** The default color of chart borders. */
    private static final Color DEFAULT_BORDER_COLOR = Color.WHITE;

    /** The number of digits after the dot in the price of the chosen market security. */
    private int digitsAfterDot;

    /** The lenght of the text area (text area is the  area in which the price level numbers are displayed) . */
    private int textAreaLenght;

    /** The lenght of a single bar. */
    private int barLenght;

    /** */
    private int bars;

    /** Instance of the ErrorHanadler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The financial data contained in this chart. */
    private ChartData data;

    /** The format which regulates the way of displaying the data. */
    private ChartFormat format;
    // end of member variables declaration
    

    // initialization of static members:
    static {
        eHandler = new ErrorHandler("Chart");
    } // end of initialization of static members


    /**
     * Constructor.
     *
     * @param cFormat The format which regulates the way of displaying the data. If null is provided to
     *                this argument error will be generated.
     * @param digits The number of digits after the dot in the price of the chosen market security. If
     *               negative number is provided to this argument error will be generated.
     */
    Chart(ChartFormat cFormat, int digits) {
        // set the background
        setBackground(Color.BLACK);

        // set the members
        setFormat(cFormat);
        setBarLenght(DEFAULT_BAR_LENGH);
        setTextAreaLenght(DEFAULT_TEXT_AREA_LENGHT);
        setDigitsAfterDot(digits);

    } // end of constructor


    /**
     * Sets the data member.
     *
     * @param close Array of close prices. If null is provided to this argument error will be generated.
     * @param high Array of high prices. If null is provided to this argument error will be generated.
     * @param low Array of low prices. If null is provided to this argument error will be generated.
     * @param vol Array of volume prices. If null is provided to this argument error will be generated.
     */
    public void setData(int close[] , int high[] , int low[] , int vol[]) {
        // check for null pointers
        if(close == null || high == null || low == null || vol == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setData");

            // exit from method
            return;

        } // end of if statement

        data = new ChartData(close , high , low , vol);

    } // end of method setData()


    /**
     * Sets the barLenght member.
     * 
     * @param len The lenght of a single bar. If non positive number is provided to this argument error 
     *            will be generated.
     */
    private void setBarLenght(int len) {
        // check for logical error
        if(len <= 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setBarLenght", "Zero or negative bar lenght");

            // set to default
            barLenght =DEFAULT_BAR_LENGH;

            // exit from method
            return;
        } // end of if statement

        // set the barLenght
        barLenght = len;

    } // end of method setBarLenght()


    /**
     * Sets the textAreaLenght member.
     *
     * @param len The lenght of the text area. If non positive number is provided to this argument error
     *            will be generated.
     */
    private void setTextAreaLenght(int len) {
        // check for logical error
        if(len <= 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setTextAreaLenght", "Zero or negative lenght");

            // set to default
            textAreaLenght = DEFAULT_TEXT_AREA_LENGHT;

            // exit from method
            return;
        } // end of if statement

        // set the textAreaLenght
        textAreaLenght = len;

    } // end of method setTextAreaLenght()


    /**
     * Sets the digitsAfterDot member.
     * 
     * @param digits The number of digits after the dot in the price of the chosen market security. If
     *               negative number is provided to this argument error will be generated.
     */
    private void setDigitsAfterDot(int digits) {
        // check for logical error
        if(digits < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setDigitsAfterDot" , "Negative digits number");

            // exit from method
            return;
        } // end of if statement

        // set the digitsAfterDot
        digitsAfterDot = digits;

    } // end of method setDigitsAfterDot()


    /**
     * Sets the format member.
     *
     * @param chartFormat The format which regulates the way of displaying the data. If null is provided to
     *                    this argument error will be generated.
     */
    public void setFormat(ChartFormat chartFormat) {
        // check for null pointer
        if( chartFormat == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setFormat");

            // exit from method
            return;
        } // end of if statement

        // set the format
        format = new ChartFormat(chartFormat);

        // force repaint of the window to update it
        repaint();

    } // end of method setFormat()


    //
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // update the text area lenght
        updateTextAreaLenght();

        // update tha number of bars
        updateBars();
        
        // draw internal borders
        drawBorders(g);

        // draw the text area
        drawTextArea(g);

        // draw the content area
        drawContentArea(g);

        // draw bars
        drawBars(g);

    } // end of method paint


    /**
     * Draws the borders in the chart.
     *
     * @param g Graphic context object.
     */
    private void drawBorders(Graphics g) {
        // set the drawing colour
        g.setColor(DEFAULT_BORDER_COLOR);

        // draw the upper border
        g.drawLine(DEFAULT_BORDER_GAP, DEFAULT_BORDER_GAP, this.getWidth()-DEFAULT_BORDER_GAP , DEFAULT_BORDER_GAP);

        // draw the bottom border
        g.drawLine(DEFAULT_BORDER_GAP, this.getHeight()-DEFAULT_BORDER_GAP, this.getWidth()-DEFAULT_BORDER_GAP ,
                   this.getHeight()-DEFAULT_BORDER_GAP);

        // draw the left border
        g.drawLine(DEFAULT_BORDER_GAP, DEFAULT_BORDER_GAP, DEFAULT_BORDER_GAP, this.getHeight()-DEFAULT_BORDER_GAP);

        // draw the right border
        g.drawLine(this.getWidth()-DEFAULT_BORDER_GAP , DEFAULT_BORDER_GAP , this.getWidth()-DEFAULT_BORDER_GAP,
                   this.getHeight()-DEFAULT_BORDER_GAP);

        // draw the horizontal internal border
        g.drawLine(DEFAULT_BORDER_GAP, this.getHeight()-25,
                   this.getWidth()-textAreaLenght , this.getHeight()-25);

        // draw the vertical internal border
        g.drawLine(this.getWidth()-textAreaLenght , DEFAULT_BORDER_GAP ,
                   this.getWidth()-textAreaLenght ,this.getHeight()-25 );

    } // end of method


    // this method draws the bars
    private void drawBars(Graphics g) {
        // declare local variables:
        int numberOfDigits=1;
        // end of local variables declaration


        // draw the bars
        for(int c = 0; c < bars ; c++) {
            // set the drawing colour
            g.setColor(Color.WHITE);

            // check the number of digits of c
            if( c > 8) {
                numberOfDigits = 2;
            } // end of if statement

            // check if bar number should be drawn
            if(c%3==0) {
                // set the colour
                g.setColor(new Color(190, 130, 0) );
                // draw bar number
                g.drawString(Integer.toString(c+1) ,
                             getWidth()- (textAreaLenght+barLenght)- barLenght*c -( 3*numberOfDigits ),
                             getHeight()-8);
            } // end of if statement

            // draw vertical mini line
            g.drawLine(getWidth()- (textAreaLenght+barLenght)- barLenght*c, getHeight()-24,
                       getWidth()- (textAreaLenght+barLenght)- barLenght*c, getHeight()-20);


        } // end of for loop

    } // end of method drawBars()


    // this method draws the text area
    private void drawTextArea(Graphics g) {
        // declare local variables:
        int buffer;
        int horizontalBars;
        int greatestMinimum;
        int greatestMaximum;
        int dataMinimums[];
        int dataMaximums[];
        int heightDifference;
        double verticalDifference;
        DisplayData displayData[];
        BigDecimal horizontalLevels[];
        // end of local variables declaration


        // set the drawing colour
        g.setColor(Color.WHITE);

        // determine what data is displayed
        displayData = format.getDisplayData();

        // check for null pointer
        if(displayData == null || data == null) {
            // exit
            return;
        } // end of if statement
        
        // initialize the minimums and maximums
        dataMinimums = new int[displayData.length];
        dataMaximums = new int[displayData.length];

        // get the minimums and maximums
        for(int c = 0; c < displayData.length; c++) {
            //
            switch(displayData[c]) {
                case CLOSE_PRICES:
                    dataMinimums[c] = data.getMinimum(data.getClosePrices(bars) );
                    dataMaximums[c] = data.getMaximum(data.getClosePrices(bars) );
                break;

                case HIGH_PRICES:
                    dataMinimums[c] = data.getMinimum(data.getHighPrices(bars) );
                    dataMaximums[c] = data.getMaximum(data.getHighPrices(bars) );
                break;

                case LOW_PRICES:
                    dataMinimums[c] = data.getMinimum(data.getLowPrices(bars) );
                    dataMaximums[c] = data.getMinimum(data.getLowPrices(bars) );
                break;

                case VOLUMES:
                    dataMinimums[c] = data.getMinimum(data.getVolumes(bars) );
                    dataMaximums[c] = data.getMinimum(data.getVolumes(bars) );
                break;
            } // end of switch statement

        } // end of for loop

        // find the greatest minimums and greatest maximum
        greatestMinimum = data.getMinimum(dataMinimums);
        greatestMaximum = data.getMaximum(dataMaximums);

        // find the heightDifference
        heightDifference = greatestMaximum - greatestMinimum;

        // check for logical error
        if(heightDifference == 0 || heightDifference < 0 ) {
            // exit from method
            return;
        } // end of if statement

        // find the horizontal bars
        horizontalBars = (getHeight() - 50 ) / 35 ;

        // find the verticalDifference
        verticalDifference = heightDifference / horizontalBars ;

        // initialize the horizontal levels array
        horizontalLevels = new BigDecimal[horizontalBars+1];

        // resolve the horizontal levels array
        for(int c = 0; c < horizontalLevels.length ; c++) {
            buffer = (int ) greatestMinimum + c* ( (int )  Math.round(verticalDifference) );
            horizontalLevels[c] = new BigDecimal(Integer.toString( buffer ) );
            horizontalLevels[c] = horizontalLevels[c].movePointLeft(digitsAfterDot);
        } // end of for loop

        // add the maximum
        horizontalLevels[horizontalLevels.length-1] = new BigDecimal(Integer.toString(greatestMaximum) );
        horizontalLevels[horizontalLevels.length-1] = horizontalLevels[horizontalLevels.length-1].movePointLeft(digitsAfterDot);

        // draw the horizontal bars
        for(int c = 0; c < horizontalLevels.length; c++) {
            g.drawLine(getWidth()-textAreaLenght,
                       getHeight() - 30 - c* ( 35 + ( Math.round ( (( getHeight() - 50 ) % 35) / horizontalBars  ) ) ),
                       getWidth()-textAreaLenght+5,
                       getHeight() - 30 - c* ( 35 + ( Math.round ( (( getHeight() - 50 ) % 35) / horizontalBars  ) ) ) );
        } // end of for loop

        // draw the horizontalLevel prices
        for(int c = 0 ; c < horizontalLevels.length; c++) {
            // draw the current horizontal level 
            g.drawString(horizontalLevels[c].toString() , getWidth()-textAreaLenght+10,
                         getHeight() + 5 - 30 - c* ( 35 + ( Math.round ( (( getHeight() - 50 ) % 35) / horizontalBars  ) ) ) );
        } // end of if statement

    } // end of method drawTextArea()


    // this method draws the content area
    private void drawContentArea(Graphics g) {
        // declare local variables:
        int contentHeight;
        // end of local variables declaration


        // resolve the contentHeight
        contentHeight = getHeight()-50;

        // check what should be drawn
        for(int c = 0; c < format.getDisplayData().length ; c++) {
            // check the current element
            switch (format.getDisplayData()[c]) {
                case CLOSE_PRICES:
                    drawClosePrices(g , contentHeight);
                break;

                case HIGH_PRICES:
                    drawHighPrices(g, contentHeight);
                break;

                case LOW_PRICES:
                    drawLowPrices(g , contentHeight);
                break;

                case VOLUMES:
                    drawVolumes(g , contentHeight);
                break;
            } // end of switch statement


        } // end of for loop

    } // end of method drawContentArea()


    // this method updates the textAreaLenght member; it should be called before all other
    // internal drawing methods
    private void updateTextAreaLenght() {
        //
    } // end of method

    // this method updates the bars member;
    // it should be called only after the updateTextAreaLenght method
    private void updateBars() {
        
        // get the number of bars to draw
        bars = ( getWidth()-5 - textAreaLenght ) / barLenght;
        
    } // end of method updateBars()




    // this method draws the close prices
    private void drawClosePrices(Graphics g , int contentHeight) {
        // declare local variables:
        int buffer;
        int actualBars;
        int dataMinimum;
        int heightDifference;
        int closePrices[];
        int dataArray[];
        double scale;
        // end of local variables declaration


        // check fro logical error
        if(data == null) {
            // exit from method
            return;
        } // end of if statement
        
        // check for logical error
        if(data.getClosePrices() == null || data.getClosePrices().length < 2) {
            // exit from method
            return;
        } // end of if statement


        // find the data minimum
        dataMinimum = ChartData.getMinimum(data.getClosePrices(bars));

        // resolve the data array
        closePrices = data.getClosePrices(bars);
        dataArray = new int[closePrices.length];
        System.arraycopy(closePrices, 0, dataArray, 0, closePrices.length);

        // resolve the actual bars
        actualBars = dataArray.length;

        // find the heightDifference
        heightDifference = ChartData.getMaximum(data.getClosePrices(bars)) - dataMinimum;

        // check if the heightDifference is too big and reduce it
        buffer = heightDifference / 50;
        if( buffer > 0) {
           heightDifference =  heightDifference / buffer;
        } // end of if statement
        
        //
        for(int c = 0; c < actualBars; c++) {
            //
            dataArray[c] = dataArray[c] - dataMinimum;
        } // end of for loop;

        //
        if(buffer > 0) {
            //
            for(int c=0; c < actualBars; c++) {
                //
                dataArray[c] = dataArray[c] / buffer;
            } // end of for loop
            
        } // end of if statement
         

        // resolve the scale
        scale = contentHeight /  heightDifference;

        // select color
        g.setColor(format.getClosePricesColor());

        // main drawing loop
        for(int c = 0; c < actualBars -1 ; c++) {
            //
            g.drawLine(getWidth()-textAreaLenght-c*barLenght,
                       getHeight()- 30 - (int) Math.round(dataArray[c]*scale),
                       getWidth()-textAreaLenght- ( (c+1)*barLenght ),
                       getHeight()- 30 - (int) Math.round(dataArray[c+1]*scale));
        } // end of for loop

    } // end of method drawClosePrices()


    // this method draws the high prices
    private void drawHighPrices(Graphics g , int contentHeight) {
        // declare local variables:
        int buffer;
        int actualBars;
        int dataMinimum;
        int heightDifference;
        int highPrices[];
        int dataArray[];
        double scale;
        // end of local variables declaration


        // check fro logical error
        if(data == null) {
            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if(data.getHighPrices() == null || data.getHighPrices().length < 2) {
            // exit from method
            return;
        } // end of if statement


        // find the data minimum
        dataMinimum = ChartData.getMinimum(data.getHighPrices(bars));

        // resolve the data array
        highPrices = data.getHighPrices(bars);
        dataArray = new int[highPrices.length];
        System.arraycopy(highPrices, 0, dataArray, 0, highPrices.length);

        // resolve the actual bars
        actualBars = dataArray.length;

        // find the heightDifference
        heightDifference = ChartData.getMaximum(data.getHighPrices(bars)) - dataMinimum;

        // check if the heightDifference is too big and reduce it
        buffer = heightDifference / 50;
        if( buffer > 0) {
           heightDifference =  heightDifference / buffer;
        } // end of if statement

        //
        for(int c = 0; c < actualBars; c++) {
            //
            dataArray[c] = dataArray[c] - dataMinimum;
        } // end of for loop;

        //
        if(buffer > 0) {
            //
            for(int c=0; c < actualBars; c++) {
                //
                dataArray[c] = dataArray[c] / buffer;
            } // end of for loop

        } // end of if statement


        // resolve the scale
        scale = contentHeight /  heightDifference;

        // select color
        g.setColor(format.getHighPricesColor());

        // main drawing loop
        for(int c = 0; c < actualBars -1 ; c++) {
            //
            g.drawLine(getWidth()-textAreaLenght-c*barLenght,
                       getHeight()- 30 - (int) Math.round(dataArray[c]*scale),
                       getWidth()-textAreaLenght- ( (c+1)*barLenght ),
                       getHeight()- 30 - (int) Math.round(dataArray[c+1]*scale));
        } // end of for loop

    } // end of method drawHighPrices()


    // this method draws the low prices
    private void drawLowPrices(Graphics g , int contentHeight) {
        // declare local variables:
        int buffer;
        int actualBars;
        int dataMinimum;
        int heightDifference;
        int lowPrices[];
        int dataArray[];
        double scale;
        // end of local variables declaration


        // check fro logical error
        if(data == null) {
            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if(data.getLowPrices() == null || data.getLowPrices().length < 2) {
            // exit from method
            return;
        } // end of if statement

        // find the data minimum
        dataMinimum = ChartData.getMinimum(data.getLowPrices(bars));

        // resolve the data array
        lowPrices = data.getLowPrices(bars);
        dataArray = new int[lowPrices.length];
        System.arraycopy(lowPrices, 0, dataArray, 0, lowPrices.length);

        // resolve the actual bars
        actualBars = dataArray.length;

        // find the heightDifference
        heightDifference = ChartData.getMaximum(data.getLowPrices(bars)) - dataMinimum;

        // check if the heightDifference is too big and reduce it
        buffer = heightDifference / 50;
        if( buffer > 0) {
           heightDifference =  heightDifference / buffer;
        } // end of if statement

        //
        for(int c = 0; c < actualBars; c++) {
            //
            dataArray[c] = dataArray[c] - dataMinimum;
        } // end of for loop;

        //
        if(buffer > 0) {
            //
            for(int c=0; c < actualBars; c++) {
                //
                dataArray[c] = dataArray[c] / buffer;
            } // end of for loop

        } // end of if statement


        // resolve the scale
        scale = contentHeight /  heightDifference;

        // select color
        g.setColor(format.getLowPricesColor());

        // main drawing loop
        for(int c = 0; c < actualBars -1 ; c++) {
            //
            g.drawLine(getWidth()-textAreaLenght-c*barLenght,
                       getHeight()- 30 - (int) Math.round(dataArray[c]*scale),
                       getWidth()-textAreaLenght- ( (c+1)*barLenght ),
                       getHeight()- 30 - (int) Math.round(dataArray[c+1]*scale));
        } // end of for loop

    } // end of method drawLowPrices()


    // this method draws the volumes
    private void drawVolumes(Graphics g , int contentHeight) {
        //
    } // end of method drawVolumes()



} // end of class Chart
