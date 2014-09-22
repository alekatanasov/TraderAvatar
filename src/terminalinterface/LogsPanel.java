/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;


// imports:
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.GroupLayout;
import javax.swing.JPanel;


/**
 * Used for displaying logs. This window holds all log data and provides capability for showing filtered
 * information.
 *
 * @author Alexandar Atanasov
 */
public class LogsPanel extends JPanel{
    // declare member variables:

    /** */
    private static final int ALL_LOGS_BUTTON_ID = 1;

    /** */
    private static final int COMMAND_LOGS_BUTTON_ID = 2;

    /** */
    private static final int ERROR_LOGS_BUTTON_ID = 3;

    /** */
    private static final int DATA_UPDATE_LOGS_BUTTON_ID = 4;

    /** */
    private static final int ORDER_LOGS_BUTTON_ID = 5;

    /** */
    private TextOutputWindow allLogs;

    /** */
    private TextOutputWindow commandLogs;

    /** */
    private TextOutputWindow errorLogs;

    /** */
    private TextOutputWindow dataUpdateLogs;

    /** */
    private TextOutputWindow orderLogs;

    /** */
    private LogButton allLogsButton;

    /** */
    private LogButton commandLogsButton;

    /** */
    private LogButton errorLogsButton;

    /** */
    private LogButton dataUpdateLogsButton;

    /** */
    private LogButton orderLogsButton;

    /** */
    private JPanel logWindowsHolder;

    /** Types of log entrys. */
    public enum LogEntryType{ COMMAND , ARRAY_DATA_UPDATE , ERROR , NEW_ORDER}

    // end of member variables declaration


    /**
     * Constructor.
     *
     */
    public LogsPanel() {
        // set the background
        setBackground(new Color(210 ,210 ,210) );

         // initialize TextOutputWindow members
        initTextOutputWindows();
        initButtons();
        initLogWindowsHolder();

        // create new layout for the logsPanel
        GroupLayout layout = new GroupLayout( this);
        setLayout(layout);

        // make gaps between the components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        // set the layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                                  .addGroup(layout.createSequentialGroup()
                                  .addComponent(allLogsButton, 80 ,GroupLayout.DEFAULT_SIZE ,80)
                                  .addComponent(commandLogsButton, 125 ,GroupLayout.DEFAULT_SIZE ,125)
                                  .addComponent(errorLogsButton, 95 ,GroupLayout.DEFAULT_SIZE ,80)
                                  .addComponent(dataUpdateLogsButton, 135 ,GroupLayout.DEFAULT_SIZE ,100)
                                  .addComponent(orderLogsButton, 100 ,GroupLayout.DEFAULT_SIZE ,100) )
                                  .addComponent(logWindowsHolder) );
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                .addComponent(allLogsButton)
                                .addComponent(commandLogsButton)
                                .addComponent(errorLogsButton)
                                .addComponent(dataUpdateLogsButton )
                                .addComponent(orderLogsButton) )
                                .addComponent(logWindowsHolder) );


    } // end of constructor


    /**
     * Initializes the logWindowsHolder member.
     */
    private void initLogWindowsHolder() {
        logWindowsHolder = new JPanel();

        // create new layout for the logsPanel
        CardLayout layout = new CardLayout();
        logWindowsHolder.setLayout(layout);

        // add the log windows to the logWindowHolder
        logWindowsHolder.add(allLogs , "allLogs" );
        logWindowsHolder.add(commandLogs, "commandLogs" );
        logWindowsHolder.add(errorLogs, "errorLogs" );
        logWindowsHolder.add(dataUpdateLogs, "dataUpdateLogs" );
        logWindowsHolder.add(orderLogs, "orderLogs" );

        // show the all logs window
        layout.show(logWindowsHolder, "allLogs");

    } // end of method initLogWindowsHolder(


    /**
     * Initializes all TextOutputWindows.
     */
    private void initTextOutputWindows() {
        // initialize
        allLogs = new TextOutputWindow();
        commandLogs = new TextOutputWindow();
        errorLogs = new TextOutputWindow();
        dataUpdateLogs = new TextOutputWindow();
        orderLogs = new TextOutputWindow();

        // display the time of the starting in the all logs window
        allLogs.appendText( Calendar.getInstance().getTime().toString() + ""
                                  + "   Terminal has been started.", new Color(0, 100 , 250 ) , true);

    } // end of method initTextOutputWindows()


    /**
     * Initializes all button members.
     */
    private void initButtons() {
        // initialize the buttons
        allLogsButton = new LogButton("All Logs" , ALL_LOGS_BUTTON_ID , this);
        commandLogsButton = new LogButton("Command Logs" , COMMAND_LOGS_BUTTON_ID , this);
        errorLogsButton =  new LogButton("Error Logs" , ERROR_LOGS_BUTTON_ID , this);
        dataUpdateLogsButton = new LogButton("Data Update Logs" , DATA_UPDATE_LOGS_BUTTON_ID , this);
        orderLogsButton = new LogButton("Order Logs" , ORDER_LOGS_BUTTON_ID , this);

        // select the allLogsButton
        allLogsButton.setSelected(true);

    } // end of method initButtons()


    /**
     * Adds new entry to the logs.
     *
     * @param logType The type of the log entry.
     * @param logData The text content of the log entry.
     */
    public void addNewEntry(LogEntryType logType , String logData) {
        // declare local variables:
        String entry;
        String currentTime;
        Color entryColor= new Color(90,90,90);
        Calendar calendar;
        SimpleDateFormat dateFormat;
        // end of local variables declaration


        // initialize the calendar and the dateFormat variables
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("HH:mm:ss");

        // get the current time
        currentTime = dateFormat.format(calendar.getTime() );

        // add the time to the log entry
        entry = currentTime;

        // determine the log entry type
        switch (logType) {
            case COMMAND:
                entry+=" Executed command: ";
                entry+=logData;
                entryColor = new Color(0, 0, 215);
                commandLogs.appendText(entry, entryColor, true);
            break;

            case ARRAY_DATA_UPDATE:
                entry+=" Array data updated: ";
                entry+=logData;
                entryColor = new Color(0, 0, 0);
                dataUpdateLogs.appendText(entry, entryColor, true);
            break;

            case ERROR:
                entry+=" Error: ";
                entry+=logData;
                entryColor = new Color(215, 0, 0);
                errorLogs.appendText(entry, entryColor, true);
            break;

            case NEW_ORDER:
                entry+=" New trade order requested: ";
                entry+=logData;
                entryColor = new Color(165, 165, 0);
                orderLogs.appendText(entry, entryColor, true);
            break;
        } // end of switch statement
        
        // store the entry in the allLogs window
        allLogs.appendText(entry, entryColor, true);

    } // end of method addNewEntry()



    /**
     * Used for handling button clicks.
     *
     * @param buttonId The Id of the buton which was pushed.
     */
    public  void  handleButtonPush(int buttonId) {
        //
        switch(buttonId) {
            case ALL_LOGS_BUTTON_ID:

                // unselect all other buttons
                commandLogsButton.setSelected(false);
                errorLogsButton.setSelected(false);
                dataUpdateLogsButton.setSelected(false);
                orderLogsButton.setSelected(false);

                // display the required logs
                ((CardLayout) logWindowsHolder.getLayout()).show(logWindowsHolder, "allLogs");
            break;

            case COMMAND_LOGS_BUTTON_ID:

                // unselect all other buttons
                allLogsButton.setSelected(false);
                errorLogsButton.setSelected(false);
                dataUpdateLogsButton.setSelected(false);
                orderLogsButton.setSelected(false);

                // display the required logs
                ((CardLayout) logWindowsHolder.getLayout()).show(logWindowsHolder, "commandLogs");
            break;

            case ERROR_LOGS_BUTTON_ID:
                // unselect all other buttons
                commandLogsButton.setSelected(false);
                allLogsButton.setSelected(false);
                dataUpdateLogsButton.setSelected(false);
                orderLogsButton.setSelected(false);

                // display the required logs
                ((CardLayout) logWindowsHolder.getLayout()).show(logWindowsHolder, "errorLogs");
            break;

            case DATA_UPDATE_LOGS_BUTTON_ID:
                // unselect all other buttons
                commandLogsButton.setSelected(false);
                allLogsButton.setSelected(false);
                errorLogsButton.setSelected(false);
                orderLogsButton.setSelected(false);

                // display the required logs
                ((CardLayout) logWindowsHolder.getLayout()).show(logWindowsHolder, "dataUpdateLogs");
            break;

            case ORDER_LOGS_BUTTON_ID:
                // unselect all other buttons
                commandLogsButton.setSelected(false);
                allLogsButton.setSelected(false);
                errorLogsButton.setSelected(false);
                dataUpdateLogsButton.setSelected(false);

                // display the required logs
                ((CardLayout) logWindowsHolder.getLayout()).show(logWindowsHolder, "orderLogs");
            break;

            default:
                // error ..
        } // end of switch statement

        //displayWindow.repaint();

    } // end of method handleButtonPush()


} // end of class LogsPanel
