/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package indicators;



// imports:
import java.util.Vector;



/**
 * Contains all created indicators.
 *
 * @author Alexandar Atanasov
 */
public enum IndicatorList {

    /** EMA indicator. */
    EMA(true , "-ema") ,

    /** SMA indicator. */
    SMA(true , "-sma") ,

    /** MACD indicator. */
    MACD(true , "-macd");


    // declare member variables:

    /** Shows if the indicator is parametric indicator*/
    private final boolean isParametric;

    /** Short string represenation of this indicator's name. */
    private final String codeName;

    // end of member variables declaration


    /**
     * Constructor.
     *
     * @param  parametric Shows if this indicator is parametric indicator.
     * @param  code Short string represenation of this indicator's name.
     */
    private IndicatorList(boolean parametric , String code) {
        this.isParametric = parametric;
        this.codeName = code;
    } // end of constructor


    /**
     * Shows if this indicator is parametric indicator.
     *
     * @return True if this indicator uses parameters , false otherwise.
     */
    public boolean isParametric() {
        return isParametric;
    } // end of method isParametric()


    public String getCodeName() {
        return codeName;
    } // end of method getNameCode()


    /**
     * Return the codeNames of all indicators found in the list.
     *
     * @return The code names of all indicators in the IndicatorList.
     */
    public static String[] getCodeNames() {
        // declare local variables:
        String result[];
        IndicatorList list[];
        // end of local variables declaration


        // initialize the result
        result = new String[values().length];

        // get the list
        list = values();

        // main extraction loop
        for(int c = 0; c < list.length; c++) {
            // store the current codeName
            result[c] = list[c].getCodeName();
        } // end of for loop

        // return the result
        return result;

    } // end of method getNameCodes()


    /**
     * Returns the enum constant which owns the provided code name.
     *
     * @param code The codeName of the enum constant.
     *
     * @return The enum constant to which the provided code belongs.
     */
    public static IndicatorList getEnumByCodeName(String code) {
        // declare local variables:
        IndicatorList result=null;
        IndicatorList list[];
        // end of local variables declaration


        // get the list
        list = values();

        // main conversion loop
        for(int c =0; c <list.length; c++) {
            //
            if(list[c].getCodeName().contentEquals(code) ) {
                result = list[c];
            } // end of if statement

        } // end of for loop

        // return the result
        return result;

    } // end of method getEnumByCodeName()


    /**
     * Creates and returns instance of the specified indicator.
     *
     * @param code The code which denotes the desired indicator.
     * @param parameters The parameters of the indicator to be created. For non parameter indicators this
     *                   argument may be null, for parametric indicators null will generated error.
     *
     * @return Instance of the specified indicator. If some error occurs this method will return null.
     */
    public static Indicator getInstanceByCodeName(String code , Vector <String> parameters ) {
        // declare local variables:
        Indicator result=null;
        IndicatorList indicator;
        // end of local variables declaration


        // get the indicator
        indicator = getEnumByCodeName(code);

        //
        switch(indicator) {
            case EMA:
                result = new IndicatorEMA(parameters);
            break;

            case SMA:
                result = new IndicatorSMA(parameters);
            break;

            case MACD:
                result = new IndicatorMACD(parameters);
            break;

            default:
                // error ..
        } // end of if statement

        // return the result
        return result;

    } // end of method getInstanceByCodeName()

} // end of enum IndicatorList
