// import java.io.BufferedReader;
// import java.io.FileWriter;
// import java.io.InputStreamReader;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.io.FileReader;
// import java.io.FileNotFoundException;

// /**
 // * Determines the high score of the jeopardy game.
 // *
 // * @author Varun Sekar and Devdigvijay Singh
 // * @version 3.0 2018-06-12
 // */
// public class HighScoreManager
// {
    // // instance variables
    // private int highScore;
    // PrintWriter outputFile;
    // BufferedReader fileReader;
    // private boolean isCorrect;
    // private boolean canBeRead;
    // private String score;

    // /**
     // * Sets the default values of the instance fields.
     // */
    // public HighScoreManager()
    // {
        // outputFile = null;
        // isCorrect = false;
        // canBeRead = false;
        // readHighScore();
        // highScore = 0;
        // score = "";
    // } // end of constructor HighScore()

    // private void readHighScore()
    // {
        // try
        // {
            // // establishes a connection to the text file
            // fileReader = new BufferedReader(new FileReader("resources/data_files/highscore.data"));

            // do
            // {
                // try
                // {
                    // // reads the line of text from the text file
                    // score = fileReader.readLine();
                    // canBeRead = true;
                // }
                // catch (IOException exception)
                // {
                    // // displays error message
                    // System.out.println("Theere was an error reading from the file");
                // } // end of catch (IOException exception)
            // }
            // while (!canBeRead);

            // while (score != null)
            // {
                // // converts the string into an integer
                // highScore = Integer.parseInt(score);
            // } // end of while (score != null)
        // }
        // catch (FileNotFoundException exception)
        // {
            // do
            // {
                // try
                // {
                    // // creates a new output file
                    // outputFile = new PrintWriter(new FileWriter("highscore.data"));
                    // isCorrect = true;

                    // // close the file
                    // outputFile.close();
                // }
                // catch (IOException ioexception)
                // {
                    // System.out.println("A connection could not be established");
                // } // end of catch (IOException ioexception)
            // }
            // while (!isCorrect);
        // } // end of catch (FileNotFoundException exception)
    // } // end of method readHighScore()

    // /**
     // * Returns the high score.
     // *
     // * @return the high score
     // */
    // public int getHighScore()
    // {
        // return highScore;
    // } // end of method getHighScore()

    // /**
     // * Adds a new score and determines if it is the new high score.
     // *
     // * @param score the score of a player
     // */
    // public void addScore(int score)
    // {
        // if (score > highScore)
        // {
            // try
            // {
                // highScore = score;
                // outputFile = new PrintWriter(new FileWriter("highscore.data"));
                // outputFile.println(highScore);
                // outputFile.close();
            // }
            // catch (IOException ioexception)
            // {
                // System.out.println("A connection could not be established");
            // } // end of catch (IOException ioexception)
        // } // end of if (score > highScore)
    // } // end of method addScore(int score)
// } // end of class HighScore
