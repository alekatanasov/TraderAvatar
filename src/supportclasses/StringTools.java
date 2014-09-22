/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package supportclasses;



// imports:
import error.ErrorHandler;



/**
 *
 * @author Alexandar Atanasov
 */
public class StringTools {
    // declare member variables:

    /** Instance of the ErrorHandler used for managing errors. */
    private static ErrorHandler eHandler;

    // end of member variables declaration
    
    
    // Initialization of static members:
    static {
        eHandler = new ErrorHandler("StringTools");
    } // end of static members initialization
    
    
    /**
     * 
     * @param string
     * @return
     */
    public static String[] ExtractWordsFromString(String string) {
        // declare local variables:
        int lastWhitespace = 0;
        int numberOfWords = 0;
        String buffer="";
        String bufferArray[];
        String result[]= null;
        // end of local variables declaration
        
        
         // main converting loop
        for(int c = 0; c < string.length() ; c++) {
            // check if the current character is white space
            if(string.charAt(c) == '_') {
                buffer = string.substring(lastWhitespace, c);

                // check if the buffer is actually existing string
                if(buffer.length() > 0) {

                    // check if the first character of the buffer is white space
                    if(buffer.charAt(0) == '_') {
                         // remove the whitespace
                        buffer = buffer.substring(1);
                    } // end of if statement

                    // check if the result array is initialized
                    if(result != null) {
                        // add the buffer to the result array
                        bufferArray = new String[result.length+1];
                        System.arraycopy(result, 0, bufferArray, 0, result.length);
                        bufferArray[bufferArray.length-1] = buffer;
                        result = bufferArray;
                    } else {
                        // initialize the word array and store the buffer as the first element
                        result = new String[1];
                        result[0] =buffer;
                    } // end of if else statement
                    
                } // end of if statement

                // new white space encountered
                lastWhitespace = c;
            } // end of if statement

        } // end of for loop

        // resolve last buffer ( if it exists )
        buffer = string.substring(lastWhitespace);

        // add the last buffer ( if it exists )
        if(buffer.length() >0) {
            // check if the first character of the buffer is white space
            if(buffer.charAt(0) == '_') {
                // remove the whitespace
                buffer = buffer.substring(1);
            } // end of if statement
            
            // check if the result array is initialized
            if(result != null) {
                // add the buffer to the result array
                bufferArray = new String[result.length+1];
                System.arraycopy(result, 0, bufferArray, 0, result.length);
                bufferArray[bufferArray.length-1] = buffer;
                result = bufferArray;
            } else {
                // initialize the word array and store the buffer as the first element
                result = new String[1];
                result[0] =buffer;
            } // end of if else statement

        } // end of if statement

        // return the result
        return result;
        
    } // end of method ExtractWordsFromString()

} // end of class StringTools
