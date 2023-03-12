package services;

/**
 * This class contains all the information that is common to all the JDBC services and database access.
 */
public class DatabaseInfo {

  public static final String DB_URL = "jdbc:sqlite:./resources/COMMUNITY_NEWS.db";

  public enum Tables {
    USERS ("USERS"),
    CURRENT_USER ("CURRENT_USER"),
    POSTS ("POSTS"),
    COMMENTS ("COMMENTS");

    public final String label;

    Tables(String label) {
      this.label = label;
    }
  }
}