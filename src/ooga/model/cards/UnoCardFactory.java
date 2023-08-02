package ooga.model.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;

/**
 * Concrete class implementing the CardFactory interface, creates UnoCards by making them one by
 * one
 *
 * @author Alicia Wu
 */
public class UnoCardFactory implements CardFactory {

  private CardMaker cardMaker;

  /**
   * instantiates its corresponding card maker class
   */
  public UnoCardFactory() {
    cardMaker = new UnoCardMaker();
  }

  /**
   * makes and returns a list of cards given the desired information on the cards
   *
   * @param allCardsInfo information on all cards in this game
   * @return list of Cards created
   */
  @Override
  public List<Card> makeCards(Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    List<Card> cardList = new ArrayList<>();
    for (Entry<CardColor, List<CardInfo[]>> cardsInAColor : allCardsInfo.entrySet()) {
      CardColor color = cardsInAColor.getKey();
      List<CardInfo[]> cardsInfo = cardsInAColor.getValue();
      for (int i = 0; i < cardsInfo.size(); i++) {
        Card newCard = cardMaker.makeCard(color, cardsInfo.get(i));
        cardList.add(newCard);
      }
    }
    return cardList;
  }

}
