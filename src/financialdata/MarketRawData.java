/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package financialdata;


// imports:
import error.*;
import java.math.BigDecimal;



/**
 * Non modified and non formated data representing the current market conditions. The data in this class comes
 * directly from the Trader Avatar database.
 *
 * @author Alexandar Atanasov
 */
public class MarketRawData {
    // declare member variables:

    /** If some incorect data is provided as array data, the arrayData member will be set to this
        default array data. This is done to prevent the arrayData from being set to null. */
    private static final ArrayDataRow DEFAULT_ARRAY_DATA[];

    /** If some incorect data is provided as non array data, the nonArrayData member will be set to this
        default non array data. This is done to prevent the nonArrayData from being set to null.*/
    private static final NonArrayData DEFAULT_NON_ARRAY_DATA;

    /** Instance of the ErrorHandler class used for dealing with errors. */
    private static ErrorHandler eHandler;

    /** The maximum number of rows in the "arraydata" table of the Trader Avatar database. */
    private int maxBars;

    /** The number of digits after the dot in the decimal fields of the Trader Avatar database. */
    private int digitsAfterDot;

    /** Represents the "arraydata" table of the Trader Avatar database. */
    private ArrayDataRow arrayData[];

    /** Represents the "nonarraydata" table of the Trader Avatar database. */
    private NonArrayData nonArrayData;
    
    // end of member variables declaration


    // initialization of static members
    static {
        // initialize the ErrorHandlel
        eHandler = new ErrorHandler("MarketRawData");
        
        // initialize the default array data
        DEFAULT_ARRAY_DATA = new ArrayDataRow[2];
        DEFAULT_ARRAY_DATA[0] = new ArrayDataRow(BigDecimal.ONE ,BigDecimal.ONE ,BigDecimal.ONE , BigDecimal.ONE );
        DEFAULT_ARRAY_DATA[1] = new ArrayDataRow(BigDecimal.ONE ,BigDecimal.ONE ,BigDecimal.ONE , BigDecimal.ONE );

        // initialize the default non array data
        DEFAULT_NON_ARRAY_DATA = new NonArrayData(BigDecimal.ONE , BigDecimal.ONE , 1 , 1 );

    } // end of initialization of static members


    /**
     * Constructor.
     *
     * @param arrData Array representing the "arraydata" table of Trader Avatar database. If null is provided to
     *                this argument or the provided array contains null element, error will be generated.
     * @param nonArrData Instance of the NonArrayData class representing the "nonarraydata" table of
     *                   Trader Avatar database. If null is provided to this argument, error will be generated.
     * @param maximumBars The maximum number of rows in the "arraydata" table of the database. If
     *                    non positive number is provided to this argument, error will be generated.
     * @param digits The number of digits after the dot in the price of the chosen market security ( instrument ).
     *               If negative number is provided to this argument, error will be generated.
     */
    public MarketRawData(ArrayDataRow arrData[] , NonArrayData nonArrData , int maximumBars ,
                         int digits) {
        // set the member
        setDigitsAfterDot(digits);
        setArrayData(arrData);
        setNonArrayData(nonArrData);
        setMaxBars(maximumBars);

    } // end of constructor


    /**
     * Copy constructor.
     *
     * @param data Another instance of the MarketRawData.
     */
    public MarketRawData(MarketRawData data) {
        this(data.getArrayData() , data.getNonArrayData() , data.getMaxBars() , data.getDigitsAfterDot()  );
    } // end of copy constructor


    /**
     * Sets the arrayData member.
     *
     * @param data Array representing the "arraydata" table of Trader Avatar database. If null is provided to this
     *             argument or the provided array contains null element, error will be generated.
     */
    private void setArrayData(ArrayDataRow data[] ) {
        // check for null pointer
        if(data == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setArrayData");

            // set to default value
            arrayData = DEFAULT_ARRAY_DATA;

            // exit from method
            return;

        } // end of if statement

        // check for null array element
        for(int c = 0; c < data.length; c++) {
            if(data[c] == null) {
                // null array element; error ..
                eHandler.newError(ErrorType.NULL_ARRAY_ELEMENT, "setArrayData" );

                // set to default value
                arrayData = DEFAULT_ARRAY_DATA;

                // exit from method
                return;
            } // end of if statement

        } // end of for loop

        // load the arrayData member
        arrayData = new ArrayDataRow[data.length];
        for(int c = 0; c < data.length; c++) {
            // load the current element
            arrayData[c] = new ArrayDataRow(data[c]);
        } // end of for loop

    } // end of method setArrayData()


    /** 
     * Sets the nonArrayData member.
     * 
     * @param nonArrData Instance of the NonArrayData class representing the "nonarraydata" table of 
     *                   Trader Avatar database. If null is provided to this argument, error will be generated.
     */
    private void setNonArrayData(NonArrayData nonArrData) {
        // check for null pointer
        if(nonArrData == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setNonArrayData");

            // set to default
            nonArrayData = this.DEFAULT_NON_ARRAY_DATA;

            // exit from method
            return;
        } // end of if statement

        // set the nonArrayData member
        nonArrayData = new NonArrayData(nonArrData);

    } // end of method setNonArrayData()



    /** 
     * Sets the maxBars member.
     * 
     * @param maximumBars The maximum number of rows in the "arraydata" table of the database. If
     *                    non positive number is provided to this argument, error will be generated.
     */
    private void setMaxBars(int maximumBars) {
        // check for logical error
        if(maximumBars <= 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setMaximumBars", "Non positive maxBars" );

            // set to default
            maxBars = 10;

            // exit from method
            return;

        } // end of if statement

        // set the maxBars
        maxBars = maximumBars;

    } // end of method setMaxBars()


    /**
     * Sets the digitsAfterDot member.
     *
     * @param digits The number of digits after the dot in the price of the chosen market security ( instrument ).
     *               If negative number is provided to this argument, error will be generated.
     */
    private void setDigitsAfterDot(int digits) {
        // check for logical error
        if(digits < 0) {
            // error
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setDigitsAfterDot" , "Negative number of digits");

            // set to default
            digitsAfterDot = 0;

            // exit from method
            return;
        } // end of if statement

        // set the digits
        digitsAfterDot = digits;

    } // end of method setDigitsAfterDot()

    
    /**
     * Returns the digitsAfterDot member.
     *
     * @return Non negative integer representing the number of digits after the dot in the price of the
     *         market security ( instrument ) .
     */
    public int getDigitsAfterDot() {
        return digitsAfterDot;
    } // end of method


    /**
     * Updates the arrayData member.
     *
     * @param newRows Array of new ArrayDataRow objects. If the provided array is null or contains null element
     *                error will be generated.
     */
    public void updateArrayData(ArrayDataRow newRows[]) {
        // declare local variables
        int redundantRows;                          // arrayData lenght + number of new rows - maxBars
        ArrayDataRow buffer[];                      // buffer used for temporary storage
        // end of local variables declaration


        // check for null pointer
        if(newRows == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "updateArrayData");
        } // end of if statement

        // check for null row
        for(int c = 0; c < newRows.length;c++) {
            if(newRows[c] == null) {
                // error
                eHandler.newError(ErrorType.NULL_ARRAY_ELEMENT , "updateArrayData" );

                // exit from loop
                break;
            } // end of if statement

        } // end of for loop

        // check if the the number of existing rows is equal to the maxBars
        if(arrayData.length == maxBars) {
            // discard the last rows from the arrayData and load the newRows
            buffer = new ArrayDataRow[arrayData.length];
            System.arraycopy(arrayData, 0, buffer, newRows.length, arrayData.length - newRows.length);
            System.arraycopy(newRows, 0, buffer, 0, newRows.length);
            arrayData=buffer;
        } else {
            // check if the number of new rows plus number of existing rows exceeds the maxMars
            if(arrayData.length+newRows.length > maxBars) {
                // find the number of redundant rows
                redundantRows = (arrayData.length+newRows.length) - maxBars;

                // add the new rows and discard tha last redundant rows
                buffer = new ArrayDataRow[maxBars];
                System.arraycopy(arrayData, newRows.length, buffer, newRows.length, arrayData.length - redundantRows);
                System.arraycopy(newRows, 0, buffer, 0, newRows.length);
                arrayData=buffer;
            } else {
                // add the new rows to the arrayData
                buffer = new ArrayDataRow[arrayData.length+newRows.length];
                System.arraycopy(arrayData, 0, buffer, newRows.length, arrayData.length );
                System.arraycopy(newRows, 0, buffer, 0, newRows.length);
                arrayData=buffer;
            } // end of if else statement

        } // end of if else statement

    } // end of method updateArrayData()


    /**
     * Updates the nonArrayData member.
     *
     * @param newData The updated NonArrayData. If null is provided to this argument, error will be generated.
     */
    public void updateNonArrayData(NonArrayData newData) {
        // check for null pointer
        if(newData == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "updateNonArrayData");
        } // end of if statement

        //check if the newData is different than the existing
        if(newData.compareTo(this.nonArrayData) ) {
            // no changes; exit from method
            return;
        } // end of if statement

        // store the new data
        setNonArrayData(newData);
    } // end of method updateNonArrayData()


    /**
     * Returns the bars member.
     *
     * @return The number of rows in the "arraydata" table of the database.
     */
    public int getBars() {
        return nonArrayData.getBars();
    } // end of method getBars()


    /**
     * Returns the buy price member.
     *
     * @return Positive BigDecimal representing the current market buy price.
     */
    public BigDecimal getBuyPrice() {
        return nonArrayData.getBuyPrice();
    } // end of method getBuyPrice()


    /**
     * Returns the sell price member.
     *
     * @return Positive BigDecimal representing the current market sell price.
     */
    public BigDecimal getSellPrice() {
        return nonArrayData.getSellPrice();
    } // end of method getSellPrice()


    /**
     * Returns the maximumBars member.
     *
     * @return Positive integer representing the maximum number of rows in the "arraydata" table of the database.
     */
    public int getMaxBars() {
        return maxBars;
    } // end of method


    /**
     * Returns the arrayData member.
     *
     * @return Non null array of ArrayDataRow objects representing the "arraydata" table of the database.
     */
    public ArrayDataRow[] getArrayData() {
        return arrayData;
    } // end of method getArrayData()


    /**
     * Returns the nonArrayData member.
     *
     * @return Non null NonArrayData object representing the "nonarraydata" table of the database.
     */
    public NonArrayData getNonArrayData() {
        return nonArrayData;
    } // end of method getNonArrayData()

} // end of class MarketRawData
