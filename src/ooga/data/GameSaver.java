package ooga.data;

import ooga.data.dataResources.DataConfig;
import ooga.data.dataExceptions.InvalidFileNameException;
import ooga.model.games.CardGame;
import ooga.model.board.GameBoard;
import ooga.model.players.Player;
import ooga.model.cards.cardcomponents.Card;
import ooga.view.resources.Resources;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Collection;

import java.io.FileWriter;
import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The purpose of this class is to save a game's information to a JSON file
 *
 * @author Keith Cressman
 */
public class GameSaver extends JSONHandler {

  private static final Logger LOGGER = LogManager.getLogger(GameSaver.class);

  /**
   * This method will take in a game (and possibly other information about the game's state) and use
   * it to create/save a JSON file with the game's information
   *
   * @param game     is a Game object whose info will be saved
   * @param fileName will the name of the saved JSON file, should not end in .json
   * @throws InvalidFileNameException if the file name is blank or contains unallowed characters
   */
  public static void saveGame(CardGame game, String fileName) throws InvalidFileNameException {
    GameBoard board = game.getGameBoard();
    JSONObject gameObj = new JSONObject();
    String gameType = game.getClass().getSimpleName();
    gameObj.put(DataConfig.UNO_TYPE_KEY, gameType);
    gameObj.put(DataConfig.PLAYERS_KEY, makeJSONArrayForPlayers(game));
    gameObj.put(DataConfig.CURRENT_PLAYER_KEY, game.getCurrPlayerId());
    gameObj.put(DataConfig.STARTED_KEY, true);
    gameObj.put(DataConfig.DRAW_DECK_KEY, makeJSONArrayForDeck(board.getAllDrawDeckCards()));
    gameObj.put(DataConfig.PLAY_DECK_KEY, makeJSONArrayForDeck(board.getAllPlayDeckCards()));
    writeGameObjToFile(gameObj, fileName);

  }

  private static void checkFileName(String fileName) throws InvalidFileNameException {
    //check if the file name contains any non-allowed characters or is blank
    if (fileName.length() <= 0) {
      LOGGER.info(DataConfig.BLANK_NAME_MESSAGE);
      throw new InvalidFileNameException(Resources.getKey(DataConfig.BLANK_NAME_MESSAGE));
    }
    for (String nonAllowed : DataConfig.NON_ALLOWED_NAME_CHARS) {
      if (fileName.contains(nonAllowed)) {
        LOGGER.info(DataConfig.NAME_CONTAINS_BAD_CHAR_MESSAGE + nonAllowed);
        throw new InvalidFileNameException(
            Resources.getKey(DataConfig.NAME_CONTAINS_BAD_CHAR_MESSAGE) + nonAllowed);
      }
    }
  }

  /**
   * Save a game described by a map, which must have all the necessary keys for describing a game
   * See ooga.data.dataResources.dataConfig.NEEDED_GAME_KEYS and NEEDED_UNSTARTED_GAME_KEYS
   *
   * @param gameObj  is a JSONObject, the keys of which should correspond to
   *                 DataConfig.NEEDED_GAME_KEYS and DataConfig.NEEDED_UNSTARTED_GAME_KEYS
   * @param fileName is the name under which we should save the new file, in the data/UnoGames/saved
   *                 folder. It should not end in .json
   * @throws InvalidFileNameException if the filePath is bad
   */
  public static void writeGameObjToFile(JSONObject gameObj, String fileName)
      throws InvalidFileNameException {
    checkFileName(fileName);
    //write the game object to a file
    String filePath = DataConfig.SAVED_GAMES_PATHS + fileName + ".json";
    File gameFile = new File(filePath);
    try {
      FileWriter fWriter = new FileWriter(filePath);
      fWriter.write(gameObj.toJSONString());
      fWriter.close();
    } catch (Exception e) {
      LOGGER.info(e.getMessage());
    }
  }

  private static JSONArray makeJSONArrayForDeck(Collection<Card> cards) {
    //create a JSONArray describing a deck
    JSONArray cardsArr = new JSONArray();
    for (Card c : cards) {
      cardsArr.add(CardAndJSONConversion.makeJSONObjectForCard(c));
    }
    return cardsArr;
  }


  private static JSONArray makeJSONArrayForPlayers(CardGame game) {
    //make a JSON array describing all players in the game
    int numPlayers = game.getNumPlayers();

    JSONArray playersArr = new JSONArray();
    for (int id = 0; id < numPlayers; id++) {
      playersArr.add(makeJSONObjectForPlayer(game.getPlayerById(id)));
    }

    return playersArr;
  }

  private static JSONObject makeJSONObjectForPlayer(Player p) {
    //make a JSON object describing a single player
    JSONObject playerObj = new JSONObject();
    playerObj.put(DataConfig.PLAYER_ID_KEY, p.getPlayerId());
    playerObj.put(DataConfig.PLAYER_HAND_KEY, makeJSONArrayForDeck(p.getAllCards()));

    return playerObj;
  }
}

