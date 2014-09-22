/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package databases;


//imports
import error.*;
import financialdata.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;



/**
 *
 * Provides capability for reading and writing data from Trader Avatar MYSQL database.
 * Trader Avatar databases have specific format ( number of predefined tables , table names, columns 
 * and column names ). This class operates only on Trader Avatar MQSQL databases.
 *
 * @author Alexandar Atanasov
 *
 */
public class DatabaseConnector {
    // declare member variables:

    /** The default number of digits after the dot in the Trader Avatar database decimal fields. */
    private final int DEFAULT_SCALE = 4;           

    /** The maximum number of rows in the arraydata table of the Trader Avatar database. */
    private int maxBars;

    /** The number of digits after the dot in the Trader Avatar database decimal fields. */
    private int digitsAfterDot;

    /** String representing valid mysql user. */
    private String sqlUser;

    /** The password associated with the mysql user. */
    private String sqlPassword;

    /** The name of the Trader Avatar database to which this instance of the DatabaseConnector is connected. */
    private String databaseName;

    /** Instance of the ErrorHandler class used for error handling. */
    private ErrorHandler eHandler;

    /** Object representing valid connection with mysql database. */
    private Connection connection;

    /** Types of orders ( trade positions ). */
    public enum OrderType {LONG , SHORT}

    // end of member variables declaration


    /**
     * Constructor
     *
     * @param dbName The name of the database to which this instance of the DatabaseConnector will be
     *               connected. Providing null or empty string to this argument will generate error.
     * @param maximumBars The maximum number of rows in the arraydata table of the database. Providing null
     *                    or empty or non integer string to this argument will generate error.
     * @param user String representing valid mysql user.If null or empty string is provided to this argument,
     *             error will be generated.
     * @param password Valid password associated with the provided mysql user.If null or empty string
     *                 is provided to this argument, error will be generated.
     * @param digits The number of digits after the dot in the decimal fields of the Trader Avatar database
     *               to which this DatabaseConnector will be connected. If this argument is less than 0,
     *               error will be generated
     */
    public DatabaseConnector(String dbName , String maximumBars , String user , String password ,
                             int digits) {
        // initialize the eHandler member
        eHandler = new ErrorHandler("DatabaseConnector");

        // load the other arguments
        setDigitsAfterDot(digits);
        setMaxBars(maximumBars);
        setSqlUser(user);
        setSqlPassword(password);
        setDatabaseName(dbName);

        // initialize connection to the database
        try {
            // load the MySQL driver
            Class.forName("com.mysql.jdbc.Driver");

            // get connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName , sqlUser , sqlPassword);

        } catch (java.lang.LinkageError e) {
            // error handling
            eHandler.newError(ErrorType.INIT_MYSQL_LINK_ERROR , "Constructor");
        }  catch (ClassNotFoundException e) {
            // error handling
            eHandler.newError(ErrorType.INIT_MYSQL_CLASS_NOT_FOUND , "Constructor");
        } catch (SQLException e) {
            // error handling
            eHandler.newError(ErrorType.SQL_ERROR , "Constructor");
        } // end of try block

    } // end of constructor


    /**
     *
     * Sets the digitsAfterDot member.
     *
     * @param digits The digits after the dot in the decimal fields of the Trader Avatar database. If
     *               this argument is less than 0, error will be generated.
     */
    private void setDigitsAfterDot(int digits) {
        // check for logical error
        if(digits < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setDigitsAfterDot");

            // exit
            return;

        } // end of if statement

        // set the digits
        digitsAfterDot = digits;

    } // end of method setDigitsAfterDot()



    /**
     *
     * Returns the digitsAfterDot member.
     *
     * @return Non negative integer representing the digits after the dot in the decimal fields of
     *         the Trader Avatar database to which database this DatabaseConnector is connected.
     */
    public int getDigitsAfterDot() {
        return digitsAfterDot;
    } // end of method getDigitsAfterDot()


    /**
     * 
     * Sets the databaseName member.
     * 
     * @param name The name of the database to which the current instance of the DatabaseConnector
     *             will be connected. Providing null or empty string to this argument will generate error.
     */
    private void setDatabaseName(String name) {
        // check for null pointer
        if(name == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setDatabaseName");

            // exit from method
            return;
        } // end of if statement

        // check for empty string
        if(name.contentEquals("") ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT , "setDatabaseName" );

            // exit from method
            return;
        } // end of if statement

        // set the databaseName
        databaseName = name;

    } // end of method setDatabaseName()


    /**
     * Sets the sqlUser member.
     *
     * @param user String representing valid mysql user.If null or empty string is provided to this argument,
     *             error will be generated.
     */
    private void setSqlUser(String user) {
        // check for null pointer
        if(user == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setSqlUser" );

            // exit from method
            return;
        } // end of if statement

        // check for empty string
        if(user.contentEquals("") ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setSqlUser" );

            // exit from method
            return;
        } // end of if statement

        // set the sqlUser
        sqlUser = user;
        
    } // end of method setSqlUser()


    /**
     * Sets the sqlPassword member.
     * 
     * @param password Valid password associated with the provided mysql user.If null or empty string 
     *                 is provided to this argument, error will be generated.
     */
    private void setSqlPassword(String password) {
        // check for null pointer
        if(password == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setSqlPassword" );

            // exit from method
            return;
        } // end of if statement

        // check for empty string
        if(password.contentEquals("") ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setSqlPassword" );

            // exit from method
            return;
        } // end of if statement

        // set the sqlPassword member
        sqlPassword = password;

    } // end of method setSqlPassword()


    /**
     * Sets the maxBars member.
     *
     * @param maximumBars String containing the maximum number of rows in the arraydata table of the Trader Avatar
     *                    database to which this DatabaseConnector is connected. Providing null or empty or non
     *                    integer string to this argument will generate error.
     */
    private void setMaxBars(String maximumBars) {
        // declare local variables:
        int buffer = 100;          // default value
        // end of local variables declaration


        // check for null pointer
        if(maximumBars == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setMaxBars" );

            // exit from method
            return;
        } // end of if statement

        // check for empty string
        if(maximumBars.contentEquals("") ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setMaxBars" );

            // exit from method
            return;
        } // end of if statement

        // check if the provided string is indeed integer
        try {
            // try to convert the string in integer
            Integer.parseInt(maximumBars);
            
        } catch (NumberFormatException e) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setMaxBars" );

            // exit from method
        } // end of try block

        // set the maxBars member
        maxBars = buffer;

    } // end of method setMaxBars()


    /**
     * Extracts all data from the arraydata table of the Trader Avatar database to which database this
     * instance of the DatabaseConnector is connected and than returns the extracted data. If the
     * arraydata table is empty the method will generate error.
     *
     * @return Array of ArrayDataRow objects. If there is no data in the arraydata table the method will
     *         return null.
     */
    public ArrayDataRow[] getArrayData() {
        // declare local variables:
        ArrayList <ArrayDataRow> arrayDataList;              // vector which holds ArrayDataRows
        ArrayDataRow result[] = null;                       // the result to be returned by the method
        ArrayDataRow singleRow;                             // single row of array data
        Statement statement;                                // object used for executing sql queryes
        ResultSet resultSet;                                // result set object produced by an sql query
        BigDecimal close;                                   // close price buffer
        BigDecimal high;                                    // high price buffer
        BigDecimal low;                                     // low price buffer
        BigDecimal volume;                                  // volume buffer
        // end of local variables declaration


        // initialize the vector of array data rows
        arrayDataList = new ArrayList <ArrayDataRow> ();

        // attempt to extract and store the arraydata in the array data vector variable
        try {
            // create statement and execute sql query to get the array data
            statement = connection.createStatement ();
            statement.executeQuery("SELECT * from arraydata ORDER BY Id");

            // get the result set produced from the sql query
            resultSet = statement.getResultSet();

            // main extraction loop
            while(resultSet.next() ) {
                // get the current row data
                close = new BigDecimal(resultSet.getString("Close") );
                high = new BigDecimal(resultSet.getString("High") );
                low = new BigDecimal(resultSet.getString("Low") );
                volume = new BigDecimal(resultSet.getString("Volume") );

                // check for scale differencies
                if(digitsAfterDot < DEFAULT_SCALE) {

                    // remove the last unnecessary digits from the extracted data( they are zeroes )
                    for(int c = 0; c < (DEFAULT_SCALE - digitsAfterDot) ; c++) {
                        // remove the digits by converting the BigDecimals to strings and discarding the last
                        // element, then converting the strings to BigDecimals again
                        close = new BigDecimal(close.toString().substring(0, close.toString().length()-1 ) );
                        high = new BigDecimal(high.toString().substring(0, high.toString().length()-1 ) );
                        low = new BigDecimal(low.toString().substring(0, low.toString().length()-1 ) );
                        volume = new BigDecimal(volume.toString().substring(0, volume.toString().length()-1 ) );
                    } // end of for loop

                } // end of if statement

                // create the current array data row
                singleRow = new ArrayDataRow(close , high , low ,volume);

                // add the current array data row to the vector of rows
                arrayDataList.add(singleRow);

            } // end of while loop

            // close the result set
            resultSet.close();

            // close the statement
            statement.close();

        } catch(SQLException e) {
            // error ..
            eHandler.newError(ErrorType.SQL_ERROR , "getArrayData");
        } // end of try block

        // convert the array list of rows  to array of rows ( if the list exists )
        if(arrayDataList.size() > 0) {
            // initialize the result array
            result = new ArrayDataRow[arrayDataList.size()];

            // main converting loop
            for(int c = 0; c < result.length ; c++) {
                result[c] = arrayDataList.get(c);
            } // end of for loop

        } else {
            // error - there are no rows ( the vector of rows is empty )
            eHandler.newError(ErrorType.ARRAY_DATA_VECTOR_WITH_ZERO_SIZE , "getArrayData");
        } // end of if else statement

        // return the result
        return result;

    }

    /**
     * Extracts all data stored in the nonarraydata table of the TraderAvatar database to which database
     * this instance of the DatabaseConnector is connected and than returns the extracted data.
     *
     * @return The NonArrayData contained in the nonArrayData table of the database to which this
     *         DatabaseConnector is connected.
     */
    public NonArrayData getNonArrayData() {
        // declare local variables:
        Statement statement;                    // Object used for executing sql queries
        ResultSet resultSet;                    // Result set produced by sql query
        BigDecimal buy;                         // buy price buffer
        BigDecimal sell;                        // sell price buffer
        int bars;                               // bars buffer
        int stopLevel;                          // stopLevel buffer
        NonArrayData result=null;               // the result to be returned by the method
        // end of local variables declaration


        // try to extract the data from the nonarraydata table
        try {
            // create statement and execute sql query to retrieve the non array data
            statement = connection.createStatement ();
            statement.executeQuery("SELECT * from nonarraydata");

            // get the result set produced by the sql query
            resultSet = statement.getResultSet();

            // retrieve the non array data ( only one row is retrieved since by definition
            // the non array data table should have only one row )
            resultSet.next();
            buy = new BigDecimal(resultSet.getString("Buy") );
            sell = new BigDecimal(resultSet.getString("Sell") );
            bars = Integer.parseInt(resultSet.getString("Bars"));
            stopLevel = Integer.parseInt(resultSet.getString("StopLevel"));

            // check if the scale is lesser than the default number of digits in the database
            if(digitsAfterDot < DEFAULT_SCALE) {
                // discard the unnecessary digits
                for(int c = 0; c < (DEFAULT_SCALE - digitsAfterDot) ; c++) {
                    buy = new BigDecimal(buy.toString().substring(0, buy.toString().length()-1 ) );
                    sell = new BigDecimal(sell.toString().substring(0, sell.toString().length()-1 ) );
                } // end of for loop

            } // end of if statement

            // store the extracted data in the result variable
            result = new NonArrayData(buy , sell , bars ,stopLevel );

            // close the result set
            resultSet.close();

            // close the statement
            statement.close();

        } catch (SQLException e) {
            // error handling
            eHandler.newError(ErrorType.SQL_ERROR , "getNonArrayData");
        } // end of try block

        // return the result
        return result;

    } // end of method getNonArrayData()


    /**
     * Returns the updated array data ( the new rows in the arraydata table ). After updated data is
     * successfully extracted and returned by this method this data will  no longer be considered updated data.
     *
     * @return Array of ArrayDataRow objects. If there is no updated array data ( zero new rows )
     *         the method will return null.
     */
    public ArrayDataRow[] getUpdatedArrayData() {
        // declare local variables:
        int counter = 1;                            // counter used in the main extraction loop
        int updatedRows;                            // the number of updated rows in the arraydata table
        String query;                               // sql query for selecting the updated data
        Vector <ArrayDataRow> arrayDataVector;      // vector of ArrayDataRow objects
        ArrayDataRow result[] =null;                // the result to be returned by this method
        ArrayDataRow singleRow;                     // object representing single row in the arraydata table
        Statement statement;                        // object used to issue sql queries
        ResultSet resultSet;                        // result set returned by sql query
        BigDecimal close;                           // close price buffer
        BigDecimal high;                            // high price buffer
        BigDecimal low;                             // low price buffer
        BigDecimal volume;                          // volume buffer
        // end of local variables declaration


        // get the number of updated rows in the arraydata table
        updatedRows = readUpdatedArrayData();

        // exit if there are no new rows in the arraydata table
        if(updatedRows == 0) {
            return result;
        } // end of if statement

        // initialize the vector of rows
        arrayDataVector = new Vector <ArrayDataRow> ();

        // try to extract the updated rows
        try {
            // prepare sql query for extraction
            query = "SELECT * from arraydata ORDER BY Id LIMIT "+Integer.toHexString(updatedRows);

            // create new statement and execute the extraction query
            statement = connection.createStatement ();
            statement.executeQuery("SELECT * from arraydata ORDER BY Id");

            // get the result set from the query
            resultSet = statement.getResultSet();

            // main extracting loop
            while(resultSet.next() ) {
                // check if all updated rows were loaded in the vector of array data rows
                if(counter > updatedRows) {
                    // exit from loop
                    break;
                } // end of if statement

                // get the current array data row
                close = new BigDecimal(resultSet.getString("Close") );
                high = new BigDecimal(resultSet.getString("High") );
                low = new BigDecimal(resultSet.getString("Low") );
                volume = new BigDecimal(resultSet.getString("Volume") );
                singleRow = new ArrayDataRow(close , high , low ,volume);

                // check for scale differencies
                if(digitsAfterDot < DEFAULT_SCALE) {

                    // remove the last unnecessary digits ( they are zeroes )
                    for(int c = 0; c < (DEFAULT_SCALE - digitsAfterDot) ; c++) {
                    close = new BigDecimal(close.toString().substring(0, close.toString().length()-1 ) );
                    high = new BigDecimal(high.toString().substring(0, high.toString().length()-1 ) );
                    low = new BigDecimal(low.toString().substring(0, low.toString().length()-1 ) );
                    volume = new BigDecimal(volume.toString().substring(0, volume.toString().length()-1 ) );
                    } // end of for loop

                } // end of if statement

                // construct the current array data row
                singleRow = new ArrayDataRow(close , high , low ,volume);

                // add the current array data row to the vector of rows
                arrayDataVector.add(singleRow);

                // prepare for the next iteration
                counter++;

            } // end of while loop

            // close the result set
            resultSet.close();

            // close the statement
            statement.close();

        } catch (SQLException e) {
            // error ..
            eHandler.newError(ErrorType.SQL_ERROR , "getUpdatedArrayData");
        } // end of try block

        // nullify the UpdatedArrayData to indicate that the new data was loaded
        nullifyUpdatedArrayData();

        // convert the vector of array data rows to array
        if(arrayDataVector.size() > 0) {
            // initialize the result array
            result = new ArrayDataRow[arrayDataVector.size()];

            // main converting loop
            for(int c = 0; c < result.length ; c++) {
                result[c] = arrayDataVector.elementAt(c);
            } // end of for loop

        } else {
            // error
            eHandler.newError(ErrorType.ARRAY_DATA_VECTOR_WITH_ZERO_SIZE , "getUpdatedArrayData");
        } // end of if else statement

        // return the result
        return result;

    } // end of method getUpdatedArrayData()


    /**
     * Returns the value of the UpdatedArrayData column from the ea_out table. By database standart
     * the UpdatedArrayData column should have only one row and thus only one integer is returned.
     *
     * @return Non negative integer representing the number of updated rows in the arraydata table.
     */
    public int readUpdatedArrayData() {
        // declare local variables:
        int result=0;                           // the result to be returned by this method
        Statement statement;                    // object for issueing sql queries
        ResultSet resultSet;                    // result set produced by sql query
        // end of local variables declaration


        // try to retrieve the value of the UpdatedArrayData
        try {
            // create and execute statement to retrieve the UpdatedArrayData value
            statement = connection.createStatement ();
            statement.executeQuery("SELECT * from ea_out");

            // get the result set from the sql query
            resultSet = statement.getResultSet();

            // get the UpdatedArrayData value
            resultSet.next();
            result = Integer.parseInt(resultSet.getString("UpdatedArrayData") );

            // close the result set
            resultSet.close();

            // close the statement
            statement.close();

        } catch(SQLException e) {
            // error ..
            eHandler.newError(ErrorType.SQL_ERROR , "readUpdatedArrayData");
        } catch(Exception e) {
            // error
            eHandler.newError(ErrorType.UNKNOWN_EXCEPTION , "readUpdatedArrayData");
        }// end of try block

        // check for logical error
        if( result < 0) {
            // error
            eHandler.newError(ErrorType.DB_CONNECTOR_NEGATIVE_UPDATED_ARR_DATA , "readUpdatedArrayData");
            result = 0;
        } // end of if statement

        // return the result
        return result;
        
    } // end of method readUpdatedArrayData()


    /**
     * Sets the single row in the UpdatedArrayData column of table ea_out to 0.
     */
    private void nullifyUpdatedArrayData() {
        // declare local variables:
        Statement statement;                    // object used for executing sql queries
        // end of local variables declaration


        // try to set the updated array data to 0
        try {
            // create statement object
            statement = connection.createStatement ();

            // execute query to set the UpdatedArrayData to 0
            statement.executeUpdate("UPDATE ea_out SET UpdatedArrayData = 0");

            // close the statement
            statement.close();

        } catch(SQLException e) {
            // error ..
            eHandler.newError(ErrorType.SQL_ERROR , "nullifyUpdatedArrayData");
        } // end of try block

    } // end of method  nullifyUpdatedArrayData()


    /**
     * Sets the single row of the DontUpdate column of table ea_in to 0 ( false )
     */
    public void nullifyDontUpdate() {
        // declare local variables:
        Statement statement;                    // object used for executing sql queries
        // end of local variables declaration


        // try to set the dont update column to 0
        try {
            // create statement object
            statement = connection.createStatement ();

            // execute query to set the DontUpdate to 0
            statement.executeUpdate("UPDATE ea_in SET DontUpdate = 0");

            // close the statement
            statement.close();

        } catch(SQLException e) {
            // error ..
            eHandler.newError(ErrorType.SQL_ERROR , "nullifyDontUpdate");
        } // end of try block

    }// end of method nullifyDontUpdate()


    /**
     * Returns the value of the single row in the Terminate column of the ea_out table. By database standart
     * the Terminate column should have only one row.
     *
     * @return True if the Trader Avatar Terminal should be closed , false if it may continue to operate.
     */
    public boolean readTerminate() {
        // declare local variables:
        boolean result = false;         // the result to be returned by thismethod
        int terminateValue=-1;          // the value of the single row in the Terminate column
        Statement statement;            // object used for executing sql queries
        ResultSet resultSet;            // result set produced by sql query
        // end of local variables declaration


        // try to read the value of the single row in the Terminate column
        try {
            // create and execute statement to retrieve the Terminate value
            statement = connection.createStatement ();
            statement.executeQuery("SELECT Terminate from ea_out");

            // get the result set
            resultSet = statement.getResultSet();

            // get the terminate value
            resultSet.next();
            terminateValue  = Integer.parseInt(resultSet.getString("Terminate") );

            // close the result set
            resultSet.close();

            // close the statement
            statement.close();

        } catch(SQLException e) {
            // error
            eHandler.newError(ErrorType.SQL_ERROR , "readTerminate");
        } catch(Exception e) {
            // error
            eHandler.newError(ErrorType.UNKNOWN_EXCEPTION , "readTerminate");
        }// end of try block

        // check if the terminal should be closed
        if(terminateValue == 1) {
            result = true;
        } // end of if statement

        // return the result
        return result;

    } // end of method readTerminate()


    /**
     * Places new order request in the database ( columns TradeAction , StopLoss and TakeProfit
     * from table ea_in will be modified ).
     *
     * @param type Shows if the order is long or short.
     * @param stopLoss Represents the stoploss value for the order. Providing null or non positive
     *                 number to this argument will generate error.
     * @param takeProfit Represents the takeprofit value for the order. Providing null or non positive
     *                   number to this argument will generate error.
     */
    public void placeNewOrder(OrderType type , BigDecimal stopLoss , BigDecimal takeProfit) {
        // declare local variables:
        Statement statement;            // object used for executing sql queries
        int tradeAction;                // integer representing the type of the order
        String updateQuery;             // sql query for placing the order data in the database
        // end of local variables declaration


        // check for null pointers
        if(stopLoss == null || takeProfit == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "placeNewOrder");

            // exit from method
            return;
        } // end of if statement

        // check for logical error
        if(stopLoss.compareTo(BigDecimal.ZERO) != 1 || takeProfit.compareTo(BigDecimal.ZERO) != 1) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT,  "placeNewOrder" );

            // exit from method
            return;
        } // end of if statement
        
        // resolve the tradeAction
        if(type == OrderType.LONG) {
            tradeAction = 1;
        } else {
            tradeAction = 2;
        } // end of if else statement

        // resolve the updateQuery
        updateQuery ="Update ea_in SET TradeAction = " + Integer.toString(tradeAction)+
                     " , StopLoss = " + stopLoss.toString() +
                     " , TakeProfit = " + takeProfit.toString();

        // try to place the order
        try {
            // create statement object
            statement = connection.createStatement ();

            // execute query to set the TradeAction
            statement.executeUpdate(updateQuery);

            // close the statement
            statement.close();

        } catch(SQLException e) {
            // error
            eHandler.newError(ErrorType.SQL_ERROR, "placeNewOrder");
        } // end of try block
    } // end of method placeNewOrder()
    
} // end of class DatabaseConnector
