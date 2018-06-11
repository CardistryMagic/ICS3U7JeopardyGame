import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.border.EmptyBorder;

/**
 * A splash screen for initializing the data necessary for a game of jeopardy.
 *
 * @author Devdigvijay Singh
 * @version 1.0 2018-06-06
 */
public class SplashScreen
{
    /* class fields */
    private static final String FRAME_TITLE = "Jeopardy!";
    private static final String JEOPARDY_FONT_SOURCE = "resources/fonts/jeopardy.ttf";
    private static final String MAIN_DATA_FILE_SOURCE = "resources/data_files/categories.data";
    private static final String JEOPARDY_TEXT = "Jeopardy!";
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 300;
    private static final int[] WINDOW_BACKGROUND_COLOR = {0, 85, 255};
    private static final int JEOPARDY_FONT_SIZE = 250;
    private static final String[] ERROR_DIALOG_MESSAGES = {"Try again", "Quit"};
    private static final int[] JEOPARDY_FONT_COLOR = {255, 255, 255};

    /* instance fields */
    private ArrayList<Category> categories;
    private BufferedReader categoriesDataFile;
    private Font jeopardyFont;
    private JLabel jeopardyLogo;
    private JFrame loadingFrame;
    private JPanel jeopardyLogoPanel;

    /* constructors */

    /**
     * Constructor for objects of class SplashScreen
     */
    public SplashScreen()
    {
        makeFrame();
        initializeGame();
    }

    /* accessors */

    /* mutators */

    /* private methods */
    /*
     * Opens the game launcher.
     */
    private void showGameLauncher()
    {
        loadingFrame.dispose();

        Category[] allCategories = new Category[categories.size()];

        int index = 0;

        for (Category currentCategory : categories)
        {
            allCategories[index] = currentCategory;
            index++;
        } // end of for (Category currentCategory : categories)

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
        int errorDialog = JOptionPane.showOptionDialog(loadingFrame, errorMessage, "Error",JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE, null, ERROR_DIALOG_MESSAGES, ERROR_DIALOG_MESSAGES[0]);

        if (errorDialog == JOptionPane.YES_OPTION)
        {
            loadingFrame.dispose();
            new SplashScreen();
        }
        else
        {
            quit();
        } // end of if (errorDialog == JOptionPane.YES_OPTION)
    } // end of method displayErrorDialog()

    /*
     * Imports the fonts necessary to create the loading frame.
     */
    private void loadFonts()
    {
        try
        {
            jeopardyFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(JEOPARDY_FONT_SOURCE))).deriveFont(Font.PLAIN, JEOPARDY_FONT_SIZE);
        }
        catch (IOException exception)
        {
            displayErrorDialog("ERROR: Unable to locate font resources.");
        }
        catch (FontFormatException exception)
        {
            displayErrorDialog("ERROR: Unable to locate font resources.");
        }
    } // end of method loadFonts()

    /*
     * Reads the data for the categories.
     */
    private void initializeGame()
    {
        categories = new ArrayList<Category>();
        boolean fileFound = false;
        try
        {
            categoriesDataFile = new BufferedReader(new FileReader(MAIN_DATA_FILE_SOURCE));
            fileFound = true;
        }
        catch (FileNotFoundException exception)
        {
            displayErrorDialog("ERROR: Unable to establish connection to \"resources/data_files/categories.data\".");
        } // end of catch (FileNotFoundException exception)

        if (fileFound)
        {
            String currentLine;
            boolean validLine;
            do
            {
                validLine = false;
                currentLine = null;
                try
                {
                    currentLine = categoriesDataFile.readLine();
                    if (currentLine != null)
                    {
                        validLine = true;
                    } // end of if (currentLine != null)
                }
                catch (IOException exception)
                {
                    displayErrorDialog("ERROR: Unable to read data from \"resources/data_files/categories.data\".");
                } // end of catch (IOException exception)

                if (validLine)
                {
                    try
                    {
                        Category currentCategory = new Category(currentLine);
                        currentCategory.importData();
                        categories.add(currentCategory);
                    }
                    catch (FileNotFoundException exception)
                    {
                        displayErrorDialog("ERROR: Unable to establish connection to \"resources/data_files/" + currentLine + ".data\".");
                        return;
                    } // end of catch (FileNotFoundException exception)
                    catch (IOException exception)
                    {
                        displayErrorDialog("ERROR: Unable to read data from \"resources/data_files/" + currentLine + ".data\".");
                        return;
                    } // end of catch (IOException exception)
                } // end of if (validLine)
            }
            while (currentLine != null);

            if (categories.size() < JeopardyGame.CATEGORY_COUNT)
            {
                displayErrorDialog("ERROR: " + JeopardyGame.CATEGORY_COUNT + " categories expected, but only " + categories.size() + " categories were successfully detected in \"resources/data_files/categories.data\"");
                return;
            }
            else
            {
                boolean sufficientNumberOfAnswers = true;
                for (Category currentCategory : categories)
                {
                    if (currentCategory.getAllAnswers().length < JeopardyGame.ANSWER_COUNT)
                    {
                        sufficientNumberOfAnswers = false;
                    } // end of if (currentCategory.getAllAnswers().size() < ANSWER_COUNT)
                } // end of for (Category currentCategory : categories)

                if (sufficientNumberOfAnswers)
                {
                    boolean sufficientNumberOfQuestions = true;
                    for (Category currentCategory : categories)
                    {
                        Answer[] allAnswers = currentCategory.getAllAnswers();

                        for (int index = 0; index < allAnswers.length; index++)
                        {
                            if (allAnswers[index].getPossibleQuestions().length < JeopardyGame.QUESTION_COUNT)
                            {
                                sufficientNumberOfQuestions = false;
                            } // end of if (allAnswers[index].getPossibleQuestions.length < QUESTION_COUNT)
                        } // end of for (int index = 0; index < allAnswers.length; index++)
                    } // end of for (Category currentCategory : categories)

                    if (!sufficientNumberOfQuestions)
                    {
                        displayErrorDialog("ERROR: Some answers did not meet the minimum question count of " + JeopardyGame.QUESTION_COUNT);
                        return;
                    } // end of if (!sufficientNumberOfAnswers)
                    else if (categories.size() > JeopardyGame.CATEGORY_COUNT)
                    {
                        int categoryCount = 1;

                        for (Category currentCategory : categories)
                        {
                            if (categoryCount > categoryCount)
                            {
                                categories.remove(categories.indexOf(currentCategory));
                            } // end of if (categoryCount > categoryCount)
                            categoryCount++;
                        } // end of for (Category currentCategory : categories)
                    } // end of if (categories.size() > CATEGORY_COUNT)
                }
                else
                {
                    displayErrorDialog("ERROR: Some categories did not meet the requirement of having a minimum answer count of " + JeopardyGame.ANSWER_COUNT);
                    return;
                } // end of if (sufficientNumberOfAnswers)
            } // end of if (categories.size() < CATEGORY_COUNT)
        } // end of if (fileFound)
        
        showGameLauncher();
    } // end of method initializeCategoryData()

    /*
     * Creates and initializes the frame.
     */
    private void makeFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        loadingFrame = new JFrame(FRAME_TITLE);
        loadingFrame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        loadingFrame.getContentPane().setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));
        loadingFrame.setLayout(new FlowLayout());
        int xLocation = Math.round((screenSize.width - FRAME_WIDTH)/2);
        int yLocation = Math.round((screenSize.height - FRAME_HEIGHT)/2);
        loadingFrame.setLocation(xLocation, yLocation);
        loadingFrame.setUndecorated(true);

        loadFonts();

        jeopardyLogo = new JLabel(JEOPARDY_TEXT);
        jeopardyLogo.setFont(jeopardyFont);
        jeopardyLogo.setForeground(new Color(JEOPARDY_FONT_COLOR[0], JEOPARDY_FONT_COLOR[1], JEOPARDY_FONT_COLOR[2]));

        jeopardyLogoPanel = new JPanel();
        jeopardyLogoPanel.add(jeopardyLogo);

        loadingFrame.add(jeopardyLogo);

        loadingFrame.pack();
        loadingFrame.setVisible(true);
    } // end of method makeFrame()

} // end of class SplashScreen
