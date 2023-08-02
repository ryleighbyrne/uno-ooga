
/**
 * Resources APIs used to generate text from resource files, and set paths to different resource
 * files for language change functionality.
 */
public interface Resources {

  // Changes the resource file path to desired language.
  public String setPath(String newPath);

  // Returns the current resource file path.
  public void getPath();

  // Generates resource bundle based on current resource file path.
  public ResourceBundle getResources();
}