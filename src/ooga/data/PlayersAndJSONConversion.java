package ooga.data;

import java.util.ArrayList;
import java.util.List;
import ooga.data.dataExceptions.MissingCardKeysException;
import ooga.data.dataExceptions.MissingPlayerKeysException;
import ooga.data.dataExceptions.UnrecognizedValueException;
import ooga.data.dataExceptions.WrongDataTypeException;
import ooga.data.dataResources.DataConfig;
import ooga.model.cardcollection.hand.Hand;
import ooga.model.cardcollection.hand.PlayerHand;
import ooga.view.resources.Resources;

import ooga.model.players.GamePlayer;
import ooga.model.players.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The purpose of this class is to assist in converting JSON code into Player instances, and vice versa.
 * The GameStarter class uses the public static methods here to help instantiate a game based on json code.
 *
 * @author Keith Cressman
 */
public class PlayersAndJSONConversion extends JSONHandler{

  private static final Logger LOGGER = LogManager.getLogger(PlayersAndJSONConversion.class);

  /**
   * get a collection of all the players described in the game file
   *
   * @param playersArr is a JSONArray from the game's json file, with an entry for each player
   * @return a collection of players corresponding to the json array
   * @throws WrongDataTypeException     if, for instance, there is a string where there should be a
   *                                    number
   * @throws MissingCardKeysException   if a card's json description is missing a needed key
   * @throws UnrecognizedValueException if there is an unacceptable value somewhere
   * @throws MissingPlayerKeysException if a player's json description is missing a value
   */
  public static List<Player> makePlayers(JSONArray playersArr) throws WrongDataTypeException,
      MissingCardKeysException, UnrecognizedValueException, MissingPlayerKeysException {
    List<Player> players = new ArrayList<>();
    for (Object o : playersArr) {
      players.add(makeSinglePlayer((JSONObject) o));
    }
    return players;
  }

  private static Player makeSinglePlayer(JSONObject playerJSON) throws WrongDataTypeException,
      MissingCardKeysException, UnrecognizedValueException, MissingPlayerKeysException {
    if (!playerJSON.keySet().containsAll(DataConfig.NEEDED_PLAYER_KEYS)) {
      LOGGER.info(DataConfig.MISSING_PLAYER_KEY_MESSAGE);
      throw new MissingPlayerKeysException(Resources.getKey(DataConfig.MISSING_PLAYER_KEY_MESSAGE));
    }

    int id = getIntValue(DataConfig.PLAYER_ID_KEY, playerJSON);
    JSONArray handArr = getJSONArray(DataConfig.PLAYER_HAND_KEY, playerJSON);
    Hand playerHand = new PlayerHand(CardAndJSONConversion.makeCardsInfo(handArr));


    Player newPlayer = new GamePlayer(id);
    newPlayer.addAllToHand(playerHand.getAllCards());
    return newPlayer;
  }





}
