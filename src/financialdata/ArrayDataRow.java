/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package financialdata;


// imports
import error.*;
import java.math.BigDecimal;



/**
 * Represents single row of data from "arraydata" table of Trader Avatar database.
 *
 * @author Alexandar Atanasov
 */
public class ArrayDataRow {
    // declare member variables:

    /** Close price. */
    private BigDecimal closePrice;

    /** High price. */
    private BigDecimal highPrice;

    /** Low price. */
    private BigDecimal lowPrice;

    /** Volume. */
    private BigDecimal volume;

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    // end of member variables declaration


    // static member initializaton
    static {
        eHandler = new ErrorHandler("ArrayDataRow");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param close The close price. Providing null or non positive number to this argument will
     *              generate error.
     * @param high  The high price. Providing null or non positive number to this argument will
     *              generate error.
     * @param low   The low price. Providing null or non positive number to this argument will
     *              generate error.
     * @param vol   The volume. Providing null or negative number to this argument will
     *              generate error.
     */
    public ArrayDataRow(BigDecimal close ,BigDecimal high , BigDecimal low , BigDecimal vol ) {
        // set the members
        setClosePrice(close);
        setHighPrice(high);
        setLowPrice(low);
        setVolume(vol);

    } // end of constructor


    /**
     * Copy constructor.
     *
     * @param arrDataRow Another instance of the ArrayDataRow.
     */
    public ArrayDataRow(ArrayDataRow arrDataRow) {
        this(arrDataRow.getClosePrice() ,arrDataRow.getHighPrice() ,arrDataRow.getLowPrice() ,
             arrDataRow.getVolume() );
    } // end of copy constructor


    /** 
     * Sets the closePrice member.
     * 
     * @param close The close price. Providing null or non positive number to this argument will
     *              generate error.
     */
    private void setClosePrice(BigDecimal close) {
        // check for null pointer
        if( close == null) {
            // error ...
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setClosePrice");

            // set to default
            closePrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if( close.compareTo(BigDecimal.ZERO) != 1) {
            // error ...
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setClosePrice");

            // set to default
            closePrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // set the close price
        closePrice = close;

    } // end of method setClosePrice()


    /**
     * Sets the highPrice member.
     *
     * @param high The high price. Providing null or non positive number to this argument will
     *             generate error.
     */
    private void setHighPrice(BigDecimal high) {
        // check for null pointer
        if( high == null) {
            // error ...
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setHighPrice");

            // set to default
            highPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if( high.compareTo(BigDecimal.ZERO) != 1) {
            // error ...
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setHighPrice");

            // set to default
            highPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // set the close price
        highPrice = high;

    } // end of method setHighPrice()


    /**
     * Sets the lowPrice member.
     *
     * @param low The low price. Providing null or non positive number to this argument will
     *            generate error.
     */
    private void setLowPrice(BigDecimal low) {
        // check for null pointer
        if( low == null) {
            // error ...
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setLowPrice");

            // set to default
            lowPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if( low.compareTo(BigDecimal.ZERO) != 1) {
            // error ...
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setLowPrice");

            // set to default
            lowPrice = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // set the close price
        lowPrice = low;

    } // end of method setLowPrice()


    /**
     * Sets the volume member.
     *
     * @param vol The volume. Providing null or negative number to this argument will
     *            generate error.
     */
    private void setVolume(BigDecimal vol) {
        // check for null pointer
        if( vol == null) {
            // error ...
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setVolume");

            // set to default
            volume = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if( vol.compareTo(BigDecimal.ZERO) == -1) {
            // error ...
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setVolume");

            // set to default
            volume = BigDecimal.ONE;

            // exit from method
            return;
        } // end of if statement

        // set the close price
        volume = vol;

    } // end of method setVolume()


    /**
     * Returns the closePrice member.
     *
     * @return Non null BigDecimal with positive value.
     */
    public BigDecimal getClosePrice() {
        return closePrice;
    } // end of method


    /**
     * Returns the high price member.
     *
     * @return Non null BigDecimal with positive value.
     */
    public BigDecimal getHighPrice() {
        return highPrice;
    } // end of method getHighPrice()


    /**
     * Returns the low price member.
     *
     * @return Non null BigDecimal with positive value.
     */
    public BigDecimal getLowPrice() {
        return lowPrice;
    } // end of method getLowPrice()


    /**
     * Returns the volume member.
     *
     * @return Non null BigDecimal with positive value.
     */
    public BigDecimal getVolume() {
        return volume;
    } // end of method getVolume()

} // end of class ArrayDataRow
