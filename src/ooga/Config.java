package ooga;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * This class stores all Model and Controller variables necessary for Model and Controller method
 * functionality.
 *
 * @author Ryleigh Byrne @rmb96, Alicia Wu @atw27
 */
public class Config {
  public final static String TXT_EXTENSION = ".txt";
  public final static String GAME_LOG_PATH = "data/gameRunLogs/game";
  public final static String GAME_DISPLAY = "GameDisplay";
  public final static String ADD_LOAD_FILE_OBSERVERS = "addLoadFileObserver";
  public final static String CHANGED_DISPLAY_TO_DEFAULT_GAME = "displayChangedToDefaultGame";
  public final static String MY_GAME_DISPLAY = "saveMyGameDisplay";
  public static final String CHANGED_DISPLAY_TO_NEW_GAME = "displayChangedToNewGameDisplay";
  public final static String CARD_SELECTED_TO_PLAY = "cardSelectedToPlay";
  public final static String CARD_DRAWN_BY_VIEW = "cardDrawnByView";
  public final static String LOAD_FILE_FOR_NEW_GAME = "loadFileToCreateNewGame";
  public final static String DEFAULT_BASIC_GAME =
      System.getProperty("user.dir") + "/data/UnoGames/BasicUnoDefault.json";
  public final static String CONTROLLER_CLASSPATH = "ooga.controller.Controller";
  public final static String DISPLAYCONTROLLER_CLASSPATH = "ooga.controller.DisplayController";
  public static final String INCREMENT_NUM = "incrementNumCards";
  public static final String WILD_CARD = "wild";
  public static final String NUMBER_CARD = "number";
  public static final int REAL_PLAYER_ID = 0;
  public static final String EXECUTE_CASCADED = "executeCascadedActions";
  public static final int EXECUTOR_WORD_LENGTH = 8;
  public static final String EXECUTOR_CLASSES_SUFFIX = "Executor";
  public static final String EXECUTOR_CLASSES_PREFIX = "ooga.model.games.executors.";
  public static final List<String> VALID_EXECUTORS = List.of("Skip", "Reverse", "Draw");
  public static final Map<CardColor, List<CardInfo[]>> EMPTY_CARD_COLLECTION = new HashMap<>() {
  };
  public static final String PROPERTIES_FILES_PATH = "resources.propertyfiles.";
  public static final String ENGLISH_EXTENSION = "english";
  public static final int MY_HEIGHT = 800;
  public static final int MY_WIDTH = 1400;
  public static final int MIN_NUM_PLAYERS = 2;
  public static final int MAX_NUM_PLAYERS = 4;
  public static int MIN_CARDS_PER_PLAYER = 1;
  public static int MAX_CARDS_PER_PLAYER = 10;
  public static final String GAME_READY_TO_SETUP = "initializeHandsAndDecksInGameSetup";
  public static int PIRATE_SPECIAL_NUM = 7;
  public static String PIRATE_SPECIAL_TYPE = "number";
  public static String REFLECTION_METHOD_ERROR_MESSAGE = "The method: %s could not be generated. Double check method you are trying to call's name";
  public static String REFLECTION_CLASS_ERROR_MESSAGE = "The class: %s could not be generated. Double check class you are trying to call's name";
  public static String PLAYER_HANDS_AT_START = "PLAYER_HANDS_AT_START";
  public static String PLAY_DECK_TOP_CARD = "PLAY_DECK_TOP_CARD";
  public static String CURRENT_PLAYER_ID = "CURRENT_PLAYER_ID";
  public static String CARD_PLAYED_CPU = "CARD_PLAYED_CPU";
  public static String TWO_STRING_FORMAT = " %s %s";
  public static String NEW_LINE = "\n";
  public static String PIRATE_GAME = "PIRATE_GAME";
  public static String THREE_STRING_NO_SPACE = "%s%s%s";
  public static String PLAYER_ID_CARDS_IN_HAND = "PLAYER_ID_CARDS_IN_HAND";
}

