/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package traderavatar;


// imports:
import commands.CommandsHandler;
import databases.DatabaseConnector;
import error.*;
import financialdata.*;
import java.math.BigDecimal;
import marketanalysis.*;
import marketanalysis.AnalysisResult;
import simulation.*;
import simulation.StrategyTester.TestInitInfo;
import terminalinterface.*;



/**
 *
 * @author Alexandar Atanasov
 */
public class TraderEngine extends Thread{
    // declare member variables:
    boolean frozen;
    boolean canModify;
    boolean inErrorState;
    boolean tradeOn;

    private ChartsPanel chartsPanel;
    private LogsPanel logsPanel;
    private StatusPanel statusPanel;
    private InputOutputPanel inputOutput;

    private ErrorHandler eHandler;

    private CommandsHandler commandsHandler;

    private DatabaseConnector dbConnector;

    private StrategyTester tester;

    private MarketRawData marketRawData;
    private MarketAnalyzer marketAnalyzer;
    // end of member variables declaration


    // constructor
    public TraderEngine(ChartsPanel chartWindow , LogsPanel logsWindow , StatusPanel statusWindow ,
                        CommandsHandler comHandler , InputOutputPanel inOutWindow, DatabaseConnector connector,
                        String digitsAfterDot , String maximumBars , String dbName) {

        // declare local variables:
        int maxBars;
        // end of local variables declaration


        // set the error state to false
        inErrorState = false;

        // set the frozen member to false
        setFrozen(false);
        
        // set the tradeOn to false
        tradeOn = false;

        // set the canModify to false
        setCanModify(false);

        // initialize the eHandler member
        eHandler = new ErrorHandler("ProgramRuler");

        // convert the maximumBars to int
        maxBars = Integer.parseInt(maximumBars);

        // check for null pointers
        if(connector == null || chartWindow == null ||  logsWindow==null || statusWindow == null ||
           comHandler == null) {

            // error handling ..
            inErrorState = true;
            eHandler.newError(ErrorType.NULL_ARGUMENT , "Constructor");

            // exit from constructor
            return;

        } // end of if statement

        // load the member components
        chartsPanel = chartWindow;
        logsPanel = logsWindow;
        statusPanel = statusWindow;
        inputOutput = inOutWindow;
        commandsHandler = comHandler;
        dbConnector = connector;

        // set the status to show that  data from database is being initialized
        statusPanel.setStatus(StatusPanel.ProgramStatus.INIT_DB_DATA);

        // initialize the MarketRawData member
        marketRawData = new MarketRawData(dbConnector.getArrayData() , dbConnector.getNonArrayData() ,maxBars ,
                                          Integer.parseInt(digitsAfterDot) );

        // set the DontUpdate value in the database to 0 since all data was extracted from the database
        dbConnector.nullifyDontUpdate();

        // initialize the marketAnalyzer member ( should be done after market rawData is initialize ! )
        marketAnalyzer = new MarketAnalyzer(marketRawData , new AnalysisFormat() , 
                                            Integer.parseInt(digitsAfterDot) );

        // initialize the tester member
        tester = new StrategyTester(marketRawData , dbName,
                                    Integer.parseInt(digitsAfterDot) );

    } // end of constructor


    // this method must be called after the constructor and before the start method !
    public void init() {
        // initialize the command handler
        commandsHandler.init(inputOutput, this);

        // set the canModify to true
        setCanModify(true);
    } // end of method init()


    // sets the canModify member
    private void setCanModify(boolean can) {
        canModify = can;
    } // end of method setCanModify()


    // returns the canModify member
    public boolean isSafeToModify() {
        return canModify;
    } // end of method isSafeToModify()


    // sets the frozen member
    public void setFrozen( boolean freeze) {
        // check if it is safe to modify
        if(canModify) {
            frozen = freeze;
        } // end of if statement

    } // end of method setFrozen


    // returns the frozen member
    public boolean isFrozen() {
        return frozen;
    } // end of method isFrozen()


    // returns the tradeOn member
    public boolean getTradeOn() {
        return tradeOn;
    } // end of method


    // returns the currently used analysis format
    public AnalysisFormat getAnalysisFormat() {
        return marketAnalyzer.getAnalysisFormat();
    } // end of method ()


    // the main function of the class
    public void run() {
        // declare local variables:
        // end of local variables declaration


        // main thread loop; will continue until application is terminated
        while(true) {

            // 
            performEngineLogic();

            // sleep for some time
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                // error ...
            } // end of try block

        } // end of main while loop

    } // end of method run


    // the run method has a while loop; this method is called inside that loop
    private void performEngineLogic() {
            // thread safety on
            setCanModify(false);

            // check if the terminal is frozen
            if(frozen) {
                // update the status window
                updateStatusWindow();

                // thread safety off
                setCanModify(true);
                
                // exit from method since the terminal is frozen
                return;
            } // end of if statement
            
            // check if the terminal should be closed
            checkForTerminate();

            // update the financial data; this operation must be performed before all other financial related
            // operations
            updateFinancialData();

            // update the chartsPanel
            updateChartsPanel();

            // analyze the current market conditions and request new performEngineLogic if necessary
            analyzeAndTrade();

            // update the error state
            inErrorState = eHandler.getInErrorState();

            // update the status window
            updateStatusWindow();

            // thread safety off
            setCanModify(true);
    } // end of method performEngineLogic()


    // this method will update the status window
    private void updateStatusWindow() {

        // update the terminal status
        if(frozen) {
            statusPanel.setStatus(StatusPanel.ProgramStatus.FROZEN);
        } else {
            // check for error state
            if(inErrorState) {
                statusPanel.setStatus(StatusPanel.ProgramStatus.IN_ERROR_STATE);
            } else {
                statusPanel.setStatus(StatusPanel.ProgramStatus.OK);
            } // end of if else statement

        } // end of if else statement


        // update the performEngineLogic window
        statusPanel.setTrading(tradeOn);

        // update the bars
        statusPanel.setBars(Integer.toString( marketRawData.getBars() ) );

        // update the buy price
        statusPanel.setBuy(marketRawData.getBuyPrice().toString() );

        // update the sell price
        statusPanel.setSell(marketRawData.getSellPrice().toString() );

    } // end of method updateStatusWindow()


    // this method updates the financial data
    private void updateFinancialData() {
        // declare local variables:
        ArrayDataRow updatedArrayData[];
        // end of local variables declaration


        // check if there is updated array data ( new rows )
        if(dbConnector.readUpdatedArrayData() > 0) {
            // get the updated array data
            
            updatedArrayData = dbConnector.getUpdatedArrayData();

            // update the array data in the marketRawData
            marketRawData.updateArrayData(updatedArrayData );

            // make new entry to the logs
            logsPanel.addNewEntry(LogsPanel.LogEntryType.ARRAY_DATA_UPDATE, " " +
                                  updatedArrayData.length + " new row(s)"
                                  /*+ "; Last row is: "
                                  + "Close( " + updatedArrayData[0].getClosePrice() +" )"
                                  + " , High( " + updatedArrayData[0].getHighPrice() + " )"
                                  + " , Low( " + updatedArrayData[0].getLowPrice() + " )"
                                  + " , Volume( " + updatedArrayData[0].getVolume() + " )" */  );
          
        } // end of if statement

        // update the non array data in marketRawData
        marketRawData.updateNonArrayData(dbConnector.getNonArrayData());
        
    } // end of method updateFinancialData()


    // this method updates the chartsPanel
    private void updateChartsPanel() {
        chartsPanel.updateChartData(marketRawData.getArrayData() );
    } // end of method updateChartsPanel()


    // this method sets the tradeOn member
    public void setTradeOn(boolean tradeAllowed) {
        // thread safety check
        if(!canModify) {
            // exit from function
            return;
        } // end of if statement

        // set the tradeOn
        tradeOn = tradeAllowed;

    } // end of method setTradeOn


    // this method checks if the java Terminal should be closed
    private void checkForTerminate() {
        if(dbConnector.readTerminate() ) {
            // close the terminal
            System.exit(0);
        } //end of if statement

    } // end of method checkForTerminate()


    // this method performs analysis of the current market conditions;
    // if the conditions are good for opening new position the method will request new performEngineLogic
    // by placining information  in the ea_in table of the database;
    // if the inErrorState member is set to true or the tradeOn member is set to false
    // no analysis is performed
    private void analyzeAndTrade() {
        // declare local variables:
        AnalysisResult  analysisResult;
        // end of local variables declaration


        // check if the method should exit
        if(inErrorState == true || tradeOn == false) {
            // exit from method
            return;
        } // end of if statement

        // analyze the current market conditions
        analysisResult = marketAnalyzer.analyzeMarket(marketRawData);

        // check if new position should be opened
        if(analysisResult.getTradeAction() != TradeAction.NO_ACTION ) {
            // find if the position is long or short
            if(analysisResult.getTradeAction() == TradeAction.OPEN_LONG_POS) {
                // request the opening of long position
                dbConnector.placeNewOrder(DatabaseConnector.OrderType.LONG,
                                          analysisResult.getStopLoss(), analysisResult.getTakeProfit() );
                
                // display entry in the logs
                logsPanel.addNewEntry(LogsPanel.LogEntryType.NEW_ORDER, " Long position");
            } else {
                // request the opening of short position
                dbConnector.placeNewOrder(DatabaseConnector.OrderType.SHORT,
                                          analysisResult.getStopLoss(), analysisResult.getTakeProfit() );

                // display entry in the logs
                logsPanel.addNewEntry(LogsPanel.LogEntryType.NEW_ORDER, " Short position");
            } // end of if else statement

        } // end of if statement

    } // end of method analyzeAndTrade()


    // this method will call the newUserInitiatedTest method of the tester member ( this will test the
    // currently used analysisFormat );
    public TestInitInfo testAnalysisFormat() {
        return this.testAnalysisFormat(this.marketAnalyzer.getAnalysisFormat() );

    } // end of method testAnalysisFormat()


     // this method will call the newUserInitiatedTest method of the tester member.
    public TestInitInfo testAnalysisFormat(AnalysisFormat aFormat) {
        // declare local variables:
        TestInitInfo result=null;
        // end of local variables declaration


        // check if it is safe to modify
        if(canModify) {
            result = tester.newUserInitiatedTest( aFormat );
        } // end of if statement

        // return the result
        return result;
        
    } // end of method testAnalysisFormat()


    // requests the opening of new performEngineLogic position
    public void requestNewPosition(DatabaseConnector.OrderType orderType , BigDecimal stopLoss,
                                   BigDecimal takeProfit) {
        // request the order
        dbConnector.placeNewOrder(orderType, stopLoss, takeProfit);

    } // end of method requestNewPosition()


    // requests the opening of new performEngineLogic position
    public void requestNewPosition(DatabaseConnector.OrderType orderType ) {
        // declare local variables:
        BigDecimal stopLevelScale = BigDecimal.ONE;
        BigDecimal stopLevel;
        BigDecimal stopLoss;
        BigDecimal takeProfit;
        // end of local variables declaration


        // resolve the stopLevelScale of the stopLevel
        for (int c = 0; c < dbConnector.getDigitsAfterDot(); c++) {
            stopLevelScale = stopLevelScale.multiply( BigDecimal.TEN );
        } // end of for loop

        // resolve the stopLevel variable
        stopLevel = new BigDecimal( Integer.toString(marketRawData.getNonArrayData().getStopLevel() ) );
        stopLevel = stopLevel.add(new BigDecimal("2") );
        stopLevel = stopLevel.divide(stopLevelScale);

        // check the type of the order
        if( orderType == DatabaseConnector.OrderType.LONG) {
            // Long position:

            // resolve the stopLoss and the takeProfit variables
            stopLoss = marketRawData.getBuyPrice();
            stopLoss = stopLoss.subtract( stopLevel);
            takeProfit = marketRawData.getBuyPrice();
            takeProfit = takeProfit.add( stopLevel );

            // request the order
            requestNewPosition(orderType, stopLoss, takeProfit );

            // add entry to the logs
            logsPanel.addNewEntry(LogsPanel.LogEntryType.NEW_ORDER, "Long position");

        } else {
            // Short position:
            
            // resolve the stopLoss and the takeProfit variables
            stopLoss = marketRawData.getSellPrice();
            stopLoss = stopLoss.add( stopLevel );
            takeProfit = marketRawData.getSellPrice();
            takeProfit = takeProfit.subtract( stopLevel );
            
            // request the order
            requestNewPosition(orderType, stopLoss, takeProfit );

            logsPanel.addNewEntry(LogsPanel.LogEntryType.NEW_ORDER, "Short position");
            
        } // end of if else statement


    } // end of method requestNewPosition()


    // loads the specified AnalysisFormat in the marketAnalyzer
    public void useAnalysisFormat(AnalysisFormat aFormat) {
        // check for null pointer
        if(aFormat == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "useAnalysisFormat");

            // exit from method
            return;
        } // end of if statement

        // load the format
        marketAnalyzer.setAnalysisFormat(aFormat);

    } // end of method useAnalysisFormat()


} // end of class TraderEngine
