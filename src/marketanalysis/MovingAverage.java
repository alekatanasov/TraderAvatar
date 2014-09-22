/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marketanalysis;


// imports:
import error.ErrorHandler;
import error.ErrorType;
import java.math.BigDecimal;


/**
 * Interval made from data points each representing average value for fixed time period.
 *
 * @author Alexandar Atanasov
 */
public class MovingAverage extends Interval {
    // declare member variables:

    /** Types of moving averages*/
    public enum MovingAverageType { SIMPLE , EXPONENTIAL }

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The type of this moving average*/
    private final MovingAverageType type;

    /** The interval from which this moving average was created. */
    private Interval parentInterval;

    /** The period of this moving average. */
    private int period;
    // end of member variables declaration


    // initialization of static members:
    static {
        eHandler = new ErrorHandler("MovingAverage");
    } // end of initialization of static members


    /**
     * Constructor.
     *
     * @param averages The elements of the moving average. If the null is provided to this argument or the
     *                 provided array contains null element error will be generated.
     *
     * @param periodVal The period of this moving average. If zero or negative value is provided to this
     *                  argument error will be generated.
     * @param intervalScale The scale of the elements in this MovingAverage. If negative number is provided to
     *                      to this argument error will be generated.
     * @param parent The interval from which this moving average was created. Null may be provided to this
     *               argument.
     */
    public MovingAverage(BigDecimal averages[] , int intervalScale , int periodVal , MovingAverageType maType ,
                         Interval parent) {
        // call to super constructor
        super(averages , intervalScale);

        // set the type of the moving average
        type = maType;

        // set members
        setParentInterval(parent);
        setPeriod(periodVal);
    } // end of constructor


    /**
     * Copy constructor.
     *
     * @param ma Another instance of the MovingAverage class.
     */
    public MovingAverage(MovingAverage ma) {
        this(ma.getElements() , ma.getParentInterval().getScale() , ma.getPeriod() , ma.getType() ,
                ma.getParentInterval() );
    } // end of copy constructor


    /**
     * Sets the parentInterval member.
     * 
     * @param parent The interval from which this moving average was created. Null may be provided to this
     *               argument.
     */
    private void setParentInterval(Interval parent) {
        // set the parentInterval
        if(parent != null) {
            parentInterval = new Interval(parent);
        } else {
            parentInterval = null;
        } // end of if else statement

    } // end of method setParentInterval()


    /**
     * Returns the parentInterval member.
     *
     * @return The interval from which this moving average was constructed.
     */
    public Interval getParentInterval() {
        return parentInterval;
    } // end of method getParentInterval()


    /**
     * Sets the period member.
     *
     * @param periodVal The period of this moving average. If zero or negative value is provided to this
     *                  argument error will be generated.
     */
    private void setPeriod(int periodVal) {
        // check for logical error
        if(periodVal < 1) {
            // error
            eHandler.newError(ErrorType.INVALID_ARGUMENT, "setPeriod" , "Non positive period");

            // set to deafult
            period = 1;

            // exit
            return;
        } // end of if statement

        // set the period
        period = periodVal;

    } // end of method setPeriod()


    /**
     * Returns the period member.
     *
     * @return Positive integer representing this moving average's period.
     */
    public int getPeriod() {
        return period;
    } // end of method getPeriod()


    /**
     * Returns the type member.
     *
     * @return The type of this moving average
     */
    public MovingAverageType getType() {
        return type;
    } // end of method getType()


    /**
     * Subtracts the provided MovingAverage from this one.
     *
     * @param subtrahend The MovingAverage to be subtracted from this one. If null is provided to this argument
     *                   error will be generated.
     *
     * @return MovingAverage
     */
    public MovingAverage subtract(MovingAverage subtrahend) {
        // declare local variables:
        MovingAverage result = null;
        Interval buffer;
        // end of local variables declaration


        // check for null pointer
        if(subtrahend == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "subtract");

            // exit from method
            return result;
        } // end of if statement

        //
        buffer = this.subtract(subtrahend , AlignmentMode.END);

        // create the result
        result = new MovingAverage(buffer.getElements() , buffer.getScale() , this.getPeriod() ,
                                   this.getType() , null);

        // return the result
        return result;

    } // end of method subtract


} // end of class MovingAverage
