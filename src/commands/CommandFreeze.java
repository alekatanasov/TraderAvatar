/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;


// imports:
import terminalinterface.LogsPanel;


/**
 *
 * The "freeze" command.
 *
 * @author Alexandar Atanasov
 *
 */
public class CommandFreeze extends Command {
    // declare member variables:

    /** The string representing this command. */
    public static final String COMMAND_STRING = "freeze";

    // end of member variables declaration

    
    /**
     * Constructor
     *
     * @param logsWindow Reference to LogsPanel class instance ( class used for displaying logs ) .
     *                   Providing null to this argument produces error.
     */
    public CommandFreeze(LogsPanel logsWindow) {
        super(logsWindow);
    } // end of constructor

    /**
     *
     * @param command The user input string converted to array of words.
     *                Providing null to this argument produces error.
     */
    public  void handleCommand(String command[] ) {
        // check the lenght of the input array
        if(command.length != 1 && command.length != 2) {
            // invalid lenght of the command
            // display system message to the user informing him that the command has not been used properly
            displaySystemMessage("incorect usage of command \"/freeze\".");

            // exit from method
            return;
        } // end of if statement

        // check if the lenght of the command array is 1
        if(command.length == 1) {

            // check if the engine member is already frozen
            if(engine.isFrozen() ) {
                // display system message , informing the user that the terminal as already frozen
                displaySystemMessage("Terminal is already frozen.");

                // exit from method
                return;
            } // end of if statement

            // freeze the prograengineer
            accessEngine().setFrozen(true);

            // display message in the logs
            displayLogMessage("/freeze");

            // display system message informing the user that the terminal has been frozen
            displaySystemMessage("Terminal is now frozen.");

            // exit from method
            return;

        } // end of if statement

        // check if the parameter ( the second string in the command array is considered parameter ) is "on"
        if(command[1].contentEquals("on") ) {

            // check if the engine member is already frozen
           if( engine.isFrozen() ) {
                // display system message informing the user that the terminal is already frozen
                displaySystemMessage("Terminal is already frozen.");

                // exit from method
                return;
            } // end of if statement

            // freeze the proengineruler member
           accessEngine().setFrozen(true);

            // display message in the logs
            displayLogMessage("/freeze on");

            // display system message informing the user the the terminal has been frozen
            displaySystemMessage("Terminal is now frozen.");

            // exit from method
            return;
        } // end of if statement

        // check if the parameter ( the second string in the command array is considered parameter ) is "off"
        if(command[1].contentEquals("off") ) {

            // check if engine  member is already frozen
           if( !(engine.isFrozen() ) ) {
                // display system message informing the user tenginehe ruler is already frozen
                displaySystemMessage("Terminal is already unfreezed.");

                // exit from method
                return;
            } // end of if statement

            // freeze theengineram ruler member
          accessEngine().setFrozen(false);

            // display message in the logs
            displayLogMessage("/freeze off");

            // display system message informing the user that the terminal is no longer frozen
            displaySystemMessage("Terminal has been unfreezed.");

            // exit from method
            return;
        } // end of if statement

        // incorect usage of the command ( the parameter is invalid )
        displaySystemMessage("incorect usage of command \"/freeze\" .");

    } // end of method handleCommand()

} // end of class CommandFreeze
