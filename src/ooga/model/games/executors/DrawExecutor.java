package ooga.model.games.executors;

import ooga.Config;

/**
 * Concrete class extending the ComplexActionExecutor, makes other players draw cards
 *
 * @author Alicia Wu
 */
public class DrawExecutor extends ComplexActionExecutor {

  @Override
  protected void initExecution(int numCardsToDraw) {
    notifyObservers(Config.INCREMENT_NUM, numCardsToDraw);
  }

  @Override
  protected void nextPlayerNoValidCards() {
    notifyObservers(Config.EXECUTE_CASCADED, null);
  }

}
