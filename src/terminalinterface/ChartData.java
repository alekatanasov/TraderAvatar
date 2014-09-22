/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;


// imports:
import error.*;



/**
 *
 * @author Alexandar Atanasov
 */
public class ChartData {
    // declare member variables:
    private static ErrorHandler eHandler;
    private int closePrices[];
    private int highPrices[];
    private int lowPrices[];
    private int volumes[];
    // end of member variables declaration


    // initialization of static members
    static {
        eHandler = new ErrorHandler("ChartData");
    } // end of static members initialization


    // constructor
    public ChartData(int close[] , int high[] , int low[] , int vol[]) {
        // check for null pointers
        if(close == null || high == null || low == null || vol == null) {
            // error
            eHandler.newError(ErrorType.NULL_ARGUMENT, "Constructor");

            // exit from method
            return;
        } // end of if statement

        // load the arguments
        closePrices = close;
        highPrices = high;
        lowPrices = low;
        volumes = vol;

    } // end of constructor


    // returns the closePrices member
    public int[] getClosePrices() {
        return closePrices;
    } // end of method


    // returns the last n closePrices; if n is greater or equal  to  the lenght of the closePrices array
    // the method will return all closePrices; if n is lesser than 1 all prices will be returned
    public int[] getClosePrices(int n) {
        // declare local variables:
        int result[] = null;
        // end of local variables declaration


        // check for logical error
        if( n < 1) {
            return closePrices;
        } // end of if statement

        // check for logical error
        if(n > closePrices.length) {
            return closePrices;
        } // end of if statement

        // initialize the result
        result = new int[n];

        // copy the last n prices to the result
        System.arraycopy(closePrices, 0, result, 0, n);

        // return the result
        return result;
    } // end of method getClosePrices()


    // returns the highPrices member
    public int[] getHighPrices() {
        return highPrices;
    } // end of method


    // returns the last n highPrices; if n is greater or equal  to the  lenght of the highPrices array
    // the method will return all highPrices; if n is lesser than 1 all prices will be returned
    public int[] getHighPrices(int n) {
        // declare local variables:
        int result[] = null;
        // end of local variables declaration


        // check for logical error
        if( n < 1) {
            return highPrices;
        } // end of if statement

        // check for logical error
        if(n > highPrices.length) {
            return highPrices;
        } // end of if statement

        // initialize the result
        result = new int[n];

        // copy the last n prices to the result
        System.arraycopy(highPrices, 0, result, 0, n);

        // return the result
        return result;
    } // end of method getHighPrices()


    // returns the lowPrices member
    public int[] getLowPrices() {
        return lowPrices;
    } // end of method


    // returns the last n lowPrices; if n is greater or equal  to the lenght of the lowPrices array
    // the method will return all lowPrices; if n is lesser than 1 all prices will be returned
    public int[] getLowPrices(int n) {
        // declare local variables:
        int result[] = null;
        // end of local variables declaration


        // check for logical error
        if( n < 1) {
            return lowPrices;
        } // end of if statement

        // check for logical error
        if(n > lowPrices.length) {
            return lowPrices;
        } // end of if statement

        // initialize the result
        result = new int[n];

        // copy the last n prices to the result
        System.arraycopy(lowPrices, 0, result, 0, n);

        // return the result
        return result;
    } // end of method getLowPrices()


    // returns the volumes member
    public int[] getVolumes() {
        return volumes;
    } // end of method


    // returns the last n volumes; if n is greater or equal  to the lenght of the volumes array
    // the method will return all volumes; if n is lesser than 1 all volumes will be returned
    public int[] getVolumes(int n) {
        // declare local variables:
        int result[] = null;
        // end of local variables declaration


        // check for logical error
        if( n < 1) {
            return volumes;
        } // end of if statement

        // check for logical error
        if(n > volumes.length) {
            return volumes;
        } // end of if statement

        // initialize the result
        result = new int[n];

        // copy the last n prices to the result
        System.arraycopy(volumes, 0, result, 0, n);

        // return the result
        return result;
    } // end of method getVolumes()


    // this method will return the minimal value from the provided array if integers
    public static int getMinimum(int integers[] ) {
        // declare local variables:
        int result = Integer.MAX_VALUE;
        // end of local variables declaration


        // main loop
        for(int c = 0; c < integers.length; c++) {
            // check if the current int is lesser than the buffer
            if(integers[c] < result) {
                // store the current in in the buffer
                result = integers[c];
            } // end of if statement

        } // end of for loop

        // return the result
        return result;

    } // end of method getMinimum()


    // this method will return the maximum value from the provided array of integers
    public static int getMaximum(int integers[] ) {
        // declare local variables:
        int result = Integer.MIN_VALUE;
        // end of local variables declaration


        // main loop
        for(int c = 0; c < integers.length; c++) {
            // check if the current int is lesser than the buffer
            if(integers[c] > result) {
                // store the current in in the buffer
                result = integers[c];
            } // end of if statement

        } // end of for loop

        // return the result
        return result;

    } // end of method getMaximum()


} // end of class ChartData
