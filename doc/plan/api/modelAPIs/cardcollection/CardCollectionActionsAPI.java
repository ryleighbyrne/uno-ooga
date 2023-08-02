/**
 * describes a general collection of cards, can add a card to the card collection and transfer cards
 * from one collection to another, throws an exception if the other collection is invalid or the
 * card is invalid
 */
public interface CardCollectionActions {

  void addCard(Card newCard);

  void transferCardTo(CardCollection otherCollection, Card card) throws Exception;
}
