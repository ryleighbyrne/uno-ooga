package ooga.model.games.executors;

import java.util.Collection;
import ooga.Config;
import ooga.PropertyChangeObservable;
import ooga.model.cards.cardcomponents.Card;
import ooga.model.cards.cardcomponents.CardInfo;
import ooga.model.cards.cardcomponents.UnoCardInfo;
import ooga.model.games.CardGame;
import ooga.model.players.Player;

/**
 * Abstract class extending PropertyChangeObservable and implementing Executor, implements
 * functionality shared for complex action executions
 *
 * @author Alicia Wu
 */
public abstract class ComplexActionExecutor extends PropertyChangeObservable implements Executor {

  private CardGame game;

  /**
   * executes the action of playing of a more complex action card that affects game play / other
   * players and interacts with the controller module
   *
   * @param game  Game instance in play
   * @param param numeric parameter of the action (ex: skip 1)
   */
  @Override
  public void execute(CardGame game, int param) {
    this.game = game;
    initExecution(param);
    Collection<Card> validCardsForNextPlayer = applyReflectionToGetValidCards(this, game, param);
    if (validCardsForNextPlayer.isEmpty()) {
      nextPlayerNoValidCards();
      return;
    }
    notifyObservers(getSimplePropertyName(this).toLowerCase(), validCardsForNextPlayer);
  }

  protected abstract void initExecution(int param);

  protected abstract void nextPlayerNoValidCards();

  protected Collection<Card> applyReflectionToGetValidCards(Executor executor, CardGame game,
      int param) {
    String className = executor.getClass().getCanonicalName()
        .split(Config.EXECUTOR_CLASSES_PREFIX)[1];
    String actionName = className.substring(0, className.length() - Config.EXECUTOR_WORD_LENGTH)
        .toLowerCase();
    return findNextPlayerValidCards(game, actionName, param);
  }

  private Collection<Card> findNextPlayerValidCards(CardGame game, String actionName, int param) {
    Player nextPlayer = game.getPlayerById(game.getNextPlayerId());
    return nextPlayer.findCardsWithSameCardInfo(new CardInfo[]{new UnoCardInfo(actionName, param)});
  }

  protected String getSimplePropertyName(Executor executor) {
    String className = executor.getClass().getSimpleName();
    return className.substring(0, className.length() - Config.EXECUTOR_WORD_LENGTH);
  }

  protected CardGame getGame() {
    return game;
  }
}
