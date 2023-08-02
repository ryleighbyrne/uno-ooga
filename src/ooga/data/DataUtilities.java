package ooga.data;

import java.io.FileInputStream;
import java.util.Properties;
import ooga.data.dataExceptions.UnrecognizedValueException;
import ooga.data.dataResources.DataConfig;
import ooga.view.resources.Resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class has public static methods used as utilities in the rest of the data package
 *
 * @author Keith Cressman
 */
public class DataUtilities {

  private static final Logger LOGGER = LogManager.getLogger(DataUtilities.class);

  /**
   * Get a value associated with an uno type.
   * The expected use case is to use this method to get the full class path associated with an unoType.
   * To do so, you would call unoTypeToValue(type, DataConfig.UNO_TYPE_CLASS_NAME_PATH)
   *
   * @param unoType is a String like "basic" or "BasicGame" or "random"
   * @param propertiesPath is a path to a properties file
   * @return a value associated with the unotype
   * @throws Exception if there is an issue opening the properties file
   * @throws UnrecognizedValueException if the the unoType key is not in the properties file
   */
  public static String unoTypeToValue(String unoType, String propertiesPath) throws Exception, UnrecognizedValueException {
    //get the class path of the game subclass associated with this unotype, throw an exception if the type is unrecognized
    Properties namesProperties = new Properties();
    namesProperties.load(new FileInputStream(propertiesPath));
    if (namesProperties.containsKey(unoType)) {
      return namesProperties.getProperty(unoType);
    } else {
      LOGGER.info(String.format("%s %s", DataConfig.UNRECOGNIZED_VALUE_MESSAGE, unoType));
      throw new UnrecognizedValueException(
          String.format("%s %s", Resources.getKey(DataConfig.UNRECOGNIZED_VALUE_MESSAGE), unoType));
    }

  }
}
