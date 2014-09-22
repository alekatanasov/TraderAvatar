/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationinterface;

import error.ErrorHandler;
import error.ErrorType;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import marketanalysis.AnalysisFormat;
import marketanalysis.AnalysisOptions;
import simulation.StrategyTestResult;
import terminalinterface.TextOutputWindow;

/**
 *
 * @author Alexandar Atanasov
 */
public class AnalysisOptionsPanel extends JPanel {
    // declare member variables:

    /** Instance of the ErrorHandler class used for managing errors. */
    private static ErrorHandler eHandler;

    /** The test result whoose data will be displayed. */
    private StrategyTestResult testResult;

    /** The title of the IndicatorsListPanel. */
    private JLabel titleLabel;

    /** Window which holds the names of the used indicators. */
    private TextOutputWindow outputWindow;

    // end of member variables declaration


    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("AnalysisOptionsPanel");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param result The test result whoose data will be displayed. Providing null to this
     *               argument will generate error.
     */
    public AnalysisOptionsPanel( StrategyTestResult result ) {
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
                                  .addComponent( titleLabel , 210 ,210 ,210)
                                  .addComponent(outputWindow, 300 , 300 , 900) );
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addComponent(titleLabel, 25 , 25 , 25 )
                                .addComponent(outputWindow) );

        // set the background
        setBackground( new Color( 100 , 100 ,100 ) );

    } // end of Constructor


    /**
     * Sets the testResult member.
     *
     * @param result The test result whoose data will be displayed. Providing null to this
     *               argument will generate error.
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

        // set the output window
        setOutputWindow();

    } // end of method setTestResult()


    /**
     * Sets the titleLabel member.
     *
     * @param labelText The text to be displayed by the titleLabel. If null or empty String is provided
     *                  to this argument error will be generated.
     */
    private void initTitleLabel() {
        // set the titleLabelA
        titleLabel = new JLabel("  Analysis Options Used In The Test:  ");

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
        AnalysisOptions options[];
        AnalysisFormat testedFormat;
        String buffer="";
        // end of local variables declaration


        // get the tested format
        testedFormat = testResult.getAnalysisFormat();

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

        // get the  analysis options
        options = testedFormat.getAnalysisOptions();

        // check if there are no options used
        if(options == null) {
            // display to the user that no options are used
             outputWindow.appendText("No analysis options were used." , new Color(140, 150, 0), true);

            // exit from method
            return;
        } // end of if statement


        // load the options
        for(int c = 0; c <options.length; c++) {

            // get the current indicator name
            buffer = options[c].getShortCode();

            // load the current option
            outputWindow.appendText(buffer , new Color(0, 150, 0), true);

        } // end of for loop

    } // end of method setOutputWindow()

    
} // end of class AnalysisOptionsPanel
