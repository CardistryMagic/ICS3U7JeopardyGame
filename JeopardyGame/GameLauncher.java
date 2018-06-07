import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
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
    private static final String[] ERROR_DIALOG_MESSAGES = {"Try again", "Quit"};
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 574;
    private static final int[] WINDOW_BACKGROUND_COLOR = {0,0,0};
    private static final String WINDOW_TITLE = "";

    /* instance fields */
    Category[] allCategories;
    JCheckBox[] categoryCheckBoxButtons;
    ArrayList<Category> categoriesToUseInGame;
    JFrame gameLauncherFrame;
    JPanel categoriesCheckBoxPanel;
    JPanel frameCenterPanel;

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
     * Loads the necessary fonts to display the settings panel.
     */
    private void loadFonts()
    {
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

        gameLauncherFrame.pack();
        gameLauncherFrame.setVisible(true);
    } // end of method makeFrame()

    /* private classes */

} // end of class GameLauncher
