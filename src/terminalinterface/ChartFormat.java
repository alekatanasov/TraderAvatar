/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;

import java.awt.Color;

/**
 *
 * @author Alexandar Atanasov
 */
public class ChartFormat {
    // declare member variables:
    public enum DisplayData { CLOSE_PRICES , HIGH_PRICES , LOW_PRICES , VOLUMES}

    private Color closePricesColor;
    private Color highPricesColor;
    private Color lowPricesColor;
    private Color volumesColor;

    private final int periodMultiplier;
    private DisplayData displayData[];          //
    // end of member variables declaration


    // default constructor
    public ChartFormat() {
        // declare local variables:
        DisplayData data[];
        // end of local variables declaration

        // initialize the data
        data = new DisplayData[1];

        // resolve the data , set it to default ( close prices )
        data[0] = DisplayData.CLOSE_PRICES;

        // set the data
        setDisplayData(data);

        // initialize the periodMultip
        periodMultiplier = 1;

        // set the default colors
        closePricesColor = new Color(160 , 160 , 0);
        highPricesColor = new Color(0 ,160 , 0);
        lowPricesColor = new Color(160 , 0 , 0);
        volumesColor = new Color(80 , 160 , 100);

    } // end of default constructor


    // constructor
    public ChartFormat( DisplayData dataToDisplay[] , int period, Color close , Color high ,
                        Color low , Color vol) {
        // check for error
        if(period < 1) {
             // error

            // assign default value
            period = 1;
        } // end of if statement

        // set the periodMultiplier
        periodMultiplier = period;

        // set the display data member
        setDisplayData(dataToDisplay);

        // set the colors
        closePricesColor = close;
        highPricesColor = high;
        lowPricesColor = low;
        volumesColor = vol;

    } // end of constructor


    // copy constructor
    public ChartFormat(ChartFormat cFormat) {
        this(cFormat.getDisplayData() , cFormat.getPeriodMultiplier() , cFormat.getClosePricesColor() ,
             cFormat.getHighPricesColor() , cFormat.getLowPricesColor() , cFormat.getVolumesColor() );
    } // end of copy constructor


    // sets the display data member
    private void setDisplayData(DisplayData dataToDisplay[] ) {
        // check for null pointer
        if(dataToDisplay == null) {
            // exit from method
            return;
        } // end of if statement

        // set the displayData
        displayData = dataToDisplay;

    } // end of method setDisplayData


    // returns the displayData member
    public DisplayData[] getDisplayData() {
        return displayData;
    } // end of method


    // returns the periodMultiplier member
    public int getPeriodMultiplier() {
        return periodMultiplier;
    } // end of method


    // returns the closePricesColour member
    public Color getClosePricesColor() {
        return closePricesColor;
    } // end of method


    // returns the highPricesColor member
    public Color getHighPricesColor() {
        return highPricesColor;
    } // end of method


    // returns the lowPricesColor member
    public Color getLowPricesColor() {
        return lowPricesColor;
    } // end of method


    // returns the volumesColor member
    public Color getVolumesColor() {
        return volumesColor;
    } // end of method

} // end of class ChartFormat
