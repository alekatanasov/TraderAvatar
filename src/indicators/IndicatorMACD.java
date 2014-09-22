/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package indicators;



// imports:
import error.ErrorHandler;
import java.util.Vector;
import javax.swing.JOptionPane;
import marketanalysis.Interval;
import marketanalysis.MovingAverage;


/**
 *
 * @author Alexandar Atanasov
 */
public class IndicatorMACD extends ParametricIndicator {
    // declare member variables:

    /** The number of parameters used by this indicator. */
    private static final int NUMBER_OF_PARAMETERS = 3;

    /** Instance of the ErrorHandler class used for error handling. */
    private static ErrorHandler eHandler;

    /** The lenght of the  short ema. */
    private int shortEmaLenght;

    /** The lenght of the  long ema. */
    private int longEmaLenght;

    /** The lenght of the  signal ema. */
    private int signalEmaLenght;

    // end of member variables declaration


    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("IndicatorMACD");
    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param parameters The parameters for this indicator.
     */
    public IndicatorMACD( Vector <String> parameters ) {
        super(parameters);
    } // end of constructor


    //
    public IndicatorResult indicatorMain() {
        // declare local variables:
        IndicatorResult result = null;
        Vector <Interval> data;
        MovingAverage emaShort;
        MovingAverage emaLong;
        MovingAverage emaResult;
        MovingAverage emaSignalLine;
        // end of local variables declaration


        // get the short and long EMAs
        emaShort = getIntervalData().getCloseInterval().generateExponentialMovingAverage(shortEmaLenght);
        emaLong = getIntervalData().getCloseInterval().generateExponentialMovingAverage(longEmaLenght);

        // generate the emaResult
        emaResult = emaShort.subtract(emaLong);

        // generate the signal line
        emaSignalLine = emaResult.generateExponentialMovingAverage(signalEmaLenght);

        // generate the indicator data
        data = new Vector <Interval> ();
        data.add(emaResult);
        data.add(emaSignalLine);

        // create the result
        result = new IndicatorResult(data , IndicatorList.MACD);

        // return the result
        return result;
        
    } // end of method indicatorMain()


    //
    public Vector <String> getDefaultParameters() {
        // declare local variables:
        Vector <String> result;
        String buffer;
        // end of local variables declaration


        // init the result
        result = new Vector <String>();

        // resolve the result
        buffer = "12";
        result.add(buffer);
        buffer = "26";
        result.add(buffer);
        buffer = "9";
        result.add(buffer);

        // return the result
        return result;

    } // end of method getDefaultParameters()


    //
    public int getNumberOfParameters() {
        return NUMBER_OF_PARAMETERS;
    } // end of method getNumberOfParameters()



    //
    public void setParameters( Vector <String> parameters) {
        // declare local variables:
        // end of local variables declaration
        

        // set the parameters
        shortEmaLenght = Integer.parseInt(parameters.elementAt(0) );
        longEmaLenght = Integer.parseInt(parameters.elementAt(1) );
        signalEmaLenght = Integer.parseInt(parameters.elementAt(2) );

    } // end of method setParameters()


    //
    public ParameterType[] getParameterTypes() {
        // declare local variables:
        ParameterType result[];
        // end of local variables declaration


        // initialize the result
        result = new ParameterType[NUMBER_OF_PARAMETERS];

        //
        result[0] = ParameterType.INTEGER;
        result[1] = ParameterType.INTEGER;
        result[2] = ParameterType.INTEGER;

        // return the result
        return result;

    } // end of method getParameterTypes()


} // end of class IndicatorMACD
