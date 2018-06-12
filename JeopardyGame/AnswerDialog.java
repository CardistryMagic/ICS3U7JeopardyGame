import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

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
    private static final int ANSWER_PIXEL_WIDTH_PER_CHARACTER = 14;
    private static final int QUESTION_PIXEL_WIDTH_PER_CHARACTER = 10;

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
            
            System.out.println(lineWithLineBreaks);
            
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
            answerFont = new Font(FONT_FAMILY, Font.PLAIN, ANSWER_FONT_SIZE);
            questionFont = new Font(FONT_FAMILY, Font.PLAIN, QUESTION_FONT_SIZE);
        }

        /*
         * Creates the question panel for this answer dialog.
         */
        private void createQuestionPanel()
        {
            questionPanel = new JPanel();
            questionPanel.setLayout(new GridLayout(0, GRID_LAYOUT_COLUMNS, MARGINS, MARGINS));
            questionPanel.setBackground(Color.BLACK);

            questionButtonGroup = new ButtonGroup();
            questionSelectionButtons = new JRadioButton[allQuestions.length];

            for (int index = 0; index < allQuestions.length; index++)
            {
                questionSelectionButtons[index] = new JRadioButton(addLineBreaks(allQuestions[index], FRAME_WIDTH, QUESTION_PIXEL_WIDTH_PER_CHARACTER));
                questionSelectionButtons[index].setFont(questionFont);
                questionSelectionButtons[index].setBackground(Color.BLACK);
                questionSelectionButtons[index].setForeground(Color.WHITE);
                questionButtonGroup.add(questionSelectionButtons[index]);
                questionPanel.add(questionSelectionButtons[index]);
            } // end of for (int index = 0; index < allQuestions.length; index++)
        } // end of method createQuestionPanel()

        /*
         * Creates the submit button panel.
         */
        private void createSubmitButtonPanel()
        {
            submitPanel = new JPanel();
            submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER, MARGINS, MARGINS));
            submitPanel.setBackground(Color.BLACK);

            submitButton = new JButton(SUBMIT_BUTTON_TEXT);
            submitButton.setPreferredSize(new Dimension(SUBMIT_BUTTON_WIDTH, SUBMIT_BUTTON_HEIGHT));
            submitButton.setBackground(Color.RED);
            submitButton.setForeground(Color.YELLOW);
            submitButton.addActionListener(new SubmitButtonListener());
            submitButton.setFont(answerFont);
            submitPanel.add(submitButton);
        } // end of method createSubmitButtonPanel

        /*
         * Creates the frame for this answer dialog.
         */
        private void makeFrame()
        {
            frame = new JFrame(frameTitle);
            frameWidth = FRAME_WIDTH;
            frameHeight = (FRAME_HEIGHT_PER_QUESTION*allQuestions.length + (2*(answer.getText().length()*Math.round(ANSWER_PIXEL_WIDTH_PER_CHARACTER/FRAME_WIDTH)))) + SUBMIT_BUTTON_HEIGHT;
            frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
            frame.setBackground(Color.BLACK);

            importFonts();

            createQuestionPanel();
            frame.add(questionPanel, BorderLayout.CENTER);

            createSubmitButtonPanel();
            frame.add(submitPanel, BorderLayout.PAGE_END);

            answerLabelPanel = new JPanel();
            answerLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            answerLabelPanel.setBackground(Color.BLACK);
            answerLabel = new JLabel(addLineBreaks(answer.getText(), FRAME_WIDTH, ANSWER_PIXEL_WIDTH_PER_CHARACTER));
            answerLabel.setFont(answerFont);
            answerLabel.setBackground(Color.BLACK);
            answerLabel.setForeground(Color.WHITE);
            answerLabelPanel.add(answerLabel);
            frame.add(answerLabelPanel, BorderLayout.PAGE_START);

            frame.pack();
            frame.setVisible(true);
        } // end of makeFrame()

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

                // checks which radio button was clicked and whether it was the right answer
                // if right, the points will be added to the player's total
                // if wrong, the points rewarded for the qustion will be removed from the player's total
                if (source == submitButton)
                {
                    boolean correctQuestionChosen = false;
                    for (int index = 0; index < QUESTION_COUNT; index++)
                    {
                        if (questionSelectionButtons[index].isSelected() && allQuestions[index].equals(answer.getCorrectQuestion()))
                        {
                            correctQuestionChosen = true;
                            //JeopardyGame.updateScore(answer.getPointReward());
                        }
                        else
                        {
                            //JeopardyGame.updateScore(-answer.getPointReward());
                        } // end of if (arr[counter].isSelected())
                    } // end of for (int counter = 0; counter < QUESTION_COUNT; counter++)
                } // end of (source == submitButton)
            } // end of method actionPerformed(ActionEvent event)
        } // end of class SelectionPanelListener implements ActionListener
    } // end of class AnswerDialog

    // Category basics = new Category("basics");
    // basics.importData();
    // Answer ans = basics.getAnswer(20);
    // AnswerDialog dia = new AnswerDialog(ans);
