package ooga.data.dataResources;

import java.util.Arrays;
import java.util.List;
import ooga.Config;

/**
 * This class is here to hold constants, mainly keys in the json files, that are needed to access
 * the correct fields in the json files describing games and players.
 *
 * @author Keith Cressman
 */
public class DataConfig {

  //messages for exceptions arising when loading a game file
  public static final String MISSING_GAME_KEY_MESSAGE = "MISSING_GAME_KEY_MESSAGE";
  public static final String MISSING_CARD_KEY_MESSAGE = "MISSING_CARD_KEY_MESSAGE";
  public static final String MISSING_PLAYER_KEY_MESSAGE = "MISSING_PLAYER_KEY_MESSAGE";
  public static final String WRONG_DATA_TYPE_MESSAGE = "WRONG_DATA_TYPE_MESSAGE";
  public static final String UNRECOGNIZED_VALUE_MESSAGE = "UNRECOGNIZED_VALUE_MESSAGE";
  public static final String IMPROPER_JSON_MESSAGE = "IMPROPER_JSON_MESSAGE";
  public static final String NOT_ENOUGH_CARDS_MESSAGE = "NOT_ENOUGH_CARDS_MESSAGE";

  //messages for exceptions arising when saving a game
  public static final String BLANK_NAME_MESSAGE = "BLANK_NAME_MESSAGE";
  public static final String NAME_CONTAINS_BAD_CHAR_MESSAGE = "NAME_CONTAINS_BAD_CHAR_MESSAGE";

  //characters not allowed in what is the argument for fileName in the methods for saving a game
  public static final String[] NON_ALLOWED_NAME_CHARS = new String[]{".", "/", "\\", "{", "}", "?", "#", "$", "@", "!", "%", "&", "|", "*", "<", ">", "[", "]"};
  //folder where the saved/paused games are stored
  public static final String SAVED_GAMES_PATHS = "data/UnoGames/saved/";

  //json keys for describing a game
  public static final String UNO_TYPE_KEY = "unoType";
  public static final String NUM_PLAYERS_KEY = "numPlayers";
  public static final String CARDS_PER_PLAYER_KEY = "cardsPerPlayer";
  public static final String CURRENT_PLAYER_KEY = "CurrentPlayerID";
  public static final String DRAW_DECK_KEY = "drawDeck";
  public static final String PLAY_DECK_KEY = "playDeck";
  public static final String STARTED_KEY = "started";
  public static final String PLAYERS_KEY = "players";
  public static final String RENEGING_POSSIBLE_KEY = "renegingPossible";
  public static final String TIMED_TURNS_KEY = "timedTurns";
  public static final String BACK_CARD_IMG_PATH = "backCardImagePath";

  //json keys for describing a player
  public static final String PLAYER_HAND_KEY = "playerHand";
  public static final String PLAYER_ID_KEY = "playerID";

  //json keys for describing a card
  public static final String COLOR_KEY = "color";
  public static final String PARAM_KEY = "param";
  public static final String TYPE_KEY = "type";
  public static final String CARD_INFOS_KEY = "cardInfos";

  //json keys needed to describe a card
  public static final List<String> NEEDED_CARD_KEYS = Arrays.asList(
      new String[]{COLOR_KEY, CARD_INFOS_KEY});
  //json keys needed to describe a cardInfo
  public static final List<String> NEEDED_CARDINFO_KEYS = Arrays.asList(
      new String[]{TYPE_KEY, PARAM_KEY});
  //json keys needed to describe a player
  public static final List<String> NEEDED_PLAYER_KEYS = Arrays.asList(
      new String[]{PLAYER_ID_KEY, PLAYER_HAND_KEY});

  //json keys needed to describe any game
  public static final List<String> NEEDED_GAME_KEYS = Arrays.asList(
      new String[]{DRAW_DECK_KEY, STARTED_KEY});
  //json keys needed to describe an unstarted game
  public static final List<String> NEEDED_UNSTARTED_KEYS = Arrays.asList(
      new String[]{NUM_PLAYERS_KEY, CARDS_PER_PLAYER_KEY});
  //json keys needed to describe a started gaem
  public static final List<String> NEEDED_STARTED_KEYS = Arrays.asList(
      new String[]{PLAY_DECK_KEY, PLAYERS_KEY, CURRENT_PLAYER_KEY});

  //the path to a properties file that maps strings like "basic" to the full path of an appropriate subclass of Game
  public static final String UNO_TYPE_CLASS_NAME_PATH = "src/ooga/data/dataResources/GameTypeToClassPath.properties";
  public static final String UNO_TYPE_FORMAL_NAME_PATH = "src/ooga/data/dataResources/FormalGameType.properties";

  //json keys to describe a player profile
  public static final String USERNAME_KEY = "Username";
  public static final String PASSWORD_KEY = "Password";
  public static final String WINS_KEY = "Wins";

  //message to be displayed when a user is not found
  public static final String USER_NOT_FOUND_MESSAGE = "USER_NOT_FOUND_MESSAGE";

  //limits on the parameters to a game.
  public static int MIN_NUMBER_PLAYERS = Config.MIN_NUM_PLAYERS;
  public static int MAX_NUMBER_PLAYERS = Config.MAX_NUM_PLAYERS;
  public static int MIN_CARDS_PER_PLAYER = Config.MIN_CARDS_PER_PLAYER;
  public static int MAX_CARDS_PER_PLAYER = Config.MAX_CARDS_PER_PLAYER;
  //the number of cards in a deck must exceed the number of players * number of cards per player + MIN_CARD_DIFF
  public static final int MIN_CARD_DIFF = 2;


  //messages if the game violates the parameters above
  public static final String BAD_NUMBER_OF_PLAYERS_MESSAGE = "BAD_NUMBER_OF_PLAYERS_MESSAGE";
  public static final String BAD_NUMBER_OF_CARDS_PER_PLAYER_MESSAGE = "BAD_NUMBER_OF_CARDS_PER_PLAYER_MESSAGE";


  //paths
  //path to the file with the profiles
  public static final String PROFILES_PATH = "data/profiles.JSON";
  //path to the basic default uno json file
  public static final String BASIC_UNO_PATH = "data/UnoGames/BasicUnoDefault.json";
  //path to the default Pirate uno json file
  public static final String PIRATE_UNO_PATH = "data/UnoGames/PirateUno.json";

}
