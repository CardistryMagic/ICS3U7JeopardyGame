import java.util.ArrayList;

/**
 * An answer in the game of Jeopardy.
 *
 * @author Devdigvijay Singh
 * @version 3.0 2018-05-30
 */
public class Answer
{
    /* instance fields */
    private String correctQuestion;
    private boolean hasBeenUsed;
    private int pointReward;
    private ArrayList<String> possibleQuestions;
    private String text;

    /* constructors */

    /**
     * Creates an answer with default characteristics.
     */
    public Answer()
    {
        hasBeenUsed = false;
        correctQuestion = "";
        pointReward = 0;
        possibleQuestions = new ArrayList<String>();
        text = "";
    } // end of constructor Answer()

    /**
     * Creates an answer with the specified state of being answered or not.
     *
     * @param hasBeenUsed whether this answer has been used; <code>true</code> if it has been used; otherwise <code>false</code>
     */
    public Answer(boolean hasBeenUsed)
    {
        this.hasBeenUsed = hasBeenUsed;
        correctQuestion = "";
        pointReward = 0;
        possibleQuestions = new ArrayList<String>();
        text = "";
    } // end of constructor Answer(boolean hasBeenUsed)

    /**
     * Creates an answer with the specified point reward.
     *
     * @param pointReward the points rewarded for selecting the correct question; must be a non-negative integer
     */
    public Answer(int pointReward)
    {
        hasBeenUsed = false;
        correctQuestion = "";
        possibleQuestions = new ArrayList<String>();
        text = "";

        // Validate input.
        if (pointReward > 0)
        {
            // Set the point reward of this answer to the specified value.
            this.pointReward = pointReward;
        }
        else
        {
            // Set the point reward of this answer to the default value.
            this.pointReward = 0;
        } // end of if (pointReward > 0)
    } // end of constructor Answer(int pointReward)

    /**
     * Creates an answer with the specified category, text and point reward.
     *
     * @param text a String stating this answer; may not be <code>null</code>
     * @param pointReward the points rewarded for selecting the correct question to this answer; must be a non-negative integer
     */
    public Answer(String text, int pointReward)
    {
        // Validate specified answer text.
        if (text != null)
        {
            // Set the text of this answer as the specified text.
            this.text = text;
        }
        else
        {
            // Set the text of this answer to the default value.
            this.text = "";
        } // end of if (text != null)

        // Validate specified point reward.
        if (pointReward > 0)
        {
            // Set the point reward of this answer to the specified value.
            this.pointReward = pointReward;
        }
        else
        {
            // Set the point reward of this answer to the default value.
            this.pointReward = 0;
        } // end of if (pointReward > 0)

        hasBeenUsed = false;
        correctQuestion = "";
        possibleQuestions = new ArrayList<String>();
    } // end of constructor Answer(String category, int pointReward)

    /* accessors */

    /**
     * Returns whether or not this answer has been used.
     *
     * @return if this answer has been used or not; <code>true</code> if the answer has been used, otherwise <code>false</code>
     */
    public boolean isUsed()
    {
        return hasBeenUsed;
    } // end of method isUsed()

    /**
     * Returns the correct question of this answer.
     *
     * @return the correct question of this answer
     */
    public String getCorrectQuestion()
    {
        return correctQuestion;
    } // end of method getCorrectQuestion()

    /**
     * Returns the point reward of this answer.
     *
     * @return the point reward of this answer.
     */
    public int getPointReward()
    {
        return pointReward;
    } // end of method getPointReward()

    /**
     * Returns all possible questions for this answer.
     *
     * @return all possible answers for this answer
     */
    public String[] getPossibleQuestions()
    {
        // Initialize array to return all possible questions.
        String[] questions = new String[possibleQuestions.size()];

        // Add all possible questions from ArrayList to array.
        for (int index = 0; index < possibleQuestions.size(); index++)
        {
            questions[index] = possibleQuestions.get(index);
        } // end of for (int index = 0; index < possibleQuestions.size(); index++)

        // Return all possible questions.
        return questions;
    } // end of method getPossibleQuestions()

    /**
     * Returns the text of this answer.
     *
     * @return the text of this answer
     */
    public String getText()
    {
        return text;
    } // end of method getText()

    /* mutators */

    /**
     * Sets the state of this answer to used.
     */
    public void use()
    {
        hasBeenUsed = true;
    } // end of method use()

    /**
     * Sets the correct question of this answer.
     *
     * @param correctQuestion the correct question of this answer; may not be <code>null</code>
     */
    public void setCorrectQuestion(String correctQuestion)
    {
        // Validate specified correct question.
        if (correctQuestion != null)
        {
            this.correctQuestion = correctQuestion;

            // Add correct question to list of possible questions.
            if (!possibleQuestions.contains(correctQuestion))
            {
                possibleQuestions.add(correctQuestion);
            } // end of if (!possibleQuestions.contains(correctQuestion))
        } // end of if (correctQuestion != null)
    } // end of method setCorrectQuestion(String correctQuestion)

    /**
     * Sets the point rewards of this answer.
     *
     * @param pointReward the points gained from selecting the right question to this answer; must be a non-negative integer
     */
    public void setPointReward(int pointReward)
    {
        // Validate specified point reward.
        if (pointReward > 0)
        {
            this.pointReward = pointReward;
        } // end of if (pointReward > 0)
    } // end of method setPointReward(int pointReward)

    /**
     * Adds a question to the possible answers.
     *
     * @param question a possible question to this answer; may not be <code>null</code>
     */
    public void addQuestion(String question)
    {
        // Validate specified incorrect question.
        if (question != null)
        {
            possibleQuestions.add(question);
        } // end of if (answer != null)
    } // end of method addQuestion(String question)

    /**
     * Removes a question from the possible questions.
     *
     * @param question a pre-existing incorrect question to this answer; may not be <code>null</code>
     */
    public void removeQuestion(String question)
    {
        // Validate specified question to remove.
        if (question != null)
        {
            if (!question.equals(correctQuestion) && possibleQuestions.contains(question))
            {
                // Remove question from possible questions if it already exists as an incorrect choice.
                possibleQuestions.remove(possibleQuestions.indexOf(question));
            } // end of if (!question.equals(correctQuestion) && possibleQuestions.contains(question))
        } // end of if (answer != null)
    } // end of method removeQuestion(String question)
} // end of class Answer
