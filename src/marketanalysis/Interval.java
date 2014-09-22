/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marketanalysis;


// imports:
import error.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Vector;
import marketanalysis.MovingAverage.MovingAverageType;



/**
 * Extended version of the BasicInterval with the capability to hold elements between the begin and the end.
 * The begin and the end of the interval are also considered elements. The minimum number of elements is 2.
 *
 * @author Alexandar Atanasov
 */
public class Interval extends BasicInterval {
    // declare member variables:

    /** Instance of the ErrorHandler used for managaing errors. */
    private static ErrorHandler eHandler;

    /** */
    public static enum AlignmentMode {BEGIN , END };

    /** The elements ( points ) from which this Interval is made of. */
    private BigDecimal elements[];

    /** The scale of the elemenets contained in this interval. */
    private int scale;

    /** The number of elements in this Interval*/
    private int lenght;

    // end of member variables declaration


    // static members initialization:
    static {
        eHandler = new ErrorHandler("Interval");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param prices The elements of this interval. If null is provided to this argument or the provided
     *               array contains null element or the provided array has lenght lesser than 2, error will
     *               be generated.
     * @param intervalScale The scale of the elements in this interval. If negative number is provided to
     *                      to this argument error will be generated.
     */
    public Interval(BigDecimal prices[] , int intervalScale) {
        // call to super constructor
        super (prices[0], prices[prices.length-1] );

        // set the elements
        setElements(prices);

        // set the scale
        setScale(intervalScale);

    } // end of constructor


    /**
     * Constructor.
     *
     * @param basicIntervals Array of basic intervals representing an interval. If null is provided to this
     *                       argument or the provided array contains null element error will be generated.
     * @param intervalScale The scale of the elements in this interval. If negative number is provided to
     *                      to this argument error will be generated.
     */
    public Interval( BasicInterval basicIntervals[] , int intervalScale) {
        this(convertBasicIntervalsToBigDecimals(basicIntervals) , intervalScale);
    } // end of constructor


    /**
     * Copy constructor.
     *
     * @param interval Another instance of the Interval class.
     */
    public Interval(Interval interval) {
        this(interval.getElements() , interval.getScale() );
    } // end of copy constructor


    /**
     * Sets the elements member.
     *
     * @param elementsArr The elements of this interval. If null is provided to this argument or the provided
     *                    array contains null element or the provided array has lenght lesser than 2, error will
     *                    be generated.
     */
    private void setElements(BigDecimal elementsArr[] ) {
        // check for null pointer
        if(elementsArr == null) {
            // error
                eHandler.newError(ErrorType.NULL_ARGUMENT, "setElements");

                // exit
                return;
        } // end of if statement

        // check for null element
        for(int c = 0; c < elementsArr.length; c++) {
            // check the current element
            if(elementsArr[c] == null) {
                // error
                eHandler.newError(ErrorType.NULL_ARRAY_ELEMENT, "setElements");

                // exit
                return;
            } // end of if statement

        } // end of for loop

        // check for logical error
        if(elementsArr.length < 2) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setElements" , "Interval with less than 2 elements");

            // exit from method
            return;
        } // end of if statement

        // load the prices array
        elements = new BigDecimal[elementsArr.length];
        System.arraycopy(elementsArr, 0, elements, 0, elements.length);

        // set the lenght
        resolveLenght();

    } // end of method setElements()


    /**
     * Calculates and sets the lenght member. Should be called by setElements().
     *
     */
    private void resolveLenght() {

        // find the lenght
        if(elements != null) {
            lenght =elements.length;
        } // end of if statement

    } // end of method resolveLenght()


    /**
     * Sets the scale member and the scales of all elements in this interval. In the constructor this method
     * should be used after the setElements() method.
     * 
     * @param intervalScale The scale of the elements in this interval. If negative number is provided to
     *                      to this argument error will be generated.
     */
    private void setScale(int intervalScale) {
        // check for negative number
        if(intervalScale < 0) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setScale" , "negative scale");

            // set to default
            scale = 0;

            // exit from method
            return;
        } // end of if statement

        // set the scale
        scale = intervalScale;

        // set the scale of the elements
        for(int c = 0; c < getLenght() ; c++) {
            // try to  set the scale of the current element
            try {
                elements[c].setScale(scale);
            } catch(ArithmeticException e) {
                // error ..
                eHandler.newError(ErrorType.FAILED_RESCALING, "setScale");
            } // end of try block

        } // end of for loop

    } // end of method setScale()


    /**
     * Returns the scale member.
     *
     * @return Non null non negative BigDecimal equal to the scale of the elements in this Interval.
     */
    public int getScale() {
        return scale;
    } // end of method getScale()


    /**
     * Returns the elements member.
     *
     * @return Non null array of non null non negative numbers.
     */
    public BigDecimal[] getElements() {
        return elements;
    } // end of method


    /**
     * Returns the lenght member.
     *
     * @return Positive integer representing the number of elements contained in this interval.
     */
    public int getLenght() {
        return lenght;
    } // end of method


    /**
     * Converts the provided array of basic intervals into array of BigDecimals. It is assumed that the
     * provided array of basic intervals represents a single interval.
     *
     * @param basicIntervals Array of basic intervals representing an interval. If null is provided to 
     *                       this argument or the provided array contains null elements error will be 
     *                       generated.
     *
     * @return Array of BigDecimals representing an interval.
     */
    public static BigDecimal[] convertBasicIntervalsToBigDecimals(BasicInterval basicIntervals[]) {
        // declare local variables:
        BigDecimal result[] =null;
        // end of local variables declaration


        // check for null pointer
        if(basicIntervals == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "convertBasicIntervalsToBigDecimals");

            // exit
            return result;
        } // end of if statement

        // check for null elements
        for(int c = 0; c < basicIntervals.length; c++) {
            if(basicIntervals[c] == null) {
                // error ..
                eHandler.newError(ErrorType.NULL_ARGUMENT, "convertBasicIntervalsToBigDecimals");

                // exit
                return result;

            } // end of if statement

        } // end of for loop

        // initialize the result
        result = new BigDecimal[basicIntervals.length+1];

        // main loop
        for(int c = 0; c < basicIntervals.length; c++) {
            // store the current element
            result[c]= new BigDecimal(basicIntervals[c].getBegin().toString() );
        } // end of for loop

        // store the last element
        result[result.length-1] = new BigDecimal(basicIntervals[basicIntervals.length-1].getEnd().toString() );

        // return the result
        return result;
    } // end of method convertBasicIntervalsToBigDecimals()


    /**
     * Converts the elements of this interval in array of basic intervals and returns it. The begining of each
     * basic interval represents the same interval element as the ending of the previous basic interval.
     *
     * @return Non null array of basic intervals representing this interval.
     */
    public BasicInterval[] getBasicIntervals() {
        // declare local variables
        BasicInterval result[]=null;            // the result to be returned by this method
        // end of local variables declaration


        // initialize the result array
        result = new BasicInterval[lenght-1];

        // resolve the result
        for(int c = 0; c < lenght-1; c++) {
            // create the current basic interval
            result[c] = new BasicInterval(elements[c] , elements[c+1] );
        } // end of for loop

        // return the result
        return result;
    } // end of method getBasicIntervals()


    /**
     * Returns the average of the interval.
     * The average is calculated as the sum  of all averages of the basic intervals in the interval ,
     * is divided by the number of basic intervals. The rounding mode used for the division is
     * ROUND_HALF_EVEN.
     *
     * @return Non null non negative number representing the average value of elements in this interval. The
     *         returned BigDecimal has scale equal to the scale of this interval + 1.
     */
    public BigDecimal getIntervalAverage() {
        // declare local variables:
        BigDecimal sum = new BigDecimal("0");       // sum of the averages of the basic intervals
        BigDecimal result;                          // the result to be returned by this method
        // end of local variables declaration


        // check for error
        if(elements == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "getIntervalAverage");

            // exit from method
            result = BigDecimal.ZERO;
            return result;
        } // end of if statement
        
        // get the sum of the interval elements
        for(int c = 0 ; c < this.getLenght() ; c++) {
            sum = sum.add(elements[c] );
        } // end of for loop

        // resolve the result
        result = sum.divide(new BigDecimal( Integer.toString(this.getLenght() ) ) ,
                            this.getScale() + 1 , BigDecimal.ROUND_HALF_EVEN );

        // return the result
        return result;

    } // end of method getIntervalAverage()



    /**
     * Converts this interval to simple moving average with period equal to the "period" argument and returns
     * the conversion result. The scale of the produced moving average is equal to the scale of this interval + 1.
     *
     * @param period Positive integer representing the period of the moving average.
     *               The maximum possible period is lenght/2; the minimum period is 2. If the "period" is
     *               greater than  lenght/2 or smaller than 2, error will be generated.
     *
     * @return Simple MovingAverage object created from the elements of this interval.
     */
    public MovingAverage generateSimpleMovingAverage(int period) {
        // declare local variables:
        MovingAverage result=null;          // the result to be returned by this method
        BigDecimal dataPoint;               // one data point of the moving average
        BigDecimal averages[];              // array which will contain the elements of the moving average
        Vector <BigDecimal> averagesVector; // temporary storage for the elements of the moving average
        // end of local variables declaration


        // check for logical error
        if(period < 1 || period > lenght/2 ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "getMovingAverage" , "Period out of bounds");

            // exit from method
            return result;
        } // end of if statement


        // initialize the averages Vector
        averagesVector = new Vector<BigDecimal>();

        // main converting loop
        for(int c = period-1; c < this.getLenght(); c++) {
            // prepare the dataPoint
            dataPoint = BigDecimal.ZERO;

            // calculate the sum for the current average
            for(int bCounter = period - 1; bCounter >= 0; bCounter--) {
                //
                dataPoint = dataPoint.add(elements[c-bCounter]);
            } // end of for loop
            
            // calculate the current average
            dataPoint = dataPoint.divide(new BigDecimal(Integer.toString(period) ),
                                         this.getScale() + 1, RoundingMode.HALF_EVEN );
            
            // add the current average to the vector of averages
            averagesVector.add(dataPoint);

        } // end of for loop

        // convert the averagesVector to array
        averages = new BigDecimal[averagesVector.size() ];
        averages = averagesVector.toArray(averages);

        // initialize the result
        result = new MovingAverage(averages , this.getScale() + 1, period , MovingAverageType.SIMPLE , this);

        // return the result
        return result;

    } // end of method generateSimpleMovingAverage()


    /**
     * Returns the largest element by value contained in this interval.
     *
     * @return Non null non negative number equal to the larges element of this interval. If all the elements
     *         in this interval are equal the method will return null.
     */
    @Override
    public BigDecimal getMaximumValue() {
        // declare local variables:
        Interval nonNeutralInterval;             // interval containing no neutral basic intervals
        BasicInterval basicIntervals[];          // array of basic intervals representing this interval
        BigDecimal nonNeutralIntervalElements[]; // the elements of the non neutral interval
        BigDecimal buffer=BigDecimal.ZERO;       // vectorBuffer used for temporary storage
        BigDecimal result;                       // the result to be returned by this method
        // end of local variables declaration


        // get the basic intervals
        basicIntervals = this.getBasicIntervals();

        // deiscard neutral basic intervals
        basicIntervals = BasicInterval.discardNeutrals(basicIntervals);

        // check if the basicINtervals array still exists
        if(basicIntervals == null) {
            // all basic intervals were neutral; there is no maximum; exit from method
            return null;
        } // end of if statement

        // construct the nonNeutralInterval
        nonNeutralInterval = new Interval(basicIntervals , this.getScale() );

        // get the elements of the non neutral interval
        nonNeutralIntervalElements = nonNeutralInterval.getElements();

        // set the vectorBuffer to the first element of the non neutral interval
        buffer = nonNeutralIntervalElements[0];

        // main loop
        for( int c = 1; c < getLenght() ; c++) {
            // check if the value of the vectorBuffer is lesser than the value of the current element
            if(buffer.compareTo(nonNeutralIntervalElements[c]) < 0 ) {
                buffer = new BigDecimal(nonNeutralIntervalElements[c].toString());
            } // end of if statement

        } // end of for loop

        // store the vectorBuffer in the result
        result = buffer;

        // return the result
        return result;

    } // end of method getMaximumValue()


    /**
     * Returns the smallest element by value contained in this interval.
     *
     * @return Non null non negative number equal to the larges element of this interval. If all the elements
     *         in this interval are equal the method will return null.
     */
    @Override
    public BigDecimal getMinimumValue() {
        // declare local variables:
        Interval nonNeutralInterval;             // interval containing no neutral basic intervals
        BasicInterval basicIntervals[];          // array of basic intervals representing this interval
        BigDecimal nonNeutralIntervalElements[]; // the elements of the non neutral interval
        BigDecimal buffer = BigDecimal.ZERO;     // vectorBuffer used for temporary storage
        BigDecimal result;                       // the result to be returned by this method
        // end of local variables declaration


         // get the basic intervals
        basicIntervals = this.getBasicIntervals();

        // deiscard neutral basic intervals
        basicIntervals = BasicInterval.discardNeutrals(basicIntervals);

        // check if the basicINtervals array still exists
        if(basicIntervals == null) {
            // all basic intervals were neutral; there is no maximum; exit from method
            return null;
        } // end of if statement

        // construct the nonNeutralInterval
        nonNeutralInterval = new Interval(basicIntervals , this.getScale());

        // get the elements of the non neutral interval
        nonNeutralIntervalElements = nonNeutralInterval.getElements();

        // set the vectorBuffer to the first element of the non neutral interval
        buffer = nonNeutralIntervalElements[0];

        // main loop
        for( int c = 1; c < getLenght() ; c++) {
            // check if the vectorBuffer is greater than the current element
            if(buffer.compareTo(nonNeutralIntervalElements[c]) > 0 ) {
                buffer = new BigDecimal(nonNeutralIntervalElements[c].toString());
            } // end of if statement

        } // end of for loop

        // store the vectorBuffer in the result
        result = buffer;

        // return the result
        return result;
    } // end of method getMinimumValue()


    /**
     * Returns all extremums which are maximums. An extremum is a point at which the price ( or volume )
     * movement changes its direction ( neutral direction is not considered in this and it is automatically
     * discarded ) .
     *
     * @return Array containing all maximums found in this interval. If there are no maximums the method will
     *         return null.
     */
    public BigDecimal[] getMaximums() {
        // declare local variables:
        BigDecimal result[] = null;         // the result to be returned by this method
        Vector <BigDecimal> vectorBuffer;   // vectorBuffer for temporary storage
        BasicInterval basicIntervals[];     // array of basic intervals representing this interval
        // end of local variables declaration


        // initialize the vectorBuffer vector
        vectorBuffer = new Vector <BigDecimal>();

        // get the basic intervals
        basicIntervals = getBasicIntervals();

        // remove neutral basic intervals
        basicIntervals = BasicInterval.discardNeutrals(basicIntervals);

        // check if the basicIntervals exists
        if(basicIntervals == null) {
            // all basic intervals were neutral; there is no maximum; exit from method
            return result;
        } // end of if statement

        // main extracting loop
        for(int c = 0; c < basicIntervals.length-1; c++) {
            // check the direction of the current interval
            if(basicIntervals[c].getDirection() == Direction.UP) {

                // check if the next basic interval direction is down
                if(basicIntervals[c+1].getDirection() == Direction.DOWN) {

                    // there is maximum
                    vectorBuffer.add(basicIntervals[c].getEnd() );

                } // end of if statement

            } // end of if statement

        } // end of for loop

        // store the vectorBuffer in the result
        result = new BigDecimal[vectorBuffer.size() ];
        for(int c=0; c < result.length; c++){
            result[c] = new BigDecimal(vectorBuffer.elementAt(c).toString() );
        } // end of for loop

        // return the result
        return result;

    } // end of method getMaximums()


    /**
     * Returns all extremums which are minimums. An extremum is a point at which the price ( or volume )
     * movement changes its direction ( neutral direction is not considered in this and it is automatically
     * discarded ) .
     *
     * @return Array containing all maximums found in this interval. If there are no maximums the method will
     *         return null.
     */
    public BigDecimal[] getMinimums() {
        // declare local variables:
        BigDecimal result[] = null;         // the result to be returned by this method
        Vector <BigDecimal> vectorBuffer;   // buffer used for temporary storage
        BasicInterval basicIntervals[];     // array of basic intervals representing this interval
        // end of local variables declaration


        // initialize the vectorBuffer vector
        vectorBuffer = new Vector <BigDecimal>();

        // get the basic intervals
        basicIntervals = getBasicIntervals();

        // remove neutral intervals
        basicIntervals = BasicInterval.discardNeutrals(basicIntervals);

        // check if the basicIntervals array exists
        if(basicIntervals == null) {
            // all basic intervals were neutral; no minimum; exit from method
            return result;
        } // end of if statement

        // main extracting loop
        for(int c = 0; c < basicIntervals.length-1; c++) {
            // check the direction of the current interval
            if(basicIntervals[c].getDirection() == Direction.DOWN) {

                // check if the next basic interval direction is up
                if(basicIntervals[c+1].getDirection() == Direction.UP) {

                    // there is minimum
                    vectorBuffer.add(basicIntervals[c].getEnd());

                } // end of if statement

            } // end of if statement

        } // end of for loop

        // store the vectorBuffer in the result
        result = new BigDecimal[vectorBuffer.size() ];
        for(int c=0; c < result.length; c++){
            result[c] = new BigDecimal(vectorBuffer.elementAt(c).toString() );
        } // end of for loop

        // return the result
        return result;
        
    } // end of method getMaximums()


    /**
     * Returns the average difference of this interval. The average difference is calculated as the sum of the
     * differencies of all basic intervals ( contained in this interval) is divided by the number of basic
     * intervals. The rounding method used in the division is ROUND_HALF_EVEN.
     *
     *
     * @return Non null signed BigDecimal representing the average difference of this interval. The scale of
     *         the returned BigDecimal is equal to the scale of this interval + 1.
     */
    public BigDecimal getAverageDifference () {
        // declare local variables:
        BasicInterval basicIntervals[];             // array of basic intervals representing this interval
        BigDecimal result = BigDecimal.ZERO;        // the result to be returned by this method
        // end of local variables declaration


        // get the basicIntervals
        basicIntervals = getBasicIntervals();

        // get the sum of the differencies of the basic intervals contained in this interval
        for(int c = 0; c < basicIntervals.length; c++) {
            result = result.add( basicIntervals[c].getDifference()) ;
        } // end of for loop

        // divide the sum by the number of basic intervals
        result = result.divide(new BigDecimal(Integer.toString(basicIntervals.length) ) ,
                               this.getScale() + 1 , BigDecimal.ROUND_HALF_EVEN );
        
        // return the result
        return result;

    } // end of method getAverageDifference ()


    /**
     * Returns the absolute average difference of this interval. The absolute average difference is calculated
     * as the sum of the absolute differencies of all basic intervals ( contained in this interval) , is divided 
     * by the number of basic intervals. The rounding mode used in the division is ROUND_HALF_EVEN.
     *
     * @return Non null non negative BigDecimal representing the absolute average difference of this interval.
     *         The scale of the returned BigDecimal is equal to the scale of this interval + 1.
     */
    public BigDecimal getAbsoluteAverageDifference () {
        // declare local variables:
        BasicInterval basicIntervals[];         // array of basic intervals representing this interval
        BigDecimal result = BigDecimal.ZERO;    // the result to be returned by this method
        // end of local variables declaration


        // get the basicIntervals
        basicIntervals = getBasicIntervals();

        // get the sum of the absolute differencies of the basic intervals contained in this interval
        for(int c = 0; c < basicIntervals.length; c++) {
            result = result.add( basicIntervals[c].getAbsoluteDifference() ) ;
        } // end of for loop

        // divide the sum by the number of basic averages
        result = result.divide(new BigDecimal(Integer.toString(basicIntervals.length) ) ,
                               this.getScale() + 1 , BigDecimal.ROUND_HALF_EVEN  );

        // return the result
        return result;

    } // end of method getAverageDifference ()


    /**
     * Returns the difference of the first basic interval in this interval.
     *
     * @return Non null signed BigDecimal representing the difference of the first basic interval found
     *         in this interval.
     */
    public BigDecimal getFirstDifference() {
        // declare local variables:
        BigDecimal result;      // the result to be returned by this method
        // end of local variables declaration


        // resolve the result
        result = getBasicIntervals()[0].getDifference() ;
        
        // return the result;
        return result;

    } // end of method  getFirstDifference()


    /**
     * Returns the  difference of the last basic interval in this interval.
     *
     * @return Non null signed BigDecimal representing the difference of the last basic interval found
     *         in this interval.
     */
    public BigDecimal getLastDifference() {
        // declare local variables:
        BigDecimal result;      // the result to be returned by this method
        // end of local variables declaration


        // resolve the result
        result = getBasicIntervals()[getBasicIntervals().length - 1].getDifference() ;

        // return the result;
        return result;
        
    } // end of method  getLastDifference()


    /**
     * Returns the direction of the last (most right) basic interval contained in this interval.
     *
     * @return The direction of the last BasicInterval in this interval.
     */
    public Direction getLastDirection() {
        // declare local variables:
        Direction result;               // the result to be returned by this method
        BasicInterval basicIntervals[]; // the basic intervals contained in this interval
        // end of member variables declaration


        // get the basic intervals
        basicIntervals = getBasicIntervals();

        // resolve the result
        result = basicIntervals[basicIntervals.length-1].getDirection();

        // return the result
        return result;

    } // end of method getLastDirection()


    /**
     * Converts this interval to exponential moving average with period equal to the "period" argument and returns
     * the conversion result. The scale of the produced moving average is equal to the scale of this interval + 1.
     *
     * @param period Positive integer representing the period of the moving average.
     *               The maximum possible period is lenght/2; the minimum period is 2. If the "period" is
     *               greater than  lenght/2 or smaller than 2, error will be generated.
     *
     * @return Exponential MovingAverage object created from the elements of this interval.
     */
    public MovingAverage generateExponentialMovingAverage(int period) {
        // declare local variables:
        MovingAverage result=null;          // the result to be returned by this method
        BigDecimal exponent;                // the exponent of the ema
        BigDecimal secondaryExponent;       //
        BigDecimal buffer;
        BigDecimal previousDayValue;        // the value of the ema for the previous day
        BigDecimal oneDataPoint;            //
        BigDecimal averages[];              // array which will contain the elements of the moving average
        Vector <BigDecimal> averagesVector; // temporary storage for the elements of the moving average
        // end of local variables declaration


        // check for logical error
        if(period < 1 || period > lenght/2 ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "getMovingAverage" , "Period out of bounds");

            // exit from method
            return result;
        } // end of if statement


        // initialize the averages Vector
        averagesVector = new Vector<BigDecimal>();

        // calculate the exponent
        exponent = new BigDecimal( (double) 2/(period+1) );
        exponent.setScale(this.getScale()+1, RoundingMode.HALF_EVEN );

        // calculate the secondary exponent
        secondaryExponent =  BigDecimal.ONE.subtract(exponent);
        secondaryExponent.setScale(this.getScale()+1, RoundingMode.HALF_EVEN );

        // initialize the previous day value
        previousDayValue = BigDecimal.ZERO;
        for(int c = 0; c < period; c++) {
            //
            previousDayValue.add(elements[c]);
        } // end of for loop
        previousDayValue.divide(new BigDecimal( Integer.toString(period) ), this.getScale()+1, RoundingMode.HALF_EVEN);

        // main converting loop
        for(int c = period; c < this.getLenght(); c++) {
            // calculate the current value for the ema
            oneDataPoint = previousDayValue.multiply(exponent);
            buffer = elements[c].divide(secondaryExponent , this.getScale() +1 , RoundingMode.HALF_EVEN );
            oneDataPoint = oneDataPoint.add(buffer);
            oneDataPoint.setScale( this.getScale() +1 , RoundingMode.HALF_EVEN);

            // add the current data point the vectore of averages ( data points )
            averagesVector.add(oneDataPoint);
            
        } // end of for loop

        // convert the averagesVector to array
        averages = new BigDecimal[averagesVector.size() ];
        averages = averagesVector.toArray(averages);

        // initialize the result
        result = new MovingAverage(averages , this.getScale() + 1, period , MovingAverageType.SIMPLE , this);

        // return the result
        return result;
        
    } // end of method generateSimpleMovingAverage()


    /**
     * Subtracts the provided interval from this one.
     *
     * @param subtrahend The interval to be subtracted from this one. If null is provided to this argument
     *                   error will be generated.
     * @param mode The equlization mode used for equlizing the 2 intervals
     *
     * @return Interval with length equal to the length of the shorter of the two intervals and elements 
     *         produced by subtraction of the subtrahend from this interval. In case of some error this
     *         method will return null.
     */
    public Interval subtract(Interval subtrahend , AlignmentMode mode ) {
        // declare local variables:
        Interval result = null;
        BigDecimal subtrahendElements[];
        BigDecimal newElements[];
        int length;
        // end of local variables declaration


        // check for null pointer
        if(subtrahend == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "subtract" );

            // exit from method
            return result;
        } // end of if statement

        // get the elements of the subtrahend
        subtrahendElements = subtrahend.getElements();

        // find the equlization mode
        if(mode == AlignmentMode.BEGIN) {
            // find the shorter interval and use its length
            if(this.getLenght() > subtrahend.getLenght() ) {
                //
                length = subtrahend.getLenght();
                newElements = new BigDecimal[length];
                for(int c = 0; c < length; c++) {
                    //
                    newElements[c] = elements[c].subtract(subtrahendElements[c]);
                } // end of for loop

            } else {
                //
                length = this.getLenght();
                newElements = new BigDecimal[length];
                for(int c = 0; c < length; c++) {
                    //
                    newElements[c] = elements[c].subtract(subtrahendElements[c] );
                } // end of for loop
            } // end of if else statement
        } else {
            // find the shorter interval and use its length
            if(this.getLenght() > subtrahend.getLenght() ) {
                //
                length = subtrahend.getLenght();
                newElements = new BigDecimal[length];
                for(int c = length; c > 0; c--  ) {
                    //
                    newElements[length - c] = elements[elements.length- c].
                                              subtract(subtrahendElements[subtrahendElements.length - c] );
                } // end of for loop

            } else {
                //
                length = this.getLenght();
                newElements = new BigDecimal[length];
                for(int c = length; c > 0; c-- ) {
                    //
                    newElements[length - c] = elements[elements.length - c].
                                              subtract(subtrahendElements[subtrahendElements.length - c] );
                } // end of for loop

            } // end of if else statement

        } // end of if statement

        // create the result
        result = new Interval(newElements, this.getScale() );

        // return the result
        return result;
        
    } // end of method subtract


    
    /**
     * 
     * @param compressionFactor
     * @return
     */
    public Interval compress(int compressionFactor , AlignmentMode mode) {
        // declare local variables:
        Interval result = this;
        // end of local variables declaration


        // check for logical error
        if(compressionFactor < 1 || compressionFactor > this.getLenght()/2.0 ) {
            // error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "compress" ,
                             "Out of bounds compression factor" );

            // exit from method
            return result;
        } // end of if statement

        //
        if(mode == AlignmentMode.END) {
            //
        } else {
            //
        } // end of if else statement

        // return the result
        return result;

    } // end of method compress()


    public Interval subInterval(int beginIndex , int endIndex) {
        // declare local variables:
        Interval result = null;
        // end of local variables declaration

        // return the result
        return result;

    }// end of method subInterval

} // end of class Interval
