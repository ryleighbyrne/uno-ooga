package ooga.data;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import ooga.data.dataExceptions.IllogicalGameException;
import ooga.data.dataExceptions.MissingCardKeysException;
import ooga.data.dataExceptions.MissingGameKeysException;
import ooga.data.dataExceptions.MissingPlayerKeysException;
import ooga.data.dataExceptions.UnrecognizedValueException;
import ooga.data.dataExceptions.WrongDataTypeException;
import ooga.data.dataResources.DataConfig;
import ooga.model.cardcollection.DeckCardCollection;
import ooga.model.cardcollection.deck.DrawDeck;
import ooga.model.cardcollection.deck.PlayDeck;
import ooga.model.games.CardGame;
import ooga.model.players.Player;
import ooga.view.resources.Resources;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The purpose of this class is to read in a JSON file and use it to create a new game I've used
 * this resource https://howtodoinjava.com/java/library/json-simple-read-write-json-examples/ to
 * learn how to interact with JSON files
 *
 * @author Keith Cressman
 */
public class GameStarter extends JSONHandler {


  private static final Logger LOGGER = LogManager.getLogger(GameStarter.class);

  /**
   * create a game based on a JSON (probably) file at the given path
   *
   * @param filePath is the path to a JSON file describing a game and its properties. Note: it
   *                 should include .json at the end
   * @return a Game with the desired properties
   * @throws Exception                  if the filePath is invalid or the file is valid but contains
   *                                    data for an invalid game
   * @throws MissingCardKeysException   if a card is missign one of DataConfig.NEEDED_CARD_KEYS
   * @throws WrongDataTypeException     if one of the keys has the wrong data type for the value,
   *                                    e.g. if the value for param is not an int
   * @throws UnrecognizedValueException if a value is unrecognized, like if color is "keith";
   */
  public static CardGame createGame(String filePath)
      throws Exception, WrongDataTypeException, MissingCardKeysException, UnrecognizedValueException {
    JSONObject gameInfo = getJSONObjectForFile(filePath);
    if (!gameInfo.keySet().containsAll(DataConfig.NEEDED_GAME_KEYS)) {
      LOGGER.info(DataConfig.MISSING_GAME_KEY_MESSAGE);
      throw new MissingGameKeysException(Resources.getKey(DataConfig.MISSING_GAME_KEY_MESSAGE));
    }

    boolean started = getBooleanVal(DataConfig.STARTED_KEY, gameInfo);
    if (started) {
      return makeNewStartedGame(gameInfo);
    } else {
      return makeNewUnstartedGame(gameInfo);
    }
  }


  private static CardGame makeNewStartedGame(JSONObject gameInfo)
      throws Exception, MissingGameKeysException, WrongDataTypeException, MissingCardKeysException, UnrecognizedValueException, MissingPlayerKeysException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    //make a game based off a paused one, i.e. there are already players and a play deck and a current player
    if (!gameInfo.keySet().containsAll(DataConfig.NEEDED_STARTED_KEYS)) {
      throw new MissingGameKeysException(Resources.getKey(DataConfig.MISSING_GAME_KEY_MESSAGE));
    }
    JSONArray drawDeckArr = getJSONArray(DataConfig.DRAW_DECK_KEY, gameInfo);
    JSONArray playDeckArr = getJSONArray(DataConfig.PLAY_DECK_KEY, gameInfo);
    int currentPlayerId = getIntValue(DataConfig.CURRENT_PLAYER_KEY, gameInfo);
    PlayDeck playDeck = new PlayDeck(CardAndJSONConversion.makeCardsInfo(playDeckArr));
    DrawDeck drawDeck = new DrawDeck(CardAndJSONConversion.makeCardsInfo(drawDeckArr));
    List<Player> allPlayers = PlayersAndJSONConversion.makePlayers(
        getJSONArray(DataConfig.PLAYERS_KEY, gameInfo));
    String gameType = getStringValue(DataConfig.UNO_TYPE_KEY, gameInfo);
    String gameClass = DataUtilities.unoTypeToValue(gameType, DataConfig.UNO_TYPE_CLASS_NAME_PATH);
    CardGame game = (CardGame) Class.forName(gameClass)
        .getConstructor(DeckCardCollection.class, DeckCardCollection.class, List.class)
        .newInstance(drawDeck, playDeck, allPlayers);
    game.setNextPlayer(currentPlayerId);
    return game;
  }

  private static CardGame makeNewUnstartedGame(JSONObject gameInfo)
      throws Exception, MissingGameKeysException, WrongDataTypeException, MissingCardKeysException, UnrecognizedValueException, IllogicalGameException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    //make a new unstarted game, i.e. there are no players yet, and the play deck is empty
    if (!gameInfo.keySet().containsAll(DataConfig.NEEDED_UNSTARTED_KEYS)) {
      throw new MissingGameKeysException(Resources.getKey(DataConfig.MISSING_GAME_KEY_MESSAGE));
    }
    int numPlayers = getIntValue(DataConfig.NUM_PLAYERS_KEY, gameInfo);
    int cardsPerPlayer = getIntValue(DataConfig.CARDS_PER_PLAYER_KEY, gameInfo);
    JSONArray drawDeckCards = getJSONArray(DataConfig.DRAW_DECK_KEY, gameInfo);
    checkThatGameIsPossible(drawDeckCards, numPlayers, cardsPerPlayer);
    String gameType = getStringValue(DataConfig.UNO_TYPE_KEY, gameInfo);
    String gameClass = DataUtilities.unoTypeToValue(gameType, DataConfig.UNO_TYPE_CLASS_NAME_PATH);
    return (CardGame) Class.forName(gameClass).getConstructor(int.class, int.class, Map.class)
        .newInstance(numPlayers, cardsPerPlayer,
            CardAndJSONConversion.makeCardsInfo(drawDeckCards));
  }

  private static void checkThatGameIsPossible(JSONArray drawDeckCards, int numPlayers,
      int cardsPerPlayer) throws IllogicalGameException {
    //check that a game is possible, i.e. that there are enough cards for each player
    int numCards = drawDeckCards.size();
    int numNeeded = numPlayers * cardsPerPlayer + DataConfig.MIN_CARD_DIFF;
    if (numPlayers < DataConfig.MIN_NUMBER_PLAYERS || numPlayers > DataConfig.MAX_NUMBER_PLAYERS) {
      LOGGER.info(DataConfig.BAD_NUMBER_OF_PLAYERS_MESSAGE);
      throw new IllogicalGameException(Resources.getKey(DataConfig.BAD_NUMBER_OF_PLAYERS_MESSAGE));
    }
    if (cardsPerPlayer < DataConfig.MIN_CARDS_PER_PLAYER
        || cardsPerPlayer > DataConfig.MAX_CARDS_PER_PLAYER) {
      LOGGER.info(DataConfig.BAD_NUMBER_OF_CARDS_PER_PLAYER_MESSAGE);
      throw new IllogicalGameException(Resources.getKey(DataConfig.BAD_NUMBER_OF_CARDS_PER_PLAYER_MESSAGE));
    }
    if (numCards < numNeeded) {
      throw new IllogicalGameException(
          String.format("%s %s", DataConfig.NOT_ENOUGH_CARDS_MESSAGE, Integer.toString(numNeeded)));
    }
  }

  /**
   * This method is really only here for testing purposes, to make sure it can create all the cards
   * correctly
   *
   * @param filePath is the path to a JSON file describing a game and its properties. Note: it
   *                 should include .json at the end
   * @return a DrawDeck with the cards specified by the file's "drawDeck" key
   * @throws Exception                  if the filePath is invalid or the file is valid but contains
   *                                    data for an invalid game
   * @throws MissingCardKeysException   if a card is missign one of DataConfig.NEEDED_CARD_KEYS
   * @throws WrongDataTypeException     if one of the keys has the wrong data type for the value,
   *                                    e.g. if the value for param is not an int
   * @throws UnrecognizedValueException if a value is unrecognized, like if color is "keith";
   * @throws MissingGameKeysException   if the DataConfig.DRAW_DECK_KEY is not in the file
   */
  public static DrawDeck createDrawDeck(String filePath)
      throws Exception, MissingGameKeysException, WrongDataTypeException, MissingCardKeysException, UnrecognizedValueException {
    JSONObject gameInfo = getJSONObjectForFile(filePath);
    if (!gameInfo.containsKey(DataConfig.DRAW_DECK_KEY)) {
      throw new MissingGameKeysException(
          String.format("%s %s", Resources.getKey(DataConfig.MISSING_GAME_KEY_MESSAGE), DataConfig.DRAW_DECK_KEY));
    }
    JSONArray drawDeckCards = getJSONArray(DataConfig.DRAW_DECK_KEY, gameInfo);
    return new DrawDeck(CardAndJSONConversion.makeCardsInfo(drawDeckCards));
  }

  /**
   * get the JSONArray describing the deck
   *
   * @param filePath is the path to a JSON file describing a game and its properties. Note: it
   *                 should include .json at the end
   * @return the JSONArray associated with the draw deck in the file
   * @throws Exception if the file cannot be found, is not proper json code, does not have a
   *                   drawDeck key, or does not have a JSONArray associated with that key
   */
  public static JSONArray getDrawDeckJSONArr(String filePath) throws Exception {
    return getJSONArray(DataConfig.DRAW_DECK_KEY, getJSONObjectForFile(filePath));
  }
}
