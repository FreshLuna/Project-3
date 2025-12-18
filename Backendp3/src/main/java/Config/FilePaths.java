package Config;

public class FilePaths {
    public static final String ACTIVITIES_FOLDER = "activities.json";
    public static String EVENTS_FOLDER =
        System.getProperty("test.env") != null
                ? "src/test/resources/events/"
                : "src/main/resources/events/";
}