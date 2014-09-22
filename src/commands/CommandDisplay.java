/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;


// imports:
import indicators.IndicatorList;
import java.util.Vector;
import marketanalysis.AnalysisFormat;
import marketanalysis.AnalysisOptions;
import terminalinterface.LogsPanel;


/**
 *
 * The "display" command.
 *
 * @author Alexandar Atanasov
 *
 */
public class CommandDisplay extends Command {
    // declare members variables:

    /** The string representing this command. */
    public static final String COMMAND_STRING = "display";

    // end of member variables declaration


    /**
     * Constructor
     *
     * @param logsWindow Reference to LogsPanel class instance ( class used for displaying logs ).
     *                   Providing null to this argument produces error.
     */
    public CommandDisplay(LogsPanel logsWindow) {
        super(logsWindow);
    } // end of constructor


    /**
     *
     * @param command The user input string converted to array of words.
     */
    public void handleCommand(String command[] ) {
        // check the lenght of the input array
        if(command.length != 2 && command.length != 3) {
            // invalid lenght of the command
            // display system message to the user informing him that the command has not been used properly
            displaySystemMessage("incorect usage of command \"/display\".");

            // exit from method
            return;
        } // end of if statement

        // check if the lenght of command array is 2
        if(command.length == 2) {
            // check for subcommand "display commands"
            if(command[1].contentEquals("commands") ) {
                // execute subcommandDisplayCommands
                subcommandDisplayCommands();

                // exit from method
                return;
            } // end of if statement

        } // end of if statement

        // check if the command array is 3
        if(command.length == 3 ) {
            // check for subcommand "display commands"
            if(command[1].contentEquals("analysis") && command[2].contentEquals("format") ) {
                // execute subcommandDisplayCommands
                subcommandDisplayAnalysisFormat();

                // exit from method
                return;

            } // end of if statement

        } // end of if statement

        // incorect usage of the command;
        // inform the user that the command was not used properly;
        displaySystemMessage("incorect usage of command \"/display\".");

    } // end of method handleCommand


    /**
     * Represents the "display commands" command.
     */
    private void subcommandDisplayCommands() {
        // display message in the logs
        displayLogMessage("/display commands");

        // display all available commands to the user
        displaySystemMessage("There are 7 available commands:\n"
                             + "  - /display [commands] , [analysis format]\n"
                             + "  - /freeze [] , [on] , [off] \n"
                             + "  - /help\n"
                             + "  - /new [position (long) , (short) ] \n"
                             + "  - /set [analysis format  (...) ] \n"
                             + "  - /test [analysis format (...) ] \n"
                             + "  - /trade [on] , [off] \n" );
    } // end of method subcommandDisplayCommands()


    /**
     * Displays the currently used analysis format.
     */
    private void subcommandDisplayAnalysisFormat() {
        // declare local variables:
        AnalysisFormat currentFormat;                       // the current analysis format
        IndicatorList[] indicators;                         // the indicators used in the market analysis
        String format="the current analysis format is: ";   // the string which will be displayed to the user
        Vector <String > parameters[];                      //
        AnalysisOptions options[];
        // end of local variables declaration


        // display message in the logs
        displayLogMessage("/display analysis format");

        // get the current analysis format
        currentFormat = accessEngine().getAnalysisFormat();

        // get the indicators
        indicators = currentFormat.getIndicators();

        // get the indicator parameters
        parameters = currentFormat.getIndicatorParameters();

        // get the analysis options
        options = currentFormat.getAnalysisOptions();

        // load the analysis options
        if(options != null) {
            //
            for(int c =0; c < options.length;c++) {
                //
                format +=options[c].getShortCode();
                format += " ";
            } // end of for loop

        } // end of if statement

        // check if there are indicators
        if(indicators != null) {
            //
            for(int c =0; c < indicators.length; c++ ) {
                //
                format += indicators[c].getCodeName();
                // display indicator parameters if they exists
                format+="(";
                if(parameters[c] != null) {
                    for(int d = 0; d < parameters[c].size(); d++) {
                        //
                        format +=parameters[c].elementAt(d);
                        format +=" ";
                    } // end of for loop
                } // end of if statement
                format +=")";
                format += " ";
            } // end of for loop
        } else {
            //
            if(options == null) {
                //
                format += "No indicators are currently used and no analysis options are specified.";
            } // end of if statement

        } // end of if else statement

        // display the format
        displaySystemMessage(format);
        
    } // end of method subCommandDisplayAnalysisFormat()

} // end of class CommandDisplay
