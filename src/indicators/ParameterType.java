/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package indicators;

/**
 * The type of an indicator's parameter.
 *
 * @author Alexandar Atanasov
 */
public enum ParameterType {
    STRING ,
    INTEGER ,
    DECIMAL;


    /**
     * Checks if the provided string is pure string ( it is not decimal or integer).
     *
     * @param parameter String to check.
     *
     * @return True if the provided string is pure string , false otherwise.
     */
    public static boolean isString(String parameter) {
        // declare local variables:
        boolean result = false;
        // end of local variables declaration


        //
        if( !isInteger(parameter ) && !isDecimal(parameter)  ) {
            // the parameter is not numeric , it is string
            result = true;
        } //end of if statement

        // return the result
        return result;

    } // end of method


    /**
     * Checks if the provided string is integer.
     *
     * @param parameter String to check.
     *
     * @return True if the provided string is integer , false otherwise.
     */
    public static boolean isInteger(String parameter) {
        // declare local variables:
        boolean result = true;
        // end of local variables declaration


        // try to convert the parameter to integer
        try {
            Integer.parseInt(parameter);
        } catch(Exception e ) {
            // failed to convert to integer
            result = false;
        } // end of try block

        // return the result
        return result;

    } // end of method


    /**
     * Checks if the provided string is decimal.
     *
     * @param parameter String to check.
     *
     * @return True if the provided string is decimal , false otherwise.
     */
    public static boolean isDecimal(String parameter) {
        // declare local variables:
        boolean result = true;
        // end of local variables declaration


        // try to convert the parameter to double
        try {
            Double.parseDouble(parameter);
        } catch(Exception e ) {
            // failed to convert to double
            result = false;
        } // end of try block

        // return the result
        return result;

    } // end of method


    /**
     * Checks if the provided string is of the specified type.
     *
     * @param parameter String to check.
     * @param type Type to check for.
     *
     * @return True if the provided string is of the specified type, false otherwise.
     */
    public static boolean isParameterOfType(String parameter , ParameterType type) {
        // declare local variables:
        boolean result=false;
        // end of local variables declaration


        //
        switch(type) {
            case STRING:
                result = isString(parameter);
            break;

            case INTEGER:
                result = isInteger(parameter);
            break;

            case DECIMAL:
                result = isDecimal(parameter);
            break;

        } // end of switch statement

        // return the result
        return result;

    } // end of method isParameterOfType()

} // end of enum ParameterType
