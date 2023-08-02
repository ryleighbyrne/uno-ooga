package ooga.model.games.executors;

import ooga.model.games.CardGame;

/**
 * Concrete class extending ComplexActionExecutor, executes skipping the turn of the following
 * player(s)
 *
 * @author Alicia Wu
 */

public class SkipExecutor extends ComplexActionExecutor {

  private int playerSkippedId;

  @Override
  protected void initExecution(int numTurnsToSkip) {
    playerSkippedId = findLastPlayerSkippedId(getGame(), numTurnsToSkip);
  }

  @Override
  protected void nextPlayerNoValidCards() {
    int newNextPlayerId = getGame().getNextPlayerId(playerSkippedId);
    getGame().setNextPlayer(newNextPlayerId);
  }

  private int findLastPlayerSkippedId(CardGame game, int numTurnsToSkip) {
    int playerSkippedId = game.getNextPlayerId(game.getCurrPlayerId());
    for (int i = 0; i < numTurnsToSkip - 1; i++) {
      int newNextPlayerId = game.getNextPlayerId(playerSkippedId);
      game.setNextPlayer(newNextPlayerId);
      playerSkippedId = newNextPlayerId;
    }
    return playerSkippedId;
  }
}