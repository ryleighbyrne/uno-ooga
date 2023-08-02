package ooga.controller;

import java.lang.reflect.Method;

import ooga.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to handle method reflection, specifically used in controller classes
 *
 * @author Ryleigh Byrne
 */
public class ReflectionMethodHandler {

  private String errorMessage;
  private Logger logger = LogManager.getLogger(ReflectionMethodHandler.class);

  /**
   * Handle method given a method name and a class name
   *
   * @param name   of method to call
   * @param aClass name of class to call the method in
   * @return desired method
   */
  public Method handleMethod(String name, String aClass) {
    try {
      Class<?> thisClass = Class.forName(aClass);
      Method m = thisClass.getDeclaredMethod(name);
      return m;
    } catch (NoSuchMethodException e) {
      errorMessage = String.format(
          Config.REFLECTION_METHOD_ERROR_MESSAGE,
          name);
      logger.error(errorMessage);
      return null;
    } catch (ClassNotFoundException e) {
      errorMessage = String.format(
          Config.REFLECTION_CLASS_ERROR_MESSAGE,
          aClass);
      logger.error(errorMessage);
      return null;
    }
  }

  /**
   * gets the error message for testing purposes
   *
   * @return error message String
   */
  public String getErrorMessage() {
    return errorMessage;
  }
}