package ooga.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ooga.data.dataExceptions.MissingCardKeysException;
import ooga.data.dataExceptions.UnrecognizedValueException;
import ooga.data.dataExceptions.WrongDataTypeException;
import ooga.data.dataResources.DataConfig;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.cards.cardcomponents.UnoCardInfo;
import ooga.view.resources.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The purpose of this class is to assist in converting JSON code into Card instances, and vice versa.
 * The GameStarter class uses the public static methods here to help instantiate a game based on json code.
 *
 * @author Keith Cressman
 */
public class CardAndJSONConversion extends JSONHandler{

  private static final Logger LOGGER = LogManager.getLogger(CardAndJSONConversion.class);

  /**
   * take in a JSONArray representing several cards and return a map that will be used to make these
   * cards in a game
   *
   * @param cardDescriptions is a JSONArray of JSONObjects each describing a card
   * @return a map where each entry has a Card Color and a list of CardInfo arrays corresopnding to
   * each card of that color
   * @throws MissingCardKeysException   if a card is missign one of DataConfig.NEEDED_CARD_KEYS
   * @throws WrongDataTypeException     if one of the keys has the wrong data type for the value,
   *                                    e.g. if the value for param is not an int
   * @throws UnrecognizedValueException if a value is unrecognized, like if color is "keith";
   */
  public static Map<CardColor, List<CardInfo[]>> makeCardsInfo(JSONArray cardDescriptions)
      throws MissingCardKeysException, WrongDataTypeException, UnrecognizedValueException {
    Map<CardColor, List<CardInfo[]>> cardsMap = new HashMap<>();
    for (Object o : cardDescriptions) {
      JSONObject cardDescription = (JSONObject) o;
      Set<String> cardKeys = cardDescription.keySet();
      if (!cardKeys.containsAll(DataConfig.NEEDED_CARD_KEYS)) {
        LOGGER.info(DataConfig.MISSING_CARD_KEY_MESSAGE);
        throw new MissingCardKeysException(Resources.getKey(DataConfig.MISSING_CARD_KEY_MESSAGE));
      } else {
        Map<CardColor, CardInfo[]> singleCardMap = makeMapForOneCard(cardDescription);
        CardColor key = singleCardMap.keySet().iterator().next();
        cardsMap.putIfAbsent(key, new ArrayList<>());
        cardsMap.get(key).add(singleCardMap.get(key));
      }
    }
    return cardsMap;
  }

  /**
   * return a map describing a card, given JSON code describing the card
   *
   * @param cardDescription is JSON code with the keys in DataConfig.NEEDED_CARD_KEYS
   * @return a map describing the card
   * @throws WrongDataTypeException     if one of the keys has the wrong data type for the value,
   *                                    e.g. if the value for param is not an int
   * @throws UnrecognizedValueException if a value is unrecognized, like if color is "keith";
   */
  private static Map<CardColor, CardInfo[]> makeMapForOneCard(JSONObject cardDescription)
      throws WrongDataTypeException, UnrecognizedValueException, MissingCardKeysException {
    String color = getStringValue(DataConfig.COLOR_KEY, cardDescription).toUpperCase();
    JSONArray JSONcardInfos = (JSONArray) cardDescription.get(DataConfig.CARD_INFOS_KEY);
    List<CardInfo> infosList = new ArrayList<>();
    for (Object infoObj : JSONcardInfos) {
      JSONObject obj = (JSONObject) infoObj;
      infosList.add(makeOneCardInfo(obj));
    }

    try {
      CardColor cardColor = CardColor.valueOf(color);
      Map<CardColor, CardInfo[]> ret = new HashMap<>();
      ret.put(cardColor, infosList.toArray(new CardInfo[0]));
      return ret;
    } catch (Exception e) {
      LOGGER.info(String.format("%s %s", DataConfig.UNRECOGNIZED_VALUE_MESSAGE, color));
      throw new UnrecognizedValueException(
          String.format("%s %s", Resources.getKey(DataConfig.UNRECOGNIZED_VALUE_MESSAGE), color));
    }
  }

  //TODO: move to separate class
  private static CardInfo makeOneCardInfo(JSONObject infoObj)
      throws WrongDataTypeException, UnrecognizedValueException, MissingCardKeysException {
    //make a single CardInfo object based on JSON code
    if (!infoObj.keySet().containsAll(DataConfig.NEEDED_CARDINFO_KEYS)) {
      throw new MissingCardKeysException(Resources.getKey(DataConfig.MISSING_CARD_KEY_MESSAGE));
    }
    String type = getStringValue(DataConfig.TYPE_KEY, infoObj);
    int param = getIntValue(DataConfig.PARAM_KEY, infoObj);
    try {
      CardInfo cardInfo = new UnoCardInfo(type, param);
      return cardInfo;
    } catch (Exception e) {
      //LOGGER.info(String.format("%s %s %s", DataConfig.UNRECOGNIZED_VALUE_MESSAGE, type, Integer.toString(param)));
      throw new UnrecognizedValueException(
          String.format("%s %s %s", Resources.getKey(DataConfig.UNRECOGNIZED_VALUE_MESSAGE), type,
              Integer.toString(param)));
    }
  }


  /**
   * Create a JSONObject describing a Card
   * @param c is a Card
   * @return a JSONArray encapsulating the information in those CardInfos, i.e. its color and array of CardInfos
   */
  public static JSONObject makeJSONObjectForCard(Card c) {
    //make a JSON object describing a single card
    String color = c.getCardColor().toString().toUpperCase();
    JSONObject cardJSON = new JSONObject();
    cardJSON.put(DataConfig.COLOR_KEY, color);
    cardJSON.put(DataConfig.CARD_INFOS_KEY, makeJSONArrayForCardInfos(c.getCardInfo()));
    return cardJSON;
  }

  private static JSONArray makeJSONArrayForCardInfos(CardInfo[] cardInfos) {
    //make a JSONArray with an element for each card info object that a card has
    JSONArray infoArray = new JSONArray();
    for (CardInfo cInfo : cardInfos) {
      infoArray.add(makeJSONObjectForCardInfo(cInfo));
    }
    return infoArray;
  }

  private static JSONObject makeJSONObjectForCardInfo(CardInfo info) {
    //make a JSON object describing a single CardInfo object
    JSONObject infoJSON = new JSONObject();
    infoJSON.put(DataConfig.TYPE_KEY, info.getType());
    infoJSON.put(DataConfig.PARAM_KEY, info.getParam());
    return infoJSON;
  }

}
