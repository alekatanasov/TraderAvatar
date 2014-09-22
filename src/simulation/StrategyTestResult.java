/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulation;



// imports:
import error.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import marketanalysis.AnalysisFormat;



/**
 * The result of a strategy test.
 *
 * @author Alexandar Atanasov
 */
public class StrategyTestResult {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The number of simulated ticks. */
    private int simulatedTicks;

    /** The number of simulated bars. */
    private int simulatedBars;

    /** The total pips balance. */
    private int pipsBalance;

    /** The number of won pips. */
    private int pipsWon;

    /** The number of lost pips. */
    private int pipsLost;

    /** The number of opened positions. */
    private int openedPositions;

    /** The number of opened long positions. */
    private int openedLongPositions;

    /** The number of opened short positions. */
    private int openedShortPositions;

    /** The number of positions won. */
    private int positionsWon;

    /** The number of long positions won. */
    private int longPositionsWon;

    /** The number of short positions won. */
    private int shortPositionsWon;

    /** The number of lost positions. */
    private int positionsLost;

    /** The number of lost long positions. */
    private int longPositionsLost;

    /** The number of lost short positions*/
    private int shortPositionsLost;

    /** The AnalysisFormat which was tested. */
    private AnalysisFormat analysisFormat;

    /** The time at which this result was created. */
    private String timeOfCreation;

    // end of member variables declaration


    // initialization of static members
    static {
        eHandler = new ErrorHandler("StrategyTestResult");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param aFormat
     * @param ticks
     * @param bars
     * @param pipsBalance The total pips balance.
     * @param wonPips The number of won pips. If negative number is provided to this argument error will be
     *                generated.
     * @param positionsOpened
     * @param longPositionsOpened
     * @param positionsWon
     * @param longPositionsWon
     * @param positionsLost
     * @param longPositionsLost
     */
    public StrategyTestResult(AnalysisFormat aFormat,
                              int ticks, int bars,
                              int pipsBalance, int wonPips,
                              int positionsOpened, int longPositionsOpened,
                              int positionsWon, int longPositionsWon,
                              int positionsLost, int longPositionsLost) {

        // set the members
        initTimeOfCreation();
        setAnalysisFormat(aFormat);
        setTicksAndBars(ticks , bars);
        setPips(pipsBalance , wonPips);
        setOpened(positionsOpened , longPositionsOpened);
        setWon(positionsWon , longPositionsWon);
        setLost(positionsLost , longPositionsLost);

    } // end of constructor


    /**
     * Copy constructor.
     * 
     * @param testResult Another instance of the StrategyTestResult class.
     */
    public StrategyTestResult(StrategyTestResult testResult) {
        this(testResult.getAnalysisFormat(), testResult.getSimulatedTicks() , testResult.getSimulatedBars() ,
             testResult.getPipsBalance() , testResult.getPipsWon() , testResult.getOpenedPositions() ,
             testResult.getOpenedLongPositions() ,testResult.getPositionsWon() , testResult.getLongPositionsWon() ,
             testResult.getPositionsLost() , testResult.getLongPositionsLost() );
    } // end of copy constructor


    /**
     * Sets the pipsBalance member , the pipsWon member and the pipsLost member.
     *
     * @param total The total pips balance.
     * @param won The number of won pips. If negative number is provided to this argument error will be
     *            generated.
     */
    private void setPips(int total , int won ) {
        // check for logical error
        if( won < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setPips" , "Negative number of won pips");

            // set to default values
            pipsBalance = 0;
            pipsWon = 0;
            pipsLost = 0;

            // exit from method
            return;
        } //end of if statement

        // set the pips
        pipsBalance = total;
        pipsWon = won;
        pipsLost = won - total;

    } // end of method setPips()


    /**
     * Sets the members openedPositions , openedLongPositions and openedShortPositions.
     *
     * @param totalOpened The number of opened positions. If negative number is provided to this argument
     *                    error will be generated.
     * @param longOpened The number of opened long positions. If negative number is provided to this argument
     *                   error will be generated.
     */
    private void setOpened(int totalOpened , int longOpened) {

        // check for logical error
        if(totalOpened < 0 || longOpened < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setOpened" , "Negative argument");

            // set default values
            openedPositions = 0;
            openedLongPositions = 0;
            openedShortPositions = 0;

            // exit
            return;
        } // end of if statement

        // set the members:
        openedPositions = totalOpened;
        openedLongPositions = longOpened;
        openedShortPositions = totalOpened - longOpened;

    } // end of method setOpened()


    /**
     * Sets the members positionsWon , longPositionsWon and shortPositionWon.
     *
     * @param totalWon The number of won positions. If negative number is provided to this argument error
     *                 will be generated.
     * @param longWon The number of won long positions. If negative number is provided to this argument error
     *                will be generated.
     */
    private void setWon(int totalWon ,int  longWon) {
        // check for logical error
        if(totalWon < 0 || longWon < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setWon" , "Negative argument");

            // set default values:
            positionsWon = 0;
            longPositionsWon = 0;
            shortPositionsWon = 0;

        } // end of if statement

        // set the members
        positionsWon = totalWon;
        longPositionsWon = longWon;
        shortPositionsWon = totalWon-longWon;

    } // end of method setWon()


    /**
     * Sets the members positionsLost , longPositionsLost and shortPositionLost.
     *
     * @param totalLost The number of lost positions. If negative number is provided to this argument error
     *                  will be generated.
     * @param longLost The number of lost long positions. If negative number is provided to this argument error
     *                 error will be generated.
     */
    private void setLost(int totalLost ,int  longLost) {
        // check for logical error
        if(totalLost < 0 || longLost < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setWon" , "Negative argument");

            // set default values:
            positionsLost = 0;
            longPositionsLost = 0;
            shortPositionsLost = 0;

        } // end of if statement

        // set the members
        positionsLost = totalLost;
        longPositionsLost = longLost;
        shortPositionsLost = totalLost - longLost;

    } // end of method setWon()


    /**
     * Sets the analysisFormat member.
     *
     * @param aFormat The AnalysisFormat which will be tested. If null is provided to this argument
     *                error will be generated.
     */
    private void setAnalysisFormat(AnalysisFormat aFormat ) {
        // check for null pointer
        if(aFormat == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setIndicators");

            // exit
            return;
        } // end of if statement

        // set the analysisFormat
        analysisFormat = aFormat;

    } // end of method setAnalysisFormat()


    /**
     * Sets the simulatedTicks member and the simulatedBars member.
     *
     * @param ticks The number of simulated ticks. If non positive number is provided to this argument
     *              error will be generated.
     * @param bars The number of simulated bars. If non positive number is provided to this argument error will
     *             be generated.
     */
    private void setTicksAndBars(int ticks , int bars) {
        // check for logical error
        if(ticks <= 0 || bars <= 0) {
            // error
            eHandler.newError(ErrorType.INVALID_ARGUMENT , "setTicksAndBars" , "Negative argument");

            // set to error value
            simulatedBars = 0;
            simulatedTicks = 0;

            // exit
            return;
        } // end of if statement

        // set the bars and ticks
        simulatedBars = bars;
        simulatedTicks = ticks;

    } // end of method setTicksAndBars


    /**
     * Returns the analysisFormat member.
     *
     * @return The AnalysisFormat which was tested.
     */
    public AnalysisFormat getAnalysisFormat() {
        return analysisFormat;
    } // end of method getAnalysisFormat()


    /**
     * Returns the simulatedBars.
     *
     * @return Non negative integer representing the number of simulated bars.
     */
    public int getSimulatedBars() {
        return simulatedBars;
    } // end of method getSimulatedBars()


    /**
     * Returns the simulatedTicks member.
     *
     * @return Non negative integer representing the number of simulated ticks.
     */
    public int getSimulatedTicks() {
        return simulatedTicks;
    } // end of method getSimulatedTicks()


    /**
     * Returns the pipsBalance member.
     *
     * @return Signed integer representing the total pips balance produced during the test.
     */
    public int getPipsBalance() {
        return pipsBalance;
    } // end of method getPipsBalance()


    /**
     * Returns the pipsWon member.
     *
     * @return Non negative integer representing the number of won pips produced during the test.
     */
    public int getPipsWon() {
        return pipsWon;
    } // end of method getPipsWon()


    /**
     * Returns the pipsLost member.
     *
     * @return Non negative integer representing the number of lost pips produced during the test.
     */
    public int getPipsLost() {
        return pipsLost;
    } // end of method getPipsLost


    /**
     * Returns the openedPositions member.
     *
     * @return Non negative integer representing the number of opened positions during the test.
     */
    public int getOpenedPositions() {
        return openedPositions;
    } // end of method getOpenedPositions()


    /**
     * Returns the openedLongPositions member.
     *
     * @return Non negative integer representing the number of opened long positions during the test.
     */
    public int getOpenedLongPositions() {
        return openedLongPositions;
    } // end of method getOpenedLongPositions()


    /**
     * Returns the openedShortPositions member.
     *
     * @return Non negative integer representing the number of opened short positions during the test.
     */
    public int getOpenedShortPositions() {
        return openedShortPositions;
    } // end of method getOpenedShortPositions()


    /**
     * Returns the positionsLost member.
     *
     * @return Non negative integere representing the number of lost positions during the test.
     */
    public int getPositionsLost() {
        return positionsLost;
    } // end of method getPositionsLost()


    /**
     * Returns the longPositionsLost member.
     *
     * @return Non negative integere representing the number of lost long positions during the test.
     */
    public int getLongPositionsLost() {
        return longPositionsLost;
    } // end of method getLongPositionsLost()


    /**
     * Returns the shortPositionsLost member.
     *
     * @return Non negative integer representing the number of lost short positions during the test.
     */
    public int getShortPositionsLost() {
        return shortPositionsLost;
    } // end of getShortPositionsLost()


    /**
     * Returns the positionsWon member.
     *
     * @return Non negative integer representing the number of won  positions during the test.
     */
    public int getPositionsWon() {
        return positionsWon;
    } // end of method getPositionsWon()


    /**
     * Returns the longPositionsWon member.
     *
     * @return Non negative integer representing the number of won long positions during the test.
     */
    public int getLongPositionsWon() {
        return longPositionsWon;
    } // end of method getLongPositionsWon()


    /**
     * Returns the shortPositionsWon member.
     *
     * @return Non negative integer representing the number of won whort positions during the test.
     */
    public int getShortPositionsWon() {
        return shortPositionsWon;
    } // end of method getShortPositionsWon()


    /**
     * Initializes the timeOfCreation member.
     */
    private void initTimeOfCreation() {
        // declare local variables:
        Calendar calendar;
        SimpleDateFormat dateFormat;
        // end of local variables declaration


        // initialize the calendar and the dateFormat variables
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("HH:mm:ss");

        // get the current time
        timeOfCreation = dateFormat.format(calendar.getTime() );

    } // end of method initTimeOfCreation()


    /**
     * Returns the timeOfCreation member.
     *
     * @return Non empty non null string representing the time at which this StrategyTestResult was created.
     */
    public String getTimeOfCreation() {
        return timeOfCreation;
    } // end of method getTimeOfCreation()

} // end of class StrategyTestResult
