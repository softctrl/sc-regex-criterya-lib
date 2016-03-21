/**
 * 
 */
package br.com.softctrl.regex.criterya.utils;

import java.io.File;

/**
 * @author timoshenko
 *
 */
public final class FileUtils {

  public static final String USER_DIR = System.getProperty("user.dir");
  private static final String RESOURCE_DIR = String.format("%s/%s", USER_DIR, "src/main/resources");

  private FileUtils() {
  }

  public static File getResourceFile(final String filename) {
    return new File(getResourceFilePath(filename));
  }

  public static String getResourceFilePath(final String filename) {
    return String.format("%s/%s", RESOURCE_DIR, filename);
  }

  private static final String DIR = "%s/%s";

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
    String result = null;
    for (final String path : System.getProperty("java.class.path").split(":")) {
      if (new File(result = String.format(DIR, path, filename)).exists())
        break;
    }
    return result;
  }
}