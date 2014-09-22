/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;


// imports:
import terminalinterface.LogsPanel;


/**
 *
 * The "trade" command.
 *
 * @author Alexandar Atanasov
 *
 */
public class CommandTrade extends Command {
    // declare member variables:

    /** The string representing this command. */
    public static final String COMMAND_STRING = "trade";
    
    // end of member variables declaration


    /** Constructor
     *
     * @param logsWindow Reference to LogsPanel class instance ( class used for displaying logs ) .
     *                   Providing null to this argument produces error.
     */
    public CommandTrade(LogsPanel logsWindow) {
        super(logsWindow);
    } // end of constructor


    /**
     *
     * @param command The user input string converted to array of words.
     *                Providing null to this argument produces error.
     */
    public void handleCommand(String command[] ) {
        // check the number of strings in the command array
        if( command.length != 2) {
            // incorect usage of the command; inform the user that the command has not been used properly
            displaySystemMessage("incorect usage of command \"/trade\"");

            // exit from method
            return;
        } // end of if statement

        // check if the first parameter ( the second string in the commands array is considered parameter )
        // is "on"
        if(command[1].contentEquals("on") ) {
            // display message in the logs
            displayLogMessage("/trade on");

            // check if trading is already allowed
            if(engine.getTradeOn() ) {
                // display message to the user informing him that trading is already allowed
                displaySystemMessage("Trading is already allowed.");

                // exit from method
                return;
            } // end of if statement

            // set the trade mode to on ( this will allow the terminal to trade )
            accessEngine().setTradeOn(true);

            // display system message informing the user that trading is now allowed
            displaySystemMessage("Trading is now allowed.");

            // exit from method
            return;

        } // end of if statement

        // check if the first parameter ( the second string in the commands array is considered parameter )
        // is "off"
        if(command[1].contentEquals("off") ) {
            // display message in the logs
            displayLogMessage("/trade off");

            // check if trading is already not allowed
            if( !engine.getTradeOn() ) {
                // inform the user that trading is already forbiden
                displaySystemMessage("Trading is already forbidden.");

                // exit from method
                return;
            } // end of if statement

            // set the trade mode to off ( this will stop the terminal from trading )
            accessEngine().setTradeOn(false);

            // display system message informing the user that trade is now not allowed
            displaySystemMessage("Trading is now not allowed.");

            // exit from method
            return;

        } // end of if statement

        // the parameter is invalid; inform the user that the command was not used properly;
        displaySystemMessage("incorect usage of command \"/trade\"");

    } // end of method handleCommand()

} // end of class CommandTrade
