/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;


// imports:
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * Used by LogButton.
 *
 * @author Alexandar Atanasov
 */
public class LogButtonListener implements ActionListener {
    // declare member variables:

    /** The Id of the LogButton who owns this instance of the LogButtonListener. */
    private int ownerButtonId;

    /** Reference to the LogsPanel who owns this instance of the LogButtonLIstener. */
    private LogsPanel ownerPanel;

    /** Reference to the LogButton who owns this LogButtonListener. */
    private LogButton ownerButton;

    // end of member variables


    /**
     * Constructor.
     *
     * @param id The ID of the owner of this listener.
     */
    public LogButtonListener(int id , LogsPanel panel , LogButton button) {
        ownerButtonId = id;
        ownerPanel = panel;
        ownerButton = button;
    } // end of constructor


    //
    public void actionPerformed(ActionEvent e) {
        //
        if(ownerButton.getSelectedObjects() == null) {
            // the button has been unselected
            ownerButton.setSelected(true);

            // exit from method
            return;
        } // end of if statement

        //
        if(ownerPanel!= null ) {
            ownerPanel.handleButtonPush(ownerButtonId);
        } // end of if statement

    } // end of method

} // end of class LogButtonListener
