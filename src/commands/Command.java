/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;



// imports:
import indicators.IndicatorList;
import java.util.Vector;
import marketanalysis.AnalysisOptions;
import supportclasses.StringTools;
import traderavatar.TraderEngine;
import terminalinterface.*;



/**
 * Serves as root class for all command classes (excluding the CommandsHandler). The purpose of
 * this class is to provide unified convention for all Command subclasses.This class is abstract because it acts
 * as prototype and it is not associated with any particular user command. The actual user commands are
 * represented by the subclasses of this class.
 *
 * @author Alexandar Atanasov
 */
public abstract class Command {
    // declare member  variables:

    /**  Reference to instance of the TraderEngine class ( the main controlling class of the terminal ). */
    protected TraderEngine engine;

    /** Reference to window which displays command execution results and details. */
    private InputOutputPanel inputOutput;

    /** Reference to window which displays logs.  */
    private LogsPanel logsPanel;

    // end of member variables declaration


    /**
     * Constructor
     *
     * @param logsWindow Reference to LogsPanel class instance ( class used for displaying logs ).
     *                   Providing null to this argument produces error.
     */
    Command(LogsPanel logsWindow) {
        //
        logsPanel = logsWindow;
    } // end of constructor


    /** 
     * After constructor initialization:
     * This method should not be called in constructors !
     * This method should be called only once , immediately after initializing Command instance via the constructor.
     * Other methods of the Command class will not function properly if this method has not been called !
     *
     * @param inOutPanel Reference to window which displays command execution results and details.
     *                   Providing null to this argument produces error.
     * @param pRuler Reference to instance of the TraderEngine class ( the main controlling class of the terminal ).
               Providing null to this argument produces error.
     */
    public void init(InputOutputPanel inOutPanel, TraderEngine pRuler) {
        inputOutput = inOutPanel;
        engine = pRuler;
    } // end of method init


    /**
     * Calls the waitForSafeModify() method and after that returns the reference to the engine member.
     *
     * @return Returns reference to the "engine" member of this class.
     */
    protected TraderEngine accessEngine() {
        // wait until the engine is safe to to modify
        waitForSafeModify();

        // return the engine
        return engine;

    } // end of method


    /**
     * Displays system message to the user.
     * The provided message string will be send to the inputOut member (which will display it to the user).
     *
     * @param message The message to be displayed.
     */
    protected void displaySystemMessage(String message) {
        // safe display of the message ( it will not be displayed if there is no loaded inputOutput )
        if(inputOutput != null) {
            inputOutput.displaySystemMessage(message);
        } // end of if statement

    } // end of method displaySystemMessage()


    /**
     * Displays message in the logs.
     * The provided message string will be send to the logsPanel member.
     *
     * @param message The message to be stored and displayed in the logs.
     *                Providing null to this argument will produce no results ( no message will be displayed ).
     */
    protected void displayLogMessage(String message) {
        // send the message to the logsPanel if it exists
        if(logsPanel != null) {
            logsPanel.addNewEntry(LogsPanel.LogEntryType.COMMAND, message);
        } // end of if statement
    } // end of method displayLogMessage


    /**
     * Blocks until it is safe to modify the "engine" member.
     * The purpose of this method is thread synchronization and safety.
     * This method should be always called before calling non get methods from the "engine" member !
     *
     */
    protected void waitForSafeModify() {
        // main waiting loop
        while(true) {
                // check for thread safety
                if(engine.isSafeToModify() ) {

                    // exit from loop
                    break;
                } // end of if statement

                // sleep for a little time and try again
                try {
                    Thread.sleep(1);
                } catch (Exception e) {

                } // end of try block

            } // end of while loop

    } // end of method waitForSafeModify()


    /**
     * Converts the provided array of words to one single string.
     * 
     * @param commandArray Array of strings (words)  representing a single user input.
     * 
     * @return Single string represnting the provided commandArray.
     */
    public String convertCommandArrayToString(String commandArray[] ) {
        // declare local variables:
        String result="/"; // the result to be returned by this method
        // end of local variables declaration


        // convert the command array
        for(int c = 0; c < commandArray.length; c++) {
            // appent white space between the command words
            if(c > 0) {
                result+=" ";
            } // end of if statement
            
            result+= commandArray[c];
        } // end of for loop

        // return the result
        return result;
    } // end of method convertCommandArrayToString()


    /**
     * Extracts all  indicators which were used as string arguments in the provided
     * command array of strings ( words ).
     *
     * @param command The user input string converted to array of words.
     *
     * @return Array of IndicatorList objects representing the extracted indicators. If no indicators were
     * specified by the user this method will return null.
     */
    public IndicatorList[] extractIndicators(String command[] ) {
        // declare local variables:
        Vector <IndicatorList> indicators;      // temporary storage variable
        String indicatorCodeNames[];            // the string code names of the indicators in the indicator list
        IndicatorList result[] = null;          // the result to be returned by this method
        IndicatorList buffer;                   //
        String stringBuffer;                    //
        // end of local variables declaration


        // initialize the result vector
        indicators = new Vector <IndicatorList>();

        // get the indicatorCodeNames
        indicatorCodeNames = IndicatorList.getCodeNames();

        // main extraction loo
        for(int c = 3; c < command.length; c++) {
            //
            for(int bCounter = 0; bCounter < indicatorCodeNames.length; bCounter++) {
                //
                stringBuffer = command[c];

                //
                stringBuffer = this.removeParameters(stringBuffer);
                
                //
                if(stringBuffer.contentEquals( indicatorCodeNames[bCounter] ) ) {
                    //
                    buffer = IndicatorList.getEnumByCodeName(indicatorCodeNames[bCounter] );

                    // add the current indicator to the vector
                    indicators.add(buffer);

                } // end of if statement

            } // end of for loop

        } // end of for loop

        // check if the indicators vector is empty
        if(indicators.isEmpty() ) {
            // exit from method
            return result;
        } // end of if statement

        // convert the indicators vector to array
        result = new IndicatorList[indicators.size() ];
        result = indicators.toArray(result);

        // return the result
        return result;

    } // end of method extractIndicators


    /**
     * Extracts the specified parameters for each specified indicator.
     * 
     * @param command The user input string converted to array of words.
     *
     * @return The specified parameters for each specified indicator. If no indicators were specified by
     *         the user this method will return null. If some indicators were not given arguments their
     *         respective parameter vector will be null.
     */
    public Vector <String> [] extractIndicatorParameters(String command[]) {
        // declare local variables:
        String buffer;                      //
        String commandCodeNames[];          //
        Vector <String> parameter;          //
        Vector < Vector <String > > resultVector;       //
        Vector <String> result[] = null;    //
        // end of local variables declaration


        // get the command code names
        commandCodeNames = IndicatorList.getCodeNames();

        // initialize the resultVector
        resultVector = new Vector < Vector <String> >();

        // main extraction loop
        for(int c = 0; c < command.length; c++) {
            // remove the parameters from the indicator name
            buffer = removeParameters( command[c] );

            // check if the indicator name will be recognized
            for(int bCounter= 0; bCounter < commandCodeNames.length; bCounter++) {
                // check for name match
                if(buffer.contentEquals(commandCodeNames[bCounter]) ) {

                    // extract the parameters of the current indicator
                    parameter = this.extractParameters(command[c] );

                    // load the parameters of the current indicator in the vector of parameters
                    resultVector.add(parameter);

                } // end of if statement

            } // end of for loop

        } // end of for loop

        // convert the result vector to array
        result = new Vector[resultVector.size() ];
        result = resultVector.toArray(result);

        // return the result
        return result;

    } // end of method extractIndicatorParameters()


    /**
     * Removes all parameters from the provided string and returns the so modified string.
     *
     * @param word String containing indicator name and indicator arguments.
     *
     * @return The word string without the parameters in it. If the provided word does not contain
     *         parameters it will not be modified.
     */
    private String removeParameters(String word) {
        // decla local variables:
        String result = word;       //
        int removeBegin=-1;         //
        // end of local variables declaration


        // find the removeBegin
        for(int c = 0; c < word.length(); c++) {
            //
            if(word.toCharArray()[c] == '(') {
                //
                removeBegin = c;
            } // end of if statement

        } // end of for loop

        //
        if(removeBegin == -1) {
            return result;
        } // end of if statement

        //
        result = result.substring(0, removeBegin);

        // return the result
        return result;
        
    } // end of method removeParameters()


    /**
     * Extracts all indicator parameters from the provided string.
     *
     * @param word String containing indicator parameters.
     *
     * @return All identified indicator parameters in the order in which they were specified by the user.
     *         If no indicator parameters were specified this method will return null.
     */
    private Vector <String> extractParameters(String word) {
        // declare local variables:
        int bufferBegin = -1;
        int bufferEnd = word.length()-1;
        String parameters;
        String extractedParameters[];
        Vector <String> result = null;
        // end of local variables declaration


        // find the bufferegin
        for(int c = 0; c < word.length(); c++) {
            //
            if(word.toCharArray()[c] == '(') {
                //
                bufferBegin = c;
            } // end of if statement

        } // end of for loop

        // find the bufferEnd
        for(int c = 0; c < word.length(); c++) {
            //
            if(word.toCharArray()[c] == ')') {
                //
                bufferEnd = c;
            } // end of if statement

            //buffer

        } // end of for loop

        //
        if(bufferBegin== -1) {
            return result;
        } // end of if statement

        // construct the parameters string
        parameters = word.substring(bufferBegin+1, bufferEnd);

        // extract the parameters
        extractedParameters = StringTools.ExtractWordsFromString(parameters );

        //
        if(extractedParameters == null) {
            return result;
        } // end of if statement

        // resolve the result
        result = new Vector <String> ();
        for(int c = 0; c < extractedParameters.length; c++) {
            result.add(extractedParameters[c]);
        } // end of for loop

        // return the result
        return result;

    } // end of method extractParameters()


    /**
     * Extracts all specified AnalysisOptions from the provided command string.
     * 
     * @param command User provided command string.
     * 
     * @return All identified analysis options. If the user didnt specify any analysis options this 
     *         method will return null.
     */
    public AnalysisOptions[] extractAnalysisOptions(String command[] ) {
        // declare local variables:
        AnalysisOptions[] result = null;
        Vector <AnalysisOptions> resultVector;
        String optionCodes[];
        // end of local variables declaration


        // get the option string codes
        optionCodes = AnalysisOptions.getShortCodes();

        // initialize the resultVector
        resultVector = new Vector <AnalysisOptions>();

        // main extraction loop
        for (int c = 0; c < command.length; c++) {
            //
            for(int d = 0; d < optionCodes.length; d++) {
                //
                if(command[c].contentEquals(optionCodes[d]) ) {
                    resultVector.add(AnalysisOptions.getEnumByShortCode(optionCodes[d] ) );
                } // end of if statement

            } // end of for loop

        } // end of for loop

        if( !resultVector.isEmpty() ) {
            // convert the vecto to array
            result = new AnalysisOptions[resultVector.size() ];
            result = resultVector.toArray(result);
        } // end of if statement

        // return the result
        return result;

    } // end of method


    /**
     * Core Command's class method overriden by all subclasses.
     * When command string is identified in the user input, the respective Command's subclass
     * handleCommand() method will be called.
     *
     * @param command The user input string converted to array of words.
     *                Providing null to this argument produces error.
     */
    public abstract void handleCommand(String command[] );



} // end of class Command
