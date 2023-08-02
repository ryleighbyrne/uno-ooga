/**
 * implementing use case in which the user selects a language. the appropriate language is updated and
 * the display is changed appropriately
 */
package ooga.view.display;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.view.View;
import javafx.scene.control.Label;
import ooga.view.ViewButton;
import ooga.view.Resources;

public class LanguageSettings extends Display {

    /**
     * Key for Language settings title.
     */
    private final String TITLE = "LanguageSettingsTitle";
    /**
     * File path for resource files.
     */
    private static final String LANGUAGE_FILEPATH = "resources/propertyFiles/%s";
    /**
     * Code for button style in .css file.
     */
    private static final String BUTTON_STYLE = "Button";
    /**
     * View object stored for language change.
     */
    private final View MY_VIEW;
    /**
     * Key for English.
     */
    private final String ENGLISH = "English";
    /**
     * Key for Spanish.
     */
    private final String SPANISH = "Spanish";
    /**
     * Key for Danish.
     */
    private final String DANISH = "Danish";
    /**
     * Key for German.
     */
    private final String GERMAN = "German";

    /**
     * Constructor for Display class.
     */
    public LanguageSettings(View v) {
        super(v);
        MY_VIEW = v;
    }

    @Override
    public BorderPane createDisplay() {
        BorderPane languageSettings = new BorderPane();
        super.setTitle(languageSettings, new Label(MY_RESOURCES.getString(TITLE)));
        languageSettings.setCenter(addLanguageOptions());
        return languageSettings;
    }

    /**
     * Adds buttons displaying available languages.
     */
    private VBox addLanguageOptions() {
        VBox languages = new VBox();
        String[] names = {ENGLISH, SPANISH, DANISH, GERMAN, "Arabic"};

        // Add button for each language
        for (String name : names) {
            Button b = ViewButton.makeButton(MY_RESOURCES.getString(name),
                    event -> Resources.setPath(String.format(
                            LANGUAGE_FILEPATH, name.toLowerCase()), MY_VIEW));

            // Set encoding to UTF-8 (For languages that aren't normally supported)
            languages.getChildren().add(b);
            b.getStyleClass().add(BUTTON_STYLE);
        }
        return languages;
    }
}

/**
 * Resources class that sets new path for resource bundle when language button is pressed
 */
package ooga.view;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class handles property files, resource bundles, and path strings.
 *
 * @author Cate Schick cms168
 */
public class Resources {

    /**
     * Default language set to English.
     */
    private static String resourcePath = "resources/propertyFiles/english";

    /**
     * Returns resources from the desired resource bundle.
     *
     * @param path the filepath indicating which resource bundle to access.
     * @return keys and values of resource bundle.
     */
    public static ResourceBundle getResources(final String path) {
        try {
            return ResourceBundle.getBundle(path);
        } catch (MissingResourceException exception) {
            Error.handleMissingResourceException(exception);
        }
        return null;
    }


    /**
     * This method returns the current resource file path in the program.
     */
    public static String getPath() {
        return resourcePath;
    }

    /**
     * Allows user to set resource path to a selected language and redraws all displays in that
     * language.
     *
     * @param path The filepath for desired resource file
     */
    public static void setPath(final String path, View v) {
        resourcePath = path;
        v.updateScene();
    }

}