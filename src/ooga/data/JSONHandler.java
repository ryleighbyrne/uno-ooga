package ooga.data;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import ooga.data.dataExceptions.WrongDataTypeException;
import ooga.data.dataResources.DataConfig;
import ooga.view.resources.Resources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The purpose of this class is to assist in reading in values out of JSON objects,
 * throwing exceptions where appropriate
 *
 * @author Keith Cressman
 */
public class JSONHandler {

  private static final Logger LOGGER = LogManager.getLogger(JSONHandler.class);

  /**
   * Get a boolean value assoicated with a key, and throw an exception if it's not a boolean
   *
   * @param key is the key for which a boolean value should be associated and we want to return
   * @param jsonObj is a JSON object with a key-value pair describing a boolean, ideally
   * @return the boolean value associated with the given key
   * @throws WrongDataTypeException if the value associated with the key is not a boolean
   */
  public static boolean getBooleanVal(String key, JSONObject jsonObj) throws WrongDataTypeException {
    try{
      boolean started = (Boolean) jsonObj.get(key);
      return started;
    } catch (Exception e){

      LOGGER.info(e.getMessage());

      throw new WrongDataTypeException(Resources.getKey(DataConfig.WRONG_DATA_TYPE_MESSAGE) + key);
    }
  }

  /**
   * Get an int value assoicated with a key, and throw an exception if it's not an int
   *
   * @param key is the key for which an int value should be associated and we want to return
   * @param jsonObj is a JSON object with a key-value pair describing an int, ideally
   * @return the int value associated with the given key
   * @throws WrongDataTypeException if the value associated with the key is not an int
   */
  public static int getIntValue(String key, JSONObject jsonObj) throws WrongDataTypeException{
    try{
      Integer i = (Integer) jsonObj.get(key);
      return i.intValue();
    } catch (Exception e0) {
      try {
        Long l = (Long) jsonObj.get(key);
        return l.intValue();
      } catch (Exception e) {

        LOGGER.info(String.format("%s %s", DataConfig.WRONG_DATA_TYPE_MESSAGE, key));

        throw new WrongDataTypeException(
            String.format("%s %s %s", Resources.getKey(DataConfig.WRONG_DATA_TYPE_MESSAGE), key, jsonObj.get(key).toString()));
      }
    }
  }

  /**
   * Get a boolean value assoicated with a key, and throw an exception if it's not a boolean
   *
   * @param key is the key for which a boolean value should be associated and we want to return
   * @param jsonObj is a JSON object with a key-value pair describing a boolean, ideally
   * @return the boolean value associated with the given key
   * @throws WrongDataTypeException if the value associated with the key is not a boolean
   */
  public static String getStringValue(String key, JSONObject jsonObj) throws WrongDataTypeException{
    try{
      return (String) jsonObj.get(key);
    } catch (Exception e){
      LOGGER.info(String.format("%s %s %s", DataConfig.WRONG_DATA_TYPE_MESSAGE, key, jsonObj.get(key).toString()));
      throw new WrongDataTypeException(String.format("%s %s %s", Resources.getKey(DataConfig.WRONG_DATA_TYPE_MESSAGE), key, jsonObj.get(key).toString()));
    }
  }

  /**
   * Get a JSONArray assoicated with a key, and throw an exception if it's not a JSONArray
   *
   * @param key is the key for which a JSONArray should be associated and we want to return
   * @param jsonObj is a JSON object with a key-value pair describing a JSONArray, ideally
   * @return the JSONArray associated with the given key
   * @throws WrongDataTypeException if the value associated with the key is not a JSONArray
   */
  public static JSONArray getJSONArray(String key, JSONObject jsonObj) throws WrongDataTypeException{
    try {
      JSONArray arr = (JSONArray) jsonObj.get(key);
      return arr;
    } catch (Exception e){
      LOGGER.info(e.getMessage());
      throw new WrongDataTypeException(String.format("%s %s", Resources.getKey(DataConfig.WRONG_DATA_TYPE_MESSAGE), key));
    }
  }

  /**
   * Get the JSONObject describing the whole file
   *
   * @param filePath is the path to the file
   * @return the JSONObject describing the whole file
   * @throws Exception if the file is not proper json code
   */
  public static JSONObject getJSONObjectForFile(String filePath) throws FileNotFoundException, IOException, ParseException {
    JSONParser jsonParser = new JSONParser();
    FileReader reader = new FileReader(filePath);
    JSONObject gameInfo = (JSONObject) jsonParser.parse(reader);
    return gameInfo;
  }

}
