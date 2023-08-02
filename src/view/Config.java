package ooga.view;

import java.util.ResourceBundle;

/**
 * This class stores all View variables necessary for View method functionality.
 *
 * @author Cate Schick cms168.
 */
public class Config {

  /**
   * Private constructor for public utility class.
   */
  private Config() {
  }

  /**
   * Class name keys.
   */
  public static final String INTRO_DISPLAY = "IntroDisplay";
  public static final String SET_GAME_DISPLAY = "GameChooserDisplay";
  public static final String ABOUT = "About";
  public static final String GAME_DISPLAY = "GameDisplay";
  public static final String GAME_SETTINGS = "GameSettings";
  public static final String CUSTOM_DECK_DISPLAY = "CustomDeckDisplay";
  public static final String DESIGN_SETTINGS = "DesignSettings";
  public static final String SIZE_DISPLAY = "SizeDisplay";
  public static final String FONT_DISPLAY = "FontDisplay";
  public static final String THEME_DISPLAY = "ThemeDisplay";
  public static final String LANGUAGE_SETTINGS = "LanguageSettings";
  public static final String PROFILE_SETTINGS = "ProfileSettings";
  public static final String SWITCH_PROFILE_DISPLAY = "SwitchProfiles";
  public static final String MY_PROFILE_DISPLAY = "MyProfileDisplay";
  public static final String END_DISPLAY = "EndDisplay";

  /**
   * Package paths.
   */
  public static final String INTRO_DISPLAY_PATH = "introDisplay.";
  public static final String GAME_SETTINGS_PATH = "gameSettings.";
  public static final String DESIGN_SETTINGS_PATH = "designSettings.";
  public static final String PROFILE_DISPLAY_PATH = "profileDisplay.";
  public static final String DISPLAY_PACKAGE_PATH = "ooga.view.display.";

  /**
   * File paths and prefixes.
   */
  public static final String GAME_FILE_PATH = "data/UnoGames";
  public static final String THEME_FILE_PATH = "src/resources/themes";
  public static final String THEME_PATH = "resources/themes/%s.css";
  public static final String PUBLIC_IMAGE_PATH = "data/profilePictures/public";
  public static final String BASIC_GAME_PATH = "data/UnoGames/BasicUnoDefault.json";
  public static final String RESOURCE_PATH = "resources/propertyFiles/%s";
  public static final String JSON_EXTENSION = "*.json";
  public static final String RESOURCE_FILE_PATH = "src/resources/propertyFiles";
  public static final String PROPERTY_EXTENSION = ".properties";
  public static final String DATA_PREFIX = "data/";
  public static final String CSS = ".css";
  public static final String FORMAT_STRING = "%s";

  /**
   * Title keys.
   */
  public static final String TITLE_KEY = "Title";
  public static final String DESIGN_SETTINGS_TITLE = "DesignSettingsTitle";
  public static final String FILE_CHOOSER_KEY = "FileChooser";
  public static final String GAME_SETTINGS_TITLE = "GameSettingsTitle";
  public static final String THEME_TITLE = "Title";
  public static final String LANGUAGE_SETTINGS_TITLE = "LanguageSettingsTitle";
  public static final String PROFILE_SETTINGS_TITLE = "ProfileSettingsTitle";
  public static final String WIN_TITLE = "WinTitle";
  public static final String LOSS_TITLE = "LossTitle";
  public static final String ERROR_TITLE = "ErrorTitle";

  /**
   * Keys for CSS style.
   */
  public static final String ROOT_STYLE = "Root";
  public static final String TITLE_STYLE = "Title";
  public static final String TOOLBAR_BUTTON_STYLE = "ToolbarButton";
  public static final String TOOLBAR_STYLE = "ToolBar";
  public static final String BUTTON_STYLE = "Button";
  public static final String SET_GAME_DISPLAY_STYLE = "SetGameDisplay";
  public static final String LOGIN_BUTTON_STYLE = "LoginButton";
  public static final String PROFILE_PICTURE_CONTAINER = "ProfilePictureContainer";
  public static final String PROFILE_PICTURE_STYLE = "MyProfilePicture";
  public static final String GRIDPANE_STYLE = "GridPane";
  public static final String THEME_STYLE = "ThemeBox";
  public static final String INFO_DISPLAY = "InfoDisplay";
  public static final String DIALOGUE_STYLE = "DialogPane";
  public static final String GAME_STATS_STYLE = "GameStats";
  public static final String GREETING_BUTTON = "GreetingButton";
  public static final String LABEL_STYLE = "Label";
  public static final String ABOUT_CONTENT_STYLE = "AboutEntry";
  public static final String ABOUT_STYLE = "AboutDescription";

  /**
   * Max buttons in GridPanes.
   */
  public static final int GAME_SETTINGS_MAX_PER_ROW = 2;
  public static final int FONT_DISPLAY_MAX_PER_ROW = 3;
  public static final int MAX_PICTURES_PER_ROW = 8;
  public static final int MAX_USERS = 10;
  public static final int SWITCH_PROFILES_MAX_BUTTONS_PER_ROW = 5;
  public static final int LANGUAGES_MAX_PER_ROW = 5;

  /**
   * Keys for intro display package buttons.
   */
  public static final String START_BUTTON = "StartButton";
  public static final String SETTINGS_BUTTON = "SettingsButton";

  public static final String BACK_BUTTON = "Back";
  public static final String LOAD_GAME = "LoadGame";
  public static final String CREATE_GAME = "CreateGame";
  public static final String BASIC_START = "StartBasic";

  public static final String HOME = "Home";
  public static final String NEW_GAME = "NewGame";
  public static final String GREETING = "Greeting";

  /**
   * About display content.
   */
  public static final String HOW_TO_PLAY_TITLE = "HowToPlayTitle";
  public static final String HOW_TO_PLAY_CONTENT = "HowToPlayContent";

  public static final String HOW_TO_WIN_TITLE = "HowToWinTitle";
  public static final String HOW_TO_WIN_CONTENT = "HowToWinContent";

  public static final String CREATOR_TITLE = "CreatorTitle";
  public static final String CREATOR_CONTENT = "CreatorContent";

  /**
   * Game Settings buttons.
   */
  public static final String GENERATE_GAME = "GenerateGame";
  public static final String NUM_PLAYERS = "NumPlayers";
  public static final String NUM_CARDS = "NumCards";
  public static final String GAME_VERSION = "GameVersion";

  public static final String BASIC = "basic";
  public static final String PIRATE = "pirate";
  public static final String RANDOM = "random";

  public static final String CUSTOM_FILENAME = "CustomGame";
  public static final String CUSTOM_GAME_FILEPATH = "data/UnoGames/saved/CustomGame.json";

  public static final String CHANGE_CARDS = "ChangeCards";
  public static final String CHANGE_PLAYERS = "ChangePlayers";
  public static final String CHANGE_VERSION = "ChangeVersion";
  public static final String CUSTOM_DECK = "CustomDeck";

  public static final int MIN_PLAYERS = 2;
  public static final int MAX_PLAYERS = 4;
  public static final int MIN_CARDS = 3;
  public static final int MAX_CARDS = 10;

  /**
   * Custom Deck buttons.
   */
  public static final String UNO = "UNO";
  public static final String SAVE_DECK = "SaveDeck";
  public static final String CREATE_CARD = "CreateCard";

  public static final int MIN_CARD_PARAM = 0;
  public static final int MAX_CARD_PARAM = 10;
  public static final int MIN_CARD_QUANTITY = 1;
  public static final int MAX_CARD_QUANTITY = 10;

  public static final String BLUE = "Blue";
  public static final String YELLOW = "Yellow";
  public static final String RED = "Red";
  public static final String GREEN = "Green";
  public static final String NONE = "None";

  public static final String NUMBER = "Number";
  public static final String REVERSE = "Reverse";
  public static final String SKIP = "Skip";
  public static final String WILD = "Wild";
  public static final String DRAW = "Draw";

  public static final String CUSTOM_CARD_LIST = "CustomCards";
  public static final String ONE_TYPE_MESSAGE = "OneTypeMessage";
  public static final String TWO_TYPE_MESSAGE = "TwoTypeMessage";

  /**
   * Design settings buttons.
   */
  public static final String SIZE_OPTIONS = "SizeSettings";
  public static final String FONT_OPTIONS = "FontSettings";
  public static final String THEME_OPTIONS = "ThemeSettings";

  public static final String THEME_DESCRIPTION = "Description";

  public static final String SMALL = "Small";
  public static final String MEDIUM = "Medium";
  public static final String LARGE = "Large";
  public static final String XLARGE = "XLarge";

  /**
   * End display buttons.
   */
  public static final String PLAY_AGAIN = "PlayAgain";
  public static final String RETURN_HOME = "BackHome";

  /**
   * Profile display buttons
   */
  public static final String EDIT_PROFILE = "EditProfile";
  public static final String SWITCH_PROFILE = "SwitchProfile";
  public static final String CREATE_PROFILE = "CreateProfile";
  public static final String DELETE_PROFILE = "DeleteProfile";
  public static final String PLAY_AS_GUEST = "PlayAsGuest";

  /**
   * Profile keys.
   */
  public static final String BIO_KEY = "Bio";
  public static final String NUM_WINS_KEY = "Wins";
  public static final String NUM_LOSSES_KEY = "Losses";
  public static final String NUM_GAMES_KEY = "NumGames";
  public static final String PROFILE_PICTURE_KEY = "ProfilePicture";
  public static final String PASSWORD_KEY = "Password";
  public static final String USERNAME_KEY = "Username";

  /**
   * Profile picture dimensions.
   */
  public static final int TOOLBAR_PIC_HEIGHT = 50;
  public static final int TOOLBAR_PIC_WIDTH = 50;
  public static final int PICTURE_OPTIONS_HEIGHT = 100;
  public static final int PICTURE_OPTIONS_WIDTH = 100;
  public static final int LOGIN_PIC_HEIGHT = 150;
  public static final int LOGIN_PIC_WIDTH = 150;

  /**
   * Symbols and prefixes.
   */
  public static final String SLASH = "/";
  public static final String DASH = "-";
  public static final String COLON = ":";
  public static final String PERIOD = "\\.";
  public static final String BLANK = "";
  public static final String SPACE = " ";
  public static final String ZERO = "0";
  public static final String OPTIONAL = "Optional";
  public static final String LEFT_BRACKET = "[";
  public static final String RIGHT_BRACKET = "]";

  /**
   * View and Resources objects.
   */
  public static View MY_VIEW;
  public static ResourceBundle MY_RESOURCES;

  /**
   * Error messages.
   */
  public static final String INFORMATION_MESSAGE_TITLE = "ALERT";
  public static final String ERROR_MESSAGE = "ErrorMessage";
  public static final String INVALID_RESOURCE = "InvalidResource";
  public static final String NULL_FILE = "NullFile";
  public static final String NO_MORE_CARDS = "NoMoreCards";

  /**
   * Game settings prompts and messages.
   */
  public static final String EMPTY_DECK = "EmptyDeck";
  public static final String NONE_COLOR_MESSAGE = "NoneColorMessage";
  public static final String CARD_TYPE_PROMPT = "CardTypePrompt";
  public static final String CARD_QUANTITY_PROMPT = "CardQuantityPrompt";
  public static final String CARD_COLOR_PROMPT = "CardColorPrompt";
  public static final String SECOND_TYPE_PROMPT = "SecondTypePrompt";
  public static final String CARD_PARAM_PROMPT = "ParamPrompt";
  public static final String NO_SECOND_TYPE = "NoSecondType";
  public static final String ADDED_TO_DECK_MESSAGE = "AddedToDeck";

  /**
   * Default settings keys.
   */
  public static final String ENGLISH = "english";
  public static final String DEFAULT_THEME = "default";

  /**
   * User Preferences keys.
   */
  public static final String PREFERRED_PREFIX = "Preferred";
  public static final String THEME_KEY = "Theme";
  public static final String FONT_KEY = "Font";
  public static final String SIZE_KEY = "Size";
  public static final String LANGUAGE = "Language";

  /**
   * Cheat code messages.
   */
  public static final String GAME_STATS_CLEARED = "GameStatsCleared";

  /**
   * Message status keys.
   */
  public static final String SUCCESS = "Success";
  public static final String CANNOT_COMPLETE = "CannotComplete";
  public static final String CAUTION = "Caution";
  public static final String REQUIRED_KEY = "Required";
  public static final String OPTIONAL_KEY = "Optional";

  /**
   * Profile prompts and messages.
   */
  public static final String NOW_GUEST = "NowGuest";
  public static final String PROFILE_PROMPT = "ProfilePrompt";
  public static final String CHANGE_KEY = "ChangeKey";
  public static final String EMPTY_RESPONSE = ".empty";
  public static final String NO_VALUE = "NoValue";
  public static final String NO_USERNAME_ERROR = "NoUsername";
  public static final String WRONG_PASSWORD_ERROR = "WrongPassword";
  public static final String LOGGED_IN_MESSAGE = "SuccessfulLogin";
  public static final String ALREADY_GUEST = "AlreadyGuest";
  public static final String PASSWORD_PROMPT = "PasswordPrompt";
  public static final String DELETE_PROFILE_PROMPT = "DeletePrompt";
  public static final String SELECT = "PleaseSelect";
  public static final String DUPLICATE_USERNAME = "DuplicateUsername";
  public static final String MAX_USERS_ERROR = "MaxUsers";
  public static final String NEW_KEY = "NewKey";
  public static final String GUEST = "Guest";
  public static final String ADD_KEY = "AddKey";
  public static final String DELETE_GUEST = "DeleteGuest";
  public static final String CHANGE_PASSWORD = "ChangePassword";
  public static final String CHARACTER_LIMIT = "MaxCharacter";
  public static final int MAX_CHARACTERS = 25;

  /**
   * Font keys.
   */
  public static final String ARIAL = "Arial";
  public static final String CHALKDUSTER = "Chalkduster";
  public static final String COPPERPLATE = "Copperplate";
  public static final String FUTURA = "Futura";
  public static final String GEORGIA = "Georgia";
  public static final String IMPACT = "Impact";
  public static final String LUMINARI = "Luminari";
  public static final String MONACO = "Monaco";
  public static final String NOTEWORTHY = "Noteworthy";
  public static final String PAPYRUS = "Papyrus";
  public static final String PHOSPHATE = "Phosphate";
  public static final String SIGNPAINTER = "SignPainter";
  public static final String FONT_EXTENSION = "-HouseScript";
}
