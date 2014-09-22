/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package indicators;



// imports:
import error.ErrorHandler;
import java.util.Vector;
import marketanalysis.Interval;
import marketanalysis.MovingAverage;



/**
 *
 * @author Alexandar Atanasov
 */
public class IndicatorSMA extends ParametricIndicator {
    // declare member variables:

    /** The number of parameters used by this indicator. */
    private static final int NUMBER_OF_PARAMETERS = 1;

    /** Instance of the ErrorHandler class used for managing errors. */
    private static ErrorHandler eHandler;

    /** The length of this SMA. */
    private int smaLength;

    // end of member variables declaration


    // Initialization of static members
    static {
        eHandler = new ErrorHandler("IndicatorSMA");
    } // end of static members initialization
    
    /**
     * Constructor.
     *
     * @param parameters The parameters of this indicator.
     */
    public IndicatorSMA(Vector <String> parameters) {
        // call to super constructor
        super(parameters);
    } // end of constructor


    //
    public IndicatorResult indicatorMain() {
        // declare local variables:
        Vector <Interval> data;
        IndicatorResult result = null;  //
        MovingAverage sma;              //
        // end of local vriables declaration


        // get the sma
        sma = getIntervalData().getCloseInterval().generateSimpleMovingAverage(smaLength);

        // create the data
        data = new Vector <Interval>();
        data.add(sma);

        // create the result
        result = new IndicatorResult(data , IndicatorList.SMA);

        // return the result
        return result;
        
    } // end of method oracleIndicatorMain()


    //
    public  int getNumberOfParameters() {
        return this.NUMBER_OF_PARAMETERS;
    } // end of method


    //
    public Vector <String> getDefaultParameters() {
        // declare local variables:
        Vector <String> result;
        String buffer;
        // end of local variables declaration


        // init the result
        result = new Vector <String>();

        // resolve the result
        buffer = "10";
        result.add(buffer);

        // return the result
        return result;

    } // end of method getDefaultParameters()


    //
    public ParameterType[] getParameterTypes() {
        // declare local variables:
        ParameterType result[];
        // end of local variables declaration


        // initialize the result
        result = new ParameterType[NUMBER_OF_PARAMETERS];

        //
        result[0] = ParameterType.INTEGER;

        // return the result
        return result;

    } // end of method getParameterTypes()


    //
    public void setParameters( Vector <String> parameters) {
        //
        smaLength = Integer.parseInt(parameters.elementAt(0) );

    } // end of method

} // end of class IndicatorSMA
