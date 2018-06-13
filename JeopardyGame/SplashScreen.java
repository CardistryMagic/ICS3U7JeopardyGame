import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
* A splash screen for initializing the data necessary for a game of jeopardy.
*
* @author Devdigvijay Singh
* @version 1.0 2018-06-06
*/
public class SplashScreen
{
  /* class fields */
  private static final String[] ERROR_DIALOG_MESSAGES = {"Try again", "Quit"};
  private static final String ERROR_DIALOG_TITLE = "Error";
  private static final int FRAME_HEIGHT = 300;
  private static final String FRAME_TITLE = "Jeopardy!";
  private static final int FRAME_WIDTH = 1000;
  private static final int[] JEOPARDY_FONT_COLOR = {255, 255, 255};
  private static final int JEOPARDY_FONT_SIZE = 250;
  private static final String JEOPARDY_FONT_SOURCE = "resources/fonts/jeopardy.ttf";
  private static final String JEOPARDY_TEXT = "Jeopardy!";
  private static final int[] WINDOW_BACKGROUND_COLOR = {0, 85, 255};
  
  /* instance fields */
  private ArrayList<Category> category;
  private BufferedReader categoryDataFile;
  private Font jeopardyFont;
  private JLabel jeopardyLogo;
  private JPanel jeopardyLogoPanel;
  private JFrame loadingFrame;

  /* constructors */

  /**
  * Constructor for objects of class SplashScreen
  */
  public SplashScreen()
  {
    makeFrame();
    initializeGame();
  } // end of constructor SplashScreen()

  /* accessors */

  /**
  * Returns all the category loaded.
  *
  * @return all the catetories in this game of jeopardy
  */
  public Category[] getCategories()
  {
    // Initialize array to store all categories.
    Category[] allCategories = new Category[category.size()];

    // Initialize increment value.
    int index = 0;

    // Iterate through all categories.
    for (Category currentCategory : category)
    {
      // Add category to array to return.
      allCategories[index] = currentCategory;

      // Increment index.
      index++;
    } // end of for (Category currentCategory : category)

    return allCategories;
  } // end of method getCategories()

  /* private methods */

  /*
  * Opens the game launcher.
  */
  private void showGameLauncher()
  {
    // Dispose of splash screen frame.
    loadingFrame.dispose();

    // Initialize array to temporarily store all categories.
    Category[] allCategories = new Category[category.size()];

    // Initialize index increment.
    int index = 0;

    // Iterate through all categories.
    for (Category currentCategory : category)
    {
      // Add current category to array.
      allCategories[index] = currentCategory;

      // Increment index.
      index++;
    } // end of for (Category currentCategory : category)

    // Create a new game launcher with the specified category data.
    new GameLauncher(allCategories);
  } // end of method showGameLauncher()

  /*
  * Quits the game.
  */
  private void quit()
  {
    System.exit(0);
  } // end of method quit()

  /*
  * Display an error message in a dialog box with the option to try again or quit the program.
  *
  * @param errorMessage the error message to display in a dialog box; may not be <code>null</code>
  */
  public void displayErrorDialog(String errorMessage)
  {
    // Show error dialog with specified parameters.
    int errorDialog = JOptionPane.showOptionDialog(loadingFrame, errorMessage, ERROR_DIALOG_TITLE,JOptionPane.YES_NO_OPTION,
    JOptionPane.ERROR_MESSAGE, null, ERROR_DIALOG_MESSAGES, ERROR_DIALOG_MESSAGES[0]);

    // Wait for user response of error dialog.
    if (errorDialog == JOptionPane.YES_OPTION)
    {
      // Dispose of the current splash screen.
      loadingFrame.dispose();

      // Load a new splash screen.
      new SplashScreen();
    }
    else
    {
      // Terminate program.
      quit();
    } // end of if (errorDialog == JOptionPane.YES_OPTION)
  } // end of method displayErrorDialog()

  /*
  * Imports the fonts necessary to create a splash screen.
  */
  private void importFonts()
  {
    try
    {
      // Load necessary fonts.
      jeopardyFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(JEOPARDY_FONT_SOURCE))).deriveFont(Font.PLAIN, JEOPARDY_FONT_SIZE);
    }
    catch (IOException exception)
    {
      // Display error message.
      displayErrorDialog("ERROR: Unable to locate font resources.");
    } // end of catch (IOException exception)
    catch (FontFormatException exception)
    {
      // Display error message.
      displayErrorDialog("ERROR: Unable to locate font resources.");
    } // end of catch (FontFormatException exception)
  } // end of method importFonts()

  /*
  * Reads data files for loading the categories.
  */
  private void initializeGame()
  {
    // Initialize category.
    category = new ArrayList<Category>();
    boolean fileFound = false;

    try
    {
      // Establish connection to categories data file.
      categoryDataFile = new BufferedReader(new FileReader(JeopardyGame.CATEGORY_DATA_FILE_SOURCE));

      // Flag the existence of the categories data file.
      fileFound = true;
    }
    catch (FileNotFoundException exception)
    {
      // Display error message.
      displayErrorDialog("ERROR: Unable to establish connection to \"" + JeopardyGame.CATEGORY_DATA_FILE_SOURCE + "\".");
    } // end of catch (FileNotFoundException exception)

    if (fileFound)
    {
      // Initialize current line.
      String currentLine = null;
      boolean validLine = false;

      do
      {
        // Reintialize current line after every iteration.
        validLine = false;
        currentLine = null;

        try
        {
          // Read next line from data file.
          currentLine = categoryDataFile.readLine();

          if (currentLine != null)
          {
            // Flag validity of current line.
            validLine = true;
          } // end of if (currentLine != null)
        }
        catch (IOException exception)
        {
          // Display error dialog.
          displayErrorDialog("ERROR: Unable to read data from \"" + JeopardyGame.CATEGORY_DATA_FILE_SOURCE + "\".");
        } // end of catch (IOException exception)

        if (validLine)
        {
          try
          {
            // Create a new category with the specified category name.
            Category currentCategory = new Category(currentLine);

            // Import data for the specified category from the data files.
            currentCategory.importData();

            // Add current category to all categories.
            category.add(currentCategory);
          }
          catch (FileNotFoundException exception)
          {
            // Display error message.
            displayErrorDialog("ERROR: Unable to establish connection to \"" + JeopardyGame.DATA_FILES_DIRECTORY + currentLine + ".data\".");

            // End execution of this method.
            return;
          } // end of catch (FileNotFoundException exception)
          catch (IOException exception)
          {
            // Display error message.
            displayErrorDialog("ERROR: Unable to read data from \"resources/data_files/" + currentLine + ".data\".");

            // End execution of this method.
            return;
          } // end of catch (IOException exception)
        } // end of if (validLine)
      }
      while (currentLine != null);

      // Verify number of detected categories.
      if (category.size() < JeopardyGame.CATEGORY_COUNT)
      {
        // Display error message.
        displayErrorDialog("ERROR: " + JeopardyGame.CATEGORY_COUNT + " category expected, but only " + category.size() + " category were successfully detected in \"resources/data_files/category.data\"");

        // Terminate execution of this method.
        return;
      }
      else
      {
        // Initialize flag variable.
        boolean sufficientNumberOfAnswers = true;

        // Iterate through detected categories.
        for (Category currentCategory : category)
        {
          // Verify number of detected answers with number of expected answers.
          if (currentCategory.getAllAnswers().length < JeopardyGame.ANSWER_COUNT)
          {
            // Flag validity.
            sufficientNumberOfAnswers = false;
          } // end of if (currentCategory.getAllAnswers().size() < ANSWER_COUNT)
        } // end of for (Category currentCategory : category)

        if (sufficientNumberOfAnswers)
        {
          // Initialize flag variable.
          boolean sufficientNumberOfQuestions = true;

          // Iterate through all detected categories.
          for (Category currentCategory : category)
          {
            // Temporarily store all detected answers of the current category.
            Answer[] allAnswers = currentCategory.getAllAnswers();

            // Iterate through all answers in this category.
            for (int index = 0; index < allAnswers.length; index++)
            {
              // Verify number of detected questions for this answer with the number of expected questions.
              if (allAnswers[index].getPossibleQuestions().length < JeopardyGame.QUESTION_COUNT)
              {
                // Flag validity.
                sufficientNumberOfQuestions = false;
              } // end of if (allAnswers[index].getPossibleQuestions.length < QUESTION_COUNT)
            } // end of for (int index = 0; index < allAnswers.length; index++)
          } // end of for (Category currentCategory : category)

          if (!sufficientNumberOfQuestions)
          {
            // Display error message.
            displayErrorDialog("ERROR: Some answers did not meet the minimum question count of " + JeopardyGame.QUESTION_COUNT);

            // Terminate execution of this method.
            return;
          } // end of if (!sufficientNumberOfAnswers)
        }
        else
        {
          // Display error message.
          displayErrorDialog("ERROR: Some category did not meet the requirement of having a minimum answer count of " + JeopardyGame.ANSWER_COUNT);

          // Terminate execution of this program.
          return;
        } // end of if (sufficientNumberOfAnswers)
      } // end of if (category.size() < CATEGORY_COUNT)
    } // end of if (fileFound)

    // Display game launcher.
    showGameLauncher();
  } // end of method initializeCategoryData()

  /*
  * Creates and initializes the frame.
  */
  private void makeFrame()
  {
    // Get user's screen size.
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Initialize frame and frame properties.
    loadingFrame = new JFrame(FRAME_TITLE);
    loadingFrame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    loadingFrame.getContentPane().setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));
    loadingFrame.setLayout(new FlowLayout());
    loadingFrame.setUndecorated(true);

    // Center frame on screen.
    int xLocation = Math.round((screenSize.width - FRAME_WIDTH)/2);
    int yLocation = Math.round((screenSize.height - FRAME_HEIGHT)/2);
    loadingFrame.setLocation(xLocation, yLocation);

    // Import fonts.
    importFonts();

    // Initialize Jeopardy logo.
    jeopardyLogo = new JLabel(JEOPARDY_TEXT);
    jeopardyLogo.setFont(jeopardyFont);
    jeopardyLogo.setForeground(new Color(JEOPARDY_FONT_COLOR[0], JEOPARDY_FONT_COLOR[1], JEOPARDY_FONT_COLOR[2]));

    // Initiailize Jeopardy logo panel.
    jeopardyLogoPanel = new JPanel();
    jeopardyLogoPanel.add(jeopardyLogo);

    // Add Jeopardy logo panel to frame.
    loadingFrame.add(jeopardyLogo);

    // Display frame.
    loadingFrame.pack();
    loadingFrame.setVisible(true);
  } // end of method makeFrame()
} // end of class SplashScreen
