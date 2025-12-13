package Events;

import Classes.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Config.FilePaths.ACTIVITIES_FOLDER;

public class Published {
    private static final File FILE = new File(ACTIVITIES_FOLDER);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void publish(Activity inputActivity) {
        try {
            List<Activity> activities;

            // Load existing file or create empty list if file doesn't exist
            if (FILE.exists() && FILE.length() > 0) {
                activities = mapper.readValue(FILE, new TypeReference<List<Activity>>() {});
            } else {
                activities = new ArrayList<>();
            }

            // Add the new activity
            activities.add(inputActivity);

            // Write back to JSON file
            mapper.writerWithDefaultPrettyPrinter().writeValue(FILE, activities);

            System.out.println("Activity published! YIPEEE");

        } catch (IOException e) {
            System.err.println("Error publishing activity: " + e.getMessage());
        }
    }
}
