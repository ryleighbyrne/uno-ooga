package ooga.model.cardcollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.Config;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.exceptions.CardNotFoundException;
import ooga.model.exceptions.EmptyCardCollectionException;

/**
 * Abstract class implementing the CardCollection interface, implements a CardCollection for UNO
 * game
 *
 * @author Alicia Wu
 */
public abstract class UnoCardCollection implements CardCollection {

  private List<Card> myCards;
  private Map<CardColor, List<CardInfo[]>> allCardsInfo;
  private ResourceBundle myResources;

  /**
   * creates a new collection of cards using the abstract factory pattern
   *
   * @param allCardsInfo information on all cards in this game
   */
  public UnoCardCollection(Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    this.allCardsInfo = allCardsInfo;
    myCards = makeCardCollection();
    myResources = ResourceBundle.getBundle(Config.PROPERTIES_FILES_PATH + Config.ENGLISH_EXTENSION);
  }

  protected abstract List<Card> makeCardCollection();

  protected Map<CardColor, List<CardInfo[]>> getAllCardsInfo() {
    return allCardsInfo;
  }

  /**
   * add a card to the card collection
   *
   * @param newCard card to add to this card collection
   */
  @Override
  public void addCard(Card newCard) {
    myCards.add(newCard);
  }

  protected void addCardToTop(Card newCard) {
    myCards.add(0, newCard);
  }

  /**
   * add a list of cards to this card collection
   *
   * @param cards list of cards to add to this card collection
   */
  @Override
  public void addAll(List<Card> cards) {
    myCards.addAll(cards);
    reorganize();
  }

  /**
   * removes a given card from this card collection
   *
   * @param card card to remove from this card collection
   * @throws CardNotFoundException if the given card is not found in this card collection
   */
  @Override
  public void remove(Card card) {
    if (cardNotInCollection(card)) {
      throwNewCardNotFoundException();
    }
    myCards.remove(card);
  }

  /**
   * transfer a card from this card collection to another
   *
   * @param otherCollection other card collection to transfer the card
   * @param card            card to transfer
   */
  @Override
  public void transferCardTo(CardCollection otherCollection, Card card) {
    if (cardNotInCollection(card)) {
      throwNewCardNotFoundException();
    }
    myCards.remove(card);
    otherCollection.addCard(card);
  }

  private void throwNewCardNotFoundException() {
    throw new CardNotFoundException(
        myResources.getString(CardNotFoundException.class.getSimpleName()));
  }

  private boolean cardNotInCollection(Card card) {
    return !myCards.contains(card);
  }

  /**
   * transfer all cards from this card collection to another card collection
   *
   * @param otherCollection other collection to transfer cards to
   */
  @Override
  public void transferAll(CardCollection otherCollection) {
    otherCollection.addAll(myCards);
    myCards.clear();
  }

  /**
   * gets all the cards in this card collection
   *
   * @return unmodifiable list of all cards in this card collection
   */
  @Override
  public List<Card> getAllCards() {
    return Collections.unmodifiableList(myCards);
  }

  /**
   * gets the size of this card collection
   *
   * @return the size of this card collection
   */
  @Override
  public int getSize() {
    return myCards.size();
  }

  /**
   * checks if this card collection is empty
   *
   * @return true if this card collection is empty
   */
  @Override
  public boolean isEmpty() {
    return myCards.isEmpty();
  }

  /**
   * reorganize this card collection
   */
  @Override
  public void reorganize() {
    myCards = reorganizeCollection(myCards);
  }

  protected abstract List<Card> reorganizeCollection(List<Card> cards);

  /**
   * gets the ordering in this card collection for a given list of cards
   *
   * @param cardsToFind cards to find the ordering of
   * @return ordering of the given cards
   */
  @Override
  public List<Integer> findCardsOrderingInCollection(List<Card> cardsToFind) {
    List<Integer> cardLocations = new ArrayList<>();
    for (Card card : cardsToFind) {
      int index = myCards.indexOf(card);
      if (index == -1) {
        throw new CardNotFoundException(
            myResources.getString(CardNotFoundException.class.getSimpleName()));
      }
      cardLocations.add(index);
    }
    return cardLocations;
  }

  protected Card getCard(int index) {
    if (myCards.isEmpty()) {
      throw new EmptyCardCollectionException(
          myResources.getString(EmptyCardCollectionException.class.getSimpleName()));
    }
    Card card = myCards.get(index);
    myCards.remove(index);
    return card;
  }

}
