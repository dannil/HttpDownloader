package com.github.dannil.httpdownloader.utility;

import java.util.Arrays;

import com.github.dannil.httpdownloader.model.URL;

/**
 * Class which constructs strings for URL operations. By utilizing this class, we achieve
 * a layer between the controllers and the config file, and can therefore change
 * implementation easily if needed.
 *
 * @author Daniel Nilsson (daniel.nilsson94@outlook.com)
 * @version 2.0.0-SNAPSHOT
 * @since 0.0.1-SNAPSHOT
 */
public final class URLUtility {

    private static final XMLUtility XML_UTILITY;

    static {
        XML_UTILITY = new XMLUtility(ConfigUtility.getConfigFileAbsolutePath());
    }

    private URLUtility() throws IllegalAccessException {
        throw new IllegalAccessException("Class " + this.getClass().getName() + " isn't allowed to be initialized");
    }

    /**
     * Construct a string where to redirect the user.
     *
     * @param destination
     *            which URL to redirect the user to
     *
     * @return a string where to redirect the user
     */
    public static String redirect(String destination) {
        return "redirect:" + destination;
    }

    /**
     * Return the specific URL string which matches the specified enumerable and prepend a
     * redirect clause.
     *
     * @param url
     *            the URL to fetch
     *
     * @return the URL from the config file which corresponds with the specified
     *         enumerable, with a redirect clause prepended to it
     *
     * @see com.github.dannil.httpdownloader.utility.URLUtility#getUrl(URL)
     * @see com.github.dannil.httpdownloader.utility.URLUtility#redirect(String)
     */
    public static String getUrlRedirect(URL url) {
        return redirect(getUrl(url));
    }

    /**
     * Return a specific URL string, which is decided by the specified enumerable.
     *
     * @param url
     *            the URL to fetch
     *
     * @return the URL from the config file which corresponds with the specified
     *         enumerable
     */
    public static String getUrl(URL url) {
        if (!Arrays.asList(URL.values()).contains(url)) {
            throw new IllegalArgumentException(url + " is not an existing enumerable for " + URL.class.getName());
        }
        // Convert the enum to the correct string representation by only keeping
        // alphanumeric characters and lowercasing the result.
        String urlAsString = url.name().replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        return XML_UTILITY.getElementValue("/configuration/app/urls/" + urlAsString);
    }

}
