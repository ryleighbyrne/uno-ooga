package ooga.data;

import java.io.FileWriter;

import ooga.data.dataResources.DataConfig;
import ooga.data.dataExceptions.UnrecognizedValueException;
import ooga.data.dataExceptions.UserNotFoundException;
import org.json.simple.JSONObject;
import ooga.view.resources.Resources;

import java.util.Map;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * The purpose of this class is to deal with the storage and retrieval of player profiles. There are
 * public static methods to add a profile, remove a profile, edit a profile, and get the contents of
 * a profile
 *
 * @author Keith Cressman
 */
public class ProfileHandler extends JSONHandler {

  public static final String USERNAME_KEY = "Username";
  public static final String PASSWORD_KEY = "Password";
  public static final String WINS_KEY = "Wins";

  private static final Logger LOGGER = LogManager.getLogger(ProfileHandler.class);

  /**
   * take the profile info and write it into profiles.json
   *
   * @param profileInfo maps keys like "username" to their corresponding value
   */
  public static void addProfile(Map<String, String> profileInfo) {
    JSONObject profileJSON = makeJSONObjForProfile(profileInfo);
    String name = profileInfo.get(DataConfig.USERNAME_KEY);
    JSONObject allProfilesObj = getAllProfiles();
    allProfilesObj.put(name, profileJSON);
    writeToJSONFile(allProfilesObj);
  }

  private static void writeToJSONFile(JSONObject jsonObj) {
    //write the JSONObject to overwrite what is in the profiles file
    try {
      FileWriter fWriter = new FileWriter(DataConfig.PROFILES_PATH, false);
      fWriter.write(jsonObj.toJSONString());
      fWriter.close();
    } catch (Exception e) {
      LOGGER.info(e.getMessage());
    }
  }


  private static JSONObject makeJSONObjForProfile(Map<String, String> profileInfo) {
    //create a JSONObject representing the profile, to go into the profiles.json file
    JSONObject profileJSON = new JSONObject();
    for (String key : profileInfo.keySet()) {
      profileJSON.put(key, profileInfo.get(key));
    }
    return profileJSON;
  }


  /**
   * get a JSON object describing all player profiles
   *
   * @return a JSON object describing all player profiles
   */
  //TODO: return Map<String, String>
  public static JSONObject getAllProfiles() {
    //return a JSON object about all players
    try {
      return getJSONObjectForFile(DataConfig.PROFILES_PATH);

    } catch (Exception e) {
     LOGGER.info(e.getMessage());
    }

    return null;
  }

  /**
   * get the information about a player from the "database"
   *
   * @param username is the username of the desired player
   * @return a map that maps keys to values for the player's information
   * @throws UserNotFoundException if the username is not in the database
   */
  public static Map<String, String> getPlayerInfo(Object username) throws UserNotFoundException {
    Map<String, String> profileMap = new HashMap<>();
    JSONObject allProfiles = new JSONObject();
    try {
      allProfiles = getJSONObjectForFile(DataConfig.PROFILES_PATH);
    } catch (Exception e){
    }
    if (!allProfiles.containsKey(username)) {
      LOGGER.info(String.format(DataConfig.USER_NOT_FOUND_MESSAGE + username));
      throw new UserNotFoundException(Resources.getKey(DataConfig.USER_NOT_FOUND_MESSAGE) + username);
    }
    try{
      JSONObject userProfile = (JSONObject) allProfiles.get(username);
      for (Object key : userProfile.keySet()) {
        profileMap.put(key.toString(), userProfile.get(key).toString());
      }
    } catch (Exception e) {
       LOGGER.info(e.getMessage());
    }
    return profileMap;
  }

  /**
   * Change the value of part of a user's profile
   *
   * @param username is the user to edit
   * @param key      is the part of their profile you want to edit, such as "Bio" or "Wins"
   * @param newVal   is the new value for that key
   * @throws UserNotFoundException      if the username is not in the database
   * @throws UnrecognizedValueException if the key is not recognized, like if you had key =
   *                                    "alkjdsfda"
   */
  public static void changeProfile(String username, String key, String newVal)
      throws UserNotFoundException, UnrecognizedValueException {

    Map<String, String> profileMap = getPlayerInfo(username);
    if (!profileMap.containsKey(key)) {
      LOGGER.info(String.format("%s %s", DataConfig.UNRECOGNIZED_VALUE_MESSAGE, key));
      throw new UnrecognizedValueException(
            String.format("%s %s", Resources.getKey(DataConfig.UNRECOGNIZED_VALUE_MESSAGE), key));
      }
      profileMap.put(key, newVal);
      addProfile(profileMap);

  }

  /**
   * remove the profile with the given username
   *
   * @param username is the name of the person to remove
   * @throws UserNotFoundException if the user is not in the database
   */
  public static void removeProfile(String username) throws UserNotFoundException {
    JSONObject allProfiles = getAllProfiles();
    if (!allProfiles.containsKey(username)) {
     LOGGER.info(String.format("%s %s", DataConfig.USER_NOT_FOUND_MESSAGE, username));
      throw new UserNotFoundException(
          String.format("%s %s", Resources.getKey(DataConfig.USER_NOT_FOUND_MESSAGE), username));
    }
    allProfiles.remove(username);
    writeToJSONFile(allProfiles);
  }


}
