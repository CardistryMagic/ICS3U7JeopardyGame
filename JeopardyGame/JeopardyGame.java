import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
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
import java.awt.Toolkit;

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

    private static final String JEOPARDY_BANNER_TEXT = "JEOPARDY!";
    private static final String JEOPARDY_FONT_SOURCE = "resources/fonts/jeopardy.ttf";
    private static final int JEOPARDY_FONT_SIZE = 128;
    private static final int[] JEOPARDY_FONT_COLOR = {255,255,255};
    private static final int[] MAIN_WINDOW_BACKGROUND_COLOR = {0,0,0};
    private static final String BUTTON_FONT_FAMILY = "Impact";
    private static final String CATEGORY_FONT_SOURCE = "resources/fonts/carson_d.Ttf";
    private static final int CATEGORY_FONT_SIZE = 28;
    private static final String[] ERROR_DIALOG_MESSAGES = {"Try again", "Quit"};
    private static final String FRAME_TITLE = "Jeopardy";
    private static final int FRAME_WIDTH = 1024;
    private static final int FRAME_HEIGHT = 576;
    private static final int[] CATEGORY_BACKGROUND_COLOR = {255, 240, 40};
    private static final int[] ANSWER_BUTTON_BACKGROUND_COLOR = {0,50,200};
    private static final int[] CATEGORY_TEXT_COLOR = {150, 150, 150};
    private static final int[] ANSWER_BUTTON_TEXT_COLOR = {255,200,0};
    private static final int ANSWER_PANEL_MARGINS = 10;
    private static final String SETTINGS_BUTTON_TEXT = "Change Settings";
    private static final String RESET_GAME_BUTTON_TEXT = "Reset Game";
    private static final String QUIT_BUTTON_TEXT = "Quit";
    private static final int[] CONTROL_PANEL_BUTTON_SIZE = {150, 40};
    private static final String RESET_ROUND_BUTTON_TEXT = "Reset Round";
    private static final String HIGHSCORES_BUTTON_TEXT = "Highscores";
    private static final int CONTROL_PANEL_MARGINS = 10;

    /* instance fields */
    private JFrame frame;
    private Category[] categoriesToUse;
    private Category[] allCategories;
    private int playerCount;
    private int roundCount;
    private int answerCount;
    private int currentRoundNumber;
    private Font jeopardyBannerFont;
    private Font categoryLabelFont;
    private Font buttonFont;
    private Color answerButtonColor;
    private Color categoryTextColor;
    private Color buttonFontColor;
    private JPanel answerPanel;
    private JButton[][] answerButtons;
    private JLabel[] categoryLabels;
    private Color categoryBackgroundColor;
    private JLabel jeopardyBanner;
    private JPanel controlPanel;
    private ControlPanelListener controlPanelListener;
    private JButton resetGameButton;
    private JButton quitButton;
    private JButton settingsButton;
    private JButton resetRoundButton;
    private JButton highscoresButton;

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

        if (numberOfPlayers >= MINIMUM_NUMBER_OF_PLAYERS && numberOfPlayers <= MAXIMUM_NUMBER_OF_PLAYERS)
        {
            playerCount = numberOfPlayers;
        }
        else
        {
            playerCount = MINIMUM_NUMBER_OF_PLAYERS;
        } // end of if (numberOfPlayers > MINIMUM_NUMBER_OF_PLAYERS && numberOfPlayers < MAXIMUM_NUMBER_OF_PLAYERS)

        if (numberOfRounds >= MINIMUM_NUMBER_OF_ROUNDS && numberOfRounds <= MAXIMUM_NUMBER_OF_ROUNDS)
        {
            roundCount = numberOfRounds;
        }
        else
        {
            roundCount = MINIMUM_NUMBER_OF_ROUNDS;
        } // end of if (numberOfRounds > MINIMUM_NUMBER_OF_ROUNDS && numberOfRounds < MAXIMUM_NUMBER_OF_ROUNDS)

        if (numberOfAnswers >= MINIMUM_NUMBER_OF_ANSWERS && numberOfAnswers <= MAXIMUM_NUMBER_OF_ANSWERS)
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
     * Quits the game.
     */
    private void quit()
    {
        System.exit(0);
    } // end of method quit()

    /*
     * Displays an error message in a dialog box with the option to try again or quit the program.
     */
    public void displayErrorDialog(String errorMessage)
    {
        int errorDialog = JOptionPane.showOptionDialog(frame, errorMessage, "Error",JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE, null, ERROR_DIALOG_MESSAGES, ERROR_DIALOG_MESSAGES[0]);

        if (errorDialog == JOptionPane.YES_OPTION)
        {
            frame.dispose();
            new JeopardyGame();
        }
        else
        {
            quit();
        } // end of if (errorDialog == JOptionPane.YES_OPTION)
    } // end of method displayErrorDialog()

    /*
     * Imports fonts for later use.
     */
    private void importFonts()
    {
        try
        {
            jeopardyBannerFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(JEOPARDY_FONT_SOURCE))).deriveFont(Font.PLAIN, JEOPARDY_FONT_SIZE);
            buttonFont = new Font(BUTTON_FONT_FAMILY, Font.PLAIN, 24);
            categoryLabelFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(CATEGORY_FONT_SOURCE))).deriveFont(Font.PLAIN, CATEGORY_FONT_SIZE);
        }
        catch (IOException exception)
        {
            displayErrorDialog("ERROR: Unable to locate font resources.");
        }
        catch (FontFormatException exception)
        {
            displayErrorDialog("ERROR: Unable to locate font resources.");
        }

    } // end of method importFonts()

    /**
     * Creates the button panel.
     */
    private void createButtonPanel()
    {
        answerButtonColor = new Color(ANSWER_BUTTON_BACKGROUND_COLOR[0], ANSWER_BUTTON_BACKGROUND_COLOR[1], ANSWER_BUTTON_BACKGROUND_COLOR[2]);
        categoryBackgroundColor = new Color(CATEGORY_BACKGROUND_COLOR[0], CATEGORY_BACKGROUND_COLOR[1], CATEGORY_BACKGROUND_COLOR[2]);
        categoryTextColor = new Color(CATEGORY_TEXT_COLOR[0], CATEGORY_TEXT_COLOR[1], CATEGORY_TEXT_COLOR[2]);
        buttonFontColor = new Color(ANSWER_BUTTON_TEXT_COLOR[0], ANSWER_BUTTON_TEXT_COLOR[1], ANSWER_BUTTON_TEXT_COLOR[2]);

        answerPanel = new JPanel();
        answerPanel.setLayout(new GridLayout(answerCount + 1, categoriesToUse.length, ANSWER_PANEL_MARGINS, ANSWER_PANEL_MARGINS));
        answerButtons = new JButton[CATEGORY_COUNT][ANSWER_COUNT];

        categoryLabels = new JLabel[CATEGORY_COUNT];

        for (int categoryIndex = 0; categoryIndex < categoriesToUse.length; categoryIndex++)
        {
            categoryLabels[categoryIndex] = new JLabel(categoriesToUse[categoryIndex].getName());
            categoryLabels[categoryIndex].setBackground(categoryBackgroundColor);
            categoryLabels[categoryIndex].setForeground(categoryTextColor);
            categoryLabels[categoryIndex].setHorizontalAlignment(JLabel.CENTER);
            categoryLabels[categoryIndex].setFont(categoryLabelFont);

            answerPanel.add(categoryLabels[categoryIndex]);
        } // end of for (int categoryIndex = 0; categoryIndex < CATEGORY_COUNT; categoryIndex++)

        for (int answerIndex = 0; answerIndex < answerCount; answerIndex++)
        {
            for (int categoryIndex = 0; categoryIndex < categoriesToUse.length; categoryIndex++)
            {
                String currentButtonText = "$" + ((answerIndex + 1)*ANSWER_SCORE_INCREMENT);
                answerButtons[categoryIndex][answerIndex] = new JButton(currentButtonText);
                answerButtons[categoryIndex][answerIndex].setBackground(answerButtonColor);
                answerButtons[categoryIndex][answerIndex].setForeground(buttonFontColor);
                answerButtons[categoryIndex][answerIndex].setOpaque(true);
                answerButtons[categoryIndex][answerIndex].setBorderPainted(false);
                answerButtons[categoryIndex][answerIndex].setFont(buttonFont);

                answerPanel.add(answerButtons[categoryIndex][answerIndex]);
            } // end of for (int categoryIndex = 0; categoryIndex < CATEGORY_COUNT; categoryIndex++)
        } // end of for (int answerIndex = 0; answerIndex < ANSWER_COUNT; answerIndex++)

        answerPanel.setBackground(new Color(MAIN_WINDOW_BACKGROUND_COLOR[0], MAIN_WINDOW_BACKGROUND_COLOR[1], MAIN_WINDOW_BACKGROUND_COLOR[2]));
    } // end of method createButtonPanel()

    /*
     * Creates the Jeopardy banner.
     */
    private void createJeopardyBanner()
    {
        jeopardyBanner = new JLabel(JEOPARDY_BANNER_TEXT);
        jeopardyBanner.setHorizontalAlignment(JLabel.CENTER);
        jeopardyBanner.setFont(jeopardyBannerFont);
        jeopardyBanner.setForeground(new Color(JEOPARDY_FONT_COLOR[0], JEOPARDY_FONT_COLOR[1], JEOPARDY_FONT_COLOR[2]));
    } // end of method createJeopardyBanner()

    private void createControlPanel()
    {
        controlPanel = new JPanel();
        controlPanel.setBackground(new Color(MAIN_WINDOW_BACKGROUND_COLOR[0], MAIN_WINDOW_BACKGROUND_COLOR[1], MAIN_WINDOW_BACKGROUND_COLOR[2]));
        controlPanel.setBorder(new EmptyBorder(CONTROL_PANEL_MARGINS, CONTROL_PANEL_MARGINS, CONTROL_PANEL_MARGINS, CONTROL_PANEL_MARGINS));
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, CONTROL_PANEL_MARGINS, 0));
        controlPanelListener = new ControlPanelListener();

        highscoresButton = new JButton(HIGHSCORES_BUTTON_TEXT);
        highscoresButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_SIZE[0], CONTROL_PANEL_BUTTON_SIZE[1]));
        highscoresButton.addActionListener(controlPanelListener);
        controlPanel.add(highscoresButton);

        resetGameButton = new JButton(RESET_GAME_BUTTON_TEXT);
        resetGameButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_SIZE[0], CONTROL_PANEL_BUTTON_SIZE[1]));
        resetGameButton.addActionListener(controlPanelListener);
        controlPanel.add(resetGameButton);

        resetRoundButton = new JButton(RESET_ROUND_BUTTON_TEXT);
        resetRoundButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_SIZE[0], CONTROL_PANEL_BUTTON_SIZE[1]));
        resetRoundButton.addActionListener(controlPanelListener);
        controlPanel.add(resetRoundButton);

        settingsButton = new JButton(SETTINGS_BUTTON_TEXT);
        settingsButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_SIZE[0], CONTROL_PANEL_BUTTON_SIZE[1]));
        settingsButton.addActionListener(controlPanelListener);
        controlPanel.add(settingsButton);

        quitButton = new JButton(QUIT_BUTTON_TEXT);
        quitButton.setPreferredSize(new Dimension(CONTROL_PANEL_BUTTON_SIZE[0], CONTROL_PANEL_BUTTON_SIZE[1]));
        quitButton.addActionListener(controlPanelListener);
        controlPanel.add(quitButton);

    } // end of method createControlPanel()

    /*
     * Creates the frame for the jeopardy game.
     */
    private void makeFrame()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame(FRAME_TITLE);
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.getContentPane().setBackground(new Color(MAIN_WINDOW_BACKGROUND_COLOR[0], MAIN_WINDOW_BACKGROUND_COLOR[1], MAIN_WINDOW_BACKGROUND_COLOR[2]));
        frame.setLayout(new BorderLayout());

        int xLocation = Math.round((screenSize.width - FRAME_WIDTH)/2);
        int yLocation = Math.round((screenSize.height - FRAME_HEIGHT)/2);
        frame.setLocation(xLocation, yLocation);

        importFonts();

        createButtonPanel();
        frame.add(answerPanel, BorderLayout.CENTER);

        createJeopardyBanner();
        frame.add(jeopardyBanner, BorderLayout.PAGE_START);

        createControlPanel();
        frame.add(controlPanel, BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);
    }

    /* private classes */

    /*
     * Listens to events from the control panel.
     */
    private class ControlPanelListener implements ActionListener
    {
        /**
         * Responds to control panel button events.
         *
         * @param event the event performed
         */
        public void actionPerformed(ActionEvent event)
        {
            Object source = event.getSource();

            if (source == quitButton)
            {
                quit();
            } // end of if (source == quitButton)
            else if (source == settingsButton)
            {
                frame.dispose();
                new GameLauncher(allCategories);
            } // end of if (source == resetGameButton)
            else if (source == resetGameButton)
            {
                frame.dispose();
                new JeopardyGame(categoriesToUse, allCategories, playerCount, roundCount, answerCount, 1);
            } // end of if (source == resetGameButton)
        } // end of method actionPerformed(ActionEvent event)
    } // end of class ControlPanelListener implements ActionListener
} // end of class JeopardyGame
