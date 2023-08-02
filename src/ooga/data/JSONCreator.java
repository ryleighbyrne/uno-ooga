package ooga.data;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import java.util.Map;

/**
 * The purpose of this class is to assist in creating JSONObjects. Its expected use is for the
 * extension feature where we create a custom deck. The methods of this class will help to convert
 * user input into a format that can be saved correctly.
 *
 * @author Keith Cressman
 */
public class JSONCreator {

  /**
   * create a json object with the given key-value pairs
   *
   * @param objMap is a map with key-value pairs to go in the json object
   * @return a JSONObject with key-value pairs corresponding to what is in the map passed in as an
   * argument
   */
  public static JSONObject makeJSONObj(Map<String, Object> objMap) {
    JSONObject jsonObj = new JSONObject();
    for (String key : objMap.keySet()) {
      jsonObj.put(key, objMap.get(key));
    }
    return jsonObj;
  }

}

