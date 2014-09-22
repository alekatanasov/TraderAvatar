/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marketanalysis;


// imports:

import error.ErrorHandler;
import error.ErrorType;
import financialdata.MarketRawData;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Used for generating stoploss nad takeprofit values.
 *
 * @author Alexandar Atanasov
 */
public class StopLevelGenerator {
    // declare member variables:

    /** Instance of the Error handler class used for managing errors. */
    private static ErrorHandler eHandler;

    /** The number of digits after the dot in the chosen market security. */
    private int digitsAfterDot;

    /** Instance of the MarketRawData providing up to date information about the current market conditions. */
    private MarketRawData rawData;

    /** */
    private BigDecimal riskRewardRatio;

    /** */
    public enum TradePositionType {LONG , SHORT}

    // end of member variables declaration


    // Initialize static members:
    static {
        eHandler = new ErrorHandler("StopLevelGenerator");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param digits The number of digits after the dot in the price of the chose security.
     */
    public StopLevelGenerator(int digits , BigDecimal riskReward) {
        // set members
        setDigitsAfterDot(digits);
        setRiskRewardRatio(riskReward);
    } // end of constructor


    /**
     * Sets the data member.
     *
     * @param data Instance of the MarketRawData providing up to date information about the current
     *             market conditions. If null is provided to this argument error will be generated.
     */
    public void setRawData(MarketRawData data) {
        // check for null pointer
        if(data == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setNonArrayData");

            // exit from method
            return;

        } // end of if statement

        // set the nonArrayData
        rawData = data;

    } // end of method setNonArrayData()


    /**
     * Sets the digitsAfterDot member.
     *
     * @param digits The number of digits after the dot in the chosen market security. If negative integer is
     * provided to this argument error will be generated.
     */
    private void setDigitsAfterDot(int digits) {
        // check for logical error
        if(digits < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setDigitsAfterDot", "Negative number of digits" );

            // set to default
            digitsAfterDot = 0;

            // exit from method
            return;
        } // end of if statement

        // set the digitsAfterDot
        digitsAfterDot = digits;

    } // end of method setDigitsAfterDot()


    /**
     * Sets the riskRewardRatio member.
     * 
     * @param value The riskRewardRation value. If null or lesser than 1 is provided to this argument
     *              error will be generated.
     */
    private void setRiskRewardRatio(BigDecimal value) {
        // check for null pointer
        if(value == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setRiskRewardRatio");

            // set to default vakue
            riskRewardRatio = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if(value.compareTo(BigDecimal.ONE) < 0 ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setRiskRewardRatio", "Too small value");

            // set to default vakue
            riskRewardRatio = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // set the riskRewardRatio
        riskRewardRatio = value;

    } // end of method


    /**
     * Converts the stopLevel from integer to BigDecimal with  accurate scale and than returns
     * this converted value.
     *
     * @return Non null non negative BigDecimal representing up to date accurate stopLevel value ready to be
     *         used by oracle indicators.
     */
    public BigDecimal getFormatedStopLevel() {
        // declare local variables:
        BigDecimal result;                          // the result to be returned by this method
        // end of local variables declaration


        // resolve the result variable
        result = new BigDecimal( Integer.toString(rawData.getNonArrayData().getStopLevel() ) );
        result = result.add(new BigDecimal("2") );
        result = result.scaleByPowerOfTen(0 - digitsAfterDot);

        // return the result
        return result;

    } // end of method getFormatedStopLevel()


    /**
     * Calculates minimal stopLoss value for new position.
     *
     * @param type The type of the position for which the stopLoss value will be calculated.
     * @param data Updated market data. Providing null to this argument will generated error.
     *
     * @return Non null non negative BigDecimal representing the minimum possible stopLoss value.
     */
    public BigDecimal calculateMinStopLoss(TradePositionType type , MarketRawData data) {
        // declare local variables:
        BigDecimal result;  // the result to be returned by this method
        // end of local variables declaration


        // update the market data
        setRawData(data);

        // calculate the result
        if(type == TradePositionType.LONG) {
            // stopLoss for long position:
            result = rawData.getBuyPrice();
            result = result.subtract(getFormatedStopLevel() );
        } else {
            // stopLoss for short position
            result = rawData.getSellPrice();
            result = result.add(getFormatedStopLevel() );
        } // end of if else statement

        // return the result
        return result;

    } // end of method calculateMinStopLoss()


    /**
     * Calculates minimal takeProfit value for new position.
     *
     * @param type The type of the position for which the takeProfit value will be calculated.
     * @param data Updated market data. Providing null to this argument will generated error.
     *
     * @return Non null non negative BigDecimal representing the minimum possible takeProfit value.
     */
    public BigDecimal calculateMinTakeProfit(TradePositionType type , MarketRawData data) {
        // declare local variables:
        BigDecimal result;  // the result to be returned by this method
        // end of local variables declaration


        // update the market data
        setRawData(data);

        // calculate the result
        if(type == TradePositionType.LONG) {
            // takeProfit for long position:
            result = rawData.getBuyPrice();
            result = result.add(getFormatedStopLevel().multiply(riskRewardRatio ) );
            result = result.round(new MathContext( digitsAfterDot, RoundingMode.HALF_UP) );
        } else {
            // takeProfit for short position
            result = rawData.getSellPrice();
            result = result.subtract(getFormatedStopLevel().multiply(riskRewardRatio ) );
            result = result.round(new MathContext( digitsAfterDot, RoundingMode.HALF_DOWN) );
        } // end of if else statement

        // return the result
        return result;

    } // end of method calculateMinTakeProfit()


    /**
     * Generates the minimum values for stoplevels.
     *
     * @param type The type of the position for which the takeProfit value will be calculated.
     * @param data Updated market data. Providing null to this argument will generated error.
     *
     * @return Instance of the StopLevels class representing the minimum possible values for
     *         stopLoss and takeProfit. If some error occures stopLoss or takeProfit may be
     *         null.
     */
    public StopLevels generateMinStopLevels(TradePositionType type , MarketRawData data) {
        // declare local variables:
        StopLevels result = null;
        BigDecimal stopLoss;
        BigDecimal takeProfit;
        // end of local variables declaration


        // get the stopLoss and takeProfit
        stopLoss = this.calculateMinStopLoss(type, data);
        takeProfit = this.calculateMinTakeProfit(type, data);

        // generate the result
        result = new StopLevels(stopLoss , takeProfit);

        // return the result
        return result;

    } // end of method generateMinStopLevels()


} // end of class StopLevelGenerator
