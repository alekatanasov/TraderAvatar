/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;



// imports:
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;



/**
 *
 * Used in the LogsPanel.
 *
 * @author Alexandar Atanasov
 */
public class LogButton extends JToggleButton {
    // declare member variables:

    /** The ID of this button. */
    private int Id;

    /** The text displayed by this button. */
    private String buttonText;

    /** */
    private LogButtonListener listener;

    // end of member variables declaration



    /**
     * Constrcutor.
     *
     * @param text The text of this button.
     * @param buttonId The ID of this button.
     */
    public LogButton(String text , int buttonId , LogsPanel panel) {
        // set members
        setButtonText(text);
        setId(buttonId);
        setListener(buttonId , panel);

        // set the text of the button
        setText(buttonText);

        // add action listener
        this.addActionListener(listener);

    } // end of constructor



    /**
     * Sets the buttonText member.
     *
     * @param text The text which will be displayed by this button.
     */
    private void setButtonText(String text) {
        buttonText = text;
    } // end of method setButtonText()


    /**
     * Sets the Id member.
     *
     * @param buttonId The Id of this button.
     */
    private void setId(int buttonId) {
        Id = buttonId;
    } // end of method setId


    /**
     * Initializes the listener member.
     *
     * @param buttonId The ID of this button.
     */
    private void setListener(int buttonId , LogsPanel panel ) {
        // initialize the listener
        listener = new LogButtonListener(Id , panel , this);
    } // end of method setListener()


} // end of class LogButton
