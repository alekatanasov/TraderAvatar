/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marketanalysis;

/**
 *
 * @author Alexandar Atanasov
 */
public enum AnalysisOptions {

    /** */
    ALWAYS_OPEN_LONG("#aol") ,

    /** */
    ALWAYS_OPEN_SHORT("#aos") ,

    /** */
    BASIC_ANALYSIS("#ba") ,

    /** */
    STANDART("#std");
    
    /** Short string represenation of this indicator's name. */
    private final String shortCode;
    
    
    /**
     * Constructor.
     *
     * @param stringCode Short string represenation of this option's name.
     */
    private AnalysisOptions(String stringCode) {
        this.shortCode = stringCode;
    } // end of Constructor


    /**
     * Returns the shortCode member.
     *
     * @return The shortCode of this enum constant.
     */
    public String getShortCode() {
        return shortCode;
    } // end of method


    /**
     * Returns all short codes.
     *
     * @return The short codes of all enums.
     */
    public static String[] getShortCodes() {
        // declare local variables:
        AnalysisOptions[] enums;
        String result[];
        // end of local variables declaration

        // get the enums
        enums = values();

        // initialize the result
        result = new String[enums.length];

        // get the short codes
        for(int c =0; c < enums.length; c++) {
            result[c] = enums[c].getShortCode();
        } // end of for loop

        // return the result
        return result;

    } // end of method getShortCodes()


    /**
     * Returns the enum associated with this enum.
     *
     * @param code String code denoting an enum constant.
     *
     * @return The enum constant associated with the provided code.
     */
    public static AnalysisOptions getEnumByShortCode(String code) {
        // declare local variables:
        AnalysisOptions result = null;
        AnalysisOptions options[];
        // end of local variables declaration


        // get the options
        options = values();

        // find the result
        for(int c =0; c < options.length; c++) {
            // check the current enum
            if(options[c].getShortCode().contentEquals(code) ) {
                result = options[c];
            } // end of if statement

        } // end of for loop


        // return the result
        return result;

    } // end of method


    /**
     * Checks if the provided options are conflicting.
     *
     * @param options Analysis options to be checked for conflict.
     *
     * @return Null if the provided options are not in conflict.
     */
    public static AnalysisOptions[] areOptionsConflicting(AnalysisOptions options[] ) {
        // declare local variables:
        AnalysisOptions result[] = null;

        boolean standart = false;
        boolean alwaysOpenLong = false;
        // end of local variables declaration


        // check for null pointer
        if(options == null) {
            return result;
        } // end of if statement

        // parse the provided options
        for(int c=0; c < options.length; c++) {
            //
            switch(options[c] ) {
                case ALWAYS_OPEN_LONG:
                    alwaysOpenLong = true;
                break;

                case STANDART:
                    standart = true;
                break;

            } // end of switch statement

        } // end of for loop


        // check for always open long and standart
        if(alwaysOpenLong && standart) {
            // conflict ...
            result = new AnalysisOptions[2];
            result[0] = AnalysisOptions.ALWAYS_OPEN_LONG;
            result[0] = AnalysisOptions.STANDART;
        } // end of if statement

        // return the result
        return result;

    } // end of method

} // end of enum AnalysisOptions
