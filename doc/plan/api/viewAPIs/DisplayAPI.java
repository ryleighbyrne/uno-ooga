
/**
 * Display APIs used to navigate through the program, load specific displays, and change the design
 * settings on a display.
 */
public interface Display {

  // Creates a new Display using the display currently set as MY_DISPLAY.
  public BorderPane createDisplay();

  // Creates a new object of Class newDisplay and creates that specified display.
  public void switchDisplay(final String newDisplay);

  // Attaches a specified title to the top of a display.
  public void setTitle(final BorderPane display, final String title);

  // Creates a back button and attaches it to the left of the display.
  public Button makeBackButton(final String display);

  // Sets display style to its class name.
  public void setDisplayStyle(final String className, final Pane display)
}