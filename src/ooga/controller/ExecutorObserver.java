package ooga.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.List;
import ooga.model.cardcollection.CardCollection;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.games.CardGame;

/**
 * Concrete class implementing the PropertyChangeListener interface, uses observer design pattern,
 * handles communication with card action executors
 *
 * @author Alicia Wu
 */
public class ExecutorObserver implements PropertyChangeListener {

  private CardGame game;
  private Controller controller;
  private DisplayController displayController;

  /**
   * create a new instance of ExecutorObserver
   *
   * @param game              CardGame instance in play
   * @param controller        controller instance to reference
   * @param displayController displayController instance to reference
   */
  public ExecutorObserver(CardGame game, Controller controller,
      DisplayController displayController) {
    this.game = game;
    this.controller = controller;
    this.displayController = displayController;
  }

  /**
   * gets called when an executor this class is observing gets changed
   *
   * @param evt PropertyChangeEvent instance that holds the name of the property that has changed
   *            and its new value (the valid cards to play in the next player's hand)
   */
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    List<Card> validCardsForNextPlayer = ((Collection<Card>) evt.getNewValue()).stream().toList();
    if (validCardsForNextPlayer.isEmpty()) {
      return;
    }
    if (game.getNextPlayerId() == 0) { // real player
      CardCollection nextPlayerHand = game.getPlayerById(game.getNextPlayerId()).getPlayerHand();
      List<Integer> cardsOrdering = nextPlayerHand.findCardsOrderingInCollection(
          validCardsForNextPlayer);
      displayController.updateValidCardsInPlayerHand(cardsOrdering);
      return;
    }
    controller.endTurn();
    controller.playRandomValidCard(validCardsForNextPlayer);
  }

}
