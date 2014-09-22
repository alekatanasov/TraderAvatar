/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package indicators;



// imports:
import error.ErrorHandler;
import error.ErrorType;
import java.util.Vector;


/**
 * Indicator which uses parameters in the formulas used to create indicator data.
 *
 * @author Alexandar Atanasov
 */
public abstract class ParametricIndicator extends Indicator {
    // declare member variables:

    /** Instance of the ErrorHandler class used for managing error. */
    private static ErrorHandler eHandler;

    // end of member variables declaration
    

    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("ParametricIndicator");
    } // end of static members initialization


    /**
     * Constructor.
     * 
     * @param parameters The parameters of the indicator. Providing null to this argument will generated error.
     */
    ParametricIndicator(Vector <String> parameters ) {
        // set the parameters
        initParameters(parameters);

    } // end of constructor


    /**
     * Sets the parameters.
     *
     * @param parameters The parameters of the indicator. Providing null to this argument will generated error.
     */
    public void initParameters(Vector <String> parameters) {
        // check if the parameters are valid
        if( !areParametersValid(parameters) ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "initParameters", "Invalid indicator parameters");

            // exit from method
            return;
        } // end of if statement

        // set members
        setParameters(parameters);

    } // end of method initParameters()


    /**
     * Tests if the provided parameters are valid.
     *
     * @param parameters The parameters to be tested.
     *
     * @return True if the provided parameters are valid , false otherwise
     */
    public boolean areParametersValid(Vector <String> parameters) {
        // declare local variables:
        boolean result = false;
        // end of local variables declaration


        // check for null pointer
        if(parameters == null) {
            // error ...
            eHandler.newError(ErrorType.NULL_ARGUMENT, "areParametersValid");

            // exit from method
            return result;
        } // end of if statement

        // check the types of parameters
        if( !areParameterTypesValid(parameters) ) {
            // the parameters are not valid
            return result;
        } // end of if statement

        // the parameters are valid
        result = true;

        // return the result
        return result;

    } // end of method areParametersValid()


    /**
     * Checks if the types of the provided parameters are identical to the types required by this indicator.
     *
     * @param parameters Parameters whose types will be checked.
     *
     * @return True if the provided parameter types are identical to the required types, false otherwise.
     */
    public boolean areParameterTypesValid(Vector <String> parameters) {
        // declare local variables:
        boolean result = false;
        ParameterType types[];
        // end of local variables declaration


        // check for null pointer
        if(parameters == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "areParameterTypesValid" );

            // exit from method
            return result;
        } // end of if statement

        // check if the number of provided parameters is correct
        if(parameters.size() != getNumberOfParameters() ) {
            // the parameters are invalid
            return result;
        } // end of if statement

        // get the parameter types
        types = getParameterTypes();

        // check the type of the parameters
        for(int c = 0; c < parameters.size(); c++) {
            // check
            if( !ParameterType.isParameterOfType(parameters.elementAt(c) , types[c])) {
                // failure
                return result;
            }// end of if statement

        } // end of for loop

        // the parameters are valid
        result = true;

        // return the result
        return result;

    } // end of method areParameterTypesValid


    /**
     * Sets the parameters of the indicator.
     * 
     * @param parameters The parameters of the indicator.
     */
    protected abstract void setParameters( Vector <String> parameters);


    /**
     * Returns the number of parameters used by this indicator.
     */
    public abstract int getNumberOfParameters();


    /**
     * Returns the default values for the parameters used by this indicator.
     *
     * @return The default values for the parameters used by the indicator.
     */
    public abstract Vector <String> getDefaultParameters();


    /**
     * Returns the types of the parameters used by the indicator.
     *
     * @return The types of the parameters used by the indicator.
     */
    public abstract ParameterType[] getParameterTypes();


} // end of interface ParametricIndicator
