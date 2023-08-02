package ooga.controller;

import ooga.model.cards.cardcomponents.Card;
import java.util.Map;
/**
 * This is the Controller to deal with the flow of information to/from the frontend/backend
 *
 * @author Keith Cressman
 */
public interface Controller {

  /**
   * alert the backend that the following card was played by the user
   * @param playedCard is a Card that was just played
   */
  public void alertBackendOfCardPlayed(Card playedCard);

  /**
   * alert the backend that the number of players has changed
   * @param newNum is the new number of players
   */
  public void alertBackendOfChangeInNumPlayers(int newNum);

  /**
   * alert the frontend that a move has passed and it is now this person's turn
   * @param playerUp is the ID of the player who is now up
   */
  public void alertFrontendOfChangeInturn(int playerUp);

  /**
   * alert the frontend that a certain player (CPU player) has just played a card
   * @param playedCard was just played by a CPU player
   * @param playerId is the ID of the player who just went
   */
  public void alertFrontendOfCardPlayed(Card playedCard, int playerId);

  /**
   * alert the backend that a user has just customized one of the blank cards with the following parameters
   * @param cardParameters will have keys like "Color", "Reverse", "Skip", and we still need to figure out what the values would be
   */
  public void alertBackendOfNewCard(Map<String, String> cardParameters);

  /**
   * alert the backend that a user has selected a type of game, like Mario or Basic Uno,
   * and needs to create the game.
   * @param gameType is the type of game, like "Basic" or "Mario"
   */
  public void alertBackendOfNewGame(String gameType);

}
