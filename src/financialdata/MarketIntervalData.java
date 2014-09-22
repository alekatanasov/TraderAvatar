/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package financialdata;


// imports:
import error.*;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import marketanalysis.*;



/**
 * Holds transformed market data in the form of Intervals.
 *
 * @author Alexandar Atanasov
 */
public class MarketIntervalData {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** Reference to instance of the MarketRawData class. */
    private MarketRawData rawData;

    /** The close prices in the form of Interval. */
    private Interval closeInterval;

    /** The high prices in the form of Interval. */
    private Interval highInterval;

    /** The low prices in the form of Interval. */
    private Interval lowInterval;

    /** The volumes in the form of Interval. */
    private Interval volumeInterval;

    // end of member variables declaration


    // initialization of static members:
    static {
        eHandler = new ErrorHandler("MarketIntervalData");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param data Reference to valid instance of the MarketRawData class.
     */
    public MarketIntervalData(MarketRawData data) {
        // check for null pointer
        if(data == null) {
            // error
            eHandler.newError(ErrorType.NULL_ARGUMENT, "Constructor");

            // exit from method
            return;
        } // end of if statement

        // set the rawData
        rawData = data;

        // set the interval data
        setIntervalData(rawData.getArrayData() );
        
    } // end of constructor


    /**
     * Copy constructor.
     * 
     * @param data Another instance of the MarketIntervalData class.
     */
    public MarketIntervalData(MarketIntervalData data) {
        // load the members
        setRawData(data.getRawData() );
        setCloseInterval(data.getCloseInterval() );
        setHighInterval(data.getHighInterval() );
        setLowInterval(data.getLowInterval() );
        setVolumeInterval(data.getVolumeInterval() );
    } // end of copy constructor


    /**
     * Sets the rawData member.
     * 
     * @param marketRawData Instance of the MarketRawData class. Providing null to this argument will generate
     *                      error.
     */
    private void setRawData(MarketRawData marketRawData) {
        // check for null pointer
        if(marketRawData == null) {
            // error..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setRawData");

            // exit from method
            return;
        } // end of if statement

        // set the rawData
        rawData = new MarketRawData(marketRawData);

    } // end of method setRawData()


    /**
     * Returns the rawData member.
     *
     * @return Reference to the instance of the MarketRawData contained by this class.
     */
    public MarketRawData getRawData() {
        return rawData;
    } // end of method getRawData()


    /**
     * Sets the interval data members.
     *
     * @param arrayData Array of ArrayDataRow objects representing the "arraydata" table of the database.
     *                  If null pointer is passed to this argument or the arrayData contains null elements,
     *                  error will be generated.
     */
    private void setIntervalData(ArrayDataRow arrayData[]) {
        // declare local variables:
        ArrayDataRow reversedData[];            // the arrayData with elements in reversed order
        BigDecimal close[];                     // close prices
        BigDecimal high[];                      // high prices
        BigDecimal low[];                       // low prices
        BigDecimal volume[];                    // volumes
        // end of local variables declaration


        // check for null pointer
        if(arrayData == null) {
            // error
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setIntervalData");

            // exit from method
            return;
        } // end of if statement

        // check the arrayData for null elements
        for(int c = 0; c < arrayData.length; c++) {

            if(arrayData[c] == null) {
                // error ..
                eHandler.newError(ErrorType.NULL_ARRAY_ELEMENT , "setIntervalData");
            } // end of if statement

        } // end of for loop

        // initialize the reversedData
        reversedData = new ArrayDataRow[arrayData.length];

        // load the reversedData
        for(int c = 0; c < reversedData.length; c++) {
            //
            reversedData[c] = arrayData[arrayData.length-1-c];
        } // end of for loop

        // initialize the arrays
        close  = new BigDecimal[reversedData.length];
        high   = new BigDecimal[reversedData.length];
        low    = new BigDecimal[reversedData.length];
        volume = new BigDecimal[reversedData.length];

        // load the arrays
        for(int c = 0; c < reversedData.length; c++) {
            // load the curent elements
            close[c] = new BigDecimal(reversedData[c].getClosePrice().toString() );
            high[c] = new BigDecimal(reversedData[c].getHighPrice().toString() );
            low[c] = new BigDecimal(reversedData[c].getLowPrice().toString() );
            volume[c] = new BigDecimal(reversedData[c].getVolume().toString() );
        } // end of for loop

        // load the data intervals
        closeInterval = new Interval(close , rawData.getDigitsAfterDot() );
        highInterval = new Interval(high, rawData.getDigitsAfterDot() );
        lowInterval = new Interval(low, rawData.getDigitsAfterDot() );
        volumeInterval = new Interval(volume, rawData.getDigitsAfterDot() );
        
    } // end of method setIntervalData()


    /**
     * Sets the closeInterval member.
     * @param close The Interval made by the close prices of the market security. Providing null
     *              to this argument will generate error.
     */
    private void setCloseInterval(Interval close) {
        // check for null pointer
        if(close == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setCloseInterval");

            // exit from method
            return;
        } // end of if statement

        // set the closeInterval
        closeInterval = new Interval(close);

    } // end of method setCloseInterval()


    /**
     * Sets the highInterval member.
     * @param close The Interval made by the high prices of the market security. Providing null
     *              to this argument will generate error.
     */
    private void setHighInterval(Interval high) {
        // check for null pointer
        if(high == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setHighInterval");

            // exit from method
            return;
        } // end of if statement

        // set the highInterval
        highInterval = new Interval(high);

    } // end of method setHighInterval()


    /**
     * Sets the lowInterval member.
     * @param low The Interval made by the low prices of the market security. Providing null
     *              to this argument will generate error.
     */
    private void setLowInterval(Interval low) {
        // check for null pointer
        if(low == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setLowInterval");

            // exit from method
            return;
        } // end of if statement

        // set the lowInterval
        lowInterval = new Interval(low);

    } // end of method setLowInterval()


    /**
     * Sets the volumeInterval member.
     * @param vol The Interval made by the volumes of the market security. Providing null
     *              to this argument will generate error.
     */
    private void setVolumeInterval(Interval vol) {
        // check for null pointer
        if(vol == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setVolumeInterval");

            // exit from method
            return;
        } // end of if statement

        // set the volumeInterval
        volumeInterval = new Interval(vol);

    } // end of method setVolumeInterval()


    /**
     * Refreshes the Interval members  by recalculating them from the raw data.
     */
    public void updateIntervalData() {
        // set the interval data
        setIntervalData(rawData.getArrayData() );
    } // end of method

    /**
     * Returns the closeIntervals member.
     *
     * @return Non null Interval made from the close prices of the market security (instrument).
     */
    public Interval getCloseInterval() {
        return closeInterval;
    } // end of method


    /**
     * Returns the highInterval member.
     *
     * @return Non null Interval made from the high prices of the market security (instrument).
     */
    public Interval getHighInterval() {
        return highInterval;
    } // end of method


    /**
     * Returns the lowInterval member.
     *
     * @return Non null Interval made from the low prices of the market security (instrument).
     */
    public Interval getLowInterval() {
        return lowInterval;
    } // end of method


    /**
     * Returns the volumeInterval member.
     *
     * @return Non null Interval made from the volumes of the market security (instrument).
     */
    public Interval getVolumeInterval() {
        return volumeInterval;
    } // end of method

} // end of class MarketIntervalData
