/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marketanalysis;


// imports:
import error.ErrorHandler;
import error.ErrorType;
import java.math.BigDecimal;



/**
 * Incorporates takeprofit and stoploss values into one class.
 *
 * @author Alexandar Atanasov
 */
public class StopLevels {
    // declare member variables:

    /** Instance of the ErrorHandler class used for managing errors. */
    private static ErrorHandler eHandler;

    /** The stoploss value. */
    private BigDecimal stopLoss;

    /** The takeprofit value. */
    private BigDecimal takeProfit;

    // end of member variables declaration


    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("StopLevels");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param stopLossVal The value to be stored in the stopLoss member. If null is provided to this
     *                    argument error will be generated.
     * @param takeProfitVal The value to be stored in the takeProfit member. If null is provided to this
     *                      argument error will be generated.
     */
    public StopLevels(BigDecimal stopLossVal , BigDecimal takeProfitVal) {
        // set members
        setStopLoss(stopLossVal);
        setTakeProfit(takeProfitVal);
    } // end of constructor


    /**
     * Copy Constructor.
     *
     * @param levels Another instance of the StopLevels class.
     */
    public StopLevels(StopLevels levels) {
        this(levels.getStopLoss() , levels.getTakeProfit() );
    } // end of copy constructor


    /**
     * Sets the stopLoss member.
     *
     * @param stopLossVal The value to be stored in the stopLoss member. If null is provided to this
     *                    argument error will be generated.
     */
    private void setStopLoss(BigDecimal stopLossVal) {
        // check for null pointer
        if(stopLossVal == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setStopLoss");

            // set to default value
            stopLoss = BigDecimal.ZERO;
        } // end of if statement

        // set the stopLoss member
        stopLoss = stopLossVal;

    } // end of method setStopLoss()


    /**
     * Returns the stopLoss member.
     *
     * @return Non null instance of the BigDecimal class representing the the stopLoss value.
     */
    public BigDecimal getStopLoss() {
        return stopLoss;
    } // end of method


    /**
     * Sets the takeProfit member.
     *
     * @param takeProfitVal The value to be stored in the takeProfit member. If null is provided to this
     *                      argument error will be generated.
     */
    private void setTakeProfit(BigDecimal takeProfitVal) {
        // check for null pointer
        if(takeProfitVal == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setStopLoss");

            // set to default value
            takeProfit = BigDecimal.ZERO;
        } // end of if statement

        // set the takeProfit member
        takeProfit = takeProfitVal;

    } // end of method setStopLoss()


    /**
     * Returns the takeProfit member.
     *
     * @return Non null instance of the BigDecimal class representing the the takeProfit value.
     */
    public BigDecimal getTakeProfit() {
        return takeProfit;
    } // end of method

} // end of class StopLevels
