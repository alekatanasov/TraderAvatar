/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationinterface;



// imports:
import error.*;
import java.awt.Color;
import java.awt.Container;
import java.util.Vector;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import simulation.*;


/**
 * The main frame window used for displaying strategy test results.
 *
 * @author Alexandar Atanasov
 */
public class SimulationResultsFrame extends JFrame {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The strategy test resultwhich will be displayed by this SimulationResultsFrame. */
    private StrategyTestResult testResult;

    /** Displays the names of the indicators which were used in market analysis. */
    private IndicatorsListPanel indicatorsList;

    /** Displays various test raw ( primary , unformated ) data. */
    private ResultsRawDataPanel rawDataPanel;

    /** Displayes formated test data. */
    private ResultsPercentageDataPanel percentageDataPanel;

    /** Displayes the analysis options used in the test. */
    private AnalysisOptionsPanel optionsPanel;

    /** Provides capability for selecting and displaying previous test results.*/
    private PreviousTestsPanel previousTestsPanel;

    /** Simulation results from previous tests. */
    private Vector <StrategyTestResult> previousResults;

    // end of member variables declaration


    // initialization of static members:
    static {
        eHandler = new ErrorHandler("SimulationFrame");
    } // end of static members initialization


    /**
     * Constructor
     *
     * @param frameName The name of this SimulationResultsFrame.
     * @param testResult The test testResult which will be displayed by this SimulationResultsFrame. If null
     *                   is provided to this argument error will be generated.
     * @param prevResults The simulation results from previous tests. If null is provided to this argument
     *                    error will be generated.
     */
    public SimulationResultsFrame(String frameName , StrategyTestResult testResult ,
                                  Vector <StrategyTestResult> prevResults) {
        super("Simulation Results Pane - " + frameName);

        // declare local variables
        Container frameContents;
        // end of local variables declaration


        // set the members
        setTestResult(testResult);
        setPreviousResults(prevResults);
        initIndicatorsList();
        initRawDataPanel();
        initPercentageDataPanel();
        initPreviousTestsPanel();
        initOptionsPanel();

        // get the frame contents of the main frame
        frameContents = this.getContentPane();
        frameContents.setBackground(new Color( 50 ,50 , 50 ) );

        // create new group layout
        GroupLayout layout = new GroupLayout( frameContents );
        setLayout(layout);

        // make gaps between the components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // set the layout
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                  .addComponent(previousTestsPanel , 200 , 400 , 1200)
                                  .addGroup(layout.createSequentialGroup()
                                  .addGroup(layout.createParallelGroup()
                                  .addComponent(indicatorsList , 300 ,300 , 900)
                                  .addComponent(optionsPanel , 300 , 300 , 900) )
                                  .addComponent(rawDataPanel , 300,300, 900)
                                  .addComponent(percentageDataPanel, 300,300, 900 ) ) );
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addComponent(previousTestsPanel ,40 , 40 , 40 )
                                .addGroup(layout.createParallelGroup()
                                .addGroup(layout.createSequentialGroup()
                                .addComponent(indicatorsList , 195 ,195 , 1200)
                                .addComponent(optionsPanel , 195 ,195 , 1200) )
                                .addComponent(rawDataPanel , 395, 395, 1200)
                                .addComponent(percentageDataPanel ,395 , 395 , 1200 ) ) );

        // prepare to display the frame
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(180, 150, 1100 , 505);
        setVisible(true);

    } // end of constructor


    /**
     * Sets the prevResults member.
     * 
     * @param prevResults The simulation results from previous tests. If null is provided to this argument
     *                    error will be generated.
     */
    private void setPreviousResults(Vector <StrategyTestResult> prevResults) {
        // check for null pointer
        if(prevResults == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setPreviousResults");

            // exit from method
            return;
        } // end of if statement

        // set the previousResults member
        previousResults = prevResults;

    } // end of method setPreviousResults()


    /**
     * Initializes the percentageDataPanel member.
     */
    private void initPercentageDataPanel() {
        this.percentageDataPanel = new ResultsPercentageDataPanel(testResult);
    } // end of method initPercentageDataPanel()


    /**
     * Initializes the previousTestPanel member
     */
    private void initPreviousTestsPanel() {
        previousTestsPanel = new PreviousTestsPanel(this , previousResults);
    } // end of method initPreviousResults()


    /**
     * Sets the result member.
     *
     * @param result The test result which will be displayed by this SimulationResultsFrame. If null
     *                   is provided to this argument error will be generated.
     */
    private void setTestResult(StrategyTestResult result ) {
        // check for null pointer
        if(result == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setResult");

            // exit from method
            return;
        } // end of if statement

        // set the result
        testResult = new StrategyTestResult (result);

    } // end of method setResult()


    /**
     * Returns the testResult member.
     *
     * @return The StrategyTestResult which is displayed by this SimulationResultsFrame.
     */
    public StrategyTestResult getTestResult() {
        return testResult;
    } // end of method getTestResult()


    /**
     * Initializes the indicatorsList member. In the constructor this method should be used after the
     * setTestResult() method !
     */
    private void initIndicatorsList() {
        // check if the testResult exists
        if(testResult == null) {
            // nothing to do; exit from method
            return;
        } // end of if statement

        // initialize the indicatorsList
        indicatorsList = new IndicatorsListPanel(testResult);

    } // end of method initIndicatorsList()


    /**
     * Initializes the rawDataPanel member. In the constructor this method should be used after the
     * setTestResul() method.
     */
    private void initRawDataPanel() {
        // check if the testResult exists
        if(testResult == null) {
            // nothing to do; exit from method
            return;
        } // end of if statement

        // initialize the rawDataPanel
        rawDataPanel = new ResultsRawDataPanel(testResult);

    } // end of method initRawDataPanel()


    /**
     * Initializes the optionsPanel member. In the constructor this method should be used after the
     * setTestResul() method.
     */
    private void initOptionsPanel() {
        // check if the testResult exists
        if(testResult == null) {
            // nothing to do; exit from method
            return;
        } // end of if statement

        // initialize the optionsPanel
        optionsPanel = new AnalysisOptionsPanel(testResult);

    } // end of method initOptionsPanel()


    /**
     * Displayes the requested test to the user.
     *
     * @param id The id of the requested test.
     */
    public void  displayTestResult(int id) {
        //
        indicatorsList.setTestResult(previousResults.elementAt(previousResults.size()-id-1 ) );
        rawDataPanel.setTestResult(previousResults.elementAt(previousResults.size()-id-1 ) );
        percentageDataPanel.setTestResult(previousResults.elementAt(previousResults.size()-id-1 ) );
        optionsPanel.setTestResult(previousResults.elementAt(previousResults.size()-id-1 ) );
        
    } // end of method displayTestResult
    
} // end of class SimulationResultsFrame
