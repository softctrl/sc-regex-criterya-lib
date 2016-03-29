/**
 * 
 */
package br.com.softctrl.regex.criterya.utils;

import java.io.File;
import java.net.URL;

/**
 * @author timoshenko
 *
 */
public final class FileUtils {

    public static final String USER_DIR = System.getProperty("user.dir");

    private FileUtils() {}

    /**
     * 
     * @param filename
     * @return
     */
    public static File getResourceFile(final String filename) {
        return new File(getFilePathInClasspath(filename));
    }

    /**
     * 
     * @param filename
     * @return
     */
    public static final File getFileInClasspath(final String filename) {
        String filePath = getFilePathInClasspath(filename);
        if ((filePath + "").trim().length() > 0) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * 
     * @param filename
     * @return
     */
    public static final String getFilePathInClasspath(final String filename) {
        URL url = ClassLoader.getSystemResource(filename);
        return (url == null ? "" : url.getFile());
    }

}