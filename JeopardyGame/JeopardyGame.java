import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

/**
* An object for the game of jeopardy.
*
* @author Devdigvijay Singh
* @version 1.0 2018-06-10
*/
public class JeopardyGame
{
  /* class fields */

  /**
  * The number of answers per category.
  */
  public static final int ANSWER_COUNT = 5;

  /**
  * The score increment between each level of answer.
  */
  public static final int ANSWER_SCORE_INCREMENT = 10;

  /**
  * The number of categories.
  */
  public static final int CATEGORY_COUNT = 5;

  /**
  * The minimum number of players.
  */
  public static final int MINIMUM_NUMBER_OF_PLAYERS = 1;

  /**
  * The minimum number of rounds.
  */
  public static final int MINIMUM_NUMBER_OF_ROUNDS = 1;

  /**
  * The minimum number of answers.
  */
  public static final int MINIMUM_NUMBER_OF_ANSWERS = 1;

  /**
  * The maximum number of answers.
  */
  public static final int MAXIMUM_NUMBER_OF_ANSWERS = ANSWER_COUNT;

  /**
  * The maximum number of players.
  */
  public static final int MAXIMUM_NUMBER_OF_PLAYERS = 5;

  /**
  * The maximum number of rounds.
  */
  public static final int MAXIMUM_NUMBER_OF_ROUNDS = 5;

  /**
  * The number of answers per category.
  */
  public static final int QUESTION_COUNT = 5;

  /**
   * The minimum number of categories.
   */
  public static final int MINIMUM_NUMBER_OF_CATEGORIES = 1;

  /**
   * The maximum number of categories.
   */
  public static final int MAXIMUM_NUMBER_OF_CATEGORIES = 5;

  /* instance fields */
  private JFrame jeopardyGameFrame;
  private Category[] categoriesToUse;
  private Category[] allCategories;
  private int playerCount;
  private int roundCount;
  private int answerCount;
  private int currentRoundNumber;


  /**
  * Creates a new JeopardyGame with default characteristics.
  */
  public JeopardyGame()
  {
    categoriesToUse = new Category[CATEGORY_COUNT];
    allCategories = new Category[CATEGORY_COUNT];
    playerCount = MINIMUM_NUMBER_OF_PLAYERS;
    roundCount = MINIMUM_NUMBER_OF_ROUNDS;
    answerCount = MINIMUM_NUMBER_OF_ANSWERS;
    currentRoundNumber = 1;
  } // end of constructor JeopardyGame()

  /**
  * Creates a new JeopardyGame with the specified game data.
  *
  * @param chosenCategories the categories chosen in the game launcher to use in this game of jeopardy
  * @param allCategories all the categories read from the data file
  * @param numberOfPlayers the number of players in this game of jeopardy; must be higher than <code>JeopardyGame.MINIMUM_NUMBER_OF_PLAYERS</code> and lower than <code>JeopardyGame.MAXIMUM_NUMBER_OF_PLAYERS</code>
  * @param numberOfRounds the number of rounds in this game of jeopardy; must be higher than <code>JeopardyGame.MINIMUM_NUMBER_OF_ROUNDS</code> and lower than <code>JeopardyGame.MAXIMUM_NUMBER_OF_ROUNDS</code>
  * @param numberOfAnswers the number of answers per category in this game of jeopardy; must be higher than <code>JeopardyGame.MINIMUM_NUMBER_OF_ANSWERS and lower than <code>JeopardyGame.MAXIMUM_NUMBER_OF_ANSWERS</code>
  * @param currentRoundNumber the current round number
  */
  public JeopardyGame(Category[] chosenCategories, Category[] allCategories, int numberOfPlayers, int numberOfRounds, int numberOfAnswers, int currentRoundNumber)
  {
    if (chosenCategories != null)
    {
      categoriesToUse = chosenCategories;
    }
    else
    {
      categoriesToUse = new Category[CATEGORY_COUNT];
    } // end of if (chosenCategories != null)

    if (allCategories != null)
    {
      this.allCategories = allCategories;
    }
    else
    {
      this.allCategories = new Category[CATEGORY_COUNT];
    } // end of if (allCategories != null)

    if (numberOfPlayers > MINIMUM_NUMBER_OF_PLAYERS && numberOfPlayers < MAXIMUM_NUMBER_OF_PLAYERS)
    {
      playerCount = numberOfPlayers;
    }
    else
    {
      playerCount = MINIMUM_NUMBER_OF_PLAYERS;
    } // end of if (numberOfPlayers > MINIMUM_NUMBER_OF_PLAYERS && numberOfPlayers < MAXIMUM_NUMBER_OF_PLAYERS)

    if (numberOfRounds > MINIMUM_NUMBER_OF_ROUNDS && numberOfRounds < MAXIMUM_NUMBER_OF_ROUNDS)
    {
      roundCount = numberOfRounds;
    }
    else
    {
      roundCount = MINIMUM_NUMBER_OF_ROUNDS;
    } // end of if (numberOfRounds > MINIMUM_NUMBER_OF_ROUNDS && numberOfRounds < MAXIMUM_NUMBER_OF_ROUNDS)

    if (numberOfAnswers > MINIMUM_NUMBER_OF_ANSWERS && numberOfAnswers < MAXIMUM_NUMBER_OF_ANSWERS)
    {
      answerCount = numberOfAnswers;
    }
    else
    {
      answerCount = MINIMUM_NUMBER_OF_ANSWERS;
    } // end of if (numberOfAnswers > MINIMUM_NUMBER_OF_ANSWERS && numberOfAnswers < MAXIMUM_NUMBER_OF_ANSWERS)

    if (currentRoundNumber > 0 && currentRoundNumber <= roundCount)
    {
      this.currentRoundNumber = currentRoundNumber;
    }
    else
    {
      currentRoundNumber = 1;
    } // end of if (currentRoundNumber > 0 && currentRoundNumber <= roundCount)

    makeFrame();
  } // end of constructor JeopardyGame(Category[] chosenCategories, Category[] allCategories, int numberOfPlayers, int numberOfRounds, int numberOfAnswers, int currentRoundNumber)

  /* accessors */

  /* mutators */

  /* private methods */

  /*
   * Creates the frame for the jeopardy game.
   */
  private void makeFrame()
  {
    
  }


  /* private classes */
} // end of class JeopardyGame
