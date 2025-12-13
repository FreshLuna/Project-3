package Database;

import Classes.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static Config.FilePaths.ACTIVITIES_FOLDER;

public class DataLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Activity> loadActivities() {
        try (InputStream is = DataLoader.class.getClassLoader().getResourceAsStream(ACTIVITIES_FOLDER)) {
            if (is == null) {
                throw new RuntimeException(ACTIVITIES_FOLDER + " is Empty");
            }

            List<Activity> list = mapper.readValue(is, new TypeReference<>() {});

            return Collections.unmodifiableList(list); // unmodifiable = no accidental changes
        } catch (Exception e) {
            throw new RuntimeException((ACTIVITIES_FOLDER + " not found in classpath"), e);
        }
    }
}
