package Database;

import Classes.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class DataLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Activity> loadActivities(String filepath) {
        try {
            List<Activity> list = mapper.readValue(
                new File(filepath),
                new TypeReference<List<Activity>>() {}
            );
            return Collections.unmodifiableList(list); // unmodifiable = no accidental changes
        } catch (Exception e) {
            throw new RuntimeException("Failed to load activities from file: " + filepath, e);
        }
    }
}
