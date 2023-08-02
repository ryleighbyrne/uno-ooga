/**
 * makes a list of cards given the information of all possible cards
 */
public interface CardFactory {

  List<Card> makeCards(Map<CardColor, List<CardInfo[]>> allCards);

}