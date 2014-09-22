/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;



// imports
import indicators.IndicatorList;
import java.util.Vector;
import marketanalysis.AnalysisFormat;
import marketanalysis.AnalysisOptions;
import simulation.StrategyTester.TestInitInfo;
import terminalinterface.LogsPanel;



/**
 * The "test" command.
 *
 * @author Alexandar Atanasov
 *
 */
public class CommandTest extends Command {
    // declare member variables:

    /** The string representing this command. */
    public static final String COMMAND_STRING = "test";

    // end of member variables declaration


    /** Constructor
     *
     * @param logsWindow Reference to LogsPanel class instance ( class used for displaying logs ) .
     *                   Providing null to this argument produces error.
     */
    public CommandTest(LogsPanel logsWindow) {
        super(logsWindow);
    } // end of constructor


    /**
     *
     * @param command The user input string converted to array of words.
     *                Providing null to this argument produces error.
     */
    public void handleCommand(String command[] ) {
        // declare local variables:
        TestInitInfo result;
        // end of local variables declaration


        // check the lenght of the command array
        if(command.length < 2) {
            // invalid lenght of the command array
            // display system message to the user informing him that the command was not used properly
            displaySystemMessage("incorect usage of command \"/test\".");

            // exit from method
            return;
        } // end of if statement

        // check if the lenght of the command array is 2
        if(command.length >= 3) {
            // check if the command is the "test analys format " command
            if(command[1].contentEquals("analysis") && command[2].contentEquals("format") ) {
                // invoke subcommand "test analysis format"
                subcommandTestAnalysisFormat(command);
                
                // exit from method
                return;
            } // end of if statement

        } // end of if statement

        // incorect usage of the command ( the parameter is invalid );
        // inform the user that the command was not used properly
        displaySystemMessage("incorect usage of command \"/test\".");

    } // end of method handleCommand


    /**
     * Represents the command "test analysis format".
     *
     * @param command The user input string converted to array of words.
     */
    private void subcommandTestAnalysisFormat(String command[]) {
        // declare local variables:
        TestInitInfo result;
        AnalysisFormat testAnalysisFormat;           // the  analysis format to be tested
        IndicatorList indicators[];                  //
        AnalysisOptions options[];
        AnalysisOptions conflictingOptions[];
        Vector < String > indicatorParameters[];     //
        // end of local variables declaration


        // check if the command lenght is 3
        if(command.length == 3) {
            // display message in the logs
            displayLogMessage("/test analysis format");

            // try to initiate test
            result = accessEngine().testAnalysisFormat();

            // check if test was made
            if(result == TestInitInfo.INIT_SUCCESFULL) {
                // test was made;
                // display message to the user informing him that test has been made;
                displaySystemMessage("new test of the current active analysis format  has been made.");
            } else {
                // failed to make test; check the reason for failure
                if(result == TestInitInfo.FAIL_NOT_ENOUGH_DATA) {
                    // test failed because there is not enough data
                    // display message to the user informing him that test whas not been made
                    displaySystemMessage("not enough bars in the database to make a test.");
                } else {
                    // test failed because there is already a running test
                    // display message to the user informing him that test whas not been made
                    displaySystemMessage("a test is already running. Only one test can be runing at any time. ");
                } // end of if else statement

            } // end of if else statement

            // exit from method
            return;

        } // end of if statement

         // check if the command is lenght is more than 3
        if(command.length > 3) {
            // try to extract indicator tags
            indicators = extractIndicators(command);

            // extract the indicatorParameters
            indicatorParameters = extractIndicatorParameters(command);

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

            // initialize the new analysis format
            testAnalysisFormat = new AnalysisFormat(indicators , indicatorParameters , options);

            // set the new analysis format
            result = accessEngine().testAnalysisFormat(testAnalysisFormat);

            // display message in the logs
            displayLogMessage( this.convertCommandArrayToString(command) );

            // check if test was made
            if(result == TestInitInfo.INIT_SUCCESFULL) {
                // test was made;
                // display message to the user informing him that test has been made;
                displaySystemMessage("new test of the specified analysis format has been made.");
            } else {
                // failed to make test; check the reason for failure
                if(result == TestInitInfo.FAIL_NOT_ENOUGH_DATA) {
                    // test failed because there is not enough data
                    // display message to the user informing him that test whas not been made
                    displaySystemMessage("not enough bars in the database to make a test.");
                } else {
                    // test failed because there is already a running test
                    // display message to the user informing him that test whas not been made
                    displaySystemMessage("a test is already running. Only one test can be runing at any time. ");
                } // end of if else statement

            } // end of if else statement

            // exit from method
            return;

        } // end of if statement

        // incorect usage of the command ( the parameter is invalid );
        // inform the user that the command was not used properly
        displaySystemMessage("incorect usage of command \"/test\".");

    } // end of method subcommandTestAnalysisFormat()


} // end of class CommandTest
