import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A category in the game of jeopardy.
 *
 * @author Devdigvijay Singh and Varun
 * @version 1.0 2018-06-04
 */
public class Category
{
    /* class fields */

    /* instance fields */
    private ArrayList<Answer> answers;
    private BufferedReader dataFile;
    private String dataFileSource;
    private boolean enabled;
    private String name;

    /* constructors */

    /**
     * Creates a category with default characteristics.
     */
    public Category() throws FileNotFoundException
    {
        name = "";
        dataFileSource = JeopardyGame.DATA_FILES_DIRECTORY + name + JeopardyGame.FILE_EXTENSION;
        answers = new ArrayList<Answer>();
        dataFile = new BufferedReader(new FileReader(dataFileSource));
        enabled = true;
    } // end of constructor Category()

    /**
     * Creates a category with the specified name.
     *
     * @param name the name of this category; may not be <code>null</code>
     */
    public Category(String name) throws FileNotFoundException
    {
        // Validate specified category name.
        if (name != null)
        {
            // Set the name of this category the specified value.
            this.name = name;
        }
        else
        {
            // Set the name of this category to the default value.
            this.name = "";
        } // end of if (name != null)
        answers = new ArrayList<Answer>();
        dataFileSource = name;
        dataFileSource = dataFileSource.toLowerCase();
        dataFileSource = dataFileSource.replaceAll(" ", "");
        dataFileSource = JeopardyGame.DATA_FILES_DIRECTORY + dataFileSource + JeopardyGame.FILE_EXTENSION;
        dataFile = new BufferedReader(new FileReader(dataFileSource));
        enabled = true;
    } // end of constructor Category(String name)

    /* accessors */

    /**
     * Returns the name of this category.
     *
     * @return the name of this category
     */
    public String getName()
    {
        return name;
    } // end of method getName()

    /**
     * Returns all Answers in this category.
     *
     * @return all Answers in this category
     */
    public Answer[] getAllAnswers()
    {
        // local variables

        // Initialize array to hold contain all answers.
        Answer[] allAnswers = new Answer[answers.size()];
        int index = 0;

        for (Answer currentAnswer : answers)
        {
            // Add answer to array of all answers.
            allAnswers[index] = currentAnswer;
            index++;
        } // end of for (Answer currentAnswer : answers)

        // Return all answers in this category.
        return allAnswers;
    } // end of method getAllAnswers()

    /**
     * Returns the Answer with the specified point value.
     *
     * @param pointValue the point value of the desired answer; must be a non-negative integer
     * @return the answer with the the specified point value
     */
    public Answer getAnswer(int pointValue)
    {
        // Find and return question in this category with the specified point value.
        for (Answer currentAnswer : answers)
        {
            if (currentAnswer.getPointReward() == pointValue)
            {
                return currentAnswer;
            } // end of if (currentAnswer.getPointReward() == pointValue)
        } // end of for (Answer currentAnswer : answers)

        // Return invalid answer if point reward does not exist.
        return new Answer(pointValue);
    } // end of method getAnswer(int pointValue)

    /**
     * Returns if this category is enabled or not.
     *
     * @return <code>true</code> is this category is enabled, otherwise <code>false</code>
     */
    public boolean isEnabled()
    {
        return enabled;
    } // end of method isEnabled()

    /* mutators */

    /**
     * Sets the name of this category.
     *
     * @param name the new name of this category; may not be <code>null</code>
     */
    public void setName(String name)
    {
        // Validate specified category name.
        if (name != null)
        {
            this.name = name;
        } // end of if (name != null)
    } // end of method setName(String name)

    /**
     * Sets the data file source of this category.
     *
     * @param dataFileSource the data file source of this category; must exist in directory of this class
     */
    public void setDataFileSource(String dataFileSource)
    {
        // Validate specified data file source.
        if (dataFileSource != null)
        {
            try
            {
                // Initialize connection to the specified data file.
                dataFile = new BufferedReader(new FileReader(dataFileSource));
                this.dataFileSource = dataFileSource;
            }
            catch (FileNotFoundException exception)
            {
                // Do nothing.
            } // end of catch (FileNotFoundException exception)
        } // end of if (dataFileSource != null)
    } // end of method setDataFileSource(String dataFileSource)

    /**
     * Marks the Answer with the specified point reward as used.
     *
     * @param pointReward the point reward of the Answer to mark as used
     */
    public void useAnswer(int pointReward)
    {
        for (Answer currentAnswer : answers)
        {
            if (currentAnswer.getPointReward() == pointReward)
            {
                currentAnswer.use();
            } // end of if (currentAnswer.getPointReward() == pointReward)
        } // end of for (Answer currentAnswer : answers)
    } // end of method useAnswer(int pointReward)

    /**
     *  Enables this category.
     */
    public void enable()
    {
        enabled = true;
    } // end of method enable()

    /**
     * Disables this category.
     */
    public void disable()
    {
        enabled = false;
    } // end of method disable()

    /**
     * Imports data from data file.
     */
    public void importData() throws IOException
    {
        // local variables.
        answers = new ArrayList<Answer>();
        String currentLine = null;
        int currentPointReward = JeopardyGame.ANSWER_SCORE_INCREMENT;
        Answer currentAnswer = null;
        boolean endOfDataInput = false;

        do
        {
            // Read line from category data file.
            currentLine = dataFile.readLine();

            if (currentLine != null)
            {
                if (currentLine.indexOf(JeopardyGame.QUESTION_IDENTIFIER) != -1)
                {
                    if (currentLine.substring(currentLine.length() - 1, currentLine.length()).equals(JeopardyGame.CORRECT_MARKER))
                    {
                        // Add correct question to the current answer.
                        currentAnswer.addQuestion(currentLine.substring(0, currentLine.length() - 1));
                        currentAnswer.setCorrectQuestion(currentLine.substring(0, currentLine.length() - 1));
                    }
                    else
                    {
                        // Add a possible incorrect question to the current answer.
                        currentAnswer.addQuestion(currentLine);
                    } // end of if (currentLine.substring(currentLine.length() - 1, currentLine.length()).equals(JeopardyGame.CORRECT_MARKER))
                } // end of if (currentLine.indexOf(JeopardyGame.QUESTION_IDENTIFIER) == JeopardyGame.QUESTION_IDENTIFIER_INDEX)
                else
                {
                    if (currentAnswer != null)
                    {
                        // Add current answer to category list of answers.
                        answers.add(currentAnswer);

                        // Increment point reward.
                        currentPointReward = currentPointReward + JeopardyGame.ANSWER_SCORE_INCREMENT;
                    } // end of if (currentAnswer != null)
                    currentAnswer = new Answer(currentLine, currentPointReward);
                }
            }
            else
            {
                endOfDataInput = true;
                if (currentAnswer != null)
                {
                    // Add last answer to category list of answers.
                    answers.add(currentAnswer);
                } // end of if (currentAnswer != null)
            } // end of if (currentLine != null)
        }
        while (!endOfDataInput);
    } // end of method importData()
} // end of class Category
