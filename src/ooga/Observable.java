package ooga;

import java.beans.PropertyChangeListener;

/**
 * Observable interface, defines interactions possible with an observable
 *
 * @author Alicia Wu
 */
public interface Observable {

  /**
   * add an observer to this observable
   *
   * @param propertyName name of the property being observed
   * @param observer     PropertyChangeListener that will be observing this observable
   */
  void addObserver(String propertyName, PropertyChangeListener observer);

  /**
   * notify the observers of this observable for the specified propertyName
   *
   * @param propertyName name of the property being observed
   * @param o            Object to be passed from the observable to the observer
   */
  void notifyObservers(String propertyName, Object o);
}
