/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package error;

/**
 * Represents an error event. Contains the name of the method and the name of the class in which the error event
 * occured, additional information about the event and the type of the error.
 *
 * @author Alexandar Atanasov
 */
public class Error {
    // declare member variables:

    /** The name of the class in which the error occured. */
    private String occurenceInClass;

    /** The name of the method in which the error occured. */
    private String occurenceInMethod;

    /** Additional information about the error. */
    private String additionalInfo;

    /** The type of the error. */
    private ErrorType type;

    // end of member variables declaration


    /**
     * Constructor.
     *
     * @param className The name of the class in which the error occured. If null or empty string is provided
     *                  to this argument, the class name will be set to "Unknown Class".
     * @param eType The type of the error.
     */
    public Error(String className , ErrorType eType) {
        this(className , eType , null , null);
    } // end of constructor


    /**
     * Constructor.
     *
     * @param className The name of the class in which the error occured. If null or empty string is provided
     *                  to this argument, the class name will be set to "Unknown Class".
     * @param eType The type of the error.
     * @param inMethod The name of the method in which the error occured. If null or empty string is provided
     *                 to this argument, the method name will be set to "Unknown Method".
     */
    public Error(String className , ErrorType eType , String inMethod) {
        this(className , eType , inMethod , null);
    } // end of constructor


    /**
     *
     * @param className The name of the class in which the error occured. If null or empty string is provided
     *                  to this argument, the class name will be set to "Unknown Class".
     * @param eType eType The type of the error.
     * @param inMethod The name of the method in which the error occured. If null or empty string is provided
     *                 to this argument, the method name will be set to "Unknown Method".
     * @param addInfo Additional information about the error. If null or empty string is provided to this
     *                argument, the additional information will be set to "No info".
     */
    public Error(String className , ErrorType eType , String inMethod , String addInfo) {
        setOccurenceInClass(className);
        setType(eType);
        setOccurenceInMethod(inMethod);
        setAdditionalInfo(addInfo);
    } // end of constructor


    /**
     * Copy constructor.
     *
     * @param e Instance of the Error class.
     */
    public Error(Error e) {
        this(e.getOccurenceInClass() , e.getType() );
    } // end of copy constructor


    /**
     * Sets the occurenceInClass member.
     *
     * @param className The name of the class in which this error occured. If null or empty string is provided
     *                  to this argument, the class name will be set to "Unknown Class".
     */
    private void setOccurenceInClass(String className) {
        // check if the class name exists
        if(className != null && !className.contentEquals("") ) {
            occurenceInClass = className;
        } else {
            // set to default
            occurenceInClass = "Unknown Class";
        } // end of if else statement

    }// end of method setOccurenceInClass()


    /**
     * Returns the occurenceInClass member.
     *
     * @return Non empty string containing the name of the class in which this error occured.
     */
    public String getOccurenceInClass() {
        return occurenceInClass;
    } // end of method getOccurenceInClass()


    /**
     * Sets the type member.
     *
     * @param eType The type of the error.
     */
    private void setType(ErrorType eType) {
        type = eType;
    } // end of method setType


    /**
     * Returns the type member.
     *
     * @return The type of this error.
     */
    public ErrorType getType() {
        return type;
    } // end of method getType()


    /**
     * Sets the occurenceInMethod member.
     *
     * @param inMethod The name of the method in which this error occured. If null or empty string is provided
     *                 to this argument, the method name will be set to "Unknown Method".
     */
    public void setOccurenceInMethod(String inMethod) {
        // check if the name of the method exists
        if(inMethod != null && !inMethod.contentEquals("") ) {
            occurenceInMethod = inMethod;
        } else {
            // set to default
            occurenceInMethod = "Unknown Method";
        } // end of if else statement

    } //  end of method setOccurenceInMethod()


    /**
     * Returns the occurenceInMethod member.
     *
     * @return Non empty string containing the name of the method in which this error occured.
     */
    public String getOccurenceInMethod() {
        return occurenceInMethod;
    } // end of method getOccurenceInMethod()


    /**
     * Sets the additionalInfo member.
     * 
     * @param info The additional information about this error. If null or empty string is provided to this
     *             argument, the additional information will be set to "No info".
     */
    private void setAdditionalInfo(String info) {
        // check if the info exists
        if(info != null && !info.contentEquals("") ) {
            additionalInfo = info;
        } else {
            // set to default
            additionalInfo = "No info";
        } // end of if else statement

    } // end of method setAdditionalInfo()


    /**
     * Returns the additionalInfo member.
     *
     * @return Non empty string containing the additional information about this error.
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    } // end of method getAdditionalInfo()


    /**
     * Returns string which represents this error.
     *
     * @return Non empty string containing all available information about this error.
     */
    @Override
    public String toString() {
        // declare local variables:
        String result = "";
        // end of local variables declaration


        // resolve the result
        result = "" + getType().toString() + " occured in Method \"" + getOccurenceInMethod()
                 + "\" of Class \"" + getOccurenceInClass() + "\"" + " with additional information: \""
                 + getAdditionalInfo() + "\"";

        // return the result
        return result;

    } // end of method toString()
    
} // end of class Error
