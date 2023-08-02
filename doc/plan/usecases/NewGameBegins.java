package ooga.data;
import ooga.model.games.Game;
import ooga.controller.Controller;
/**
 * This file is listing out the code flow for a use case where a user selects a certain game type and
 * then a new game of that kind is created
 *
 * @author Keith Cressman
 */
public class GameStarter {



  /**
   * this method will be called from the controller, telling an instance of GameStarter to create a new game.
   * Within this method, it will find a list of cards for the game, and instantiate a new game then call setUpGameBoard()
   * with a map created from the data found at the filePath
   */
  Game g = createGame(filePath);

  //it will then go back to the view classes to resume and proceed




}
