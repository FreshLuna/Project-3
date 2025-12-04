package Database;

import Classes.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class DataLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Activity> loadActivities() {
        try (InputStream is = DataLoader.class.getClassLoader().getResourceAsStream("activities.json")) {
            if (is == null) {
                throw new RuntimeException("activities.json not found in classpath");
            }

            List<Activity> list = mapper.readValue(is, new TypeReference<List<Activity>>() {});
            return Collections.unmodifiableList(list); // unmodifiable = no accidental changes
        } catch (Exception e) {
            throw new RuntimeException("Failed to load activities from classpath", e);
        }
    }
}
