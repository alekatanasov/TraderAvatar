/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commands;


// imports:
import traderavatar.TraderEngine;
import terminalinterface.*;



/**
 *
 * Used for searching, identifying and executing user commands contained in user input string.
 *
 * @author Alexandar Atanasov
 *
 */
public class CommandsHandler {
    // declare member variables:

    /** Reference to window which displays logs. */
    private LogsPanel logWindow;       

    /** Reference to window which displays command output. */
    private InputOutputPanel inputOutput;

    /** Reference to instance of the TraderEngine class ( the main application controlling class ) .*/
    private TraderEngine ruler;

    /** Array of Command subclasses , each representing a specific user command. */
    private Command commands[];

    // end of member variables declaration


    /**
     * Constructor
     *
     * @param logs Reference to window which displays logs.
     *             Providing null to this argument produces error.
     */
    public CommandsHandler(LogsPanel logs) {
        // load the logs window
        logWindow = logs;

        // initialize the commands array
        commands = new Command[7];

        // make instances of all the commands
        commands[0] = new CommandDisplay(logs);     // display command
        commands[1] = new CommandTrade(logs);       // trade command
        commands[2] = new CommandFreeze(logs);      // freeze command
        commands[3] = new CommandTest(logs);        // test command
        commands[4] = new CommandNew(logs);         // new command
        commands[5] = new CommandHelp(logs);        // help command
        commands[6] = new CommandSet(logs);         // set command
        
    } // end of constructor


    /**
     * After constructor initialization
     * This method should not be called in constructors !
     * This method should be called only once , immediately after initializing CommandsHandler instance 
     * via the constructor. Other methods of the CommandsHandler class will not function properly if this method 
     * has not been called !
     *
     * @param inOutPanel Reference to window which displays command execution results and details.
     *                   Providing null to this argument produces error.
     * @param pRuler     Reference to instance of the TraderEngine class ( the main controlling class of the terminal ).
                   Providing null to this argument produces error.
     */
    public void init(InputOutputPanel inOutPanel, TraderEngine pRuler) {
        // initialize and prepare for use the members ruler and inputOutput
        inputOutput = inOutPanel;
        ruler = pRuler;

        // initialize the Command subclasses in the commands array
        for(int c =0; c < commands.length; c++) {
            // init the current command
            commands[c].init(inOutPanel, pRuler);
        } // end of for loop

    } // end of method init ()


    /**
     * Core method of the CommandsHandler class used to search the provided user input string for
     * commands. If command is found by this method it will be immediately executed (the respective
     * Command subclass 's handleCommand() method will be called with the user input as argument ) .
     *
     * @param input The user input string. Providing null to this argument produces error.
     */
    public void newUserInput(String input) {
        // declare local variables:
        int lastWhitespace = 0;
        int numberOfWords = 0;
        String words[]=null;
        String bufferArray[];
        String buffer="";
        // end of local variables declaration


        // display the user input in the outputWindow
        inputOutput.displayUserInput(input);

        // check if the first character from the string is '/'
        if(input.charAt(0)=='/' ) {
            // remove the '/' character
            input = input.substring(1);
        } // end of if statement;

        // check if the string has lenght of 0
        if(input.length() == 0) {
            // display system message to the user
            inputOutput.displaySystemMessage(" \"/\" is not valid command. Type /display commands to"
                                             + " see all available commands.");

            // exit from method
            return;
        } // end of if statement

        // main converting loop
        for(int c = 0; c < input.length() ; c++) {
            // check if the current character is white space
            if(input.charAt(c) == ' ') {
                buffer = input.substring(lastWhitespace, c);

                // check if the buffer is actually existing string
                if(buffer.length() > 0) {

                    // check if the first character of the buffer is white space
                    if(buffer.charAt(0) == ' ') {
                         // remove the whitespace
                        buffer = buffer.substring(1);
                    } // end of if statement

                    // check if the words array is initialized
                    if(words != null) {
                        // add the buffer to the words array
                        bufferArray = new String[words.length+1];
                        System.arraycopy(words, 0, bufferArray, 0, words.length);
                        bufferArray[bufferArray.length-1] = buffer;
                        words = bufferArray;
                    } else {
                        // initialize the word array and store the buffer as the first element
                        words = new String[1];
                        words[0] =buffer;
                    } // end of if else statement
                    
                } // end of if statement

                // new white space encountered
                lastWhitespace = c;
            } // end of if statement

        } // end of for loop

        // resolve last buffer ( if it exists )
        buffer = input.substring(lastWhitespace);

        // add the last buffer ( if it exists )
        if(buffer.length() >0) {
            // check if the first character of the buffer is white space
            if(buffer.charAt(0) == ' ') {
                // remove the whitespace
                buffer = buffer.substring(1);
            } // end of if statement
            
            // check if the words array is initialized
            if(words != null) {
                // add the buffer to the words array
                bufferArray = new String[words.length+1];
                System.arraycopy(words, 0, bufferArray, 0, words.length);
                bufferArray[bufferArray.length-1] = buffer;
                words = bufferArray;
            } else {
                // initialize the word array and store the buffer as the first element
                words = new String[1];
                words[0] =buffer;
            } // end of if else statement

        } // end of if statement

        // check the first word  to determine if it is the "trade" command
        if(words[0].contentEquals(CommandTrade.COMMAND_STRING) ) {
            // call command module trade
            commands[1].handleCommand(words);

            // exit from method
            return;
        } // end of if statement

        // check the first word to determine if it is the "display" command
        if(words[0].contentEquals(CommandDisplay.COMMAND_STRING) ) {
            // call command module trade
            commands[0].handleCommand(words);

            // exit from method
            return;
        } // end of if statement

        // check the first word to determine if it is the "freeze" command
        if(words[0].contentEquals(CommandFreeze.COMMAND_STRING) ) {
            // call command module trade
            commands[2].handleCommand(words);

            // exit from method
            return;
        } // end of if statement

        // check the first word to determine if it is the "test" command
        if(words[0].contentEquals(CommandTest.COMMAND_STRING) ) {
            // call command module trade
            commands[3].handleCommand(words);

            // exit from method
            return;
        } // end of if statement

        // check the first word to determine if it is the "new" command
        if(words[0].contentEquals(CommandNew.COMMAND_STRING) ) {
            // call command module trade
            commands[4].handleCommand(words);

            // exit from method
            return;
        } // end of if statement

        // check the first word to determine if it is the "help" command
        if(words[0].contentEquals(CommandHelp.COMMAND_STRING) ) {
            // call command module trade
            commands[5].handleCommand(words);

            // exit from method
            return;
        } // end of if statement

        // check the first word to determine if it is the "set" command
        if(words[0].contentEquals(CommandSet.COMMAND_STRING) ) {
            // call command module trade
            commands[6].handleCommand(words);

            // exit from method
            return;
        } // end of if statement

        // no command has been recognized in the input; display message to the user
        inputOutput.displaySystemMessage(" \"" +input+ "\" is not valid command. Type /display commands to"
                                         + " see all available commands.");
       
        // set the focus to the input window
        inputOutput.setFocusToInputWindow();

    } // end of method newUserInput()


} // end of class CommandsHandler
