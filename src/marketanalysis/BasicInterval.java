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
 *
 * This is an interval between 2 points ( The two points represent price or volume values ).
 * Basic intervals are always calculated from left to right ( chartwise ) or that is from older to newer values.
 * The left point of basic interval is considered begining and the right point is considered end.
 *
 * @author Alexandar Atanasov
 */
public class BasicInterval {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The begining point of the basic interval. */
    private BigDecimal begin;

    /** The end point of the basic interval*/
    private BigDecimal end;

    /** The direction of the basic interval defined as the angle between the basic interval and the
     *  x axis of the coordinate system in which the basic interval resides. */
    private Direction direction;

    // end of member variables declaration


    // initialization of static member variables
    static{
        eHandler = new ErrorHandler("BasicInterval");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param beginPrice The begining point of this basic interval. Providing null or negative number to
     *                   this argument will generated error.
     * @param endPrice The ending point of this basic interval. Providing null or negative number to
     *                 this argument will generated error.
     */
    public BasicInterval( BigDecimal beginPrice , BigDecimal endPrice) {
        // set the members
        setBegin(beginPrice);
        setEnd (endPrice);

        // find and set the direction of the basic interval
        resolveDirection();

    } // end of constructor


    /**
     * Copy constructor.
     *
     * @param interval Another instance of the BasicInterval class.
     */
    public BasicInterval(BasicInterval interval) {
        this(interval.getBegin() , interval.getEnd() );
    } // end of copy constructor


    /**
     * Sets the begin member.
     *
     * @param beginValue The begining point of this basic interval. Providing null or negative number to
     *                   this argument will generated error.
     */
    private void setBegin(BigDecimal beginValue) {
        // check for null pointer
        if(beginValue == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setBegin");

            // set default value
            begin = BigDecimal.ZERO;
            
            // exit
            return;
        } // end of if statement

        // check for logical error
        if(beginValue.compareTo(BigDecimal.ZERO ) == -1) {
            // logical error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setBegin" , "Negative begin");

            // set default value
            begin = BigDecimal.ZERO;

            // exit
            return;
        } // end of if statement

        // set the begin
        begin = new BigDecimal(beginValue.toString() );

    } // end of method setBegin


    /**
     * Sets the end member.
     *
     * @param endValue The ending point of this basic interval. Providing null or negative number to
     *                 this argument will generated error.
     */
    private void setEnd(BigDecimal endValue) {
        // check for null pointer
        if(endValue == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setEnd");

            // set default value
            end = BigDecimal.ZERO;

            // exit
            return;
        } // end of if statement

        // check for logical error
        if(endValue.compareTo(BigDecimal.ZERO ) == -1) {
            // logical error ..
            eHandler.newError(ErrorType.INVALID_ARGUMENT , "setEnd");

            // set default value
            end = BigDecimal.ZERO;

            // exit
            return;
        } // end of if statement

        // set the begin
        end = new BigDecimal(endValue.toString() );

    } // end of method setBegin


    /**
     * Determines and sets the direction of this basic interval.
     *
     */
    private void resolveDirection() {
        // declare local variables:
        BigDecimal difference;              // the difference of this basic interval
        Direction result=null;              // the direction of this basic interval
        // end of local variables declaration


        // get the difference
        difference = getDifference();

        // resolve the result:
        if(difference.compareTo(BigDecimal.ZERO) > 0) {
            result = Direction.UP;
        } // end of if statement

        if(difference.compareTo(BigDecimal.ZERO) == 0) {
            result = Direction.NEUTRAL;
        } // end of if statement

        if(difference.compareTo(BigDecimal.ZERO) < 0) {
            result = Direction.DOWN;
        } // end of if statement


        // set the direction
        direction = result;

    } // end of method resolveDirection()


    /**
     * Returns the begin member.
     *
     * @return Non null non negative BigDecimal representing the begining point of this BasicInterval.
     */
    public BigDecimal getBegin() {
        return begin;
    } // end of method


    /** 
     * Returns the end member.
     *
     * @return Non null non negative BigDecimal representing the ending point of this BasicInterval.
     */
    public BigDecimal getEnd() {
        return end;
    } // end of method


    /**
     * Returns the direction member.
     *
     * @return Non null instance of the Direction class showing the direction of this BasicInterval.
     */
    public Direction getDirection() {
        return direction;
    } // end of method


    /**
     * Returns the difference between the values of the begin and the end members. If the end is lesser than
     * the begin , the difference is negative. If the end and the begin are equal the difference is 0.
     *
     * @return Non null signed number representing the difference of this BasicINterval.
     */
    public BigDecimal getDifference() {
        // declare local variables:
        BigDecimal result;              // the result to be returned by this method
        // end of local variables declaration


        // resolve the result
        result = end.subtract(begin);
        
        // return the result
        return result;

    } // end of method  getDifference()


    /**
     * Returns the absolute value of the difference between the begin and the end.
     *
     * @return Non null non negative number representing the absolute value of the difference of this
     *         BasicInterval.
     */
    public BigDecimal getAbsoluteDifference() {
        // declare local variables:
        BigDecimal difference;              // the difference of this BasicInterval
        BigDecimal result;                  // the result to be returned by this method
        // end of local variables declaration


        // find the difference
        difference = getDifference();

        // resolve the result
        result = difference.abs();

        // return the result
        return result;

    } // end of method getAbsoluteDifference()


    /**
     * Returns the average value of the begin and the end members. The average is calculated as the sum of
     * the begin and the end members is divided by 2.
     *
     * @return Non null non negative number representing the average of this BasicInterval.
     */
    public BigDecimal getBasicIntervalAverage() {
        // declare local variables
        BigDecimal sum;                 // the sum of the begin and the end members
        BigDecimal result;              // the result to be returned by this method
        // end of loca variables declaration


        // get the sum
        sum = getBegin().add(getEnd() );
        
        // resolve the result
        result = sum.divide( new BigDecimal("2") );

        // return the result
        return result;
    } // end of method getBasicIntervalAverage


    /**
     * Returns the greater of the 2 numbers stored in this basic interval.
     * If the begin and the end are equal the method will return null.
     *
     * @return Non null non negative number equal to the bigger of the 2 values stored in this BasicInterval.
     *         If the direction of this BasicINterval is neutral ( no maximum ) the method will return null.
     */
    public BigDecimal getMaximumValue() {
        // declare local variables:
        BigDecimal result;              // the result to be returned by this method
        // end of local variables declaration


        // check if there is minimum
        if(getBegin().compareTo(getEnd() ) == 0 ) {
            // there is no minimum
            return null;
        } // end of if statement

        // resolve the result
        if(getBegin().compareTo(getEnd() ) > 0 ) {
            result = getBegin();
        } else {
            result = getEnd();
        } // end of if else statement

        // return the result
        return result;

    } // end of method getMinimumValue()


    /**
     * Returns the lesser of the 2 values stored in this BasicInterval.
     * Ff the begin and the end are equal the method will return null.
     *
     * @return Non null non negative number equal to the lesser of the 2 values stored in this BasicInterval.
     *         If the direction of this BasicINterval is neutral ( no minimum ) the method will return null.
     */
    public BigDecimal getMinimumValue() {
        // declare local variables:
        BigDecimal result;
        // end of local variables declaration


        // check if there is minimum
        if(getBegin().compareTo(getEnd() ) == 0 ) {
            // there is no minimum
            return null;
        } // end of if statement

        // resolve the result
        if(getBegin().compareTo(getEnd() ) < 0 ) {
            result = getBegin();
        } else {
            result = getEnd();
        } // end of if else statement

        // return the result
        return result;
    } // end of method getMinimumValue()


    /**
     * Removes all basic intervals with neutral direction from the provided array of basic intervals
     * and returns the so modified array.
     *
     * @param intervals Array of basic intervals. If null is provided to this argument or the provided array
     *                  contains null element, error will be generated.
     * @return Array of basic intervals containing no basic intervals with neutral direction. If all basic
     *         intervals in the provided array have neutral direction the method will return null.
     */
    public static BasicInterval[] discardNeutrals(BasicInterval intervals[] ) {
        // declare local variables:
        BasicInterval result[]=null;            // the result to be returned by this method
        Vector <BasicInterval> buffer;          // vector for temporary storage of basic intervals
        // end of local variables deckaration.


        // check for null pointer
        if(intervals == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "discardNeutrals");

            // exit from method
            return result;

        } // end of if statement

        // check for null array element
        for(int c = 0; c < intervals.length; c++) {
            // check if the current element is null
            if(intervals[c] == null) {
                // error ..
                eHandler.newError(ErrorType.NULL_ARRAY_ELEMENT, "discardNeutrals");

                // exit from method
                return result;
            } // end of if statement

        } // end of for loop

        // initialize the buffer vector
        buffer = new Vector <BasicInterval> ();

        // main discarding loop
        for(int c = 0; c < intervals.length; c++) {

            // check if the current element is not with neutral direction
            if(intervals[c].getDirection() != Direction.NEUTRAL) {
                // add the current element to the buffer
                buffer.add(new BasicInterval(intervals[c] ) );
            } // end of if statement

        } // end of for loop

        // store the vector buffer in the result
        result = new BasicInterval[buffer.size() ];
        for(int c=0; c < result.length; c++) {
            result[c] = new BasicInterval(buffer.elementAt(c) );
        } // end of for loop

        // return the result
        return result;

    } // end of method discardNeutrals()


    /**
     * Checks if the 2 provided basic intervals are intercected.
     *
     * @param first BasicInterval to be checked for intercection. Providing null to this
     *              argument will generate error.
     * @param second BasicInterval to be checked for intercection. Providing null to this
     *              argument will generate error.
     *
     * @return True if the basic intervals are intercected, false otherwise.
     */
    public static boolean areIntercected(BasicInterval first , BasicInterval second ) {
        // declare local variables:
        boolean result = false;             // the result to be returned by this method
        BigDecimal beginDifference;         // numeric difference between the begining points of the 2 basic intervals
        BigDecimal endDifference;           // numeric difference between the ending points of the 2 basic intervals
        // end of local variables declaration


        // check for null pointers
        if(first == null || second == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "areIntercected");

            // exit from method
            return result;
        } // end of if statement


        // calculate the beginDifference
        beginDifference = first.getBegin().subtract(second.getBegin() );

        // calculate the endDifference
        endDifference = first.getEnd().subtract(second.getEnd() );

        // check if the two differencies have different signs
        if(beginDifference.compareTo( BigDecimal.ZERO ) != endDifference.compareTo( BigDecimal.ZERO) ) {
            // there is intercection
            result = true;
        } // end of if statement

        // return the result
        return result;

    } // end of method areIntercected


    /**
     * Checks if the 2 provided basic intervals are strictly intercected.
     *
     * @param first BasicInterval to be checked for intercection. Providing null to this
     *              argument will generate error.
     * @param second BasicInterval to be checked for intercection. Providing null to this
     *              argument will generate error.
     *
     * @return True if the basic intervals are intercected, false otherwise.
     */
    public static boolean areStrictlyIntercected(BasicInterval first , BasicInterval second ) {
        // declare local variables:
        boolean result = false;             // the result to be returned by this method
        BigDecimal beginDifference;         // numeric difference between the begining points of the 2 basic intervals
        BigDecimal endDifference;           // numeric difference between the ending points of the 2 basic intervals
        // end of local variables declaration


        // check for null pointers
        if(first == null || second == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "areIntercected");

            // exit from method
            return result;
        } // end of if statement


        // calculate the beginDifference
        beginDifference = first.getBegin().subtract(second.getBegin() );

        // calculate the endDifference
        endDifference = first.getEnd().subtract(second.getEnd() );

        // check if the two differencies have different signs
        if(beginDifference.compareTo( BigDecimal.ZERO ) != endDifference.compareTo( BigDecimal.ZERO) ) {
            // there is intercection
            // check if the intercection is strict
            if(beginDifference.compareTo(BigDecimal.ZERO) != 0 || endDifference.compareTo(BigDecimal.ZERO) != 0) {
                // the two basic intervals are strictly intercected
                result = true;
            } // end of if statement.

        } // end of if statement

        // return the result
        return result;

    } // end of method areStrictlyIntercected

} // end of class BasicInterval
