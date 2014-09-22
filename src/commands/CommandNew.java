/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;


// imports:
import databases.DatabaseConnector.OrderType;
import terminalinterface.LogsPanel;



/**
 *
 * The "new" command.
 *
 * @author Alexandar Atanasov
 */
public class CommandNew extends Command{
    // declare member variables:

    /** The string representing this command. */
    public static final String COMMAND_STRING = "new";

    // end of member variables declaration


    /**
     * Constructor
     *
     * @param logsWindow Reference to LogsPanel class instance ( class used for displaying logs ).
     *                   Providing null to this argument produces error.
     */
    public CommandNew(LogsPanel logsWindow) {
        super(logsWindow);
    } // end of constructor


    /**
     *
     * @param command The user input string converted to array of words.
     */
    public void handleCommand(String command[] ) {
        // check the lenght of the command array
        if(command.length != 3) {
            // invalid lenght of the command array
            // display system message to the user informing him that the command was not used properly
            displaySystemMessage("incorect usage of command \"/new\".");

            // exit from method
            return;
        } // end of if statement

        // check if the lenght of the command array is 3
        if(command.length == 3) {

            // check if the command is the "new position" command
            if(command[1].contentEquals("position") ) {
                // execute  subcommmand "new position"
                subcommandNewPosition(command);

                // exit from method
                return;
            } // end of if statement

        } // end of if statement

        // incorect usage of the command;
        // inform the user that the command was not used properly;
        displaySystemMessage("incorect usage of command \"/new\".");

    } // end of method handleCommand


    /**
     * Represents the command "new position".
     *
     * @param command The user input string converted to array of words.
     */
    private void subcommandNewPosition(String command[] ) {

        // check if trading is allowed
        if( !engine.getTradeOn()) {
            // trading is not allowed; new position cannot be opened;
            // inform the user that he cannot open new positions if trading is not allowed
            displaySystemMessage("Cannot open new position because trading is not allowed.");

            // exit from method
            return;

        } // end of if statement

        // check if the new position should be long
        if(command[2].contentEquals("long") || command[2].contentEquals("-l") ) {

            // display message in the logs
            displayLogMessage("/new position long");

            // display system message informing the user that opening of new long position was requested
            displaySystemMessage("The opening of new long position has been requested.");

            // attempt to open new long position
            accessEngine().requestNewPosition(OrderType.LONG);

            // exit from method
            return;

        } // end of if statement

        // check if the new position should be short
        if(command[2].contentEquals("short") || command[2].contentEquals("-s")) {

            // display message in the logs
            displayLogMessage("/new position short" );

            // display system message informing the user that opening of new short position was requested
            displaySystemMessage("The opening of new short position has been requested.");

            // attempt to open new short position
            accessEngine().requestNewPosition(OrderType.SHORT);

            // exit from method
            return;

        } // end of if statement

        // incorect usage of the command ( the parameter is invalid );
        // inform the user that the command was not used properly
        displaySystemMessage("incorect usage of command \"/new\".");

    } // end of method subcommandNewPosition()


} // end of class CommandNew
