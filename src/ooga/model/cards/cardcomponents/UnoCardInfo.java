package ooga.model.cards.cardcomponents;

import java.util.ResourceBundle;
import ooga.Config;
import ooga.model.exceptions.InvalidCardParameterException;

/**
 * Concrete immutable class implementing the CardInfo interface, represents the information on a
 * UnoCard, no setter methods / no way to modify after creation
 *
 * @author Alicia Wu
 */
public class UnoCardInfo implements CardInfo {

  private String type;
  private int param;

  /**
   * creates a new instance of UnoCardInfo given the card's type and parameter value
   *
   * @param type  UnoCard's type
   * @param param UnoCard's action parameter
   */
  public UnoCardInfo(String type, int param) {
    if (param < 0) {
      ResourceBundle myResources = ResourceBundle.getBundle(
          Config.PROPERTIES_FILES_PATH + Config.ENGLISH_EXTENSION);
      throw new InvalidCardParameterException(
          myResources.getString(InvalidCardParameterException.class.getSimpleName()));
    }
    this.type = type.toLowerCase();
    this.param = param;
  }

  /**
   * gets the card's type (e.g.: skip, number, wild, etc.)
   *
   * @return String representing a card's type
   */
  @Override
  public String getType() {
    return type;
  }

  /**
   * gets the card's action parameter (i.e.: 2 in "draw 2", 1 in "skip 1", 7 in "number 7")
   *
   * @return int representing a card's parameter
   */
  @Override
  public int getParam() {
    return param;
  }

}
