/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marketanalysis;



// imports:
import error.*;
import financialdata.*;
import java.math.BigDecimal;
import javax.swing.JOptionPane;



/**
 * AnalysisResult which contains active ( different than NO_ACTION ) TradeAction sujjestion.
 *
 * @author Alexandar Atanasov
 */
public class StandingOrder  extends AnalysisResult {
    // declare member variables:

    /** instance of ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** Shows if this standing order is no longer active ( it is no longer standing ). */
    private boolean isClosed;

    /** The price at which this standing order was opened*/
    private BigDecimal openPrice;
    
    /** Shows the profit or loss generated on closing for this standing offer. */
    private BigDecimal financialResult;

    // end of member variables declaration


    // initialization of static members:
    static {
        eHandler = new ErrorHandler("StandingOrder");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param tradeAction The proposed trade action. If NO_ACTION is provided to this argument error will be
     *                    generated.
     * @param stopLoss The proposed stoploss value. If non positive number is provided to this argument error
     *                 will be generated.
     * @param takeProfit The proposed takeProfit value. If non positive number is provided to this argument error
     *                   will be generated.
     * @param price The price at which this standing order has been opened. If null is provided to this
     *              argument or the provided number is non positive error will be generated.
     */
    public StandingOrder(TradeAction tradeAction, BigDecimal price ,
                         BigDecimal stopLoss , BigDecimal takeProfit) {
        // call to super constructor
        super(tradeAction , stopLoss , takeProfit);

        // check if the provided data indeed represnets a standing order
        if( !isStandingOrder(new AnalysisResult(tradeAction , stopLoss , takeProfit) ) ) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "Constructor", "Invalid standing order");
        } // end of if statement

        // set member
        setIsClosed(false);
        setFinancialResult(BigDecimal.ZERO );
        setOpenPrice(price);

    } // end of constructor


    /**
     * Sets the isClosed member.
     * 
     * @param closed Shows if this standing order is no longer standing ( it has been resolved ).
     */
    private void setIsClosed(boolean closed) {
        isClosed = closed;
    } // end of method setIsClosed()


    /**
     * Returns the isClosed member.
     *
     * @return True if this standing order is no longer active (it has been closed), false otherwise.
     */
    public boolean isClosed() {
        return isClosed;
    } // end of method isClosed()


    /**
     * Sets the openPrice member.
     * 
     * @param price The price at which this standing order has been opened. If null is provided to this
     *              argument or the provided number is non positive error will be generated.
     */
    private void setOpenPrice(BigDecimal price) {
        // check for null pointer
        if(price == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setOpenPrice");

            // set to default value
            openPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if(price.compareTo(BigDecimal.ZERO) < 1) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setOpenPrice", "Non positive open price");

            // set to default value
            openPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // set the openPrice
        openPrice = new BigDecimal(price.toString() );

    } // end of method setOpenPrice()


    /**
     * Returns the openPrice member.
     *
     * @return Positive value representing the price at which this order was opened.
     */
    public BigDecimal getOpenPrice() {
        return openPrice;
    } // end of method getOpenPrice()


    /**
     * Checks if the provided analysis financialResultValue is standing order ( contains tradeAction different
     * than NO_ACTION and valid stopLoss and takeProfit values ).
     * 
     * @param aResult The analysisResult to be checked. If null is provided to this argument error will 
     *                be generated.
     * 
     * @return True if the provided analysis financialResultValue represents standing order, false otherwise. If null is
     *         provided to the aResult argument the method will return false.
     */
    public static boolean isStandingOrder(AnalysisResult aResult) {
        // declare local variables:
        boolean result = false; // the financialResultValue to be returned by the method
        // end of local variables declaration


        // check if the provided analysis financialResultValue represents standing order
        if(aResult.getTradeAction() != TradeAction.NO_ACTION &&
           aResult.getStopLoss().compareTo(BigDecimal.ZERO) > 0 &&
           aResult.getTakeProfit().compareTo(BigDecimal.ZERO) > 0) {

            // the provided analysis financialResultValue is standing order
            result = true;

        } // end of if statement

        // return the financialResultValue
        return result;
    } // end of method isStandingOrder()


    /**
     * Sets the financialResult member.
     * 
     * @param financialResultValue Signed value representing the financialResult. Providing null to this argument will
     *               generated error.
     */
    private void setFinancialResult(BigDecimal result) {
        // check for null pointer
        if(result == null) {
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setFinancialResult");
            
            // set to default
            financialResult = BigDecimal.ZERO;
            
            // exit from method
            return;
        } // end of if statement
        
        // set the financial financialResultValue
        financialResult = new BigDecimal (result.toString() );
        
    } //end of method setFinancialResult()
    
    
    /**
     * Returns the financialResult member.
     * 
     * @return Non null BigDecimal representing the difference (in pips) between the opening price and 
     *         the closing price. Positive return value means profit , negative means loss.
     *         If the method returns 0 , it means that the order has not been closed or there was no profit
     *         nor loss (the order was closed at price equal to the price at which was opened).
     */
    public BigDecimal getFinancialResult() {
        return this.financialResult;
    } // end of method getFinancialResult()
    
    
    /**
     * Checks if the prices contained in the rawData argument have reached level needed to close
     * this standing order. If the levels have indeed been reached this standing order will be closed -
     * the financial financialResultValue member will be resolved and the isClosed member will be set to true.
     * This method should be called when the rawData is updated.
     *
     * @param rawData Instance of the MarketRawData class holding up to date information about the current
     *                market conditions.
     */
    public void checkIfShouldBeClosed(MarketRawData rawData) {
        // declare local variables:
        BigDecimal financialResultValue;
        // end of local variables declaration

        
        // determine the type of the standing order
        if(getTradeAction() == TradeAction.OPEN_LONG_POS) {
            // LONG POSITION
            // check if the current sell price is lesser or equal to the stopLoss
            if(rawData.getNonArrayData().getSellPrice().compareTo(getStopLoss() ) <=0 ) {
                // loss
                financialResultValue = getStopLoss().subtract( getOpenPrice() );
                setFinancialResult(financialResultValue);
                setIsClosed(true);
            } // end of if statement

            // check if the current sell price is greater or equal to the takeProfit
            if(rawData.getNonArrayData().getSellPrice().compareTo(getTakeProfit() ) >=0 ) {
                // profit
                financialResultValue = getTakeProfit().subtract(getOpenPrice() );
                setFinancialResult(financialResultValue);
                setIsClosed(true);
            } // end of if statement

        } else {
            // SHORT POSITION
            // check if the current buy price is greater or equal to the stopLoss
            if(rawData.getNonArrayData().getBuyPrice().compareTo(getStopLoss() ) >=0 ) {
                // loss
                financialResultValue = getOpenPrice().subtract(getStopLoss() );
                setFinancialResult(financialResultValue);
                setIsClosed(true);
            } // end of if statement

            // check if the current buy price is lesser or equal to the takeProfit
            if(rawData.getNonArrayData().getBuyPrice().compareTo(getTakeProfit() ) <=0 ) {
                // profit
                financialResultValue = getOpenPrice().subtract(getTakeProfit() );
                setFinancialResult(financialResultValue);
                setIsClosed(true);
            } // end of if statement

        } // end of if else statement
        
    } // end of method checkIfShouldBeClosed()

} // end of class StandingOrder
