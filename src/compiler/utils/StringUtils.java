package compiler.utils;

/**
 * Random string utils.
 */
public class StringUtils {

  public static String escape(Character character) {
    return escape("" + character);
  }

  public static String escape(String string) {
    return string
      .replace("\n", "\\n")
      .replace("\t", "\\t")
      .replace("\r", "\\r")
      .replace("\f", "\\f");
  }

}
