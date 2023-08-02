package ooga.model.games;

import java.util.Collection;
import java.util.List;
import ooga.model.board.GameBoard;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.games.executors.Executor;
import ooga.model.players.Player;

/**
 * Defines API of a card game, allows players to draw cards and play cards and make interactions
 * with the game board that contains different card collections in the game
 *
 * @author Alicia Wu
 */
public interface CardGame {

  /**
   * finds the cards in the current player's hand that can be played
   *
   * @return collection of cards that can be played
   */
  Collection<Card> findCurrPlayerValidCardsToPlay();

  /**
   * sets the given player's hand of cards to the given list of cards
   *
   * @param playerId player's id number
   * @param cards    list of cards that will be added to the player's hand
   */
  void addCardsToPlayerHand(int playerId, List<Card> cards);

  /**
   * current player draws a card
   *
   * @return card that was drawn
   */
  Card currPlayerDrawCard();

  /**
   * player of the given player id will draw a given number of cards
   *
   * @param playerId       player id of player to draw cards
   * @param numCardsToDraw number of cards the player should draw
   */
  void playerDrawCards(int playerId, int numCardsToDraw);

  /**
   * current player will play the given card
   *
   * @param cardToPlay card the current player will play
   */
  void currPlayerPlayCard(Card cardToPlay);

  /**
   * ends the current player's turn
   */
  void finishTurn();

  /**
   * sets the next player
   *
   * @param playerId player id of next player
   */
  void setNextPlayer(int playerId);

  /**
   * gets the next player's id number given the previous player's id number
   *
   * @param prevPlayerId previous player's id number
   * @return next player's id number
   */
  int getNextPlayerId(int prevPlayerId);

  /**
   * get the next player's id number based on the current player
   *
   * @return next player's id number
   */
  int getNextPlayerId();

  /**
   * get the current player's id number
   *
   * @return current player's id number
   */
  int getCurrPlayerId();

  /**
   * get the current player
   *
   * @return Player instance representing the current player
   */
  Player getCurrPlayer();

  /**
   * gets the Player instance of a given player id number
   *
   * @param playerId player id of the player to return
   * @return Player instance with the given player id
   */
  Player getPlayerById(int playerId);

  /**
   * reverses the direction of gameplay
   */
  void reverseDirection();

  /**
   * gets a reference to the GameBoard instance in this game
   *
   * @return game board of this game
   */
  GameBoard getGameBoard();

  /**
   * gets all the players
   *
   * @return unmodifiable list of all players
   */
  List<Player> getPlayers();

  /**
   * get the number of players in this game
   *
   * @return number of players in this game
   */
  int getNumPlayers();

  /**
   * checks if the game is over
   *
   * @return true if a player in the game has no more cards
   */
  boolean isGameOver();

  /**
   * execute an action that will affect gameplay and/or other players
   *
   * @param actionParam int parameter for the action
   * @param executor    Executor to execute the action
   */
  void executeAction(int actionParam, Executor executor);
}
