/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;



// imports:
import error.*;
import financialdata.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import marketanalysis.*;
import simulationinterface.*;


/**
 * Used for testing the performace of financial strategies. Financial strategies are essentialy defined by
 * the AnalysisFormat class and thus the testing of financial strategies is testing of analysis formats.
 *
 * @author Alexandar Atanasov
 */
public class StrategyTester {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The ID used for user initiatd tests. */
    private static final int USER_INITIATED_TEST_ID = 1;

    /** The minimum number of bars in the arrayData  of the rawData member. */
    private static final int MINIMUM_RAW_DATA_SIZE = 900;

    /** The maximum number of elements in the lastTestResults vector member. */
    private static final int MAXIMUM_USER_TEST_RECORDS = 10;

    /** Shows the possible outcomes from initiating test. */
    public enum TestInitInfo {INIT_SUCCESFULL , FAIL_NOT_ENOUGH_DATA , FAIL_TEST_ALREADY_RUNNING};

    /** Shows if there is an active user initiated test. */
    private boolean activeUserTest;

    /** The number of digits after the dot in the price of the chosen market security ( instrument ). */
    private int digitsAfterDot;

    /** The name of the database to which this Trader Avatar Terminal is connected. */
    private String dbName;

    /** The market raw data with which tests will be performed. */
    private MarketRawData rawData;

    /** Instance of the SimmulationFrame class used for displayng test results to the user. */
    private SimulationResultsFrame mainFrame;

    /** The result of the last performed user initiated strategy test. */
    private StrategyTestResult lastTestResult;

    /** The results of the last performed user initiated strategy tests. */
    private Vector <StrategyTestResult> lastTestResults;

    /** Holds the currently active ( in process of execution ) tests. */
    private Vector <StrategyTest> activeTests;

    /** Instance of the SimulationProgressFrame class used for displaying simulation progress. */
    private SimulationProgressFrame progressFrame;

    // end of member variables declaration


    // initialization of static members:
    static {
        eHandler = new ErrorHandler("StrategyTester");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param data The market raw data with which tests will be performed. If null is provided to this argument
     *             error will be generated.
     * @param databaseName The name of the database to which this Trader Avatar Terminal is connected.
     *                     If null or empty string is provided to this argument error will be generated.
     * @param digits The number of digits after the dot in the price of the chosed market security ( instrument ).
     *               If negative number is provided to this argument error will be generated.
     */
    public StrategyTester(MarketRawData data , String databaseName , int digits) {
        // set the members
       setRawData(data);
       setDbName(databaseName);
       setDigitsAfterDot(digits);
       initActiveTests();
       initProgressFrame();
       lastTestResults = new Vector<StrategyTestResult>();

    } // end of constructor


    /** 
     * Initializes the progressFrame member.
     */
    private void initProgressFrame() {
        // initialize
        progressFrame = new SimulationProgressFrame(getDbName() );
    } // end of method initProgressFrame()


    /**
     * Sets the activeUserTest member.
     * 
     * @param userTest The boolean value to which the activeUserTest member will be set. Must be true if 
     *                 user initiated test is currently running and false otherwise.
     */
    private void setActiveUserTest(boolean userTest) {
        // set the activeUserTest
        activeUserTest = userTest;
    } // end of method setActiveUserTest()


    /**
     * Returns the activeUserTest member.
     *
     * @return True if there is  running user initiated test, false otherwise.
     */
    public boolean getActiveUserTest() {
        return activeUserTest;
    } // end of method getActiveUserTest()


    /**
     * Sets the digitsAfterDot member.
     *
     * @param digits The number of digits after the dot in the price of the chosed market security ( instrument ).
     *               If negative number is provided to this argument error will be generated.
     */
    private void setDigitsAfterDot(int digits) {
        // check negative number
        if (digits < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setDigitsAfterDot" , "Negative digits value");

            // set to default
            digitsAfterDot = 0;

            // exit
            return;
        } // end of if statement

        // set the digits
        digitsAfterDot = digits;

    } // end of method setDigitsAfterDot()


    /**
     * Returns the digitsAfterDot member.
     * 
     * @return Non negative integer representing the number of digits after the dot in the price of the chosen
     *         market security (financial instrument).
     */
    public int getDigitsAfterDot() {
        return digitsAfterDot;
    } // end of method getDigitsAfterDot()


    /**
     * Sets the dbName member.
     *
     * @param databaseName The name of the database to which this Trader Avatar Terminal is connected.
     *                     If null or empty string is provided to this argument error will be generated.
     */
    private void setDbName(String databaseName) {
        // check for null or empty string
        if(databaseName == null ) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setDbName");

            // set to error value
            dbName = "Unknown";

           // exit
           return;
        } // end of if statement

        if( databaseName.contentEquals("") ) {
             // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT , "setDbName" , "Empty database name");

            // set to error value
            dbName = "Unknown";

           // exit
           return;
        } // end of if statement

        // set the db name
        dbName = databaseName;

    } // end of method


    /**
     * Returns the dbName member.
     *
     * @return Non null non empty string representing the name of the database to which this
     *         Trader Avatar Terminal is connected.
     */
    public String getDbName() {
        return dbName;
    } // end of method


    /**
     * Sets the rawData member.
     *
     * @param data The market raw data with which tests will be performed. If null is provided to this argument
     *             error will be generated.
     */
    private void setRawData(MarketRawData data) {
        // check for null pointer
       if(data == null) {
           // error ..
           eHandler.newError(ErrorType.NULL_ARGUMENT , "setRawData");

           // exit
           return;
       } // end of if statement

       // set the data
       rawData = data;

    } // end of method


    /**
     * Returns the rawData member.
     *
     * @return The market raw data  which is used for testting.
     */
    public MarketRawData getRawData() {
        return rawData;
    } // end of method


    /**
     * Initializes the activeTests member.
     */
    private void initActiveTests() {
        // initialize the vector
        activeTests = new Vector <StrategyTest> ();
    } // end of method initActiveTests()


    /**
     * Adds new active test to the vector of active tests.
     *
     * @param newTest The new test to be added. If null is provided to this argument error will be generated.
     */
    private void addNewActiveTest(StrategyTest newTest) {
        // check for null pointer
        if(newTest == null) {
            // error ...
            eHandler.newError(ErrorType.NULL_ARGUMENT , "addNewActiveTest");

            // exit from method
            return;
        } // end of if statement

        // add the new test
        activeTests.add(newTest);

    } // end of method addNewActiveTest()


    /**
     * Returns the Ids of the strategy tests contained in the activeTests member.
     * 
     * @return Array of integers representing the ids of the strategy test contained in the activeTests vector.
     *         The ids position in the array is the same as their position in the vector. If the vector is empty
     *         this method will return null.
     */
    private int[] getActiveTestsIds() {
        // declare local variables:
        int result[] = null;
        // end of local variables declaration


        // check if the vector of active tests is empty
        if( activeTests.isEmpty() ) {
            // exit from method
            return result;
        } // end of if statement

        // initialize the result array
        result = new int[activeTests.size() ];

        // get the ids
        for(int c =0; c < result.length; c++) {
            result[c] = activeTests.elementAt(c).getTestId();
        } // end of for loop

        // return the result
        return result;
    } // end of method getActiveTestsIds()


    /**
     * Removes the test with the specified id from the vector of active tests. 
     * 
     * @param id The ID of the active test to be removed. If there is no test with ID matching the provided ID or
     *           the activeTests member is empty error will be generated
     */
    private void removeActiveTestById(int id) {
        // declare local variables:
        int activeTestsIds[];
        // end of local variables declaration


        // check if the activeTests member is empty
        if(activeTests.isEmpty() ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_METHOD_CALL, "removeActiveTestById", "There are no active tests");

            // exit from method
            return;

        } // end of if statement

        // get the ids of the active tests
        activeTestsIds = getActiveTestsIds();

        // main removing loop
        for(int c = 0; c < activeTestsIds.length; c++) {
            // check if the current ID in the array of IDs mathes the ID which should be removed
            if(activeTestsIds[c] == id) {
                // remove the element at "c" in the vector of active tests
                activeTests.removeElementAt(c);

                // exit from method
                return;
            } // end of if statement

        } // end of for loop

        // the specified id for was not found
        // error ..
        eHandler.newError(ErrorType.INVALID_ARGUMENT , "removeActiveTestById", "Non existant ID has been provided");

    } // end of method removeTestById()


    /**
     * Initializes the mainFrame member. If the mainFrame is already initialized (different than null)
     * the previous instance will be discarded. 
     * 
     * @param testResult The StrategyTestResult which will be displayed by the mainFrame. If null is 
     *                   provided to this argument error will be generated.
     */
    private void initMainFrame(StrategyTestResult testResult) {
        // check for null pointer
        if(testResult == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "initMainFrame");

            // exit from method
            return;
        } // end of if statement

        // close the mainFrame (if it exists)
        closeMainFrame();

        // initialize the mainFrame
        mainFrame = new SimulationResultsFrame( getDbName() , testResult , lastTestResults);

    } // end of method initMainFrame()


    /**
     * Closes the mainFrame window and sets the mainFrame member to null. If the main frame is already null
     * this method will exit.
     */
    private void closeMainFrame() {
        // check if the mainFrame exists ( it is differet than null)
        if(mainFrame == null) {
            // nothing to close
            return;
        } // end of if statement

        // close the mainFrame
        mainFrame.dispose();

        // set the mainFrame to null
        mainFrame = null;

    } // end of method


    /**
     * Starts new user initiated test.
     *
     * @param aFormat The AnalysisFormat which will be tested. If null is provided to this argument error
     *                will be generated.
     *
     * @return Instance of the TestInitInfo which shows if the test was initiated successfuly or initialization
     *         failed.
     */
    public TestInitInfo newUserInitiatedTest(AnalysisFormat aFormat) {
        // declare local variables
        TestInitInfo result = TestInitInfo.INIT_SUCCESFULL; // the result to be returned by the method
        // end of local variables declaration


        // check if the rawData is large enough for test
        if(rawData.getBars() < MINIMUM_RAW_DATA_SIZE ) {
            // not enough data
            result = TestInitInfo.FAIL_NOT_ENOUGH_DATA;

            // exit from method
            return result;
        } // end of if statement

        // check if there is already user initiated test which is running
        if(getActiveUserTest() ) {
            // cannot run 2 user tests at the same time
            result = TestInitInfo.FAIL_TEST_ALREADY_RUNNING;

            // return the result
            return result;
        } // end of if statement

        // initiated the test
        this.startNewTest(aFormat, USER_INITIATED_TEST_ID , true);

        // set the activeUserTest to true
        setActiveUserTest(true);

        // return the result
        return result;

    } // end of method newUserInitiatedTest()


    
    /**
     * Starts new strategy test.
     * 
     * @param aFormat The format which will be tested. Providing null to this argument will generate error.
     * @param testId The id of the test.
     */
    private void startNewTest(AnalysisFormat aFormat , int testId , boolean showProgress) {
        // declare local variables:
        StrategyTest newTest;   // the new test which will be started
        // end of local variables declaration


        // check for null pointer
        if(aFormat == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "startNewTest" );

            // exit from method
            return;
        } // end of if statement

        // create the test
        if(showProgress == true) {
            newTest = new StrategyTest(getDigitsAfterDot() , testId , this , aFormat , getRawData() ,
                                       progressFrame);
        } else {
            newTest = new StrategyTest(getDigitsAfterDot() , testId , this , aFormat , getRawData() ,
                                       null);
        } // end of if else statement

        // start the test
        newTest.start();

        // load the test in the vector of active tests
        addNewActiveTest(newTest);

    } // end of method startNewTest()


    /**
     * Used for receiving the result of strategy test.
     *
     * @param testId The ID of the completed strategy test.
     * @param result The actual result of the completed strategyTest If null is provided to this argument
     *               error will be generated.
     */
    public void receiveTestResult(int testId , StrategyTestResult result) {
        // check for null pointer
        if(result == null) {
            // error ..
            eHandler.newError(ErrorType.SQL_ERROR, "receiveTestResult");

            // exit from method
            return;
        } // end of if statement

        // check if the test was initiated by the user
        if(testId == USER_INITIATED_TEST_ID ) {
            // store the result
            addLastUserTestResult(result);
            
            // display the results to the user
            initMainFrame(result);

            // remove the test from the active tests vector
            removeActiveTestById(testId);

            // set the activeUserTest to false
            setActiveUserTest(false);

            // exit from method
            return;

        } // end of if statement
        
    } // end of method receiveTestResult()


    /**
     * Sets the lastTestResult member and adds the provided result in the lastTestResults vector.
     *
     * @param result The last user initiated test result.
     */
    private void addLastUserTestResult(StrategyTestResult result) {
        // set the lastTestResult
        lastTestResult = result;

        // check if the vector is full
        if(lastTestResults.size() == MAXIMUM_USER_TEST_RECORDS) {
            // discard the first test
            lastTestResults.remove(0);
        } // end of if statement

        // add the result to the vector of results
        lastTestResults.add(result);

    } // end of method


} // end of class StrategyTester
