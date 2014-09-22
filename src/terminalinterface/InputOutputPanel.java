/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package terminalinterface;


// imports:
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import commands.CommandsHandler;


/**
 *
 * @author Alexandar Atanasov
 */
public class InputOutputPanel extends JPanel{
    // declare member variables:
    TextInputWindow inputWindow;
    TextOutputWindow outputWindow;
    // end of member variables declaration

    // constructor
    public InputOutputPanel(CommandsHandler handler) {
        // set the background
        setBackground( new Color(150,190,220) );

        // initialize the input and output windows
        inputWindow = new TextInputWindow(handler);
        outputWindow = new TextOutputWindow();

        // create new layout
        GroupLayout layout = new GroupLayout( this);
        setLayout(layout);

        // make the layout create gaps automatically
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // set the layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                                  .addComponent(inputWindow)
                                  .addComponent(outputWindow) );
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addComponent(outputWindow)
                                .addComponent(inputWindow, 40 ,40 ,40) );
        
    } // end of constructor


    // this method will set the focus to the input window
    public void setFocusToInputWindow() {
        inputWindow.setFocus();
    } // end of method setFocusToInputWindow()


    // this method will display system message in the output window
    public void displaySystemMessage(String message) {
        // declare local variables
        String systemMessage;
        // end of local variables declaration


        // resolve the beggining of the systemMessage
        systemMessage = "System: ";

        // append the message
        systemMessage += message;

        // display the systemMessage
        outputWindow.appendText(systemMessage, new Color(0, 0, 175), true);
    } // end of method displaySystemMessage()


    // this method will display the provided user input string in the outputWindow
    public void displayUserInput(String input) {

        // display the user input
        outputWindow.appendText(input, new Color(50,50,50), true);

    } // end of method displayUserInput()

} // end of class InputOutputPanel
