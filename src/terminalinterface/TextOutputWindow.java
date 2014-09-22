/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalinterface;


// imports:
import error.ErrorHandler;
import error.ErrorType;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyleContext;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;





/**
 * Used for displaying text to the user. Can display text in different colours.
 *
 * @author Alexandar Atanasov
 */
public class TextOutputWindow extends JPanel {
    // declare member variables:

    /** Instance of the ErrorHandler used for menaging errors. */
    private  static ErrorHandler eHandler;

    /** The scrollbar of the text displaying window. */
    private JScrollPane  scrollPane;

    /** Window which holds the displayed text. */
    private JTextPane    textPane;
    
    //JPopupMenu  popupContextMenu;

    // end of member variables declaration



    // Initialize static members
    static {
        eHandler = new ErrorHandler("TextOutputWindow");
    } // end of initialization of static members


    /**
     * Constructor.
     */
    public TextOutputWindow() {

        // initialize and set members
        initTextPane();
        initScrollPane();

        // create new layout
        GroupLayout layout = new GroupLayout( this);
        setLayout(layout);

        // set the layout
        layout.setHorizontalGroup(layout.createParallelGroup()
                                  .addComponent(scrollPane) );
        layout.setVerticalGroup(layout.createParallelGroup()
                                .addComponent(scrollPane) );


    } // end of constructor



    /**
     * Initializes the textPane member.
     */
    private void initTextPane() {
        // initialize the text pane
        textPane = new JTextPane();

        // make the text pane not editable
        textPane.setEditable(false);

        //
        textPane.setFocusable(false);
    } // end of method initTextPane()


    /**
     * Initializes the scrollPane member.
     */
    private void initScrollPane() {
        // initialize the scroll pane
        scrollPane = new JScrollPane();

        // make adjustments to the scroll pane
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textPane.setFont(new java.awt.Font("Arial", 0, 14));
        scrollPane.setViewportView(textPane);

        //
        scrollPane.setFocusable(false);

    } // end of method initScrollPane()


    /**
     * Appends the provided text to the end of the already existing text (if any )
     * in the output window.
     *
     * @param text The text to be appended.
     * @param textColour The colour of the text which will be appended.
     * @param newLine If true the provided text will be appended on new line.
     */
    public final void appendText(String text,Color textColour,boolean newLine) {
        // declare local method variables:
        int textLen;                                                  // stores the lenght of the existing text
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY,
                                                      StyleConstants.Foreground, textColour);
        // end of local variables declaration


        // check if text is empy
        if(text == null) {
            // exit from method
            return;
        } // end if

        // make the text pane editable
        textPane.setEditable(true);

        // get the lenght of the currently existing text in the text pane
        textLen = textPane.getDocument().getLength();

        // set focus to the text pane
        textPane.requestFocus();

        // place caret at the end of the existing text (with no selection)
        textPane.setCaretPosition(textLen);

        // set the colour
        textPane.setCharacterAttributes(attributeSet, false);

        // check if the text should be on the next line
        if( newLine == true && textPane.getText().length() != 0) {
            // add new line and than append the text
            textPane.replaceSelection("\n"+text);
        }else {
            // append the text
            textPane.replaceSelection(text);

        // make the text pane not editable
        textPane.setEditable(false);
        } // end of if else statement

        // force repaint
        repaint();

    } // end of method AppendText



    //
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // declare local variables:
        int textLen;
        // end of local variables declaration


        // make the text pane editable
        textPane.setEditable(true);

        // get the lenght of the currently existing text in the text pane
        textLen = textPane.getDocument().getLength();

        // place caret at the end of the existing text (with no selection)
        textPane.setCaretPosition(textLen);

        // make the text pane not editable
        textPane.setEditable(false);

    } // end of method paint()


    /**
     * Discards all text found in the textPane.
     */
    public void DiscardContents() {
        //
        textPane.setText(new String("") );

    } // end of method



} // end of class TextOutputWindow
