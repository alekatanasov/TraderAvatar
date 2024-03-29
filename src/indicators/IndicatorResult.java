/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package indicators;


// imports:
import error.ErrorHandler;
import error.ErrorType;
import java.util.Vector;
import marketanalysis.Interval;



/**
 * Capsulates the result of market analysis made by an indicator.
 *
 * @author Alexandar Atanasov
 */
public class IndicatorResult {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** The indicator which created this result. */
    private IndicatorList creator;

    /** The data generated by the indicator which produced this result*/
    private Vector <Interval> indicatorData;

    // end of member variables declaration


    // initialization of static members:
    static {
        eHandler = new ErrorHandler("IndicatorResult");
    } // end of initialization of static members


    /**
     * Constructor.
     *
     */
    public IndicatorResult(Vector <Interval> data , IndicatorList creatorIndicator) {
        // set members
        setIndicatorData(data);
        setCreator(creatorIndicator);
    } // end of constructor


    /**
     * Copy Constructor.
     *
     * @param result Another instance of the IndicatorResult class.
     */
    public IndicatorResult(IndicatorResult result) {
        //
        this(result.getIndicatorData() , result.getCreator() );

    } // end of Copy Constructor


    /**
     * Sets the indicatorData member.
     *
     * @param data The data produced by the indicator which created this result. If null is provided to this
     *              argument error will be generated.
     */
    private void setIndicatorData( Vector <Interval> data) {
        // check for null pointer
        if( data == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setIndicatorData");

            // exit from method
            return;
        } // end of if statement

        // set the indicatorData
        indicatorData = data;

    } // end of method setIndicatorData()


    /**
     * Returns the indicatorData member.
     *
     * @return The data produced by the indicator which generated this result.
     */
    public Vector<Interval> getIndicatorData() {
        return indicatorData;
    } // end of method getIndicatorData()


    /**
     * Sets the creator member.
     *
     * @param creatorIndicator The indicator which created this result. If null is provided to this argument
     *                         error will be generated.
     */
    private void setCreator(IndicatorList creatorIndicator) {
        // check for null pointer
        if(creatorIndicator == null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setCreator");

            // exit from method
            return;
        } // end of if statement

        // set the creator
        creator = creatorIndicator;

    } // end of method setCreator()


    /**
     * Returns the creator member.
     *
     * @return The indicator which produced this result.
     */
    public IndicatorList getCreator() {
        return creator;
    } // end of method getCreator()

} // end of class IndicatorResult
