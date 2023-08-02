package ooga.controller;

import java.io.File;
import java.io.PrintStream;
import java.util.ResourceBundle;
import ooga.Config;
import ooga.data.GameStarter;
import ooga.data.GameValueRetriever;
import ooga.model.exceptions.InvalidNumberOfCardsToStartException;
import ooga.model.exceptions.InvalidNumberOfPlayersException;
import ooga.model.games.CardGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DataController interacts with controller and GameStarter to start a new game instance from a json
 * file
 *
 * @author Ryleigh Byrne, Alicia Wu
 */
public class DataController {

  private Logger logger = LogManager.getLogger(DataController.class);
  private GameStarter gameStarter;
  private String backOfCardFilePath;
  private int gameCount; // for game log output for testing purposes

  /**
   * create a new instance of DataController
   */
  public DataController() {
    gameStarter = new GameStarter();
  }

  // creates a txt file to record turns in game, demonstrating our implementation works
  private void createFileToRecordGameTurnsForTesting() {
    File file = new File(Config.GAME_LOG_PATH + gameCount + Config.TXT_EXTENSION);
    try {
      PrintStream stream = new PrintStream(file);
      System.setOut(stream);
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
  }

  /**
   * create a new game instance from game starter
   *
   * @param filePath filepath to data to start the game with
   * @return new game instance
   * @throws Exception if file is incorrectly formatted
   */
  public CardGame startNewGame(String filePath) throws Exception {
    CardGame game = gameStarter.createGame(filePath); // new game is begun with file chosen
    createFileToRecordGameTurnsForTesting();
    gameCount++;
    backOfCardFilePath = GameValueRetriever.getBackCardImgPath(filePath);
    checkNumPlayers(game);
    return game;
  }

  private void checkNumPlayers(CardGame game) {
    if (game.getNumPlayers() > Config.MAX_NUM_PLAYERS
        || game.getNumPlayers() < Config.MIN_NUM_PLAYERS) {
      ResourceBundle myResources = ResourceBundle.getBundle(
          Config.PROPERTIES_FILES_PATH + Config.ENGLISH_EXTENSION);
      throw new InvalidNumberOfPlayersException(myResources.getString(
          InvalidNumberOfCardsToStartException.class.getSimpleName()));
    }
  }

  /**
   * gets the file path to the image to display for the back of the card
   *
   * @return file path to the image to display for the back of the card
   */
  public String getBackOfCardFilePath() {
    return backOfCardFilePath;
  }


}