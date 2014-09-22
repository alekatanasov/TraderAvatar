/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marketanalysis;



// imports:
import error.ErrorHandler;
import error.ErrorType;
import indicators.IndicatorList;
import java.util.Vector;



/**
 * Regulates various market analysis properties like number and type of used indicators.
 *
 * @author Alexandar Atanasov
 */
public class AnalysisFormat {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    /** Default analysis options*/
    private static AnalysisOptions DEFAULT_OPTIONS[];

    /** The indicators to be used in analysis of the market. */
    private IndicatorList indicators[];

    /** Parameters of the indicators which will be used in market analysis. */
    private Vector <String> indicatorParameters[];

    /** Special options for market analysis. */
    private AnalysisOptions analysisOptions[];

    // end of member variables declaration


    // initialization of static variables:
    static {
        // initialize the ErrorHandler
        eHandler = new ErrorHandler("AnalysisFormat");

        // initialize the DEFAULT_OPTIONS
        DEFAULT_OPTIONS = new AnalysisOptions[1];
        DEFAULT_OPTIONS[0] = AnalysisOptions.STANDART;

    } // end of static members initialization


    /**
     * Constructor.
     *
     * @param analysisIndicators The indicators which will be used in market analysis. If null is provided to this
     *                           argument no indicators will be used.
     * @param parameters The parameters of the indicators which will be used in market analysis. If null is
     *                   provided to this argument and the analysisIndicators argument is not null
     *                   error will be generated.
     * @param options
     */

    public AnalysisFormat(IndicatorList analysisIndicators[] , Vector <String> parameters[] ,
                          AnalysisOptions options[] ) {
        // set the members
        setIndicators(analysisIndicators);
        setIndicatorParameters(parameters);
        setAnalysisOptions(options);

    } // end of constructor


    /**
     * Default Constrcutor
     */
    public AnalysisFormat () {
        this(null , null , null);
    } // end of default constructor


    /**
     * Copy constructor.
     *
     * @param aFormat Another instance of the AnalysisFormat class.
     */
    public AnalysisFormat ( AnalysisFormat aFormat) {
        //
        this(aFormat.getIndicators() , aFormat.getIndicatorParameters() ,
             aFormat.getAnalysisOptions() );

    } // end of copy constructor


    /**
     * Sets the indicators member.
     *
     * @param AnalysisIndicators The indicators which will be used in market analysis. If null is provided to this
     *                           argument no indicators will be used.
     */
    private void setIndicators(IndicatorList analysisIndicators[] ) {
        // set the indicators
        indicators = analysisIndicators;

    } // end of method setIndicators()


    /**
     * Returns the indicators member.
     *
     * @return The indicators which are used in analysis format.
     */
    public IndicatorList[] getIndicators() {
        return indicators;
    } // end of method getIndicators()


    /**
     * Sets the indicatorParameters member. This method should be used after the setIndicators() method in the
     * constructor.
     *
     * @param parameters The parameters of the indicators which will be used in market analysis. If null is
     *                   provided to this argument and the indicators member is not null
     *                   error will be generated.
     */
    private void setIndicatorParameters( Vector <String> parameters[] ) {
        // check for null pointer
        if(parameters == null && indicators != null) {
            // error ..
            eHandler.newError(ErrorType.NULL_ARGUMENT, "setIndicatorParameters");

            // exit from method
            return;

        } // end of if statement

        // set the indicatorParameters
        indicatorParameters = parameters;

    } // end of method setIndicatorParameters()


    /**
     * Returns the indicatorParameters member.
     *
     * @return The parameters of the indicators used in market analysis.
     */
    public  Vector <String> [] getIndicatorParameters() {
        return indicatorParameters;
    } // end of method getIndicatorParameters()


    /**
     * Sets the analysisOptions member.
     *
     * @param options The analysis options which will be used in market analysis. If null is provided
     *                to this argument the analysis options will be set to default value.
     */
    private void setAnalysisOptions(AnalysisOptions options[]) {
        // check for null
        if(options == null) {

            // set to default
            analysisOptions = DEFAULT_OPTIONS;

            // exit from method
            return;
        } // end of if statement

        // set the analysisOptions
        analysisOptions = options;

    } // end of method


    /**
     * Returns the analysisOptions member.
     *
     * @return The special options used in market analysis. If no options are used this method will
     *         return null.
     */
    public AnalysisOptions[] getAnalysisOptions() {
        return analysisOptions;
    } // end of method





} // end of class AnalysisFormat
