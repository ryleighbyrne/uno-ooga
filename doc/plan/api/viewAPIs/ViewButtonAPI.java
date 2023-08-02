/**
 * ViewButton APIs used to create a button and set its functionality.
 */
public interface ViewButton {

  // Create a button and set an event when it's clicked on.
  public Button makeButton(final String label,
      final EventHandler<ActionEvent> handler)
}