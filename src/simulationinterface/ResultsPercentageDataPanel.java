/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationinterface;


// imports:
import error.ErrorHandler;
import error.ErrorType;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import simulation.StrategyTestResult;
import terminalinterface.TextOutputWindow;

/**
 *
 * @author Alexandar Atanasov
 */
public class ResultsPercentageDataPanel extends JPanel {
    // declare member variables:

    /** Instance of the ErrorHandler class used for managing errors. */
    private static ErrorHandler eHandler;

    /** The window in which the test result percentage data will be displayed. */
    private TextOutputWindow dataOutput;

    /** The title of this data panel*/
    private JLabel titleLabel;

    /** The test result whoose data will be displayed. */
    private StrategyTestResult testResult;

    // end of member variables declaration



    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("ResultsPercentageDataPanel");
    } // end of initialization of static members


    /**
     * Constructor.
     *
     * @param result The test result whoose data will be displayed. If null is provided to this argument error
     *               will be generated.
     */
    public ResultsPercentageDataPanel (StrategyTestResult result) {
        // set members
        initDataOutput();
        initTitleLabel();
        setTestResult(result);

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
                                .addComponent(titleLabel , 25 , 25 , 25 )
                                .addComponent(dataOutput) );

        // set the background
        setBackground( new Color( 100 , 100 ,100 ) );

    } // end of constructor


    /**
     * Sets the testResult member.
     *
     * @param result The test result whoose data will be displayed. If null is provided to this argument error
     *               will be generated.
     */
    public void setTestResult(StrategyTestResult result) {
        // check for null pointer
        if(result == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setTestResult");

            // exit from method
            return;
        } // end of if statement

        // set the testResult
        testResult = result;

        // set the dataOutputWindow
        setDataOutput();

    } // end of method setTestResult()


    /**
     * Initializes the titleLabel member.
     */
    private void initTitleLabel() {
        // set the titleLabel
        titleLabel = new JLabel("  Test Statistics:  ");

        // set the background
        titleLabel.setBackground(Color.WHITE);

        // make the title label not transparent
        titleLabel.setOpaque(true);

    } // end of method initTitleLabel()

    /**
     * Initializes the dataOutputWindow
     */
    private void initDataOutput() {
        dataOutput = new TextOutputWindow();
    } // end of method initDataOutput()


    /**
     * Sets the dataOutput member. This method should be used by the setTestResult() method and after
     * the initDataOutput method.
     */
    private void setDataOutput() {
        // declare local variables
        BigDecimal decimalBuffer;            // buffer used for temporary storage
        double doubleBuffer;                 // buffer used for temporary storage
        double doubleBuffer2;                // buffer used for temporary storage
        String stringBuffer;                 // buffer used for temporary storage
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
        stringBuffer = "Pure Profit Ratio: ";
        decimalBuffer = this.calculatePureProfitRatio();
        stringBuffer += decimalBuffer.toString();
        dataOutput.appendText(stringBuffer, new Color(10 , 90 , 90), true);

        stringBuffer = "\nPositions Won: ";
        doubleBuffer = (double) testResult.getOpenedPositions() / testResult.getPositionsWon();
        if( !Double.isNaN(doubleBuffer) ) {
            decimalBuffer = new BigDecimal( (100 / doubleBuffer) );
        } else {
            decimalBuffer = BigDecimal.ZERO;
        } // end of if else statement
        decimalBuffer = decimalBuffer.setScale(2, RoundingMode.HALF_EVEN);
        stringBuffer += decimalBuffer.toString();
        stringBuffer += " %";
        dataOutput.appendText(stringBuffer, new Color(10 , 90 , 90), true);
        
        stringBuffer = "Long Positions Won: ";
        doubleBuffer = (double) testResult.getPositionsWon() / testResult.getLongPositionsWon();
        if(!Double.isNaN(doubleBuffer)) {
            decimalBuffer = new BigDecimal( (100 / doubleBuffer) );
        } else {
            decimalBuffer = BigDecimal.ZERO;
        } // end of if else statement
        decimalBuffer = decimalBuffer.setScale(2, RoundingMode.HALF_EVEN);
        stringBuffer += decimalBuffer.toString();
        stringBuffer += " %";
        dataOutput.appendText(stringBuffer, new Color(10 , 90 , 90), true);
        
        stringBuffer = "Short Positions Won: ";
        doubleBuffer = (double) testResult.getPositionsWon() / testResult.getShortPositionsWon();
        if(!Double.isNaN(doubleBuffer)) {
            decimalBuffer = new BigDecimal( (100 / doubleBuffer) );
        } else {
            decimalBuffer = BigDecimal.ZERO;
        } // end of if else statement
        decimalBuffer = decimalBuffer.setScale(2, RoundingMode.HALF_EVEN);
        stringBuffer += decimalBuffer.toString();
        stringBuffer += " %";
        dataOutput.appendText(stringBuffer, new Color(10 , 90 , 90), true);
        
        stringBuffer = "\nPositions Lost: ";
        doubleBuffer = (double) testResult.getOpenedPositions() / testResult.getPositionsLost();
        if(!Double.isNaN(doubleBuffer)) {
            decimalBuffer = new BigDecimal( (100 / doubleBuffer) );
        } else {
            decimalBuffer = BigDecimal.ZERO;
        } // end of if else statement
        decimalBuffer = decimalBuffer.setScale(2, RoundingMode.HALF_EVEN);
        stringBuffer += decimalBuffer.toString();
        stringBuffer += " %";
        dataOutput.appendText(stringBuffer, new Color(10 , 90 , 90), true);
        
        stringBuffer = "Long Positions Lost: ";
        doubleBuffer = (double) testResult.getPositionsLost() / testResult.getLongPositionsLost();
        if(!Double.isNaN(doubleBuffer)) {
            decimalBuffer = new BigDecimal( (100 / doubleBuffer) );
        } else {
            decimalBuffer = BigDecimal.ZERO;
        } // end of if else statement
        decimalBuffer = decimalBuffer.setScale(2, RoundingMode.HALF_EVEN);
        stringBuffer += decimalBuffer.toString();
        stringBuffer += " %";
        dataOutput.appendText(stringBuffer, new Color(10 , 90 , 90), true);
        
        stringBuffer = "Short Positions Lost: ";
        doubleBuffer = (double) testResult.getPositionsLost() / testResult.getShortPositionsLost();
        if(!Double.isNaN(doubleBuffer) ) {
            decimalBuffer = new BigDecimal( (100 / doubleBuffer) );
        } else {
            decimalBuffer = BigDecimal.ZERO;
        } // end of if else statement
        decimalBuffer = decimalBuffer.setScale(2, RoundingMode.HALF_EVEN);
        stringBuffer += decimalBuffer.toString();
        stringBuffer += " %";
        dataOutput.appendText(stringBuffer, new Color(10 , 90 , 90), true);

    } // end of  method setDataOut()


    /**
     * Calculates the pure profit ratio.
     *
     * @return Non null BigDecimal representing the pure profit ratio.
     */
    private BigDecimal calculatePureProfitRatio() {
        // declare local variables:
        BigDecimal result;
        BigDecimal positionsWonRatio;
        BigDecimal positionsLostRatio;
        BigDecimal positionsOpened;
        BigDecimal buffer;
        // end of local variables declaration


        // get the number of opened positions
        positionsOpened = new BigDecimal (testResult.getOpenedPositions() );

        // get positionsWonRation
        buffer = new BigDecimal (testResult.getPositionsWon() );
        if( buffer.compareTo(BigDecimal.ZERO) == 0 ) {
            positionsWonRatio = BigDecimal.ZERO;
        } else {
            positionsWonRatio = positionsOpened.divide(buffer, 3 , RoundingMode.HALF_EVEN);
        } // end of if statement
        if(positionsWonRatio.compareTo(BigDecimal.ZERO) == 0) {
            positionsWonRatio = BigDecimal.ZERO;
        } else {
            positionsWonRatio = new BigDecimal("100").divide(positionsWonRatio , 3 , RoundingMode.HALF_EVEN);
        } // end of if else statement

        // get positionsWonRation
        buffer = new BigDecimal (testResult.getPositionsLost() );
        if(buffer.compareTo(BigDecimal.ZERO) == 0 ) {
            positionsLostRatio = BigDecimal.ZERO;
        } else {
            positionsLostRatio = positionsOpened.divide(buffer, 3 , RoundingMode.HALF_EVEN);
        } // end of if statement
        if(positionsLostRatio.compareTo(BigDecimal.ZERO) == 0 ) {
            positionsLostRatio = BigDecimal.ZERO;
        } else {
            positionsLostRatio = new BigDecimal("100").divide(positionsLostRatio , 3 , RoundingMode.HALF_EVEN);
        } // end of if else
        
        // calculate the result
        result = positionsWonRatio.subtract(positionsLostRatio);
        result = result.multiply(positionsOpened);
        result = result.divide( new BigDecimal("100") , 2 , RoundingMode.HALF_EVEN);

        // return the result
        return result;

    } // end of method calculatePureProfitRatio()

} // end of class ResultsPercentageDataPanel()
