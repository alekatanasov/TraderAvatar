/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;


// imports:
import indicators.IndicatorList;
import java.util.Vector;
import javax.swing.JOptionPane;
import marketanalysis.AnalysisFormat;
import marketanalysis.AnalysisOptions;
import terminalinterface.LogsPanel;



/**
 *
 * @author Alexandar Atanasov
 */
public class CommandSet extends Command{
    // declare member variables:

    /** The string representing this command. */
    public static final String COMMAND_STRING = "set";

    // end of member variables declaration


    /**
     * Constructor
     *
     * @param logsWindow Reference to LogsPanel class instance ( class used for displaying logs ).
     *                   Providing null to this argument produces error.
     */
    public CommandSet(LogsPanel logsWindow) {
        super(logsWindow);
    } // end of constructor


    /**
     *
     * @param command The user input string converted to array of words.
     */
    public void handleCommand(String command[] ) {
        // check if the command array lenght is greater than 3
        if(command.length > 3) {
            // check if the command is the "set analysis format" command
            if(command[1].contentEquals("analysis") && command[2].contentEquals("format") ) {
                // call method subcommandSetAnalysisFormat()
                subcommandSetAnalysisFormat(command);

                // exit from method
                return;
            } // end of if statement

        } // end of if statement

        // display system message to the user informing him that the command was not used properly
        displaySystemMessage("incorect usage of command \"/set\".");

    } // end of method handleCommand()


    /**
     * Represents the command "set analysis format".
     *
     * @param command The user input string converted to array of words.
     */
    private void subcommandSetAnalysisFormat(String command[] ) {
        // declare local variables:
        IndicatorList indicators[];                  //
        Vector <String > parameters[];               //
        AnalysisOptions options[];
        AnalysisOptions conflictingOptions[];
        AnalysisFormat newAnalysisFormat;            // the new analysis format
        // end of local variables declaration


        // get the indicators 
        indicators = extractIndicators(command);

        // get the indicator parameters
        parameters = extractIndicatorParameters(command);

        // get the analysis options
        options = this.extractAnalysisOptions(command);

        // check for conflicting analysis options
        conflictingOptions = AnalysisOptions.areOptionsConflicting(options);
        if(conflictingOptions != null) {
            // display message to the user informing him that he provided conflicting analysis options
            displaySystemMessage("Cannot set analysis format because conflicting analysis options"
                                 + " have been provided. ");

            // exit from method
            return;

        } // end of if statement

        // check if the indicators and options array are both null
        if(indicators == null && options == null) {
             displaySystemMessage("No indicators and no analysis options have been specified. ");

             // exit from method
             return;
        } // end of if statement

        // initialize the new analysis format
        newAnalysisFormat = new AnalysisFormat(indicators , parameters , options);

        // set the new analysis format
        accessEngine().useAnalysisFormat(newAnalysisFormat);

        // display log message
        displayLogMessage(convertCommandArrayToString(command) );

        // display message to the user informing him that the new analysis format has been set successfuly
        displaySystemMessage("the specified analysis format has been set successfully and is now in use.");

    } // end of method subcommandSetAnalysisFormat()



} // end of class CommandSet

