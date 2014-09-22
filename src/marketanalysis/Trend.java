/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marketanalysis;


// imports:
import error.*;
import java.math.BigDecimal;
import java.util.Vector;



/**
 * Interval made from basic intervals with the same direction.
 *
 * @author Alexandar Atanasov
 */
public  class Trend extends Interval{
    // declare member variables:

    /** Constant used in case of  errors ( The purpose of this constant is to prevent trend object
     *  being set to null ) . */
    public static final Trend DEFAULT_TREND;

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    // end of member variables declaration


    // static members initialization:
    static {
        // initialize the error handler
        eHandler = new ErrorHandler("Trend");

        // initialize the default trend
        BigDecimal defaultTrend[] = {BigDecimal.ONE , BigDecimal.ONE };
        DEFAULT_TREND = new Trend(defaultTrend , 0);
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param trendElements Array of BigDecimals representing this trend. If null is provided to this argument
     *                      or the provided array contains null element or negative element, error will be
     *                      generated. If the provided array doesnt represent valid trend object error will be
     *                      generated.
     * @param intervalScale The scale of the elements in this Trend. If negative number is provided to
     *                      to this argument error will be generated.
     */
    public Trend( BigDecimal trendElements[]  , int intervalScale) {
        // call to super constructor
        super(trendElements , intervalScale);

        // check if the provided trend is indeed a valid trend
        if( !isTrend(trendElements , intervalScale) ) {
            // the provided array does not represent valid trend; error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "Constructor", "Non Trend Interval" );
        } // end of if statement
      
    } // end of constructor


    /**
     * Constructor.
     *
     * @param intervals Array of basic intervals representing this trend. If null is provided to this argument
     *                  or the provided array contains null elements, error will be generated.
     * @param intervalScale The scale of the elements in this Trend. If negative number is provided to
     *                      to this argument error will be generated.
     */
    public Trend( BasicInterval intervals[] , int intervalScale) {
        this(Interval.convertBasicIntervalsToBigDecimals(intervals) , intervalScale);
    } // end of constructor


    /**
     * Copy constructor.
     *
     * @param trend Another instance of the Trend class.
     */
    public Trend(Trend trend) {
        this(trend.getElements() , trend.getScale() );
    } // end of copy constructor


    /**
     * Returns the direction of this trend.
     *
     * @return Non null instance of the Direction enum indicating the direction of this trend.
     */
    public Direction getTrendDirection() {
        return getDirection();
    } // end of method 


    /**
     * Attempts to predict the value of the next element ( not yet existing ) in this trend. The element
     * considered to be "next" is an imaginary element found immediately after the last element of the trend
     * ( last element of the trend is the element trend[lenght-1] ).
     *
     * @return Non null non negative BigDecimal representing the estimated value of the next element in the trend.
     */
    public BigDecimal predictNextElement() {
        // declare local variables:
        BigDecimal buffer;      // buffer used for temporary storage
        BigDecimal result;      // the result to be returned by this method
        // end of local variables declaration


        // resolve the result
        if(this.getTrendDirection() == Direction.NEUTRAL) {
            result = new BigDecimal( getEnd().toString() );
        } else {
            //
            if(this.getTrendDirection() == Direction.UP ) {

                buffer = getEnd().add(getAbsoluteAverageDifference() );
                result = buffer;
            } else {
                //
                buffer = getEnd().subtract(getAbsoluteAverageDifference() );
                result = buffer;
            } // end of if else statement

        } // end of if else statement

        // return the result
        return result;
    } // end of method predictNextElement()


    /**
     * Checks if the provided interval is trend.
     *
     * @param interval Instance of the interval class representing a trend. If null is provided to this
     *                 argument error will be generated.
     *
     * @return True if the provided interval is indeed a trend, false otherwise. If null is provided to the
     *         interval argument the method will return false.
     */
    public static boolean isTrend(Interval interval) {
        // declare local variables:
        boolean result = false;          // the result to be returned by this method
        BasicInterval basicIntervals[];  // array of basic intervals representing the provided interval
        // end of local variables declaration


        // check for null pointer
        if(interval == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "isTrend");

            // exit form method
            return result;

        } // end of if statement

        // get the basicIntervals of the interval
        basicIntervals = interval.getBasicIntervals();

        // set the result to true
        result = true;

        // check if there is only one basic interval
        if(basicIntervals.length == 1) {
            return result;
        } // end of if statement

        // main checking loop
        for(int c = 0; c < basicIntervals.length - 1 ; c++) {

            // check if the direction of the current basic interval is different from the direction of the next
            if(basicIntervals[c].getDirection() != basicIntervals[c+1].getDirection() ) {
                // the provided interval is not trend
                result = false;

                // exit from loop
                break;
            } // end of if statement

        } // end of for loop

        // return the result
        return result;

    }// end of method isTrend()


    /**
     * Checks if the provided array of BigDecimals represents valid trend.
     *
     * @param trendElements Array of BigDecimals representing a trend. If null is provided to this
     *                 argument error will be generated.
     *
     * @return True if the provided trendElements is indeed a trend, false otherwise. If null is provided to the
     *         trendElements argument the method will return false.
     */
    public static boolean isTrend( BigDecimal trendElements[] , int intervalScale) {

        // check for null pointer
        if(trendElements == null) {
            // error
            eHandler.newError(ErrorType.NULL_ARGUMENT, "isTrend");

            // exit from method
            return false;

        } // end of if statement

        // return the result
        return isTrend(new Interval(trendElements , intervalScale ) );

    } // end of method isTrend()


    /**
     * Returns the Trends contained in the provided interval.
     *
     * @param interval Interval object. If null is provided to this argument error will be generated.
     *
     * @return Non null array containing the Trends in the provided interval. The minimum number of returned Trends
     *         is one ( if one is returned by this method , it means that the provided interval itself is trend ).
     */
    public static Trend[] getTrendsInInterval(Interval interval) {
        // declare local variables:
        Trend result[]= null;           // the result to be returned by this method
        BasicInterval basicIntervals[]; // array of basic intervals representing the provided interval
        BasicInterval lastTrend[];      // array of basic intervals representing the last encountered trend
        Vector <Trend> trendsBuffer;    // vector buffer used for temporary storage
        Vector<BasicInterval> lastTrendVector; //
        // end of local variables declaration


        // check for null pointer
        if(interval == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "getTrendsInInterval");

            // exit from method
            return result;
        } // end of if statement

        // initialize the trendsBuffer vector
        trendsBuffer = new Vector<Trend>();

        // get the basic intervals
        basicIntervals = interval.getBasicIntervals();

        // initialize the lastTrendVector
        lastTrendVector = new Vector<BasicInterval>();
        lastTrendVector.add(basicIntervals[0]);

        // main extraction loop
        for(int c = 1; c < basicIntervals.length; c++) {
            // check if the current basic interval has the same direction as the last
            if(basicIntervals[c].getDirection() == basicIntervals[c-1].getDirection() ) {

                //
                lastTrendVector.add(basicIntervals[c]);

            } else {

                // there is new trend; store the last trend in the trendsBuffer vector
                lastTrend = new BasicInterval[ lastTrendVector.size() ];
                for(int counter = 0; counter < lastTrend.length; counter++) {
                    lastTrend[counter] = lastTrendVector.elementAt(counter);
                } // end of for loop
                trendsBuffer.add(new Trend(lastTrend , interval.getScale() ) );
                lastTrendVector.clear();

                //
                lastTrendVector.add(basicIntervals[c]);

            } // end of if else statement

        } // end of for loop

        // add the latest trend to the vector of trends
        lastTrend = new BasicInterval[ lastTrendVector.size() ];
        for(int counter = 0; counter < lastTrend.length; counter++) {
            lastTrend[counter] = lastTrendVector.elementAt(counter);
        } // end of for loop
        trendsBuffer.add(new Trend(lastTrend , interval.getScale() ) ); 

        // load the trend vector in the result
        result = new Trend[trendsBuffer.size() ];
        for(int c = 0; c < result.length; c++) {
            result[c] = trendsBuffer.elementAt(c);
        } // end of for loop

        // return the result
        return result;
        
    } // end of method getTrendsInInterval()


    /**
     * Returns the last trend from the provided interval.
     *
     * @param interval Interval object. If null is provided to this argument error will be generated.
     *
     * @return The last trend found in the provided interval.
     */
    public static Trend getLastTrendInInterval(Interval interval) {
        // declare local variables:
        Trend result=null;      // the result to be returned by this method
        Trend trends[];         // the trends in the provided interval
        // end of local variables declaration


        // check for null pointer
        if(interval == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "getLastTrendInInterval");

            // exit from method
            return result;
        } // end of if statement

        // get the last trend
        trends = getTrendsInInterval(interval);
        result = trends[trends.length-1];

        // return the result
        return result;

    } // end of method getLastTrendInInterval()


} // end of class Trend
