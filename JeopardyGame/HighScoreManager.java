import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Determines the high score of the jeopardy game.
 *
 * @author Varun Sekar and Devdigvijay Singh
 * @version 3.0 2018-06-12
 */
public class HighScoreManager
{
    /* class constants */
    private static final String DATA_FILE_SOURCE = "resources/data_files/highscore.data";

    /* instance fields */
    private boolean canBeRead;
    private BufferedReader fileReader;
    private int highScore;
    private boolean isCorrect;
    private PrintWriter outputFile;
    private String score;

    /* constructors */

    /**
     * Sets the default values of the instance fields.
     */
    public HighScoreManager()
    {
      // Initialize all fields with default values.
        outputFile = null;
        isCorrect = false;
        canBeRead = false;
        readHighScore();
        score = "";
    } // end of constructor HighScore()

    /* accessors */

    /**
     * Returns the high score.
     *
     * @return the high score
     */
    public int getHighScore()
    {
        return highScore;
    } // end of method getHighScore()

    /* mutators */

    /**
     * Adds a new score and determines if it is the new high score.
     *
     * @param score the score of a player
     */
    public void addScore(int score)
    {
        if (score > highScore)
        {
            try
            {
              // Update high score.
                highScore = score;

                // Write high score to output file.
                outputFile = new PrintWriter(new FileWriter(DATA_FILE_SOURCE));
                outputFile.println(highScore);
                outputFile.close();
            }
            catch (IOException ioexception)
            {
              // Do nothing.
            } // end of catch (IOException ioexception)
        } // end of if (score > highScore)
    } // end of method addScore(int score)

    /* private methods */

    /*
     * Reads the high score data file.
     */
    private void readHighScore()
    {
        try
        {
            // Establishes a connection to the data file.
            fileReader = new BufferedReader(new FileReader(DATA_FILE_SOURCE));

            try
            {
                // Reads the first line of the high score data file.
                score = fileReader.readLine();

                // Flag connection to data file.
                canBeRead = true;

                // Parse high score as an integer.
                highScore = Integer.parseInt(score);

            }
            catch (IOException exception)
            {
              // Do nothing.
            } // end of catch (IOException exception)
            catch (NumberFormatException exception)
            {
              // Do nothing.
            } // end of catch (NumberFormatException exception)
        }
        catch (FileNotFoundException exception)
        {
            highScore = 0;
            try
            {
                // Establish connection to data file as an output.
                outputFile = new PrintWriter(new FileWriter(DATA_FILE_SOURCE));
                outputFile.println(highScore);
                
                // Close connection to data file output.
                outputFile.close();
            }
            catch (IOException ioexception)
            {
              // Do nothing.
            } // end of catch (IOException ioexception)
        } // end of catch (FileNotFoundException exception)
    } // end of method readHighScore()
} // end of class HighScore
