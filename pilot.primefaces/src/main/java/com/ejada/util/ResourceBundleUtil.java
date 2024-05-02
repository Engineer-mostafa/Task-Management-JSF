package com.ejada.util;

import java.util.ResourceBundle;
import java.util.Locale;
import javax.faces.context.FacesContext;

public class ResourceBundleUtil {
    private static ResourceBundle resourceBundle;

    public static void loadResourceBundle() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        resourceBundle = ResourceBundle.getBundle("com.ejada.util.messages", locale);
    }

    public static String getMessage(String key) {
        if (resourceBundle == null) {
            loadResourceBundle();
        }
        return resourceBundle.getString(key);
    }
    
    public static void setLocale(Locale locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        resourceBundle = ResourceBundle.getBundle("com.ejada.util.messages", locale);
    }
}
