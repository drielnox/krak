import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * string management for different languages
 * @author Till Riemer
 *
 */
public class Messages {
    private static String LANG_SET = "messages"; //$NON-NLS-1$

    private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(LANG_SET);

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    public static void setLanguage(String langset){
    	LANG_SET = langset;
    	RESOURCE_BUNDLE = ResourceBundle.getBundle(LANG_SET);
    	View.mainView.dispose();
    	View.mainView = new View();
    }
    
    public static String getLanguage() { return LANG_SET; }

    private Messages() {
    }
}
