package ooga.data;

import ooga.model.cards.cardtypes.Card;


/**
 * This class is used to read in a JSON or properties file describing a card and use the
 * CardFactory class to create an actual card based on what is in that file
 *
 * @author Keith Cressman
 */
public interface CardStarter {

  /**
   * Create a card with the properties at the given file path. Will get parameters out of the file
   * and then use CardFactory to create a corresponding card.
   *
   * @param filePath is a path to the file (JSON or SIM) describing the properties of a card
   * @return a Card with properties corresponding to what is in the file at filePath
   * @throws Exception if the filePath is invalid or the file at that path does not have the right
   *                   data
   */
  public Card createCard(String filePath) throws Exception;


}

