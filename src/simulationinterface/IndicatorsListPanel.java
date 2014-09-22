/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationinterface;


// imports:
import error.ErrorHandler;
import error.ErrorType;
import indicators.IndicatorList;
import java.awt.Color;
import java.util.Vector;
import javax.swing.*;
import marketanalysis.AnalysisFormat;
import simulation.StrategyTestResult;
import terminalinterface.*;



/**
 * Displays the names of the indicators which were used by the MarketAnalyzer class for evaluating the market
 * conditions.
 *
 * @author Alexandar Atanasov
 */
public class IndicatorsListPanel extends JPanel {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The AnalysisFormat which was tested. */
    private AnalysisFormat testedFormat;

    /** The title of the IndicatorsListPanel. */
    private JLabel titleLabel;

    /** Window which holds the names of the used indicators. */
    private TextOutputWindow outputWindow;

    /** The test result whoose data will be displayed. */
    private StrategyTestResult testResult;

    // end of member variables declaration


    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("IndicatorsListPanel");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param result The test result whoose data will be displayed. Providing null to this argument will
     *               generated error.
     */
    public IndicatorsListPanel(StrategyTestResult result) {
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
                                  .addComponent( titleLabel , 170 ,170 ,170)
                                  .addComponent(outputWindow, 300 , 300 , 900) );
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addComponent(titleLabel, 25 , 25 , 25 )
                                .addComponent(outputWindow) );

        // set the background
        setBackground( new Color( 100 , 100 ,100 ) );

    } // end of constructor


    /**
     * Sets the testResult member.
     * 
     * @param result The test result whoose data will be displayed. Providing null to this argument will
     *               generated error.
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

        // set the testedFormat
        setTestedFormat(result.getAnalysisFormat() );

        // set the data in the output window
        setOutputWindow();

        //
        this.repaint();

    } // end of method setTestResult()


    /**
     * Sets the testedFormat member.
     *
     * @param aFormat The AnalysisFormat which was tested. If null is provided to this argument error
     *                will be generated.
     */
    private void setTestedFormat(AnalysisFormat aFormat) {
        // check for null pointer
        if(aFormat == null) {
            // error ..
            eHandler.newError(ErrorType.SQL_ERROR, "Constructor");

            // exit from method
            return;
        } // end of if statement

        // set the testedFormat
        testedFormat = new AnalysisFormat(aFormat);

    } // end of method setTestedFormat()


    /**
     * Returns the testedFormat member.
     *
     * @return The AnalysisFormat contained by this class.
     */
    public AnalysisFormat getTestedFormat() {
        return testedFormat;
    } // end of method getTestedFormat()


    /**
     * Sets the titleLabel member.
     *
     * @param labelText The text to be displayed by the titleLabel. If null or empty String is provided
     *                  to this argument error will be generated.
     */
    private void initTitleLabel() {
        // set the titleLabel
        titleLabel = new JLabel("  Indicators Used In The Test:  ");

        // set the background
        titleLabel.setBackground(Color.WHITE);

        // make the title label not transparent
        titleLabel.setOpaque(true);

    } // end of method initTitleLabel()


    /**
     * Sets the data in the outputWindow.
     */
    private void setOutputWindow() {
        // declare local variables:
        IndicatorList indicators[];
        Vector <String> parameters[];
        String buffer="";
        // end of local variables declaration


        // check if the testedFormat exists
        if(testedFormat == null) {
            // nothing to do. Exit from method
            return;
        } // end of if statement

        // inititialize the outputWindow or reset it
        if(outputWindow == null) {
            outputWindow = new TextOutputWindow();
        } else {
            outputWindow.DiscardContents();
        }// end of if statement

        // get the  indicators
        indicators = testedFormat.getIndicators();

        // get the indicator parameters
        parameters = testedFormat.getIndicatorParameters();

        // check if there are no indicators used
        if(indicators == null) {
            // display to the user that no indicators are used
             outputWindow.appendText("No indicators were used." , new Color(140, 150, 0), true);

            // exit from method
            return;
        } // end of if statement

        
        // load the  indicator names
        for(int c = 0; c <indicators.length; c++) {

            // get the current indicator name
            buffer = indicators[c].toString();
            buffer+="(";

            // check if there are parameter indicators
            if(parameters[c] != null) {
                //
                for(int d =0; d < parameters[c].size(); d++ ) {
                    // append the current element
                    buffer+=" ";
                    buffer+= parameters[c].elementAt(d);
                } // end of for loop

            } // end of if statement

            // appent the ending
            buffer +=" )";

            // load the current indicator
            outputWindow.appendText(buffer , new Color(0, 150, 0), true);

        } // end of for loop

    } // end of method setOutputWindow()




} // end of class IndicatorListPanel
