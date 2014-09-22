/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationinterface;



// imports:
import error.ErrorHandler;
import error.ErrorType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;



/**
 *
 * @author Alexandar Atanasov
 */
public class TestButtonListener implements ActionListener {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** Button associated with the latest test. */
    private JToggleButton thisTestButton;

    /** Buttons associated with previous tests. . */
    private JToggleButton buttonsArray[];

    /** The frame which owns this listener. */
    private SimulationResultsFrame ownerFrame;

    // end of member variables declaration


    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("TestButtonListener");
    } // end of static members initialization


    /**
     * Constructor.
     * @param owner The frame which owns this listener.If null is provided to this argument error will
     *              be generated.
     */
    public TestButtonListener(SimulationResultsFrame owner) {
        // set members
        setOwnerFrame(owner);
    } // end of constructor


    /**
     * Sets the thisTestButton member.
     *
     * @param buttonThisTest The button associated with the latest test.  If null pointer is provided to this
     *                       argument error will be generated.
     */
    private void setThisTestButton(JToggleButton buttonThisTest) {
        // check for null pointer
        if(buttonThisTest == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setThisTestButton");

            // exit from metho
            return;
        } // end of if statement

        // set the thisTestButton
        thisTestButton = buttonThisTest;
        
    } // end of method initThisTestButton()

    
    /**
     * Sets the ownerFrame member.
     * 
     * @param owner The frame which owns this listener.If null is provided to this argument error will 
     *              be generated.
     */
    private void setOwnerFrame(SimulationResultsFrame owner) {
        // check for null pointer
        if(owner == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setOwnerFrame");

            //
            return;
        } // end of if statement

        // set the ownerFrame
        ownerFrame = owner;

    } // end of method initOwnerFrame


    /**
     * After constructor initialization.
     * @param buttons Buttons associated with previous tests. If null pointer is provided to this argument error
     *                will be generated.
     * @param buttonThisTest The button associated with the latest test.  If null pointer is provided to this
     *                       argument error will be generated.
     */
    public void init(JToggleButton buttons[] , JToggleButton buttonThisTest) {
        setButtonsArray(buttons);
        setThisTestButton(buttonThisTest);

    } // end of method init()


    /**
     * Sets the buttonsArray member.
     *
     * @param buttons Buttons associated with previous tests. If null pointer is provided to this argument error
     *                will be generated.
     */
    private void setButtonsArray(JToggleButton buttons[] ) {
        // check for null pointer
        if(buttons == null) {
            // error ...
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setButtonsArray");

            // exit from method
            return;

        } // end of if statement

        // set the buttonsArray
        buttonsArray = buttons;

    } // end of method setButtonsArray


    //
    public void actionPerformed(ActionEvent e) {
        // delcare local variables:
        int buttonId;
        // end of local variables declaration
        
        
        //
        buttonId = Integer.parseInt( e.getActionCommand() );

        //
        setSelected(buttonId);

        ownerFrame.displayTestResult(buttonId);

    } // end of method actionPerformed()


    /**
     * Sets the specified button to selected state. All other buttons will be set to unselected state.
     *
     * @param id The id of the button to be selected.
     */
    private void setSelected(int id) {
        //
        if(id == 0) {
            thisTestButton.setSelected(true);

            //
            for(int c = 0; c < buttonsArray.length; c++) {
                buttonsArray[c].setSelected(false);
            } // end of for loop

            // exit from method
            return;
        } // end of if statement

        for(int c = 0; c < buttonsArray.length ; c++) {
            //
            if(c+1 == id) {
                buttonsArray[c].setSelected(true);
                thisTestButton.setSelected(false);
            } else {
                buttonsArray[c].setSelected(false);
                thisTestButton.setSelected(false);
            } // end of if else statement
            
        } // end of for loop

    } // end of method setSelected

} // end of class TestButtonListener
