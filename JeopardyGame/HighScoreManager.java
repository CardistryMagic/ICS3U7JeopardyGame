import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;

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

    // instance variables
    private int highScore;
    PrintWriter outputFile;
    BufferedReader fileReader;
    private boolean isCorrect;
    private boolean canBeRead;
    private String score;

    /**
     * Sets the default values of the instance fields.
     */
    public HighScoreManager()
    {
        outputFile = null;
        isCorrect = false;
        canBeRead = false;
        readHighScore();
        score = "";
    } // end of constructor HighScore()

    private void readHighScore()
    {
        try
        {
            // establishes a connection to the text file
            fileReader = new BufferedReader(new FileReader(DATA_FILE_SOURCE));

            try 
            {
                // reads the line of text from the text file
                score = fileReader.readLine();
                canBeRead = true;
                
                
                
                highScore = Integer.parseInt(score);
                
                System.out.println(highScore);
            } 
            catch (IOException exception)
            {

            } // end of catch (IOException exception)
            catch (NumberFormatException exception)
            {
                System.out.println(highScore);
            }
        } 
        catch (FileNotFoundException exception)
        {
            highScore = 0;
            try
            {
                // creates a new output file
                outputFile = new PrintWriter(new FileWriter(DATA_FILE_SOURCE));
                
                outputFile.println(highScore);
                
                isCorrect = true;

                // close the file
                outputFile.close();
            }
            catch (IOException ioexception)
            {   
                System.out.println("A connection could not be established");
            } // end of catch (IOException ioexception)
        } // end of catch (FileNotFoundException exception)
    } // end of method readHighScore()

    /**
     * Returns the high score.
     * 
     * @return the high score
     */
    public int getHighScore()
    {
        return highScore;
    } // end of method getHighScore()

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
                highScore = score;
                outputFile = new PrintWriter(new FileWriter(DATA_FILE_SOURCE));
                outputFile.println(highScore);
                outputFile.close();
            }
            catch (IOException ioexception)
            {
                System.out.println("A connection could not be established");
            } // end of catch (IOException ioexception)
        } // end of if (score > highScore)
    } // end of method addScore(int score)
} // end of class HighScore