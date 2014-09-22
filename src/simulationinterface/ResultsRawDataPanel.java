/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationinterface;


// imports:
import error.*;
import java.awt.Color;
import javax.swing.*;
import simulation.StrategyTestResult;
import terminalinterface.TextOutputWindow;



/**
 * Dispays various information about the completed strategy test.
 *
 * @author Alexandar Atanasov
 */
public class ResultsRawDataPanel extends JPanel {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The title of this data panel*/
    private JLabel titleLabel;

    /** The test result whoose data will be displayed. */
    private StrategyTestResult testResult;

    /** The window in which the test result raw data will be displayed. */
    private TextOutputWindow dataOutput;

    // end of member variables declaration


    // initialization of static members
    static {
        eHandler = new ErrorHandler("ResultsDataPanel");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param result The test result whoose data will be displayed. If null is provided to this argument
     *               error will be generated.
     */
    public ResultsRawDataPanel(StrategyTestResult result) {
        // set members
        setTestResult(result);
        initTitleLabel();

        // create new group layout
        GroupLayout layout = new GroupLayout( this);
        setLayout(layout);

        // make gaps between the components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // set the layout
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                  .addComponent(titleLabel , 100 , 100 , 100 )
                                  .addComponent(dataOutput) );
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addComponent(titleLabel , 25 , 25 , 25)
                                .addComponent(dataOutput) );

        // set the background
        setBackground( new Color( 100 , 100 ,100 ) );

    } // end of constructor


    /**
     * Sets the testResult member.
     *
     * @param result The test result whoose data will be displayed. If null is provided to this argument
     *               error will be generated.
     */
    public void setTestResult(StrategyTestResult result) {
        // check for null pointer
        if(result == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "seTestResult");

            // exit from method
            return;
        } // end of if statement

        // set the testResult
        testResult = result;

        // set the dataOutput
        setDataOutput();

    } // end of method setTestResult()


    /**
     * Initializes the titleLabel member.
     */
    private void initTitleLabel() {
        // set the titleLabel
        titleLabel = new JLabel("  Test Raw Data:  ");

        // set the background
        titleLabel.setBackground(Color.WHITE);

        // make the title label not transparent
        titleLabel.setOpaque(true);

    } // end of method initTitleLabel()


    /**
     * Initializes the dataOutput member. This method should be used by the setTestResult method.
     */
    private void setDataOutput() {
        // declare local variables:
        String buffer;      // buffer used for temporary storage
        // end of local variables declaration


        // check if the testResults exists
        if(testResult == null) {
            // nothing to do; exit from method
            return;
        } // end of if statement

        // inititialize the outputWindow or reset it
        if(dataOutput == null) {
            dataOutput = new TextOutputWindow();
        } else {
            dataOutput.DiscardContents();
        }// end of if statement

        // load the data:
        buffer = "Number Of Simulated Bars: ";
        buffer += Integer.toString(testResult.getSimulatedBars() );
        dataOutput.appendText(buffer, new Color(10 , 90 , 90) , true);

        buffer = "Number Of Simulated Ticks: ";
        buffer += Integer.toString(testResult.getSimulatedTicks() );
        dataOutput.appendText(buffer, new Color(10 , 90 , 90) , true);

        buffer = "\nTotal Pips Balance: ";
        buffer += Integer.toString(testResult.getPipsBalance() );
        dataOutput.appendText(buffer, new Color(10 , 90 , 90) , true);

        buffer = "Pips Won: ";
        buffer += Integer.toString(testResult.getPipsWon() );
        dataOutput.appendText(buffer, new Color(10 , 90 , 90), true);

        buffer = "Pips Lost: ";
        buffer += Integer.toString(testResult.getPipsLost() );
        dataOutput.appendText(buffer, new Color(10 , 90 , 90), true);

        buffer = "\nPositions Opened: ";
        buffer += Integer.toString(testResult.getOpenedPositions() );
        dataOutput.appendText(buffer, new Color(10 , 90 , 90), true);

        buffer = "Long Positions Opened: ";
        buffer += Integer.toString(testResult.getOpenedLongPositions() );
        dataOutput.appendText(buffer, new Color(10 , 90 , 90), true);

        buffer = "Short Positions Opened: ";
        buffer += Integer.toString(testResult.getOpenedShortPositions() );
        dataOutput.appendText(buffer, new Color(10 , 90 , 90), true);

        buffer = "\nPositions Won: ";
        buffer += Integer.toString(testResult.getPositionsWon() );
        dataOutput.appendText(buffer, new Color(0 , 140 , 70), true);

        buffer = "Long Positions Won: ";
        buffer += Integer.toString(testResult.getLongPositionsWon() );
        dataOutput.appendText(buffer, new Color(0 , 140 , 70), true);

        buffer = "Short Positions Won: ";
        buffer += Integer.toString(testResult.getShortPositionsWon() );
        dataOutput.appendText(buffer, new Color(0 , 140 , 70), true);

        buffer = "\nPositions Lost: ";
        buffer += Integer.toString(testResult.getPositionsLost());
        dataOutput.appendText(buffer, new Color(140 , 80 , 80), true);

        buffer = "Long Positions Lost: ";
        buffer += Integer.toString(testResult.getLongPositionsLost() );
        dataOutput.appendText(buffer, new Color(140 , 80 , 80), true);

        buffer = "Short Positions Lost: ";
        buffer += Integer.toString(testResult.getShortPositionsLost() );
        dataOutput.appendText(buffer, new Color(140 , 80 , 80), true);



    } // end of method setDataOutput()


} // end of class ResultsRawDataPanel
