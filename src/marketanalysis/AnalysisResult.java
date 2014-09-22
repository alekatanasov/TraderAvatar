/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marketanalysis;



// imports:
import error.*;
import java.math.BigDecimal;




/**
 * The result of analyzing the current market conditions.
 *
 * @author Alexandar Atanasov
 */
public class AnalysisResult {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The  best trade action concluded by the performed analysis. */
    private TradeAction tradeAction;

    /** Proposed stoploss price. It will be zero if tradeAction is NO_ACTION. */
    private BigDecimal stopLoss;

    /** Proposed takeprofit price. It will be zero if tradeAction is NO_ACTION. */
    private BigDecimal takeProfit;
    // end of member variables declaration


    // initialize static member variables:
    static {
        eHandler = new ErrorHandler("AnalysisResult");
    } // end of static member variables initialization


    /**
     * Constructor.
     *
     * @param action The proposed trade action. If null is provided to this argument error will be generated.
     * @param stopLossVal   The proposed stoploss value. If null is provided to this argument the
     *                      stopLoss member will be set to zero. If negative number is provided error will be
     *                      generated.
     * @param takeProfitVal The proposed takeProfit value. If null is provided to this argument the
     *                      takeProfit member will be set to zero. If negative number is provided error will be
     *                      generated.
     */
    public AnalysisResult(TradeAction action , BigDecimal stopLossVal , BigDecimal takeProfitVal) {
        // set the members
        setTradeAction(action);
        setStopLoss(stopLossVal);
        setTakeProfit(takeProfitVal);

    } // end of constructor


    /**
     * Default constructor. Sets the tradeAction to NO_ACTION and the takeProfit and stopLoss to zero.
     */
    public AnalysisResult() {
        this(TradeAction.NO_ACTION , null , null);
    } // end of default constructor


    /**
     * Sets the tradeAction member.
     *
     * @param action The proposed trade action. If null is provided to this argument error will be generated.
     */
    private void setTradeAction(TradeAction action) {
        // check for null pointer
        if(action == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setTradeAction");

            // set to default value
            tradeAction = TradeAction.NO_ACTION;

            // exit from method
            return;
        } // end of if statement

        // set the tradeAction
        tradeAction = action;

    }// end of method setTradeAction


    /**
     * Returns the tradeAction member.
     *
     * @return The proposed trade action.
     */
    public TradeAction getTradeAction() {
        return tradeAction;
    } // end of method getTradeAction()


    /**
     * Sets the stopLoss member.
     *
     * @param stopLossVal The proposed stoploss value. If null is provided to this argument the
     *                    stopLoss member will be set to zero. If negative number is provided error will be
     *                    generated.
     */
    private void setStopLoss(BigDecimal stopLossVal) {
        // check for null pointer
        if(stopLossVal == null) {
            // set to zero
            stopLoss = BigDecimal.ZERO;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if(stopLossVal.compareTo(BigDecimal.ZERO) < 0) {
            // error ...
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setStopLoss", "Negative stopLoss");

            // set to default
            stopLoss = BigDecimal.ZERO;

            // exit from method
            return;
        } // end of if statement

        // set the stopLoss
        stopLoss = new BigDecimal(stopLossVal.toString() );

    } // end of method setStopLoss()


    /**
     * Returns the stopLoss member.
     *
     * @return Non null non negative BigDecimal representing the proposed stoploss.
     */
    public BigDecimal getStopLoss() {
        return stopLoss;
    } // end of method getStopLoss()


    /**
     * Sets the takeProfit member.
     *
     * @param takeProfitVal The proposed takeProfit value. If null is provided to this argument the
     *                      takeProfit member will be set to zero. If negative number is provided error will be
     *                      generated.
     */
    private void setTakeProfit(BigDecimal takeProfitVal) {
        // check for null pointer
        if(takeProfitVal == null) {
            // set to zero
            takeProfit = BigDecimal.ZERO;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if(takeProfitVal.compareTo(BigDecimal.ZERO) < 0) {
            // error ...
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setTakeProfit", "Negative takeprofit");

            // set to default
            takeProfit = BigDecimal.ZERO;

            // exit from method
            return;
        } // end of if statement

        // set the stopLoss
        takeProfit = new BigDecimal(takeProfitVal.toString() );

    } // end of method setStopLoss()


    /**
     * Returns the takeProfit member.
     *
     * @return Non null non negative BigDecimal representing the proposed takeProfit.
     */
    public BigDecimal getTakeProfit() {
        return takeProfit;
    } // end of method getTakeProfit()


} // end of class AnalysisResult
