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
  private static final String WINDOW_TITLE = "Jeopardy Game Launcher";
  private static final String JEOPARDY_FONT_SOURCE = "resources/fonts/jeopardy.ttf";
  private static final String JEOPARDY_TEXT = "Jeopardy!";
  private static int JEOPARDY_FONT_SIZE = 150;
  private static int[] FONT_COLOR = {255, 255, 255};
  private static final String CATEGORY_FONT_SOURCE = "resources/fonts/carson_d.Ttf";
  private static final int CATEGORY_FONT_SIZE = 28;
  private static final int JEOPARDY_BANNER_HEIGHT = 300;
  private static final int CHECK_BOX_HEIGHT = 65;
  private static final int BOTTOM_PANEL_HEIGHT = 100;
  private static final String START_GAME_TEXT = "LAUNCH GAME";
  private static final String QUIT_BUTTON_TEXT = "QUIT";
  private static final String RELOAD_BUTTON_TEXT = "RELOAD";
  private static final int BUTTON_WIDTH = 200;
  private static final int BUTTON_HEIGHT = 80;
  private static final int SMALL_BUTTON_WIDTH = 200;
  private static final int SMALL_BUTTON_HEIGHT = 35;
  private static final int CONTROL_PANEL_MARGINS = 10;
  private static final int NUMERICAL_CHOICES_PANEL_MARGINS = 15;
  private static final String HEADING_LABEL_FONT_NAME = "Arial";
  private static final int HEADING_LABEL_FONT_SIZE = 48;
  private static final String CATEGORY_HEADING_LABEL_TEXT = "Categories";
  private static final String NUMBER_OF_PLAYERS_HEADING_LABEL_TEXT = "Number of Players";
  private static final String ADD_PLAYER_BUTTON_TEXT = "Add";
  private static final String REMOVE_BUTTON_TEXT = "Remove";
  private static int ADD_OR_REMOVE_BUTTON_WIDTH = 100;
  private static int ADD_OR_REMOVE_BUTTON_HEIGHT = 35;
  private static int CENTER_PANEL_MARGINS = 100;
  private static String NUMBER_OF_ROUNDS_HEADING_LABEL_TEXT = "Number of Rounds";
  private static String NUMBER_OF_ANSWERS_HEADING_LABEL_TEXT = "Number of Answers";

  /* instance fields */
  private Category[] allCategories;
  private JCheckBox[] categoryCheckBoxButtons;
  private Category[] categoriesToUseInGame;
  private JFrame gameLauncherFrame;
  private JPanel categoriesCheckBoxPanel;
  private JPanel frameCenterPanel;
  private Font jeopardyFont;
  private JLabel jeopardyLogo;
  private JPanel jeopardyLogoPanel;
  private Font labelFont;
  private int frameHeight;
  private int frameWidth;
  private JButton startGameButton;
  private JPanel bottomControlPanel;
  private JButton quitButton;
  private JButton reloadButton;
  private JPanel quitAndReloadPanel;
  private ControlPanelListener controlPanelListener;
  private JButton addPlayerButton;
  private JButton removePlayerButton;
  private JLabel numberOfPlayersLabel;
  private JPanel innerNumberOfPlayersPanel;
  private JPanel outerNumberOfPlayersPanel;
  private int numberOfPlayers;
  private Font headingLabelFont;
  private JLabel categoryHeadingLabel;
  private JLabel numberOfPlayersHeadingLabel;
  private NumericalGameOptionsListener numericalGameOptionsListener;
  private JPanel numericalChoicesPanel;
  private JPanel innerNumberOfRoundsPanel;
  private JPanel outerNumberOfRoundsPanel;
  private JLabel numberOfRoundsLabel;
  private int numberOfRounds;
  private JButton addRoundButton;
  private JButton removeRoundButton;
  private JLabel numberOfRoundsHeadingLabel;
  private JPanel innerNumberOfAnswersPanel;
  private JPanel outerNumberOfAnswersPanel;
  private int numberOfAnswers;
  private JButton removeAnswerButton;
  private JButton addAnswerButton;
  private JLabel numberOfAnswersHeadingLabel;
  private JLabel numberOfAnswersLabel;

  /* constructors */

  /**
  * Creates a GameLauncher with default characteristics.
  */
  public GameLauncher()
  {
    allCategories = new Category[JeopardyGame.CATEGORY_COUNT];
  } // end of constructor GameLauncher()

  /**
  * Creates a GameLauncher with the specified game data.
  */
  public GameLauncher(Category[] categoryData)
  {
    if (categoryData != null)
    {
      // Set as specified value.
      allCategories = categoryData;
    }
    else
    {
      // Set as default value.
      allCategories = new Category[JeopardyGame.CATEGORY_COUNT];
    } // end of if (categoryData != null)
    makeFrame();
  } // end of constructor GameLauncher(Category[] categoryData)

  /* accessors */

  /**
  * Returns all the category loaded.
  *
  * @return all the catetories in this game of jeopardy
  */
  public Category[] getCategories()
  {
    return allCategories;
  } // end of method getCategories()

  /* private methods */

  /*
  * Quits the game.
  */
  private void quit()
  {
    System.exit(0);
  } // end of method quit()

  /*
  * Display an error message in a dialog box with the option to try again or quit the game.
  *
  * @param errorMessage the error message to display in a dialog box; may not be <code>null</code>
  */
  private void displayErrorDialog(String errorMessage)
  {
    // Verify validity of parameter.
    String messageToDisplay;
    if (errorMessage != null)
    {
      messageToDisplay = errorMessage;
    }
    else
    {
      messageToDisplay = "ERROR";
    } // end of if (errorMessage != null)

    // Display error dialog with specified message.
    int errorDialog = JOptionPane.showOptionDialog(gameLauncherFrame, messageToDisplay, "Error",JOptionPane.YES_NO_OPTION,
    JOptionPane.ERROR_MESSAGE, null, ERROR_DIALOG_MESSAGES, ERROR_DIALOG_MESSAGES[0]);

    // Wait for user input.
    if (errorDialog == JOptionPane.YES_OPTION)
    {
      // Dispose current game launcher.
      gameLauncherFrame.dispose();

      // Restart game through splash screen.
      new SplashScreen();
    }
    else
    {
      // Quit the game.
      quit();
    } // end of if (errorDialog == JOptionPane.YES_OPTION)
  } // end of method displayErrorDialog()

  private void launchGame()
  {
    // Identify number of categories to use in game.
    int numberOfCategoriesToUse = 0;
    for (int categoryIndex = 0; categoryIndex < categoryCheckBoxButtons.length; categoryIndex++)
    {
      if (categoryCheckBoxButtons[categoryIndex].isSelected())
      {
        numberOfCategoriesToUse++;
      } // end of if (categoryCheckBoxButtons[categoryIndex].isSelected())
    } // end of for (int categoryIndex = 0; categoryIndex < categoryCheckBoxButtons.length; categoryIndex++)

    // Test user's choices with specified game constants.
    if (numberOfCategoriesToUse < JeopardyGame.MINIMUM_NUMBER_OF_CATEGORIES)
    {
      // Display warning message.
      createWarningDialog("ERROR: The minimum number of categories (" + JeopardyGame.MINIMUM_NUMBER_OF_CATEGORIES + ")"  + " was not met. Please try again.");
    } // end of if (numberOfCategoriesToUse < JeopardyGame.MINIMUM_NUMBER_OF_CATEGORIES)
    else if (numberOfCategoriesToUse > JeopardyGame.MAXIMUM_NUMBER_OF_CATEGORIES)
    {
      // Display warning message.
      createWarningDialog("ERROR: The maximum number of categories (" + JeopardyGame.MINIMUM_NUMBER_OF_CATEGORIES + ")"  + " was exceeded. Please try again.");
    } // end of if (numberOfCategoriesToUse > JeopardyGame.MAXIMUM_NUMBER_OF_CATEGORIES)
    else
    {
      // Initialize categories to use.
      categoriesToUseInGame = new Category[numberOfCategoriesToUse];

      // Initialize index counter.
      int index = 0;

      // Iterate through all category radio buttons to check for selection.
      for (int categoryIndex = 0; categoryIndex < categoryCheckBoxButtons.length; categoryIndex++)
      {
        if (categoryCheckBoxButtons[categoryIndex].isSelected())
        {
          // Add current category to categories to use in game.
          categoriesToUseInGame[index] = allCategories[categoryIndex];

          // Increment index.
          index++;
        } // end of if (categoryCheckBoxButtons[categoryIndex].isSelected())
      } // end of for (int categoryIndex = 0; categoryIndex < categoryCheckBoxButtons.length; categoryIndex++)

      // Dispose of game launcher.
      gameLauncherFrame.dispose();

      // Start Jeopardy game.
      new JeopardyGame(categoriesToUseInGame, allCategories, numberOfPlayers, numberOfRounds, numberOfAnswers);
    } // end of if (numberOfCategoriesToUse < JeopardyGame.MINIMUM_NUMBER_OF_CATEGORIES)
  } // end of method launchGame()

  /*
  * Creates a warning dialog with the specified message.
  *
  * @param warningDialogText the text of the warning dialog; may not be <code>null</code>
  */
  public void createWarningDialog(String warningDialogText)
  {
    // Test specified parameter.
    if (warningDialogText != null)
    {
      // Display error message.
      JOptionPane.showMessageDialog(null, warningDialogText, "Warning",JOptionPane.ERROR_MESSAGE);
    } // end of if (warningDialogText != null)
  } // end of method createWarningDialog(String warningDialogText)

  /*
  * Imports the fonts necessary to create the loading frame.
  */
  private void loadFonts()
  {
    try
    {
      // Import fonts.
      jeopardyFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(JEOPARDY_FONT_SOURCE))).deriveFont(Font.PLAIN, JEOPARDY_FONT_SIZE);
      labelFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(CATEGORY_FONT_SOURCE))).deriveFont(Font.PLAIN, CATEGORY_FONT_SIZE);
      headingLabelFont = new Font(HEADING_LABEL_FONT_NAME, Font.BOLD + Font.ITALIC, HEADING_LABEL_FONT_SIZE);
    }
    catch (IOException exception)
    {
      // Display error dialog.
      displayErrorDialog("ERROR: Unable to locate font resources.");
    } // end of catch (IOException exception)
    catch (FontFormatException exception)
    {
      // Display error dialog.
      displayErrorDialog("ERROR: Unable to locate font resources.");
    } // end of catch (FontFormatException exception)
  } // end of method loadFonts()

  /*
  * Creates the check box panel for categories.
  */
  private void createCategoryPanel()
  {
    // Create categories panel.
    categoriesCheckBoxPanel = new JPanel();
    categoriesCheckBoxPanel.setLayout(new GridLayout(0, 1, CATEGORY_PANEL_MARGINS, CATEGORY_PANEL_MARGINS));
    categoriesCheckBoxPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));

    // Create categories panel heading.
    categoryHeadingLabel = new JLabel(CATEGORY_HEADING_LABEL_TEXT);
    categoryHeadingLabel.setFont(headingLabelFont);
    categoryHeadingLabel.setForeground(new Color(FONT_COLOR[0], FONT_COLOR[1], FONT_COLOR[2]));
    categoriesCheckBoxPanel.add(categoryHeadingLabel);

    // Create category check box buttons.
    categoryCheckBoxButtons = new JCheckBox[allCategories.length];

    for (int index = 0; index < allCategories.length; index++)
    {
      // Initialize current check box button.
      categoryCheckBoxButtons[index] = new JCheckBox(allCategories[index].getName());
      categoryCheckBoxButtons[index].setSelected(true);
      categoryCheckBoxButtons[index].setFont(labelFont);
      categoryCheckBoxButtons[index].setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));
      categoryCheckBoxButtons[index].setForeground(new Color(FONT_COLOR[0], FONT_COLOR[1], FONT_COLOR[2]));

      // Add check box button to categories panel.
      categoriesCheckBoxPanel.add(categoryCheckBoxButtons[index]);
    } // end of for (int index = 0; index < allCategories.length; index++)
  } // end of method createCategoryPanel()

  /*
  * Creates the jeopardy banner.
  */
  private void createJeopardyBanner()
  {
    // Create Jeopardy panel.
    jeopardyLogoPanel = new JPanel();
    jeopardyLogoPanel.setLayout(new FlowLayout());
    jeopardyLogoPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));

    // Create Jeopardy logo.
    jeopardyLogo = new JLabel(JEOPARDY_TEXT);
    jeopardyLogo.setFont(jeopardyFont);
    jeopardyLogo.setForeground(new Color(FONT_COLOR[0], FONT_COLOR[1], FONT_COLOR[2]));

    // Add Jeopardy logo to panel.
    jeopardyLogoPanel.add(jeopardyLogo);
  } // end of method createJeopardyBanner()

  /*
  * Creates the control panel.
  */
  public void createControlPanel()
  {
    // Create listener to listen to control panel events.
    controlPanelListener = new ControlPanelListener();

    // Create control panel.
    bottomControlPanel = new JPanel();
    bottomControlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, CONTROL_PANEL_MARGINS, CONTROL_PANEL_MARGINS));
    bottomControlPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));

    // Create start game button.
    startGameButton = new JButton(START_GAME_TEXT);
    startGameButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    startGameButton.addActionListener(controlPanelListener);
    bottomControlPanel.add(startGameButton);

    // Create quit and reload panel.
    quitAndReloadPanel = new JPanel();
    quitAndReloadPanel.setLayout(new GridLayout(0, 1, CONTROL_PANEL_MARGINS, CONTROL_PANEL_MARGINS));
    quitAndReloadPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));

    // Create quit button.
    quitButton = new JButton(QUIT_BUTTON_TEXT);
    quitButton.setPreferredSize(new Dimension(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT));
    quitButton.addActionListener(controlPanelListener);

    // Add quit button to quit and reload panel.
    quitAndReloadPanel.add(quitButton);

    // Create reload button.
    reloadButton = new JButton(RELOAD_BUTTON_TEXT);
    reloadButton.setPreferredSize(new Dimension(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT));
    reloadButton.addActionListener(controlPanelListener);

    // Add reload button to quit and reload panel.
    quitAndReloadPanel.add(reloadButton);

    // Add quit and reload panel to control panel.
    bottomControlPanel.add(quitAndReloadPanel);
  } // end of method createControlPanel()

  /*
  * Creates the number of players panel.
  */
  private void createPlayerCountPanel()
  {
    // Initialize number of players.
    numberOfPlayers = JeopardyGame.MINIMUM_NUMBER_OF_PLAYERS;

    // Create outer panel to hold inner components.
    outerNumberOfPlayersPanel = new JPanel();
    outerNumberOfPlayersPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));
    outerNumberOfPlayersPanel.setLayout(new GridLayout(0, 1));

    // Create heading for number of players.
    numberOfPlayersHeadingLabel = new JLabel(NUMBER_OF_PLAYERS_HEADING_LABEL_TEXT);
    numberOfPlayersHeadingLabel.setFont(headingLabelFont);
    numberOfPlayersHeadingLabel.setForeground(new Color(FONT_COLOR[0], FONT_COLOR[1], FONT_COLOR[2]));
    outerNumberOfPlayersPanel.add(numberOfPlayersHeadingLabel);

    // Create inner panel for number of players.
    innerNumberOfPlayersPanel = new JPanel();
    innerNumberOfPlayersPanel.setLayout(new GridLayout(0, 3, NUMERICAL_CHOICES_PANEL_MARGINS, NUMERICAL_CHOICES_PANEL_MARGINS));
    innerNumberOfPlayersPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));

    // Create remove player button.
    removePlayerButton = new JButton(REMOVE_BUTTON_TEXT);
    removePlayerButton.setPreferredSize(new Dimension(ADD_OR_REMOVE_BUTTON_WIDTH, ADD_OR_REMOVE_BUTTON_HEIGHT));
    removePlayerButton.setFont(labelFont);
    removePlayerButton.addActionListener(numericalGameOptionsListener);
    removePlayerButton.setEnabled(false);

    // Add remove player button to inner panel.
    innerNumberOfPlayersPanel.add(removePlayerButton);

    // Create number of players label.
    numberOfPlayersLabel = new JLabel(Integer.toString(numberOfPlayers));
    numberOfPlayersLabel.setFont(labelFont);
    numberOfPlayersLabel.setForeground(new Color(FONT_COLOR[0], FONT_COLOR[1], FONT_COLOR[2]));
    numberOfPlayersLabel.setHorizontalAlignment(JLabel.CENTER);

    // Add number of players label to inner panel.
    innerNumberOfPlayersPanel.add(numberOfPlayersLabel);

    // Create add player button.
    addPlayerButton = new JButton(ADD_PLAYER_BUTTON_TEXT);
    addPlayerButton.setPreferredSize(new Dimension(ADD_OR_REMOVE_BUTTON_WIDTH, ADD_OR_REMOVE_BUTTON_HEIGHT));
    addPlayerButton.setFont(labelFont);
    addPlayerButton.addActionListener(numericalGameOptionsListener);

    // Add add player button to inner panel.
    innerNumberOfPlayersPanel.add(addPlayerButton);

    // Add inner panel to outer panel.
    outerNumberOfPlayersPanel.add(innerNumberOfPlayersPanel);
  } // end of method createPlayerCountPanel()

  /*
  * Creates the number of rounds panel.
  */
  public void createRoundCountPanel()
  {
    // Initialize number of rounds.
    numberOfRounds = JeopardyGame.MINIMUM_NUMBER_OF_ROUNDS;

    // Create outer panel for number of rounds to house inner components.
    outerNumberOfRoundsPanel = new JPanel();
    outerNumberOfRoundsPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));
    outerNumberOfRoundsPanel.setLayout(new GridLayout(0, 1));

    // Create number of rounds heading label.
    numberOfRoundsHeadingLabel = new JLabel(NUMBER_OF_ROUNDS_HEADING_LABEL_TEXT);
    numberOfRoundsHeadingLabel.setFont(headingLabelFont);
    numberOfRoundsHeadingLabel.setForeground(new Color(FONT_COLOR[0], FONT_COLOR[1], FONT_COLOR[2]));

    // Add heading label to outer panel.
    outerNumberOfRoundsPanel.add(numberOfRoundsHeadingLabel);

    // Create inner number of rounds panel.
    innerNumberOfRoundsPanel = new JPanel();
    innerNumberOfRoundsPanel.setLayout(new GridLayout(0, 3, NUMERICAL_CHOICES_PANEL_MARGINS, NUMERICAL_CHOICES_PANEL_MARGINS));
    innerNumberOfRoundsPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));

    // Create remove round button.
    removeRoundButton = new JButton(REMOVE_BUTTON_TEXT);
    removeRoundButton.setPreferredSize(new Dimension(ADD_OR_REMOVE_BUTTON_WIDTH, ADD_OR_REMOVE_BUTTON_HEIGHT));
    removeRoundButton.setFont(labelFont);
    removeRoundButton.addActionListener(numericalGameOptionsListener);
    removeRoundButton.setEnabled(false);

    // Add remove round button to inner panel.
    innerNumberOfRoundsPanel.add(removeRoundButton);

    // Create number of rounds label.
    numberOfRoundsLabel = new JLabel(Integer.toString(numberOfRounds));
    numberOfRoundsLabel.setFont(labelFont);
    numberOfRoundsLabel.setForeground(new Color(FONT_COLOR[0], FONT_COLOR[1], FONT_COLOR[2]));
    numberOfRoundsLabel.setHorizontalAlignment(JLabel.CENTER);

    // Add number of rounds label to inner panel.
    innerNumberOfRoundsPanel.add(numberOfRoundsLabel);

    // Create add round button.
    addRoundButton = new JButton(ADD_PLAYER_BUTTON_TEXT);
    addRoundButton.setPreferredSize(new Dimension(ADD_OR_REMOVE_BUTTON_WIDTH, ADD_OR_REMOVE_BUTTON_HEIGHT));
    addRoundButton.setFont(labelFont);
    addRoundButton.addActionListener(numericalGameOptionsListener);

    // Add add round button to inner panel.
    innerNumberOfRoundsPanel.add(addRoundButton);

    // Add inner panel to outer panel.
    outerNumberOfRoundsPanel.add(innerNumberOfRoundsPanel);
  } // end of method createRoundCountPanel()

  /*
  * Creates the number of answers panel.
  */
  private void createAnswerCountPanel()
  {
    // Initialize number of answers.
    numberOfAnswers = JeopardyGame.MAXIMUM_NUMBER_OF_ANSWERS;

    // Create outer panel for number of answers to store inner components.
    outerNumberOfAnswersPanel = new JPanel();
    outerNumberOfAnswersPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));
    outerNumberOfAnswersPanel.setLayout(new GridLayout(0, 1));

    // Create number of answers heading label.
    numberOfAnswersHeadingLabel = new JLabel(NUMBER_OF_ANSWERS_HEADING_LABEL_TEXT);
    numberOfAnswersHeadingLabel.setFont(headingLabelFont);
    numberOfAnswersHeadingLabel.setForeground(new Color(FONT_COLOR[0], FONT_COLOR[1], FONT_COLOR[2]));

    // Add number of answers heading label to outer panel.
    outerNumberOfAnswersPanel.add(numberOfAnswersHeadingLabel);

    // Create inner number of answers panel.
    innerNumberOfAnswersPanel = new JPanel();
    innerNumberOfAnswersPanel.setLayout(new GridLayout(0, 3, NUMERICAL_CHOICES_PANEL_MARGINS, NUMERICAL_CHOICES_PANEL_MARGINS));
    innerNumberOfAnswersPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));

    // Create remove answer button.
    removeAnswerButton = new JButton(REMOVE_BUTTON_TEXT);
    removeAnswerButton.setPreferredSize(new Dimension(ADD_OR_REMOVE_BUTTON_WIDTH, ADD_OR_REMOVE_BUTTON_HEIGHT));
    removeAnswerButton.setFont(labelFont);
    removeAnswerButton.addActionListener(numericalGameOptionsListener);

    // Add remove answer button to inner panel.
    innerNumberOfAnswersPanel.add(removeAnswerButton);

    // Create number of answers label.
    numberOfAnswersLabel = new JLabel(Integer.toString(numberOfAnswers));
    numberOfAnswersLabel.setFont(labelFont);
    numberOfAnswersLabel.setForeground(new Color(FONT_COLOR[0], FONT_COLOR[1], FONT_COLOR[2]));
    numberOfAnswersLabel.setHorizontalAlignment(JLabel.CENTER);

    // Add number of answers label to inner panel.
    innerNumberOfAnswersPanel.add(numberOfAnswersLabel);

    // Create add answer button.
    addAnswerButton = new JButton(ADD_PLAYER_BUTTON_TEXT);
    addAnswerButton.setPreferredSize(new Dimension(ADD_OR_REMOVE_BUTTON_WIDTH, ADD_OR_REMOVE_BUTTON_HEIGHT));
    addAnswerButton.setFont(labelFont);
    addAnswerButton.addActionListener(numericalGameOptionsListener);
    addAnswerButton.setEnabled(false);

    // Add add answer button to inner panel.
    innerNumberOfAnswersPanel.add(addAnswerButton);

    // Add inner panel to outer panel.
    outerNumberOfAnswersPanel.add(innerNumberOfAnswersPanel);
  } // end of method createAnswerCountPanel()

  /*
  * Creates the numerical choices panel.
  */
  public void createNumericalChoicePanel()
  {
    // Create new action listener to listen to events from the numerical game options.
    numericalGameOptionsListener = new NumericalGameOptionsListener();

    // Create new numerical choices panel.
    numericalChoicesPanel = new JPanel();
    numericalChoicesPanel.setLayout(new GridLayout(0, 1, 0, NUMERICAL_CHOICES_PANEL_MARGINS));
    numericalChoicesPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));

    // Add player options panel.
    createPlayerCountPanel();
    numericalChoicesPanel.add(outerNumberOfPlayersPanel);

    // Add round options panel.
    createRoundCountPanel();
    numericalChoicesPanel.add(outerNumberOfRoundsPanel);

    // Add answer options panel.
    createAnswerCountPanel();
    numericalChoicesPanel.add(outerNumberOfAnswersPanel);
  } // end of method createNumericalChoicePanel()

  /*
  * Creates a frame for the game launcher.
  */
  private void makeFrame()
  {
    frameWidth = FRAME_WIDTH;
    frameHeight = (CHECK_BOX_HEIGHT * allCategories.length) + JEOPARDY_BANNER_HEIGHT + BOTTOM_PANEL_HEIGHT;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    gameLauncherFrame = new JFrame(WINDOW_TITLE);
    gameLauncherFrame.setPreferredSize(new Dimension(frameWidth, frameHeight));
    gameLauncherFrame.getContentPane().setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));
    gameLauncherFrame.setLayout(new BorderLayout());
    int xLocation = Math.round((screenSize.width - FRAME_WIDTH)/2);
    int yLocation = Math.round((screenSize.height - FRAME_HEIGHT)/2);
    gameLauncherFrame.setLocation(xLocation, yLocation);

    loadFonts();

    createJeopardyBanner();
    gameLauncherFrame.add(jeopardyLogoPanel, BorderLayout.PAGE_START);

    createControlPanel();
    gameLauncherFrame.add(bottomControlPanel, BorderLayout.PAGE_END);

    frameCenterPanel = new JPanel();
    frameCenterPanel.setLayout(new FlowLayout(FlowLayout.CENTER, CENTER_PANEL_MARGINS, 0));
    frameCenterPanel.setBackground(new Color(WINDOW_BACKGROUND_COLOR[0], WINDOW_BACKGROUND_COLOR[1], WINDOW_BACKGROUND_COLOR[2]));

    createCategoryPanel();
    frameCenterPanel.add(categoriesCheckBoxPanel);

    createNumericalChoicePanel();
    frameCenterPanel.add(numericalChoicesPanel);

    gameLauncherFrame.add(frameCenterPanel, BorderLayout.CENTER);

    gameLauncherFrame.pack();
    gameLauncherFrame.setVisible(true);
  } // end of method makeFrame()

  /* private classes */

  /*
  * Listens to events from the control panel.
  */
  private class ControlPanelListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      Object source = event.getSource();

      if (source == quitButton)
      {
        quit();
      } // end of if (source == quitButton)
      else if (source == reloadButton)
      {
        gameLauncherFrame.dispose();
        new SplashScreen();
      } // end of if (source == reloadButton)
      else if (source == startGameButton)
      {
        launchGame();
      } // end of if (source == startGameButton)
    } // end of method actionPerformed(ActionEvent event)
  } // end of class ControlPanelListener implements ActionListener

  /*
  * Listens to events from the numerical game choices panel.
  */
  private class NumericalGameOptionsListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      Object source = event.getSource();

      if (source == addPlayerButton)
      {
        numberOfPlayers++;
        numberOfPlayersLabel.setText(Integer.toString(numberOfPlayers));

        if (numberOfPlayers == JeopardyGame.MAXIMUM_NUMBER_OF_PLAYERS)
        {
          addPlayerButton.setEnabled(false);
        } // end of if (numberOfPlayers == JeopardyGame.MAXIMUM_NUMBER_OF_PLAYERS)
        removePlayerButton.setEnabled(true);
      } // end of if (source == addPlayerButton)
      else if (source == removePlayerButton)
      {
        numberOfPlayers--;
        numberOfPlayersLabel.setText(Integer.toString(numberOfPlayers));

        if (numberOfPlayers == JeopardyGame.MINIMUM_NUMBER_OF_PLAYERS)
        {
          removePlayerButton.setEnabled(false);
        } // end of if (numberOfPlayers == JeopardyGame.MINIMUM_NUMBER_OF_PLAYERS)
        addPlayerButton.setEnabled(true);
      } // end of if (source == removePlayerButton)
      else if (source == addRoundButton)
      {
        numberOfRounds++;
        numberOfRoundsLabel.setText(Integer.toString(numberOfRounds));

        if (numberOfRounds == JeopardyGame.MAXIMUM_NUMBER_OF_ROUNDS)
        {
          addRoundButton.setEnabled(false);
        } // end of if (numberOfRounds == JeopardyGame.MAXIMUM_NUMBER_OF_ROUNDS)
        removeRoundButton.setEnabled(true);
      } // end of if (source == addRoundButton)
      else if (source == removeRoundButton)
      {
        numberOfRounds--;
        numberOfRoundsLabel.setText(Integer.toString(numberOfRounds));

        if (numberOfRounds == JeopardyGame.MINIMUM_NUMBER_OF_ROUNDS)
        {
          removeRoundButton.setEnabled(false);
        } // end of if (numberOfRounds == JeopardyGame.MINIMUM_NUMBER_OF_ROUNDS)
        addRoundButton.setEnabled(true);
      } // end of if (source == removeRoundButton)
      else if (source == addAnswerButton)
      {
        numberOfAnswers++;
        numberOfAnswersLabel.setText(Integer.toString(numberOfAnswers));

        if (numberOfAnswers == JeopardyGame.MAXIMUM_NUMBER_OF_ANSWERS)
        {
          addAnswerButton.setEnabled(false);
        } // end of if (numberOfAnswers == JeopardyGame.MAXIMUM_NUMBER_OF_ANSWERS)
        removeAnswerButton.setEnabled(true);
      } // end of if (source == addAnswerButton)
      else if (source == removeAnswerButton)
      {
        numberOfAnswers--;
        numberOfAnswersLabel.setText(Integer.toString(numberOfAnswers));

        if (numberOfAnswers == JeopardyGame.MINIMUM_NUMBER_OF_ANSWERS)
        {
          removeAnswerButton.setEnabled(false);
        } // end of if (numberOfAnswers == JeopardyGame.MINIMUM_NUMBER_OF_ANSWERS)
        addAnswerButton.setEnabled(true);
      } // end of if (source == removeAnswerButton)
    } // end of method actionPerformed(ActionEvent event)
  } // end of class NumericalGameOptionsListener
} // end of class GameLauncher
