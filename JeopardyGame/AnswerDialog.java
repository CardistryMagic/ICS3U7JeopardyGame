import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;

/**
 * A window to display the answer and prompt for the selection of the correct question.
 *
 * @author Varun and Dave
 * @version 3.0 2018-06-04
 */
public class AnswerDialog
{
    /* class constants */
    private static final int FRAME_WIDTH = 800;
    private static int QUESTION_COUNT = 5;
    private static final int FRAME_HEIGHT_PER_QUESTION = 60;
    private static final int GRID_LAYOUT_COLUMNS = 1;
    private static final int MARGINS = 10;
    private static final String SUBMIT_BUTTON_TEXT = "SUBMIT";
    private static final int SUBMIT_BUTTON_WIDTH = 160;
    private static final int SUBMIT_BUTTON_HEIGHT = 40;
    private static final String FONT_FAMILY = "Century Gothic";
    private static final int ANSWER_FONT_SIZE = 24;
    private static final int QUESTION_FONT_SIZE = 16;
    private static final int ANSWER_PIXEL_WIDTH_PER_CHARACTER = 18;
    private static final int QUESTION_PIXEL_WIDTH_PER_CHARACTER = 11;
    private static final String CORRECT_TEXT = "CORRECT";
    private static final String INCORRECT_TEXT = "INCORRECT";
    private static final int FONT_SIZE_CORRECTION_FACTOR = 3;
    private static final String CONTINUE_BUTTON_TEXT = "CONTINUE";

    /* instance fields */
    private Answer answer;
    private JRadioButton[] questionSelectionButtons;
    private JFrame frame;
    private String[] allQuestions;
    private JPanel questionPanel;
    private JButton submitButton;
    private boolean finished;
    private String frameTitle;
    private int frameWidth;
    private int frameHeight;
    private JPanel submitPanel;
    private ButtonGroup questionButtonGroup;
    private Font questionFont;
    private Font answerFont;
    private JLabel answerLabel;
    private JPanel answerLabelPanel;
    private boolean correctQuestionSelected;
    private boolean displayResult;
    private Font resultFont;
    private JPanel fillingRectangle;
    private ActionListener continueButtonActionListener;

    /* constructors */

    /**
     * Creates an answer dialog with default characteristics.
     */
    public AnswerDialog()
    {
        answer = new Answer();
        allQuestions = answer.getPossibleQuestions();
        finished = false;
        frameTitle = "Playing for $" + Integer.toString(answer.getPointReward());
        correctQuestionSelected = false;
        displayResult = false;

        makeFrame();
    }

    /**
     * Creates an answer dialog with the specified Answer.
     * @param answer the answer to display in this answer dialog; may not be <code>null</code>
     */
    public AnswerDialog(Answer answer)
    {
        if (answer != null)
        {
            this.answer = answer;
        } // end of if (answer != null)

        allQuestions = answer.getPossibleQuestions();
        finished = false;
        correctQuestionSelected = false;
        displayResult = false;

        makeFrame();
    } // end of constructor AnswerDialog()

    /* accessors */

    /**
     * Returns this answer dialog's Answer.
     *
     * @return this AnswerDialog's Answer
     */
    public Answer getAnswer()
    {
        return answer;
    } // end of method getAnswer()

    /**
     * Returns if this answer dialog has completed its cycle of displaying the answer.
     *
     * @return whether this answer dialog has completed its cycle; <code>true</code> if it has completed its cycle; otherwise <code>false</code>
     */
    public boolean isFinished()
    {
        return finished;
    } // end of method isFinished()

    /**
     * Returns whether or not the user chose the correct question.
     *
     * @return whather the user chose the correct question; <code>true</code> if the user chose the correct question, otherwise <code>false</code>
     */
    public boolean correctQuestionPicked()
    {
        return correctQuestionSelected;
    } // end of method correctQuestionPicked()

    /* mutators */

    /**
     * Sets the Answer of this answer dialog.
     *
     * @param answer the new Answer of this answer dialog; may not be <code>null</code>
     */
    public void setAnswer(Answer answer)
    {
        if (answer !=  null)
        {
            this.answer = answer;
        } // end of if (ans != null)
    } // end of method setAnswer(Answer answer)

    /**
     * Sets the action listener to the continue button.
     *
     * @param listener the action listener for the continue button
     */
    public void setContinueButtonActionListener(ActionListener listener)
    {
        continueButtonActionListener = listener;
    } // end of method setContinueButtonActionListener(ActionListener listener)

    /* private methods */

    /*
     * Adds HTML line break tags to a given String given the necessary parameters.
     */
    private String addLineBreaks(String line, int totalPixelWidth, int averageWidthPerCharacter)
    {
        int maxNumberOfCharacters = Math.round(totalPixelWidth/averageWidthPerCharacter);

        if (line != null)
        {
            String[] lineSeparatedBySpaces = line.split(" ");
            String lineWithLineBreaks = lineSeparatedBySpaces[0];
            int currentLength = 0;

            for (int index = 1; index < lineSeparatedBySpaces.length; index++)
            {
                if (currentLength + lineSeparatedBySpaces[index].length() < maxNumberOfCharacters)
                {
                    lineWithLineBreaks = lineWithLineBreaks + " " + lineSeparatedBySpaces[index];
                    currentLength = currentLength + lineSeparatedBySpaces[index].length();
                }
                else
                {
                    lineWithLineBreaks = lineWithLineBreaks + "<br>" + lineSeparatedBySpaces[index];
                    currentLength = lineSeparatedBySpaces[index].length();
                } // end of if (currentLength + lineSeparatedBySpaces[index].length() < maxNumberOfCharacters)
            } // end of for (int index = 0; index < lineSeparatedBySpace.size(); index++)

            lineWithLineBreaks = "<html>" + lineWithLineBreaks + "</html>";

            return lineWithLineBreaks;
        }
        else
        {
            return "";
        } // end of if (line != null)

    } // end of method addLineBreaks()
    /*
     * Imports the necessary fonts for later use.
     */
    private void importFonts()
    {
      // Import fonts.
        answerFont = new Font(FONT_FAMILY, Font.PLAIN, ANSWER_FONT_SIZE);
        questionFont = new Font(FONT_FAMILY, Font.PLAIN, QUESTION_FONT_SIZE);
        resultFont = new Font(FONT_FAMILY, Font.PLAIN, Math.round(frameHeight/FONT_SIZE_CORRECTION_FACTOR));
    }

    /*
     * Creates the question panel for this answer dialog.
     */
    private void createQuestionPanel()
    {
      // Create question panel.
        questionPanel = new JPanel();
        questionPanel.setBorder(new EmptyBorder(MARGINS, MARGINS, MARGINS, MARGINS));
        questionPanel.setLayout(new GridLayout(0, GRID_LAYOUT_COLUMNS, MARGINS, MARGINS));
        questionPanel.setBackground(Color.BLACK);

        // Create button group to store all radio buttons.
        questionButtonGroup = new ButtonGroup();
        questionSelectionButtons = new JRadioButton[allQuestions.length];

        for (int index = 0; index < allQuestions.length; index++)
        {
          // Create radio button with current question.
            questionSelectionButtons[index] = new JRadioButton(addLineBreaks(allQuestions[index], FRAME_WIDTH, QUESTION_PIXEL_WIDTH_PER_CHARACTER));
            questionSelectionButtons[index].setFont(questionFont);
            questionSelectionButtons[index].setBackground(Color.BLACK);
            questionSelectionButtons[index].setForeground(Color.WHITE);

            // Add radio button to button group and panel.
            questionButtonGroup.add(questionSelectionButtons[index]);
            questionPanel.add(questionSelectionButtons[index]);
        } // end of for (int index = 0; index < allQuestions.length; index++)
    } // end of method createQuestionPanel()

    /*
     * Creates the submit button panel.
     */
    private void createSubmitButtonPanel()
    {
      // Create submit button panel.
        submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER, MARGINS, MARGINS));
        submitPanel.setBackground(Color.BLACK);

        // Create submit button.
        submitButton = new JButton(SUBMIT_BUTTON_TEXT);
        submitButton.setPreferredSize(new Dimension(SUBMIT_BUTTON_WIDTH, SUBMIT_BUTTON_HEIGHT));
        submitButton.setBackground(Color.RED);
        submitButton.setForeground(Color.YELLOW);
        submitButton.addActionListener(new SubmitButtonListener());
        submitButton.setFont(answerFont);

        // Add submit panel to submit button panel.
        submitPanel.add(submitButton);
    } // end of method createSubmitButtonPanel

    /*
     * Creates the frame for this answer dialog.
     */
    private void makeFrame()
    {
      // Create frame.
        frame = new JFrame(frameTitle);
        frame.setLayout(new BorderLayout());
        frameWidth = FRAME_WIDTH;
        frameHeight = (FRAME_HEIGHT_PER_QUESTION*allQuestions.length + (2*(answer.getText().length()*Math.round(ANSWER_PIXEL_WIDTH_PER_CHARACTER/FRAME_WIDTH)))) + SUBMIT_BUTTON_HEIGHT;
        frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
        frame.setBackground(Color.BLACK);
        frame.setUndecorated(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(MARGINS, MARGINS, MARGINS, MARGINS, Color.BLUE));

        // Get screen dimensions.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Center frame on screen.
        int xLocation = Math.round((screenSize.width - frameWidth)/2);
        int yLocation = Math.round((screenSize.height - frameHeight)/2);
        frame.setLocation(xLocation, yLocation);

        // Import fonts.
        importFonts();

        // Create question panel.
        createQuestionPanel();

        // Add question panel to frame.
        frame.add(questionPanel, BorderLayout.CENTER);

        // Create submit button panel.
        createSubmitButtonPanel();

        // Add submit button panel to frame.
        frame.add(submitPanel, BorderLayout.PAGE_END);

        // Create answer label panel.
        answerLabelPanel = new JPanel();
        answerLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        answerLabelPanel.setBackground(Color.BLACK);
        answerLabelPanel.setBorder(new EmptyBorder(MARGINS, MARGINS, MARGINS, MARGINS));

        // Create answer label.
        answerLabel = new JLabel(addLineBreaks(answer.getText(), FRAME_WIDTH, ANSWER_PIXEL_WIDTH_PER_CHARACTER));
        answerLabel.setFont(answerFont);
        answerLabel.setBackground(Color.BLACK);
        answerLabel.setForeground(Color.WHITE);
        answerLabelPanel.add(answerLabel);

        // Add answer label panel to frame.
        frame.add(answerLabelPanel, BorderLayout.PAGE_START);

        // Display frame.
        frame.pack();
        frame.setVisible(true);
    } // end of makeFrame()

    /*
     * Changes the components of the frame to show the result.
     *
     * @param correctQuestionChosen whether the correct question was selected by the user; <code>true</code> if the correct question was chosen, otherwise <code>false</code>
     */
    public void showResult(boolean correctQuestionChosen)
    {
        displayResult = true;
        if (correctQuestionChosen)
        {
            answerLabel.setText(CORRECT_TEXT);
        }
        else
        {
            answerLabel.setText(INCORRECT_TEXT);
        } // end of if (correctQuestionChosen)

        answerLabel.setFont(resultFont);
        answerLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, MARGINS, MARGINS));

        BorderLayout layout = (BorderLayout) (frame.getContentPane()).getLayout();
        frame.remove(layout.getLayoutComponent(BorderLayout.CENTER));

        submitButton.setText(CONTINUE_BUTTON_TEXT);
        submitButton.addActionListener(continueButtonActionListener);

        fillingRectangle = new JPanel();
        fillingRectangle.setBackground(Color.BLACK);
        frame.add(fillingRectangle, BorderLayout.CENTER);

        frame.pack();
        frame.repaint();
    } // end of method showResult(boolean correctQuestionChosen)

    /* private classes */

    /*
     * Responds to events from the selection panel.
     */
    private class SubmitButtonListener implements ActionListener
    {
        /**
         * Responds to an action event that takes place.
         *
         * @param event the event that takes place
         */
        public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();

            if (source == submitButton)
            {
                if (displayResult)
                {
                  // Dispose of current frame.
                    frame.dispose();
                    finished = false;
                }
                else
                {
                    boolean correctQuestionChosen = false;
                    for (int index = 0; index < QUESTION_COUNT; index++)
                    {
                        if (questionSelectionButtons[index].isSelected() && allQuestions[index].equals(answer.getCorrectQuestion()))
                        {
                          // Flag correct question chosen.
                            correctQuestionChosen = true;
                        } // end of if (questionSelectionButtons[index].isSelected() && allQuestions[index].equals(answer.getCorrectQuestion()))
                    } // end of for (int counter = 0; counter < QUESTION_COUNT; counter++)

                    if (correctQuestionChosen)
                    {
                        correctQuestionSelected = true;
                    } // end of if (correctQuestionChosen)

                    // Show result.
                    showResult(correctQuestionSelected);
                } // end of if (displayResult)
            } // end of (source == submitButton)
        } // end of method actionPerformed(ActionEvent event)
    } // end of class SelectionPanelListener implements ActionListener
} // end of class AnswerDialog
