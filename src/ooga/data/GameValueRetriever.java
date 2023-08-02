package ooga.data;

import ooga.data.dataExceptions.MissingGameKeysException;
import ooga.data.dataExceptions.UnrecognizedValueException;
import ooga.data.dataExceptions.WrongDataTypeException;
import ooga.data.dataResources.DataConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * This class has public static methods to retrieve certain values out of a game json file.
 *
 * @author Keith Cressman
 */
public class GameValueRetriever extends JSONHandler{

  public static final String DEFAULT_IMG_PATH = null;
  public static final String REQUIRED_IMG_ENDING = ".png";
  private static final Logger LOGGER = LogManager.getLogger(GameValueRetriever.class);

  /**
   * see whether timed turns should happen for the game described by the json file at the given path
   * @param filePath is the path to a JSON file describing a game and its properties. Note: it
   *    *                 should include .json at the end
   * @return true iff the file is valid json code, and it has a key for DataConfig.RENEGING_POSSIBLE_KEY which is associated with the value true
   */
  public static boolean hasTimedTurns(String filePath){
    try{
      return  getBooleanVal(DataConfig.TIMED_TURNS_KEY, getJSONObjectForFile(filePath));
    }catch (Exception e){
      return false;
    }
  }

  /**
   * get the path to the image file for displaying the back of a card.
   * If any issue arises, the default path ( a null String) will be returned
   * @param filePath is the path to a JSON file describing a game and its properties. Note: it
   *    *                 should include .json at the end
   * @return the path to the image used for displaying the back of a card.
   */
  public static String getBackCardImgPath(String filePath) {
    try{
      String imgPath =  getStringValue(DataConfig.BACK_CARD_IMG_PATH, getJSONObjectForFile(filePath));
      String finalImgPath = checkImgPath(imgPath);
      return finalImgPath;
    }catch (Exception e){
      return DEFAULT_IMG_PATH;
    }
  }

  private static String checkImgPath(String imgPath){
    //check if the img path is valid. If not, return the default path, which is null
    File tempFile = new File(imgPath);
    if (!imgPath.endsWith(REQUIRED_IMG_ENDING) || !tempFile.exists()){
      return DEFAULT_IMG_PATH;
    } else{
      return imgPath;
    }
  }


  /**
   * get the type of game, such as "ooga.model.games.BasicGame" of the game file at the given path
   *
   * @param filePath is the path to a JSON file describing a game and its properties. Note: it
   *                 should include .json at the end
   * @return a class path of a game subclass, such as "ooga.model.games.BasicGame"
   * @throws Exception                  if the file cannot be found or it is not valid json code
   * @throws MissingGameKeysException   if the file does not have the key DataConfig.UNO_TYPE_KEY
   * @throws WrongDataTypeException     if the value associated with DataConfig.UNO_TYPE_KEY is not
   *                                    a String
   * @throws UnrecognizedValueException if the game type is unrecognized, e.g. "unoType" :
   *                                    "alkjfalskdfj" is in the file
   */
  public static String getGameType(String filePath)
      throws Exception, MissingGameKeysException, WrongDataTypeException, UnrecognizedValueException {

    String unoTypeVal = getStringValue(DataConfig.UNO_TYPE_KEY, getJSONObjectForFile(filePath));
    return DataUtilities.unoTypeToValue(unoTypeVal, DataConfig.UNO_TYPE_FORMAL_NAME_PATH);
  }
}
