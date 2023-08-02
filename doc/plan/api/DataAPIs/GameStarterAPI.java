package ooga.data;
import ooga.model.games.Game;
/**
 * The purpose of this class is to read in a game .SIM or JSON file and use it to create a new game
 *
 * @author Keith Cressman
 */
public interface GameStarter {

  /**
   * create a game based on a JSON (probably) file at the given path
   * @param filePath is the path to a JSON file describing a game and its properties
   * @throws Exception if the filePath is invalid or the file is valid but contains data for an invalid game
   * @return a Game with the desired properties
   */
  public Game createGame(String filePath) throws Exception;

}
