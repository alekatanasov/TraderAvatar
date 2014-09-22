/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;


// imports:
import error.*;
import financialdata.ArrayDataRow;
import financialdata.MarketRawData;
import financialdata.NonArrayData;
import java.math.BigDecimal;
import marketanalysis.AnalysisFormat;
import marketanalysis.AnalysisResult;
import marketanalysis.MarketAnalyzer;
import marketanalysis.StandingOrder;
import marketanalysis.TradeAction;
import simulationinterface.SimulationProgressFrame;


/**
 * Performs test of the specified strategy ( AnalysisFormat ).
 *
 * @author Alexandar Atanasov
 */
public class StrategyTest extends Thread{
    // declare member variables:

    /** The default size of the dynamicDataSize member. */
    public static final int DYNAMIC_DATA_DEFAULT_SEZE = 600;

    /** The minimum size of the dynamicDataSize member. */
    public static final int DYNAMIC_DATA_MINIMUM_SIZE = 10;

    /** The ID number of this strategy test*/
    private int testId;

    /** Instanceo of the ErrorHandler used for managing errors.*/
    private static ErrorHandler eHandler;

    /** The number of digits after the dot in the price of the chosen market security ( instrument ).*/
    private int digitsAfterDot;

    /** The lenght of the dynamic raw data used for simulations. */
    private int dynamicDataSize;

    /** Reference to the  StrategyTester who created this test. */
    private StrategyTester owner;

    /** The market analyzer which will be used for analyzing the simulated market conditions. */
    private MarketAnalyzer analyzer;

    /** The AnalysisFormat to be tested. */
    private AnalysisFormat analysisFormat;

    /** The MarketRawData by which the analysisFormat will be tested. */
    private MarketRawData rawData;

    /** Instance of the SimulationProgressFrame class used for displaying simulation progress. */
    private SimulationProgressFrame progressFrame;

    // end of member variables declaration


    // static members initialization:
    static {
        eHandler = new ErrorHandler("StrategyTest");
    } // end of static member initialization


    /**
     * Constructor.
     *
     * @param digits The number of digits after the dot in the price of the chosed market security ( instrument ).
     *               If negative number is provided to this argument error will be generated.
     * @param ownerTester Reference to the  StrategyTester who created this test. If null is provided to this
     *                    argument error will be generated.
     * @param aFormat The analysis format used for testing. If null is provided to this argument error will be
     *                    generated.
     * @param marketRawData The market raw data with which tests will be performed. If null is provided to this argument
     *                      error will be generated.
     * @param id Signed integer representing the ID of this strategy test.
     * @param frame If different than null ,the provided progress frame will be used to display the test progress.
     */
    public StrategyTest(int digits , int id, StrategyTester ownerTester , AnalysisFormat aFormat ,
                        MarketRawData marketRawData , SimulationProgressFrame frame) {
        // set the members
        setDynamicDataSize(DYNAMIC_DATA_DEFAULT_SEZE);
        setDigitsAfterDot(digits);
        setOwner(ownerTester);
        setAnalysisFormat(aFormat);
        setRawData(marketRawData);
        setTestId(id);
        progressFrame = frame;

    } // end of Constructor


    /**
     * Sets the testId member.
     * 
     * @param identification Signed integer representing the ID of this strategy test.
     */
    private void setTestId(int identification) {
        // set the testId
        testId = identification;
    } //end of method setId()


    /**
     * Returns the testId member.
     *
     * @return Signed integer representing the ID of this strategy test.
     */
    public int getTestId() {
        return testId;
    } // end of method getTestId


    /**
     * Sets the owner member.
     * 
     * @param tester Reference to the  StrategyTester who created this test. If null is provided to this
     *               argument error will be generated.
     */
    private void setOwner(StrategyTester tester) {
        // check for null pointer
        if(tester == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "setStrategyTester");

            // exit from method
            return;
        } // end of if statement

        // set the owner
        owner = tester;

    } // end of method setOwner()


    /**
     * Returns the owner member.
     *
     * @return Reference to the  StrategyTester who created this test.
     */
    private StrategyTester getOwner() {
        return owner;
    } // end of method getOwner()


    /**
     * Sets the dynamicDataSize member.
     * 
     * @param size The lenght of the dynamic raw data used for simulations. If number lesser than 10 is provided
     *             to this argument error will be generated.
     */
    private void setDynamicDataSize(int size) {
        // check for logical error
        if(size < 10) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setDynamicDataSize", "Too small size");

            // set to minimum value
            dynamicDataSize = DYNAMIC_DATA_MINIMUM_SIZE;

            // exit from method
            return;

        } // end of if statement

        // set the dynamicDataSize
        dynamicDataSize = size;

    } // end of method setDynamicDataSize()


    /**
     * Returns the dynamicDataSize member.
     *
     * @return Positive integer repreenting teh size of the dynamic raw data used in simulations.
     */
    private int getDynamicDataSize() {
        return dynamicDataSize;
    } // end of method  getDynamicDataSize()


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
     * @return Positive integer representing the number of digits after the dot in the price of
     *         the chosen market security ( instrument ).
     */
    private int getDigitsAfterDot() {
        return digitsAfterDot;
    } // end of method getDigitsAfterDot()


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

    } // end of method setRawData()


    /**
     * Returns the rawData member.
     *
     * @return The market raw data  which is used for testting.
     */
    public MarketRawData getRawData() {
        return rawData;
    } // end of method getRawData()


    /**
     * Sets the analysisFormat member.
     *
     * @param analysisFormat The analysis format used for testing. If null is provided to this argument error will be
     *                       generated.
     */
    private void setAnalysisFormat(AnalysisFormat aFormat) {
        // check for null pointer
        if(aFormat == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setAnalysisFormat" );

            // exit
            return;
        } // end of if statement

        // set the format
        analysisFormat = new AnalysisFormat(aFormat);

    } // end of method setAnalysisFormat()


    /**
     * Returns the analysisFormat member
     *
     * @return The analysis format used for testing.
     */
    public AnalysisFormat getAnalysisFormat() {
        return analysisFormat;
    } // end of method getAnalysisFormat


    /**
     * Constructs and returns the dynamic raw data which will be used for testing.
     * 
     * @return Instance of the MarketRawData class. If some error occures the method will return null.
     */
    private MarketRawData constructDynamicData() {
        // declare local variables:
        MarketRawData result=null;      // the result to be returned by this method
        ArrayDataRow arrayData[];       // the arrayData of the rawData member
        ArrayDataRow protoArrayData[];  //
        NonArrayData nonArrayData;      // the nonArrayData of the rawData member
        // end of local variables declaration


        // check for error
        if(getRawData() == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_CLASS_MEMBER, "constructDynamicData" );

            // exit from method
            return result;
        } // end of if statement

        // get the arrayData
        arrayData = rawData.getArrayData();

        // construct the protoArrayData
        protoArrayData = new ArrayDataRow[600];
        System.arraycopy(arrayData, arrayData.length-getDynamicDataSize() -1 , protoArrayData, 0, getDynamicDataSize() );

        // construct the nonArrayData
        nonArrayData = new NonArrayData(protoArrayData[0].getClosePrice() ,  protoArrayData[0].getClosePrice() ,
                                        getDynamicDataSize() , rawData.getNonArrayData().getStopLevel() );

        // construct the dynamic data
        result = new MarketRawData(protoArrayData , nonArrayData , getDynamicDataSize() , getDigitsAfterDot() );

        // return the result
        return result;

    } // end of method constructDynamicData()


    /**
     * Initializes the analyzer member. This method should not be used in the constructor!
     *
     * @param dynamicData Instance of the MarketRawData which will be used by the analyzer.
     *                    If null is provided to this argument error will be generated.
     */
    private void initAnalyzer(MarketRawData dynamicData) {
        // check null pointer
        if(dynamicData == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT , "initAnalyzer" );

            // exit from method
            return;
        } // end of if statement

        // check for null member
        if( getAnalysisFormat() == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_CLASS_MEMBER , "initAnalyzer" );

            // exit from method
            return;
        } // end of if statement

        // initialize the analyzer
        analyzer = new MarketAnalyzer(dynamicData , getAnalysisFormat() , getDigitsAfterDot() );

    } // end of method initAnalyzer()


    /**
     * Strategy testing is performed in this method.
     */
    @Override
    public void run() {
        // declare local variables:
        MarketRawData dynamicData;     // Instance of the MarketRawData which will be used for simulating changing market conditions
        StrategyTestResult testResult; // the result of the test to be performed
        // end of local variables declaration

        // initialize the dynamicData
        dynamicData = constructDynamicData();

        // initialize the analyzer member
        initAnalyzer(dynamicData);

        // perform testting
        testResult = performTest(dynamicData);

        // return the result of the testting to the owner
        returnResultToOwner(testResult);

    } // end of method run()


    /**
     * Perform finacial strategy test.
     *
     * @param dynamicRawData Instance of the MarketRawData used for simulating changing market
     *                       conditions. If null is provided to this argument error will be generated.
     *
     * @return The result of the performed test. If some error occures the method will return null.
     */
    private StrategyTestResult performTest(MarketRawData dynamicRawData) {
        // declare local variables:
        StrategyTestResult result=null;      // the result to be returned by this method
        StandingOrder lastClosedStandingOrder; //

        int onePercentProgress;              //
        int testedBars=0;                    // the number of tested bars
        int testedTicks = 0;                 // the number of tested ticks
        int pipsBalance = 0;                 // the total pips balance produced by the test
        int pipsWon = 0;                     // the number of won pips
        int openedPositions = 0;             // the number of opened postions
        int openedLongPositions = 0;         // the number of long opened positions
        int positionsWon = 0;                // the number of won postions
        int longPositionsWon = 0;            // the number of won long position
        int positionsLost = 0;               // the number of lost positions
        int longPositionsLost = 0;           // the number of lost long positions

        ArrayDataRow arrayTotalData[];          // the arrayData  of the rawData member
        NonArrayData nonArrayData;              //
        ArrayDataRow updateRow[];               // ArrayDataRow buffer used for updating the dynamic array data
        BigDecimal buyPrice;                    // buy price buffer
        BigDecimal spread;
        BigDecimal buffer;                      // buffer used for temporary storage
        AnalysisResult analysisResult;          // the AnalysisResult generated by MarketAnalyzer class
        // end of local variables declaration


        // check for null pointer
        if(dynamicRawData == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "performTest");

            // exit from method
            return result;
        } // end of if statement

        // check for null member
        if(analyzer == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_CLASS_MEMBER, "performTest" );

            // exit from method
            return result;
        } // end of if statement

        // display test progress if the progress frame exists
        if(progressFrame != null) {
            progressFrame.resetAndDisplay();
        } // end of if statement

        // get the arrayTotalData
        arrayTotalData = rawData.getArrayData();

        // initialize the updateRow
        updateRow = new ArrayDataRow[1];

        // get the spread
        spread = rawData.getNonArrayData().getSpread();

        // calculate the onePercentProgress
        onePercentProgress = (rawData.getBars() - dynamicRawData.getBars() ) / 100 ;

        // main testting loop
        for(int c = arrayTotalData.length - getDynamicDataSize() - 1; c >= 0; c--) {
            // update the dynaminRawData
            updateRow[0] = new ArrayDataRow(arrayTotalData[c] );
            buyPrice =  updateRow[0].getClosePrice().add(spread);
            nonArrayData = new NonArrayData(buyPrice , updateRow[0].getClosePrice() ,
                                            getDynamicDataSize() , rawData.getNonArrayData().getStopLevel() );
            dynamicRawData.updateArrayData(updateRow);
            dynamicRawData.updateNonArrayData(nonArrayData);

            // analyze the current market conditions
            analysisResult = analyzer.analyzeMarket(dynamicRawData);

            // check if the marketAnalyzer produced analysis result which is standing order
            if(analyzer.getNewStandingOrder() ) {
                // store the standing order

                // new position has been opened
                openedPositions++;

                // check if the new position is short or long
                if(analyzer.getLastStandingOrder().getTradeAction() == TradeAction.OPEN_LONG_POS) {
                    // new long position has been opened
                    openedLongPositions++;
                } // end of if statement

            } // end of if statement

            // get the lastClosedStandingOrder
            lastClosedStandingOrder = analyzer.getLastClosedStandingOrder();

            // check if standing order was closed
            if(lastClosedStandingOrder != null) {
                // get the financial result from the closed position
                buffer = analyzer.getLastFinancialResult();
                buffer = buffer.scaleByPowerOfTen(getDigitsAfterDot() );
                pipsBalance += Integer.parseInt(buffer.toString() );

                // check if the position was won
                if(buffer.compareTo(BigDecimal.ZERO) > 0 ) {
                    // position was won
                    positionsWon++;

                    // pips have been won
                    pipsWon += Integer.parseInt(buffer.toString() );

                    // determine the type of the closed standing order
                    if(lastClosedStandingOrder.getTradeAction() == TradeAction.OPEN_LONG_POS ) {
                        // long position was won
                        longPositionsWon++;
                    } // end of if statement
                } else {
                    // check if the position was lost
                    if(buffer.compareTo(BigDecimal.ZERO) < 0 ) {
                        // position was lost
                        positionsLost++;

                        // check if the position was long
                        if(lastClosedStandingOrder.getTradeAction() == TradeAction.OPEN_LONG_POS ) {
                            // long position lost
                            longPositionsLost++;
                        } // end of if statement

                    } // end of if statement

                } // end of if else statement

            } // end of if statement

            //
            buyPrice =  updateRow[0].getHighPrice().add(spread);
            nonArrayData = new NonArrayData(buyPrice , updateRow[0].getHighPrice() ,
                                            getDynamicDataSize() , rawData.getNonArrayData().getStopLevel() );
            analyzer.updateNonArrayData(nonArrayData);

            //
            buyPrice =  updateRow[0].getLowPrice().add(spread);
            nonArrayData = new NonArrayData(buyPrice , updateRow[0].getLowPrice() ,
                                            getDynamicDataSize() , rawData.getNonArrayData().getStopLevel() );
            analyzer.updateNonArrayData(nonArrayData);   

            // new tick has been tested
            testedTicks++;
            testedTicks++;
            testedTicks++;

            // new bar has been tested
            testedBars++;

            // show test progress
            if(progressFrame != null && c % onePercentProgress == 0) {
                //
                progressFrame.addOnePercentProgress();
                
            } // end of if statement

        } // end of for loop

        // hide the progress frame if it exists
        if(progressFrame != null) {
            progressFrame.setVisible(false);
        } // end of if statement

        // construct the result
        result = new StrategyTestResult(getAnalysisFormat() , testedTicks , testedBars , pipsBalance , pipsWon ,
                                        openedPositions , openedLongPositions , positionsWon , longPositionsWon ,
                                        positionsLost , longPositionsLost );

        // return the result
        return result;

    } // end of method performTest()


    /**
     * Returns the result of the testing to the StrategyTester who initiated the test.
     *
     * @param result The result to be returned.
     */
    private void returnResultToOwner(StrategyTestResult result) {
        // check for null pointer
        if(owner == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_CLASS_MEMBER, "returnResultToOwner");

            // exit from method
            return;
        } // end of if statement

        // return the result
        owner.receiveTestResult(getTestId() , result);

    } // end of method returnResultToOwner()

} // end of class StrategyTest
