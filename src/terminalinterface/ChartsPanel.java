/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;


// imports:
import error.*;
import financialdata.ArrayDataRow;
import java.awt.*;
import javax.swing.*;



/**
 *
 * @author Alexandar Atanasov
 */
public class ChartsPanel extends JPanel {
    // declare member variables:
    private static ErrorHandler eHandler;
    private int digitsAfterDot;                 // the number of digits after the dot for the financial instrument
    private Chart  chart;
    private Button button1;
    private Button button2;
    // end of member variables declaration



    // initialization of static variables
    static {
        eHandler = new ErrorHandler("ChartsPanel");
    } // end of initialization of static variables


    // constructor
    public ChartsPanel(int digitsAfterDot) {
        // set the background colour of the panel
        setBackground(Color.BLACK);

        // initialize the chart member
        chart = new Chart( new ChartFormat() , digitsAfterDot);

        // initialize the buttons
        button1 = new Button("Core Chart");
        button2 = new Button("Button 2");

        // create new layout
        GroupLayout layout = new GroupLayout( this);
        setLayout(layout);

        // set the layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                                  .addComponent(chart)
                                  .addGroup(layout.createSequentialGroup()
                                  .addComponent(button1)
                                  .addComponent(button2) ) );
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                .addComponent(button1 , 15 ,GroupLayout.DEFAULT_SIZE ,20)
                                .addComponent(button2 , 15 ,GroupLayout.DEFAULT_SIZE ,20) )
                                .addComponent(chart) );

    } // end of constructor


    // this method updates the chart data
    public void updateChartData( ArrayDataRow data[]) {
        // declare local variables:
        int close[];
        int high[];
        int low[];
        int vol[];
        String buffer;
        // end of local variables declaration
        
        
        // check for null pointer
        if(data == null) {
            // error .
            eHandler.newError(ErrorType.NULL_ARGUMENT , "UpdateChartData");
        } // end of if statement
        
        // initialize the arrays
        close = new int[data.length];
        high = new int[data.length];
        low = new int[data.length];
        vol = new int[data.length];
        
        // load the arrays
        for(int c =0; c < data.length; c++) {
            buffer = data[c].getClosePrice().toPlainString();
            buffer = buffer.replaceAll("\\D", "");
            close[c] = Integer.parseInt(buffer);

            buffer = data[c].getHighPrice().toPlainString();
            buffer = buffer.replaceAll("\\D", "");
            high[c] = Integer.parseInt(buffer);

            buffer = data[c].getLowPrice().toPlainString();
            buffer = buffer.replaceAll("\\D", "");
            low[c] = Integer.parseInt(buffer);

            buffer = data[c].getVolume().toPlainString();
            buffer = buffer.replaceAll("\\D", "");
            vol[c] = Integer.parseInt(buffer );
        } // end of for loop

        // pass the data
        chart.setData(close, high, low, vol);

        // force redraw of the chart
        chart.repaint();

    } // end of method updateChartData()

} // end of class  ChartsPanel
