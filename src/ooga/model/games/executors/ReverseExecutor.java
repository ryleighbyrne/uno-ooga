package ooga.model.games.executors;

import ooga.PropertyChangeObservable;
import ooga.model.games.CardGame;

/**
 * Concrete class implementing the Executor interface, reverses direction a given number of times
 *
 * @author Alicia Wu
 */
public class ReverseExecutor extends PropertyChangeObservable implements Executor {

  /**
   * executes the action of playing a reverse card to reverse gameflow direction
   *
   * @param game  Game instance in play
   * @param numTimesToReverse number of times to reverse
   */
  @Override
  public void execute(CardGame game, int numTimesToReverse) {
    for (int i = 0; i < numTimesToReverse; i++) {
      game.reverseDirection();
    }
  }
}
