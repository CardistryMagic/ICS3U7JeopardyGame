import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * Write a description of class AnswerDisplay here.
 * 
 * @author Varun and Dave 
 * @version 3.0 2018-06-04
 */
public class AnswerDialog
{
    /* class constants */ 
    public static final int WIDTH = 500;
    public static int QUESTION_COUNT = 5;
    public static final int QUESTION_HEIGHT = 40;
    public static final int GRID_LAYOUT_LENGTH = 1;

    /* Instance fields */
    private Answer answer;
    JRadioButton[] arr;
    String[] arr1;
    JFrame frame;
    GridLayout grid;
    private int height;
    JPanel panel;
    JButton submitButton;

    public AnswerDialog(Answer ans)
    {
        if (ans !=  null)
        {
            answer = ans;
        } // end of if (ans != null)

        arr = new JRadioButton[QUESTION_COUNT];
        arr1 = answer.getPossibleQuestions();
        makeFrame();
    } // end of constructor AnswerDialog()

    private void makeFrame()
    {
        height = QUESTION_HEIGHT * answer.getPossibleQuestions().length;        

        frame = new JFrame("QUESTION");
        panel = new JPanel();
        frame.setPreferredSize(new Dimension(WIDTH,height));
        submitButton = new JButton("Submit");
        frame.add(submitButton, BorderLayout.PAGE_END);
        ButtonGroup group = new ButtonGroup();
        panel.setLayout(new GridLayout(QUESTION_COUNT,GRID_LAYOUT_LENGTH));

        for (int index = 0; index < QUESTION_COUNT; index++)
        {
            arr[index] = new JRadioButton(arr1[index]);
            arr[index].addActionListener(new ButtonListener());
            group.add(arr[index]);
            panel.add(arr[index]);
        } // end of for (int index = 0; index < QUESTION_COUNT; index++)

        frame.add(panel, BorderLayout.LINE_START);
        frame.pack();
        frame.setVisible(true);
    }

    private class ButtonListener implements ActionListener
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
                for (int counter = 0; counter < QUESTION_COUNT; counter++)
                {
                    if (arr[counter].isSelected() && arr1[counter].equals(answer.getCorrectQuestion()))
                    {
                        //JeopardyGame.updateScore(answer.getPointReward());
                    }
                    else
                    {
                        //JeopardyGame.updateScore(-answer.getPointReward());
                    } // end of if (arr[counter].isSelected())
                } // end of for (int counter = 0; counter < QUESTION_COUNT; counter++)
            } // end of (source == submitButton)
        } // end of method actionPerformed(ActionEvent event)
    } // end of class ButtonListener implements ActionListener

    public Answer getAnswer()
    {
        return answer;
    }
} // end of class AnswerDialog

// Category basics = new Category("basics");
// basics.importData();
// Answer ans = basics.getAnswer(20);
// AnswerDialog dia = new AnswerDialog(ans);