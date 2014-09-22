/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;


// imports:
import commands.CommandsHandler;
import databases.*;
import error.*;
import java.awt.*;
import javax.swing.*;
import traderavatar.*;


/**
 *
 * @author Alexandar Atanasov
 */
public class MainInterfaceFrame extends JFrame {
    // declare member variables:
    private ChartsPanel chartPanel;
    private InputOutputPanel inputOutputPanel;
    private LogsPanel logsPanel;
    private StatusPanel statusPanel;
    private ErrorHandler eHandler;
    private CommandsHandler commandsHandler;
    private TraderEngine ruler;


    // constructor
    public MainInterfaceFrame(String databaseName , DatabaseConnector dbConnector , String maximumBars ,
                         String digitsAfterDot) {
        // call constructor of parent
        super("Trader Avatar Terminal - " + databaseName);

        // declare local variables
        Container frameContents;
        // end of local variables declaration

        // initialize components
        chartPanel = new ChartsPanel(Integer.parseInt(digitsAfterDot) );
        logsPanel = new LogsPanel();
        statusPanel = new StatusPanel();
        commandsHandler = new CommandsHandler(logsPanel);
        inputOutputPanel = new InputOutputPanel(commandsHandler);
        eHandler = new ErrorHandler("Interface Main" , logsPanel);

        // check for null pointers
        if(databaseName == null || dbConnector == null || digitsAfterDot == null) {
            // error ...
            eHandler.newError(ErrorType.NULL_ARGUMENT , "constructor");
        } // end of if statement

        // get the frame contents of the main frame
        frameContents = this.getContentPane();
        frameContents.setBackground(new Color( 155,160,155 ) );

        // create new layout object and assosiate it with the main fraime 's content pane
        GroupLayout layout = new GroupLayout(frameContents);
        frameContents.setLayout(layout);

        // make gaps between the components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // set the layout
        layout.setHorizontalGroup( layout.createSequentialGroup()
                                   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                   .addComponent(chartPanel)
                                   .addComponent(inputOutputPanel) )
                                   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                   .addComponent(statusPanel, 300 ,GroupLayout.DEFAULT_SIZE ,580)
                                   .addComponent(logsPanel, 580 , 580 ,580) ) );
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addComponent(statusPanel , 80 , 80 ,80)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(logsPanel) 
                                .addGroup(layout.createSequentialGroup()
                                .addComponent(chartPanel, 190 ,GroupLayout.DEFAULT_SIZE ,190)
                                .addComponent(inputOutputPanel) ) ) );


        // set the default close operation to exit on close
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // prepare to display the window
        setBounds(40, 75, 1280, 620);
        setVisible(true);

        // set the focus to the input window
        inputOutputPanel.setFocusToInputWindow();

        // initialize the program ruler member
        ruler = new TraderEngine(chartPanel , logsPanel , statusPanel , commandsHandler ,
                                 inputOutputPanel , dbConnector , digitsAfterDot , maximumBars ,
                                 databaseName);

        // after constructor init of the ruler ( ruler cannot function without this )
        ruler.init();
        
        // start the program ruler
        ruler.start();
        
    } // end of constructor
    
} // end of class MainInterfaceFrame
