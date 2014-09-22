/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;


// imports:
import java.awt.Color;
import java.awt.TextArea;
import javax.swing.*;


/**
 * Displays various information about the status of the Trader Avatar Terminal.
 *
 * @author Alexandar Atanasov
 */
public class StatusPanel extends JPanel{
    // declare member variables:

    /** Shows the current terminal status. */
    JLabel statusLabel;

    /** Shows if trade is allowed or not. */
    JLabel tradeLabel;

    /** Shows the current buy price. */
    JLabel buyLabel;

    /** Shows the current sell price. */
    JLabel sellLabel;

    /** Shows the number of bars in the arraydata table of the database to which this terminal is connected. */
    JLabel barsLabel;

    /** */
    public enum ProgramStatus{ OK , INIT_DB_DATA , IN_ERROR_STATE , FROZEN}

    // end of member variables declaration


    /**
     * Constructor.
     *
     */
    public StatusPanel() {
        // set the background of the StatusPanel
        setBackground(new Color(185 , 180 , 180) );

        // initialize and set members
        initLabels();

        // create new layout for the status panel
        GroupLayout layout = new GroupLayout( this);
        setLayout(layout);

        // make the layout create gaps automatically
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // set the layout
        layout.setHorizontalGroup(layout.createSequentialGroup()
                                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                  .addComponent(tradeLabel , 0 ,GroupLayout.DEFAULT_SIZE ,120)
                                  .addComponent(barsLabel, 0 ,GroupLayout.DEFAULT_SIZE ,120) )
                                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                  .addComponent(statusLabel, 0 ,GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                  .addGroup(layout.createSequentialGroup()
                                  .addComponent(buyLabel ,  0 ,GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                  .addComponent(sellLabel ,  0 ,GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE) ) ) );
        layout.setVerticalGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(tradeLabel, 0 ,GroupLayout.DEFAULT_SIZE ,25)
                                .addComponent(statusLabel, 0 ,GroupLayout.DEFAULT_SIZE , 25) )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(barsLabel, 0 ,GroupLayout.DEFAULT_SIZE ,25)
                                .addComponent(buyLabel, 0 ,GroupLayout.DEFAULT_SIZE ,25)
                                .addComponent(sellLabel , 0 ,GroupLayout.DEFAULT_SIZE ,25) ) );
    } // end of constructor



    /** 
     * Initializes all JLabel members.
     */
    private void initLabels() {
        // initialize the labels
        statusLabel = new JLabel(" Status: initializing application");
        tradeLabel = new JLabel(" Trading: not allowed");
        buyLabel = new JLabel(" Buy: ");
        sellLabel = new JLabel(" Sell Price: ");
        barsLabel = new JLabel(" Bars Price: ");

        // make the labels not transperant
        statusLabel.setOpaque(true);
        tradeLabel.setOpaque(true);
        buyLabel.setOpaque(true);
        sellLabel.setOpaque(true);
        barsLabel.setOpaque(true);

        // set the label backgrounds
        statusLabel.setBackground(Color.WHITE);
        tradeLabel.setBackground(Color.WHITE);
        buyLabel.setBackground(Color.WHITE);
        sellLabel.setBackground(Color.WHITE);
        barsLabel.setBackground(Color.WHITE);

    } // end of method initLabels()


    /**
     * Changes the terminal status.
     *
     * @param pStatus The current terminal status.
     */
    public void setStatus(ProgramStatus pStatus) {
        // declare local variables:
        String status="";
        // end of local variables declaration


        // determine the status
        switch(pStatus) {
            case OK:
                status =" Terminal Status:  OK";
                statusLabel.setBackground(new Color(255 , 255 , 255) );
                buyLabel.setBackground(new Color(255 , 255 , 255) );
                sellLabel.setBackground(new Color(255 , 255 , 255) );
                barsLabel.setBackground(new Color(255 , 255 , 255) );
            break;

            case INIT_DB_DATA:
                status =" Terminal Status: initializing data from database";
                statusLabel.setBackground(new Color(255 , 255 , 255) );
            break;

            case IN_ERROR_STATE:
                status =" Terminal Status:  in error state";
                statusLabel.setBackground(new Color(250 , 160 , 160) );
                buyLabel.setBackground(new Color(255 , 255 , 255) );
                sellLabel.setBackground(new Color(255 , 255 , 255) );
                barsLabel.setBackground(new Color(255 , 255 , 255) );
            break;

            case FROZEN:
                status = " Terminal Status: frozen";
                statusLabel.setBackground(new Color(150 , 200 , 240 ) );
                barsLabel.setBackground(new Color(150 , 200 , 240 ) );
                buyLabel.setBackground(new Color(150 , 200 , 240 ) );
                sellLabel.setBackground(new Color(150 , 200 , 240 ) );
            break;
        } // end of switch statement

        // set the status
        statusLabel.setText(status);

    } // end of method setStatus()


    /**
     * Changes the trade status.
     *
     * @param allowed True means that trading is allowed , false means that trading is not allowed.
     */
    public void setTrading(boolean allowed) {

        // set the trading
        if(allowed) {
            tradeLabel.setText(" Trading: allowed");
            tradeLabel.setBackground(new Color(210, 255, 210) );
        } else {
            tradeLabel.setText(" Trading: not allowed");
            tradeLabel.setBackground(new Color(240, 230, 180) );
        } // end of if else statement

    } // end of method setTrading()


    /**
     * Sets the bars label.
     *
     * @param bars The number of bars in the arraydata table of the database to which this terminal is connected.
     */
    public void setBars(String bars) {
        barsLabel.setText(" Bars:  " + bars);
    } // end of method


    /**
     * Sets the buy label.
     *
     * @param buy The current buy price.
     */
    public void setBuy(String buy) {
        buyLabel.setText(" Buy Price:  " + buy);
    } // end of method


    /**
     * Sets the sell label.
     *
     * @param sell The current sell price.
     */
    public void setSell(String sell) {
        sellLabel.setText(" Sell Price:  " + sell);
    } // end of method

} // end of class StatusPanel
