import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

/**
 * A game launcher to start a game of jeopardy.
 *
 * @author Devdigvijay Singh
 * @version 1.0 2018-06-07
 */
public class GameLauncher
{
    /* class fields */
    private static final int CATEGORY_PANEL_MARGINS = 10;
    private static final String[] ERROR_DIALOG_MESSAGES = {"Try again", "Quit"};
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 574;
    private static final int[] WINDOW_BACKGROUND_COLOR = {0,0,0};
    private static final String WINDOW_TITLE = "";
    private static final String JEOPARDY_FONT_SOURCE = "resources/fonts/jeopardy.ttf";
    private static final String JEOPARDY_TEXT = "Jeopardy!";
    private static int JEOPARDY_FONT_SIZE = 150;
    private static int[] JEOPARDY_FONT_COLOR = {255, 255, 255};

    /* instance fields */
    Category[] allCategories;
    JCheckBox[] categoryCheckBoxButtons;
    ArrayList<Category> categoriesToUseInGame;
    JFrame gameLauncherFrame;
    JPanel categoriesCheckBoxPanel;
    JPanel frameCenterPanel;
    Font jeopardyFont;
    JLabel jeopardyLogo;
    JPanel jeopardyLogoPanel;

    /* constructors */

    /**
     * Creates a GameLauncher with the specified game data.
     */
    public GameLauncher(Category[] categoryData)
    {
        if (categoryData != null)
        {
            allCategories = categoryData;
        }
        else
        {
            allCategories = new Category[JeopardyGame.CATEGORY_COUNT];
        } // end of if (categoryData != null)
        makeFrame();
    } // end of constructor GameLauncher()

    /* accessors */

    /* mutators */

    /* private methods */

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
        int errorDialog = JOptionPane.showOptionDialog(gameLauncherFrame, errorMessage, "Error",JOptionPane.YES_NO_OPTION, 
                JOptionPane.ERROR_MESSAGE, null, ERROR_DIALOG_MESSAGES, ERROR_DIALOG_MESSAGES[0]);

        if (errorDialog == JOptionPane.YES_OPTION)
        {
            gameLauncherFrame.dispose();
            new SplashScreen();
        }
        else
        {
            quit();
        } // end of if (errorDialog == JOptionPane.YES_OPTION)
    } // end of method displayErrorDialog() 
    
    /*
     * Creates the check box panel for categories.
     */
    private void createCategoryPanel()
    {
        categoriesCheckBoxPanel = new JPanel();
        categoriesCheckBoxPanel.setLayout(new GridLayout(0, 1, CATEGORY_PANEL_MARGINS, CATEGORY_PANEL_MARGINS));
        categoryCheckBoxButtons = new JCheckBox[allCategories.length];
        
        for (int index = 0; index < allCategories.length; index++)
        {
            categoryCheckBoxButtons[index] = new JCheckBox(allCategories[index].getName());
            categoryCheckBoxButtons[index].setSelected(true);
            categoriesCheckBoxPanel.add(categoryCheckBoxButtons[index]);
        } // end of for (int index = 0; index < allCategories.length; index++)
    } // end of method createCategoryPanel()
    
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
     * Creates a frame for the game launcher.
     */
    private void makeFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        gameLauncherFrame = new JFrame(WINDOW_TITLE);
        gameLauncherFrame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        gameLauncherFrame.getContentPane().setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));
        gameLauncherFrame.setLayout(new BorderLayout());
        int xLocation = Math.round((screenSize.width - FRAME_WIDTH)/2);
        int yLocation = Math.round((screenSize.height - FRAME_HEIGHT)/2);
        gameLauncherFrame.setLocation(xLocation, yLocation);

        loadFonts();
        
        jeopardyLogo = new JLabel(JEOPARDY_TEXT);
        jeopardyLogo.setFont(jeopardyFont);
        jeopardyLogo.setForeground(new Color(JEOPARDY_FONT_COLOR[0], JEOPARDY_FONT_COLOR[1], JEOPARDY_FONT_COLOR[2]));

        jeopardyLogoPanel = new JPanel();
        jeopardyLogoPanel.setLayout(new FlowLayout());
        jeopardyLogoPanel.add(jeopardyLogo);
        
        gameLauncherFrame.add(jeopardyLogoPanel, BorderLayout.PAGE_START);
        
        createCategoryPanel();
        frameCenterPanel = new JPanel();
        frameCenterPanel.setLayout(new FlowLayout());
        frameCenterPanel.add(categoriesCheckBoxPanel);
        gameLauncherFrame.add(frameCenterPanel, BorderLayout.CENTER);

        gameLauncherFrame.pack();
        gameLauncherFrame.setVisible(true);
    } // end of method makeFrame()

    /* private classes */

} // end of class GameLauncher
