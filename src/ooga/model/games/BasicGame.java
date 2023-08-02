package ooga.model.games;

import java.util.List;
import java.util.Map;
import ooga.model.cardcollection.DeckCardCollection;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.players.Player;

public class BasicGame extends Game {

  public BasicGame(int numPlayers, int numCardsToBeginWith,
      Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    super(numPlayers, numCardsToBeginWith, allCardsInfo);
  }

  /**
   * Create a new BasicGame, with the given drawDeck, playDeck, and players. This will be used to
   * create a Game based on a paused game that has been saved
   *
   * @param drawDeck   will be the draw deck on the board
   * @param playDeck   will be the play deck on the board
   * @param allPlayers is a list of all the players in the game
   */
  public BasicGame(DeckCardCollection drawDeck, DeckCardCollection playDeck,
      List<Player> allPlayers) {
    super(drawDeck, playDeck, allPlayers);
  }

}
