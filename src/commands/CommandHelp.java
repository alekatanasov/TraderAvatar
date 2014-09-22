/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;


// imports:
import terminalinterface.LogsPanel;



/**
 *
 * @author Alexandar Atanasov
 */
public class CommandHelp extends Command{
    // declare member variables:

    /** The string representing this command. */
    public static final String COMMAND_STRING = "help";

    // end of member variables declaration



    /**
     * Constructor
     *
     * @param logsWindow Reference to LogsPanel class instance ( class used for displaying logs ) .
     *                   Providing null to this argument produces error.
     */
    public CommandHelp(LogsPanel logsWindow) {
        super(logsWindow);
    } // end of constructor


    /**
     *
     * @param command The user input string converted to array of words.
     *                Providing null to this argument produces error.
     */
    public  void handleCommand(String command[] ) {
        //
    } // end of method handleCommand

} // end of class CommandHelp
