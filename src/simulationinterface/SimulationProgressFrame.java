/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationinterface;



// imports:
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/**
 * Displays the progress of user initiated strategy test.
 *
 * @author Alexandar Atanasov
 */
public class SimulationProgressFrame extends JFrame{
    // declare member variables:

    /** The name of this frame. */
    private String frameName;
    
    /** Used for visual representation of test progress. */
    private JPanel progressPanel;

    /** The test progress in percents. */
    private int progress;

    // end of member variables declaration


    /**
     * Constructor.
     *
     * @param name The name of this frame.
     */
    public SimulationProgressFrame(String name) {
        // call super constructor
        super("Simulation progress for " + name + "  0%");

        // set members
        setFrameName("Simulation progress " + name);
        initProgressPanel();
        initProgress();

        // set bounds
        this.setBounds(300, 300, 500, 100);

        // set the background colour
        this.setBackground(Color.WHITE);

        // set the default close operation
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

    } // end of constructor


    /** 
     * Initializes the progress member.
     */
    private void initProgress() {
        progress = 0;
    } // end of method initProgress()


    /**
     * Sets the frameName member.
     *
     * @param name The name of this frame.
     */
    private void setFrameName(String name) {
        // set the frameName
        frameName = name;
    } // end of method setFrameName


    /** 
     * Initializes the progressPanel member.
     */
    private void initProgressPanel() {
        progressPanel = new JPanel();
        progressPanel.setBackground(Color.BLUE);
        progressPanel.setBounds(0, 0, 1, this.getHeight() );
        this.add(progressPanel);
    } // end of method initProgressPanel()


    /**
     * Resets the displayed test progress to 0 % and makes this frame visible.
     */
    public void resetAndDisplay() {
        //
        this.setBounds(300, 300, 500, 100);

        //
        progress = 0;

        // make the frame visible
        this.setVisible(true);

    } // end of method resetAndDisplay()


    /**
     * Adds one percent to the displayed progress.
     */
    public void addOnePercentProgress() {
        //
        if(progress < 100) {
            progress++;
        } // end of if statement
        setTitle(frameName + " " + Integer.toString(progress) + " %" );

        //
        progressPanel.setBounds(0, 0, progress*5, this.getHeight() );

    } // end of method




} // end of class SimulationProgressFrame
