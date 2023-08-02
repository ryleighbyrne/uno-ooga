package ooga.model.games.executors;

import ooga.Observable;
import ooga.model.games.CardGame;

/**
 * Defines API of an Executor extending Observable interface, represents the execution of a playing
 * a card that affects game play / other players
 *
 * @author Alicia Wu
 */
public interface Executor extends Observable {

  /**
   * executes the action of playing of a card that affects game play / other players
   *
   * @param game  Game instance in play
   * @param param numeric parameter of the action (ex: skip 1)
   */
  void execute(CardGame game, int param);
}
