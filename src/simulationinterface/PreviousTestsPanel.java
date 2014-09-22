/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationinterface;


// imports:
import error.ErrorHandler;
import error.ErrorType;
import java.awt.Color;
import java.util.Vector;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import simulation.StrategyTestResult;


/**
 *
 * @author Alexandar Atanasov
 */
public class PreviousTestsPanel extends JPanel {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The SimulationResultsFrame which owns this window. */
    private SimulationResultsFrame ownerFrame;

    /** Simulation results from previous tests. */
    private Vector <StrategyTestResult> previousResults;

    /** Displays simple string about the nature of the buttons in this panel. . */
    private JLabel infoLabel;

    /** The button associated with the latest test. */
    private JToggleButton thisTestButton;

    /** The number of elements in the previousButtons member array. */
    private static final int NUMBER_OF_PREVIOUS_BUTTONS = 7;

    /** Contains buttons associated with previous tests. */
    private JToggleButton previousTestButtons[];

    /** */
    private TestButtonListener buttonListener;

    // end of member variables declaration


    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("PreviousTestsPanel");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param parentFrame The SimulationResultsFrame which owns this window. If null is provided to this argument
     *                    error will be generated.
     * @param prevResults Simulation results from previous tests. If null is provided to this argument error
     *                    will be generated.
     */
    public PreviousTestsPanel(SimulationResultsFrame parentFrame ,  Vector <StrategyTestResult> prevResults) {
        // set members
        setOwnerFrame(parentFrame);
        setPreviousResults(prevResults);
        initInfoLabel();
        initButtonListener(parentFrame);
        initThisTestButton();
        initPreviousButtons();
        buttonListener.init(previousTestButtons , thisTestButton);

        // set the background colour
        this.setBackground( new Color(0,60,80) );

        // create new group layout
        GroupLayout layout = new GroupLayout( this);
        setLayout(layout);

        // make gaps between the components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // set the layout
        layout.setHorizontalGroup(layout.createSequentialGroup()
                                  .addComponent(thisTestButton)
                                  .addComponent(infoLabel)
                                  .addComponent(previousTestButtons[0] )
                                  .addComponent(previousTestButtons[1] )
                                  .addComponent(previousTestButtons[2] )
                                  .addComponent(previousTestButtons[3] )
                                  .addComponent(previousTestButtons[4] )
                                  .addComponent(previousTestButtons[5] )
                                  .addComponent(previousTestButtons[6] ) );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(thisTestButton , 20 , 20  , 20 )
                                .addComponent(infoLabel , 20 ,20 , 20 )
                                .addComponent(previousTestButtons[0] , 20 ,20 , 20 )
                                .addComponent(previousTestButtons[1] , 20 ,20 , 20 )
                                .addComponent(previousTestButtons[2] , 20 ,20 , 20 )
                                .addComponent(previousTestButtons[3] , 20 ,20 , 20 )
                                .addComponent(previousTestButtons[4] , 20 ,20 , 20 )
                                .addComponent(previousTestButtons[5] , 20 ,20 , 20 )
                                .addComponent(previousTestButtons[6] , 20 ,20 , 20 ) );

    } // end of constructor


    /**
     * Initializes the buttonListener member. In constructor this method should be used before the 
     * initPreviousButtons method !
     */
    private void initButtonListener(SimulationResultsFrame owner) {
        // init
        buttonListener = new TestButtonListener(owner);

    } // end of method
    /**
     * Initializes the infoLabel member
     */
    private void initInfoLabel() {
        // init the infoLabel
        infoLabel = new JLabel("  Previous Tests: ->  ");

        //
        infoLabel.setBackground(new Color( 230 , 220 , 220 ) );
        infoLabel.setOpaque(true);

        // check if this window should be visible
        if(previousResults != null) {
            if(previousResults.size() < 2) {
                //
                infoLabel.setVisible(false);
            } // end of if statement

        } // end of if statement

    } // end of method initInfoLabel()


    /**
     * Initializes the thisTestButton
     */
    private void initThisTestButton() {
        // init the button
        thisTestButton = new JToggleButton("Last Test");
        thisTestButton.setSelected(true);
        thisTestButton.setActionCommand("0");
        thisTestButton.addActionListener(buttonListener);

        // check if this window should be visible
        if(previousResults != null) {
            if(previousResults.size() < 2) {
                //
                thisTestButton.setVisible(false);
            } // end of if statement

        } // end of if statement

    } // end of method initThisTestButton()


    /**
     * Initializes all button associated with previous tests.
     */
    private void initPreviousButtons() {
        // initialize all previous buttons
        previousTestButtons = new JToggleButton[NUMBER_OF_PREVIOUS_BUTTONS];
        for(int c =0; c < previousTestButtons.length ; c++) {
            // initialize the current button
            previousTestButtons[c] = new JToggleButton();

            // make the previous buttons not visible
            previousTestButtons[c].setVisible(false);

            // set action command
            previousTestButtons[c].setActionCommand(Integer.toString(c+1) );

            // add listener
            previousTestButtons[c].addActionListener(buttonListener);
            
        } // end of for loop

        //
        for(int c = 1 ; c < previousResults.size() ; c++) {
            // check if the the looping should stop
            if(c > NUMBER_OF_PREVIOUS_BUTTONS) {
                // exit from loop
                return;
            } // end of if statement

            //
            previousTestButtons[c-1].setText(previousResults.elementAt(previousResults.size()-1-c).getTimeOfCreation()
                                             + "  (-" + Integer.toString(c) + ")");
            previousTestButtons[c-1].setVisible(true);
        } // end of for loop

    } // end of method initPreviousButtons()


    /**
     * Sets the ownerFrame member.
     *
     * @param parentFrame The SimulationResultsFrame which owns this window. If null is provided to this argument
     *                    error will be generated.
     */
    private void setOwnerFrame(SimulationResultsFrame parentFrame) {
        // check for null pointer
        if(parentFrame == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setOwnerFrame");

            // exit from method
            return;
        } // end of if statement

        // set the
        ownerFrame = parentFrame;

    } // end of method setOwnerFrame()


    /**
     * Sets the previousResults member.
     *
     * @param prevResults Simulation results from previous tests. If null is provided to this argument error
     *                    will be generated.
     */
    private void setPreviousResults(Vector <StrategyTestResult> prevResults) {
        // check for null pointer
        if(prevResults == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setPreviousResults");

            // exit from method
            return;
        } // end of if statement

        // set the previousResults
        previousResults = prevResults;

    } // end of method setPreviousResults()


} // end of class PreviousTestsPanel
