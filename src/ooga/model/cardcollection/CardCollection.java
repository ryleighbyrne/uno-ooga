package ooga.model.cardcollection;

import java.util.List;
import ooga.model.cards.cardcomponents.Card;

/**
 * Defines API of a CardCollection, any collection of cards & their necessary interactions
 *
 * @author Alicia Wu
 */
public interface CardCollection {

  /**
   * add a card to the card collection
   *
   * @param newCard card to add to this card collection
   */
  void addCard(Card newCard);

  /**
   * add a list of cards to this card collection
   *
   * @param cards list of cards to add to this card collection
   */
  void addAll(List<Card> cards);

  /**
   * removes a given card from this card collection
   *
   * @param card card to remove from this card collection
   */
  void remove(Card card);

  /**
   * transfer a card from this card collection to another
   *
   * @param otherCollection other card collection to transfer the card
   * @param card            card to transfer
   */
  void transferCardTo(CardCollection otherCollection, Card card);

  /**
   * transfer all cards from this card collection to another card collection
   *
   * @param otherCollection other collection to transfer cards to
   */
  void transferAll(CardCollection otherCollection);

  /**
   * gets all the cards in this card collection
   *
   * @return unmodifiable list of all cards in this card collection
   */
  List<Card> getAllCards();

  /**
   * gets the size of this card collection
   *
   * @return the size of this card collection
   */
  int getSize();

  /**
   * checks if this card collection is empty
   *
   * @return true if this card collection is empty
   */
  boolean isEmpty();

  /**
   * reorganize this card collection
   */
  void reorganize();

  /**
   * gets the ordering in this card collection for a given list of cards
   *
   * @param cardsToFind cards to find the ordering of
   * @return ordering of the given cards
   */
  List<Integer> findCardsOrderingInCollection(List<Card> cardsToFind);

}
