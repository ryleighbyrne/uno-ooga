package ooga;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Abstract class implementing the Observable interface
 *
 * @author Alicia Wu
 */
public abstract class PropertyChangeObservable implements Observable {

  private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

  /**
   * add an observer to this observable
   *
   * @param propertyName name of the property being observed
   * @param observer     PropertyChangeListener that will be observing this observable
   */
  public void addObserver(String propertyName, PropertyChangeListener observer) {
    propertyChangeSupport.addPropertyChangeListener(propertyName, observer);
  }

  /**
   * notify the observers of this observable for the specified propertyName
   *
   * @param propertyName name of the property being observed
   * @param o            Object to be passed from the observable to the observer
   */
  public void notifyObservers(String propertyName, Object o) {
    propertyChangeSupport.firePropertyChange(propertyName, null, o);
  }

}
