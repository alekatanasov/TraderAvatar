/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package financialdata;


// imports:
import error.*;
import java.math.BigDecimal;



/**
 * Represents the "nonarraydata" table of Trader Avatar database.
 *
 * @author Alexandar Atanasov
 */
public class NonArrayData {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The current market buy price. */
    private BigDecimal buyPrice;

    /** The current market sell price. */
    private BigDecimal sellPrice;

    /** The number of rows in the "arraydata" table of the database. */
    private int bars;

    /** The minimum distance, between current price and stoploss & takeprofit, needed for
        opening new position. */
    private int stopLevel;

    // end of member variables declaration


    // initialization of static members
    static {
        eHandler = new ErrorHandler("NonArrayData");
    } // end of initialization of static members


    /**
     * Constructor.
     *
     * @param buy The current market buy price. Providing null or non positive number to this argument will
     *            generate error.
     * @param sell The current market sell price. Providing null or non positive number to this argument will
     *            generate error.
     * @param intBars The number of rows in the "arraydata" table of the database. Providing non positive number
     *                to this argument will generate error.
     * @param intStopLevel The minimum distance, between current price and stoploss & takeprofit, needed for
                           opening new position. Providing negative number to this argument will generate error.
     */
    public NonArrayData(BigDecimal buy , BigDecimal sell , int intBars , int intStopLevel) {
        // set the members
        setBuyPrice(buy);
        setSellPrice (sell);
        setBars(intBars);
        setStopLevel(intStopLevel);

    } // end of constructor
    
    
    /**
     * Copy constructor.
     *
     * @param nonArrData Another instance of the NonArrayData class.
     */
    public NonArrayData(NonArrayData nonArrData) {
        this(nonArrData.getBuyPrice() , nonArrData.getSellPrice() , nonArrData.getBars() , 
             nonArrData.getStopLevel() );
    } // end of copy constructor
    
    
    /**
     * Sets the buyPrice member.
     * 
     * @param buy The current market buy price.Providing null or non positive number to this argument will
     *            generate error.
     */
    private void setBuyPrice(BigDecimal buy) {
        // check for null pointer
        if(buy == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setBuyPrice" );

            // set to default value
            buyPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if(buy.compareTo(BigDecimal.ZERO) != 1) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT , "setBuyPrice", "Non positive buy price");

            // set to default
            buyPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // set the buyPrice
        buyPrice = new BigDecimal(buy.toString() );

    } // end of method setBuyPrice()


    /**
     * Sets the sellPrice member.
     *
     * @param sell The current market sell price. Providing null or non positive number to this argument will
     *             generate error.
     */
    private void setSellPrice(BigDecimal sell) {
        // check for null pointer
        if(sell == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setSellPrice" );

            // set to default value
            sellPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if(sell.compareTo(BigDecimal.ZERO) != 1) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT , "setSellPrice", "Non positive sell price");

            // set to default
            sellPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // set the buyPrice
        sellPrice = new BigDecimal(sell.toString() );

    } // end of method setSellPrice()


    /**
     * Sets the bars member.
     *
     * @param intBars The number of bars in the "arraydata" table of the database. Providing non positive number
     *                to this argument will generate error.
     */
    private void setBars( int intBars) {
        // check for logical error
        if( intBars <= 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT , "setBars",  "Non positive number of bars");

            // set to default
            bars = 1;

            // exit from method
            return;
        } // end of if statement

        // set the bars member
        bars = intBars;

    } // end of method setBars()


    /**
     * Sets the stopLevel member.
     *
     * @param intStopLevel The minimum distance, between current price and stoploss & takeprofit, needed for
                           opening new position. Providing negative number to this argument will generate error.
     */
    private void setStopLevel(int intStopLevel) {
        // check for logical error
        if(intStopLevel < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT , "setStopLevel", "Negative stopLevel" );

            // set to default
            stopLevel = 1;

            // exit from method
            return;
        } // end of if statement

        // set the stopLevel
        stopLevel = intStopLevel;

    } // end of method setStopLevel()


    /**
     * Returns the buyPrice member.
     *
     * @return Positive BigDecimal representing the current market buy price.
     */
    public BigDecimal getBuyPrice() {
        return buyPrice;
    } // end of method
    
    
    /**
     * Returns the sellPrice member.
     *
     * @return Positive BigDecimal representing the current market sell price.
     */
    public BigDecimal getSellPrice() {
        return sellPrice;
    } // end of method
    
    
    /**
     * Returns the bars member.
     *
     * @return Positive integer representing the number of rows in the "arraydata" table of the database.
     */
    public int getBars() {
        return bars;
    } // end of method
    
    
    /**
     * Returns the stopLevel member.
     *
     * @return Non negative number representing the minimum distance, between the current price and
     *         stopLoss & takeProfit prices, needed for successfully opening new trade position.
     */
    public int getStopLevel() {
        return stopLevel;
    } // end of method


    /**
     * Checks if the data in the provided instance of NonArrayData is identical to
     * the data in this instance.
     *
     * @param data Another instance of the NonArrayData class.
     * @return True if the provided NonArrayData object is identical to this one, false otherwise.
     */
    public boolean compareTo(NonArrayData data) {
        // declare local variables:
        boolean result =false;                      // the result to be returned by the method
        // end of local variables declaration


        // compare the provided NonArrayData with this one
        if(data.getBars() == bars && data.getStopLevel() == stopLevel &&
           data.getBuyPrice().compareTo(buyPrice) == 0 &&
           data.getSellPrice().compareTo(sellPrice) == 0){
            // the 2 NonArrayData objects are identical
            result = true;
        } // end of if statement

        // return the result
        return result;

    } // end of method compareTo()


    /**
     * Returns the spread.
     *
     * @return Non negative BigDecimal representing the difference between the buy price and the sell price.
     */
    public BigDecimal getSpread() {
        // declare local variables:
        BigDecimal result;
        // end of local variables declaration

        // resolve the result
        result = getBuyPrice().subtract( getSellPrice() );

        // return the result
        return result;
    } // end of method getSpread()

} // end of class NonArrayData compareTo()
