/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package traderavatar;


// imports:
import databases.DatabaseConnector;
import javax.swing.JOptionPane;
import terminalinterface.MainInterfaceFrame;



/**
 *
 * @author Alexandar Atanasov
 */
public class TraderAvatarMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // declare local variables:
        String commandLine="";
        String dbName="";
        DatabaseConnector dbConnector;
        MainInterfaceFrame interfaceMain;
        // end of local variables declaration


        // resolve the commandLine
        for(int c =0; c < args.length; c++) {
            commandLine+=" ";
            commandLine+=args[c];

        } // end of for loop

        // check for correct command line arguments
        if(verifyCommandLine(args) )
        {
            // command line is ok;
            // get the database name
            dbName = args[0];
        }
         else
        {
            // there is some error in the command line arguments;
            // error .. show error message to the user
            JOptionPane.showMessageDialog(null, "Command Line is: " + commandLine
                                          + "\nIncorect command line arguments have been provided."
                                          + " Terminating application ..");
            // exit from program
            System.exit(1);
        } // end of if else statement 

        // initialize the DatabaseConnector class
        dbConnector = new DatabaseConnector(args[0] , args[1] , args[2] ,args[3] ,
                                            Integer.parseInt( args[4] ) );

        // create the main frame
        interfaceMain = new MainInterfaceFrame(dbName , dbConnector, args[1], args[4]);
        
    } // end of function main


    // this function verifies the command line arguments
    private static boolean verifyCommandLine(String arguments[] ) {
        // declare local variables:
        boolean result=true;
        // end of local variables declaration


        // check the number of arguments
        if(arguments.length != 5) {
            result = false;

            // exit from method
            return result;
        } // end of if statement

        // check if the second argument ( the number of max bars ) is a valid integer
        try {
            Integer.parseInt(arguments[1] );
        } catch(NumberFormatException e) {
            // the string is not valid integer; the command line arguments are invalid
            result = false;
        } // end of try catch statement

        // check if the fift argument ( the number digits afther the dot ) is a valid integer
        try {
            Integer.parseInt(arguments[4] );
        } catch(NumberFormatException e) {
            // the string is not valid integer; the command line arguments are invalid
            result = false;
        } // end of try catch statement

        // return the result
        return result;
    } // end of method verifyCommandLine()

} // end of class TraderAvatarMain
