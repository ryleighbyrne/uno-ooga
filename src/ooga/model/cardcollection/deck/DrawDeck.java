package ooga.model.cardcollection.deck;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.Config;
import ooga.model.cards.CardColor;
import ooga.model.cards.UnoCardFactory;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.exceptions.EmptyCardCollectionException;

/**
 * Concrete class extending Deck, represents the draw deck from which players obtain new cards
 *
 * @author Alicia Wu
 */
public class DrawDeck extends Deck {

  /**
   * creates a new instance of DrawDeck given information of all the cards that will go in this
   * deck
   *
   * @param allCardsInfo information on all cards in this game
   */
  public DrawDeck(Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    super(allCardsInfo);
  }

  @Override
  protected List<Card> makeCardCollection() {
    if (getAllCardsInfo().isEmpty()) {
      ResourceBundle myResources = ResourceBundle.getBundle(
          Config.PROPERTIES_FILES_PATH + Config.ENGLISH_EXTENSION);
      throw new EmptyCardCollectionException(
          myResources.getString(EmptyCardCollectionException.class.getSimpleName()));
    }
    return new UnoCardFactory().makeCards(getAllCardsInfo());
  }
}
