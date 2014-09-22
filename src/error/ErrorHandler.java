/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package error;

import java.util.Vector;
import terminalinterface.LogsPanel;

/**
 * Manages error events. If logsPanel is provided occurences of errors will be displayed in it.
 * This class may hold limited amount of errors - if the limit is exceeded, the oldest errors will be
 * discarded to release space for the new ones.
 *
 * @author Alexandar Atanasov
 */
public class ErrorHandler {
     // declare member variables:

    /** The lenght of the errors array. */
    private static final int ERRORS_ARRAY_LENGHT;

    /** Shows if any errors were passed to the ErrorHandler (this member is set to true if at
     * least one error has been encountered ). */
    private static boolean inErrorState;

    /** The number of non null errors in the errors array. */
    private static int numberOfErrors;

    /** The name of the class which holds this instance of the ErrorHandler. */
    private String nameOfOwner;

    /** Array of errors. */
    private static Error[] errors;

    /** Array of errors not yet displayed in the log window ( because the log window is not yet created). */
    private static Vector <Error> undisplayedErrors;

    /** Reference to window which displays logs. */
    private static LogsPanel logsPanel;
    
    // end of member variables declaration


    // methods:

    // initialization of static members
    static {
        logsPanel = null;
        inErrorState = false;
        ERRORS_ARRAY_LENGHT = 100;
        numberOfErrors = 0;
        errors = new Error[ERRORS_ARRAY_LENGHT];
        undisplayedErrors = new Vector <Error>();
    } // end of initialization of static members


    /**
     * Constructor.
     *
     * @param owningClassName The name of the class  which holds this instance of the ErrorHandler. If
     *                        null or empty string is provided to this argument the owning class name will
     *                        be set to "Unknown class".
     */
    public ErrorHandler(String owningClassName) {

        // check for null pointer or empty string
        if(owningClassName == null || owningClassName.contentEquals("") ) {
            nameOfOwner = "Unknown class";
        } else {
            // set to default
            nameOfOwner = owningClassName;
        } // end of if statement

    } // end of constructor


    /**
     * Constructor.
     *
     * @param owningClassName The name of the class  which holds this instance of the ErrorHandler. If
     *                        null or empty string is provided to this argument the owning class name will
     *                        be set to "Unknown class".
     * @param logsWindow Reference to window which displays logs.
     */
    public ErrorHandler(String owningClassName , LogsPanel logsWindow) {

        // check for null pointer
        if(owningClassName == null) {
            nameOfOwner = "null name";
        } else {
            nameOfOwner = owningClassName;
        } // end of if statement

        // load the logsPanel
        logsPanel = logsWindow;

        // display all undisplayed errors if the provided logsPanel is not null
        if(logsPanel !=null) {
            // displaying loop
            for(int c = 0; c < undisplayedErrors.size(); c++) {
                // display the current error in the vector
                logsPanel.addNewEntry(LogsPanel.LogEntryType.ERROR ,
                                      undisplayedErrors.elementAt(c).getType().toString() );
            } // end of for loop

        } // end of if statement

    } // end of constructor


    /**
     * Copy constructor.
     *
     * @param h Instance of the ErrorHandler class.
     */
    public ErrorHandler(ErrorHandler h) {
        this(h.getNameOfOwner());
    } // end of copy constructor


    /**
     * Discards the first element of the errors array and moves all other elements one
     * postion forward, thus creating one free position at the end of the array.
     */
    private void discardFirstError() {
        System.arraycopy(errors, 1, errors, 0, ERRORS_ARRAY_LENGHT-1);
    } // end of method discardFirstError()


    /**
     * Sets the errors member.
     *
     * @param e Array of Error objects.
     */
    @Deprecated
    private void setErrors(Error[] e) {
        // check for null pointer
        if(e == null) {
            // exit from method
            return;
        } // end of if statement

        // check for array lenght mismatch
        if(e.length != 100) {
            // exit from method
            return;
        } // end of if statement

        // copy the provided array into the errors member
        System.arraycopy(e, 0, errors, 0, ERRORS_ARRAY_LENGHT);

    } // end of method setErrors()


    /**
     * Returns the inErrorState member.
     *
     * @return The value of the inErrorState member.
     */
    public boolean getInErrorState() {
        return inErrorState;
    } // end of method getInErrorState


    /**
     * Returns the errors member.
     *
     * @return Non null array of Error objects. It is possible that some or all of the elements of the
     *         returned array are null.
     */
    public static Error[] getErrors() {
        return errors;
    } // end of method getErrors()


    /**
     * Returns the nameOfOwner member.
     *
     * @return Non empty string representing the name of the class which holds this instance of the
     *         ErrorHandler.
     */
    public String getNameOfOwner() {
        return nameOfOwner;
    } // end of method getNameOfOwner()


    /**
     * Core method of the ErrorHandler for managing new errors.
     *
     * @param type The type of the error.
     */
    public void newError(ErrorType type) {
         newError(type , null );
    } // end of method newError()


    /**
     * Core method of the ErrorHandler for managing new errors.
     *
     * @param type The type of the error.
     * @param inMethod The name of the method in which the error occured.
     */
    public void newError(ErrorType type, String inMethod ) {
        newError(type , inMethod , null);
    } // end of method newError()


    /**
     * Core method of the ErrorHandler for managing new errors.
     *
     * @param type type The type of the error.
     * @param inMethod The name of the method in which the error occured.
     * @param additionalInfo Additional information about the error.
     */
    public void newError(ErrorType type, String inMethod , String additionalInfo) {
        // declare local variables:
        Error newError;
        // end of local variables declaration


        // set the error state to true
        inErrorState = true;

        // construct the new error
        newError = new Error(getNameOfOwner() , type , inMethod , additionalInfo);

        // display the error in the logs if there is logsPanel loaded
        if(logsPanel != null) {
            logsPanel.addNewEntry(LogsPanel.LogEntryType.ERROR, newError.toString() );
        } else {
            // store the error in the undisplayedErrors array
            undisplayedErrors.add( newError);
        } // end of if else statement

        // check if the errors array is full
        if(numberOfErrors == ERRORS_ARRAY_LENGHT) {
            // free some space in the errors array
            discardFirstError();

            // store the error as the last error in the errors array
            errors[ERRORS_ARRAY_LENGHT-1] = newError;

        } else { // there is some free space in the errors array

            // parse the errors array until null element is encountered
            for(int counter = 0; counter < ERRORS_ARRAY_LENGHT; counter++) {

                // check if the current element is null pointer
                if(errors[counter] == null) {

                    // store the error
                    errors[counter] = newError;

                    // indicate that the number of errors has grown
                    numberOfErrors++;

                    // exit from the for loop
                    break;

                } // end of if statement

            } // end of for loop

        } // end of if statement

    } // end of method newError()

} // end of class ErrorHandler
