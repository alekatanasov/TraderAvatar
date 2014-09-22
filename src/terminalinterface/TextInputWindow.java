/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;


// imports:
import java.awt.event.KeyEvent;
import javax.swing.*;
import commands.CommandsHandler;
import error.ErrorHandler;
import error.ErrorType;
import java.util.Vector;




/**
 * Window used for text input.
 *
 * @author Alexandar Atanasov
 */
public class TextInputWindow extends JPanel {
    // declare member variables:

    /** Instance of the ErrorHandler class used for managing errors. */
    private static ErrorHandler eHandler;

    /** The maximum number of user inputs which can be stored in the lastInputs member. */
    public static final int MAXIMUM_NUMBER_OF_INPUTS = 50;

    /** Iterator used for accesing previos user input. */
    private int userInputIterator;

    /** The actual text window. */
    private JTextArea    textArea;

    /** The scrollbar of the text window. */
    private JScrollPane  scrollPane;

    /** The last user input. */
    private String  lastInput;

    /** Vector containing the last user inputs. */
    private Vector <String>    lastInputs;

    /** Reference to instance of the CommandsHandler class to which all input text will be sended. */
    private CommandsHandler commandsHandler;

    // end of member variables declaration



    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("TextInputWindow");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param handler Reference to instance of the CommandsHandler class to which all input text will be sended.
     *                Providing null to this argument will generated error.
     */
    public TextInputWindow(CommandsHandler handler) {
        // initialize and set members
        setCommmandsHandler(handler);
        initTextArea();
        initScrollPane();
        userInputIterator = -1;
        lastInput = null;
        lastInputs = new Vector <String> ();

        // create new layout
        GroupLayout layout = new GroupLayout( this);
        setLayout(layout);

        // set the layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                                  .addComponent(scrollPane) );
        layout.setVerticalGroup(layout.createParallelGroup()
                                .addComponent(scrollPane) );

    } // end of constructor



    /**
     * Sets the commandsHandler member.
     * 
     * @param handler Reference to instance of the CommandsHandler class to which all input text will be sended.
     *                Providing null to this argument will generated error.
     */
    private void setCommmandsHandler(CommandsHandler handler) {
        // check for null pointer
        if(handler == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setCommandsHandler");

            // exit from method
            return;
        } // end of if statement

        // set the commandHandler
        commandsHandler = handler;

    } // end of method setCommmandsHandler()


    /**
     * Initializes the textArea member.
     */
    private void initTextArea() {
        // initialize the text area
        textArea = new JTextArea();

        // wrap lines when necessary
        textArea.setLineWrap(true);

        // add key listener to intercept the usage of certain keys
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputWindowKeyPressed(evt);
            }
        });

        // set font
        textArea.setFont(new java.awt.Font("Arial", 0, 14));

    } // end of method initTextArea()


    /**
     * Initializes the scrollPane member.
     */
    private void initScrollPane() {
        // initialize the scroll pane
        scrollPane = new JScrollPane();

        // make adjustments to the scroll pane
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(textArea);

    } // end of method initScrollPane()


    /**
     * Clears the textArea. All text content is discarded. The userInputIterator is set to zero.
     */
    private void clearTextWindow() {
        textArea.setText("");
        userInputIterator = -1;
    } // end of method clearTextWindow


    /**
     * Returns the lastInput member.
     *
     * @return The last user input.If there is no user input since the creation of the window
     * this method will return null string.
     */
    public String getLastInput() {
        return lastInput;
    } // end of method getLastInput


    /**
     * Returns the lastInputs member.
     *
     * @return The lastInputs member. If there is no user input since the creation of the window this method
     *         will return empty vector.
     */
    public Vector <String> getLastInputs() {
        return lastInputs;
    } // end of method  getLastTenInputs


    /* 
     * Sends the provided string to the commandsHandler member.
     */
    protected  void handleInput(String input) {
        // send the input to the CommandsHandler instance if
        if(commandsHandler != null) {
            commandsHandler.newUserInput(input);
        } // end of if statement

    } // end of method handleInput


    /**
     * Extracts the text of the textArea and sends it to the commmandHandler.
     *
     * @param input The user input.
     */
    private void internalHandleInput(String input) {
        // check for empty string
        if(input == null || input.equals("")) {
            // exit from method
            return;
        } // end of if statements

        // set the last input
        lastInput = input;

        // store the input
        storeUserInput(input);

        // pass the input to the handleInput method
        handleInput(input);

        // clear the text area window
        clearTextWindow();
    } // end of method internalHandleInput


    // this function will be called when the user presses a key;
    private void inputWindowKeyPressed(java.awt.event.KeyEvent evt) {
        // check if the pressed key is of some interest
        switch(evt.getKeyCode() ) {

            // check if the pressed key is Enter
            case KeyEvent.VK_ENTER:
                // handle the input
                internalHandleInput(textArea.getText() );

                // indicate that the key pressing action has been entirely handled
                evt.consume();
            break;

            // check if the pressed key is the arrow up key
            case KeyEvent.VK_UP:
                moveInputIteratorUp();
            break;

            // check if the pressed key is the arrow up key
            case KeyEvent.VK_DOWN:
                moveInputIteratorDown();
            break;

            default:
                //
        } // end of switch statement

    } // end of method inputWindowKeyPressed


    /**
     * Sets the focus to the text area of the input window.
     */
    public void setFocus() {
        textArea.requestFocus();
    } // end of method setFocus()


    /**
     *  Stores the provided string in the lastInputs vector.
     *
     * @param input The user input to be stored.
     */
    private void storeUserInput(String input) {
        // check the size of the lastInputs member.
        if(lastInputs.size() == MAXIMUM_NUMBER_OF_INPUTS) {
            // discard the last element of the lastInputs vector
            lastInputs.remove(0);
        } // end of if statement

        // store the user input
        lastInputs.add(input);

    } // end of method addUserInput


    /**
     * Adds one to the value of the userInputIterator. If the value of the userInputIterator is
     * equal to the MAXIMUM_NUMBER_OF_INPUTS constant or to the lenght of the lastInputs member
     * the value of userInputIterator will not be modified.
     */
    private void moveInputIteratorUp() {
        // check if the value of the userInputIterator is equal to the MAXIMUM_NUMBER_OF_INPUTS
        if(userInputIterator == MAXIMUM_NUMBER_OF_INPUTS) {
            // exit from method
            return;
        } // end of if statement

        // add one to the value of the userInputIterator
        if(userInputIterator +1 < lastInputs.size() ) {
            userInputIterator++;
        }

        // set the user input pointed by the userInputIterator
        textArea.setText(lastInputs.elementAt(lastInputs.size() - 1 - userInputIterator) );

    } // end of method moveInputIteratorUp()


    /**
     * Subtracts one from the value of the userInputIterator. If the value of the userInputIterator is
     * equal to -1  it will not be modified.
     */
    private void moveInputIteratorDown() {
        // check if the value of the userInputIterator is equal to the -1
        if(userInputIterator == -1) {
            // exit from method
            return;
        } // end of if statement

        //
        userInputIterator--;

        //
        if(userInputIterator == -1) {
            textArea.setText("");
        } else {
            textArea.setText(lastInputs.elementAt(lastInputs.size()  -1 - userInputIterator) );
        } // end of if else statement

    } // end of method moveInputIteratorDown()

} // end of class TextInputWindow
