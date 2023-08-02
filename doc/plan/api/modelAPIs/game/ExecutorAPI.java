/**
 * executes an action that may affect the game, (ex: reverse 1 -> reverses game direction once)
 */
public interface Executor {

  void execute(Game game, int param) throws Exception;

}