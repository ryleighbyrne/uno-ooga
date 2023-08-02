/**
 * APIs for the View class used primarily to alter the current display.
 */
public interface View {

  // Returns root BorderPane to reset ToolBar/Display.
  public BorderPane getRoot();

  // Changes .css filepath to desired theme.
  public void setTheme(String newTheme);

  // Changes current display to load.
  public Display setMyDisplay(Display newDisplay);

  // Returns the current display object.
  public void getMyDisplay();

  // Creates a scene with specified dimensions.
  public Scene makeScene(final int width, final int height);

  // Updates scene when theme/language is changed.
  public Scene updateScene(final int width, finalt int height);
}