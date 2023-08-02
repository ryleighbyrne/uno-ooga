package ooga.model.games;

import java.util.List;
import java.util.Map;
import java.util.Random;
import ooga.model.cardcollection.DeckCardCollection;
import ooga.model.cards.CardColor;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.players.Player;

/**
 * Concrete subclass extending the abstract Game class. Game play variation. Turns are selected
 * randomly instead of in a clockwise/counterclockwise direction a player can be selected to go
 * twice in the row, this means that playing a draw 2 card could potentially cause yourself to draw
 * 2 cards
 *
 * @author Alicia Wu
 */
public class RandomTurnsGame extends Game {

  /**
   * creates a new instance of RandomTurnsGame
   *
   * @param numPlayers          number of players in this game
   * @param numCardsToBeginWith number of cards for each player to begin with in this game
   * @param allCardsInfo        information on all the cards in this game
   */
  public RandomTurnsGame(int numPlayers, int numCardsToBeginWith,
      Map<CardColor, List<CardInfo[]>> allCardsInfo) {
    super(numPlayers, numCardsToBeginWith, allCardsInfo);
  }

  /**
   * Create a new RandomTurnsGame, with the given drawDeck, playDeck, and players. This will be used
   * to create a Game based on a paused game that has been saved
   *
   * @param drawDeck   will be the draw deck on the board
   * @param playDeck   will be the play deck on the board
   * @param allPlayers is a list of all the players in the game
   */
  public RandomTurnsGame(DeckCardCollection drawDeck, DeckCardCollection playDeck,
      List<Player> allPlayers) {
    super(drawDeck, playDeck, allPlayers);
  }

  /**
   * selects the next player randomly
   *
   * @param currId previous player's id number
   * @return random next player's id number
   */
  @Override
  public int getNextPlayerId(int currId) {
    return new Random().nextInt(getNumPlayers());
  }

  /**
   * reversing the direction just selects a new random next player
   */
  @Override
  public void reverseDirection() {
    getNextPlayerId(getCurrPlayerId());
  }

}
