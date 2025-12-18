package Events;

import Classes.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static Config.FilePaths.ACTIVITIES_FOLDER;

public class Removed {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File FILE = new File(ACTIVITIES_FOLDER);

    public static void removeActivityById(int id) throws Exception {

        // Load activities.json
        List<Activity> activities = mapper.readValue(
                FILE,
                new TypeReference<>() {}
        );

        // Remove the one with the given id
        List<Activity> updated = activities.stream()
                .filter(a -> a.getActivityID() != id)
                .collect(Collectors.toList());

        // Save updated list
        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(FILE, updated);

        System.out.println("Removed activity with ID: " + id);
    }

    public static void main(String[] args) throws Exception {
        removeActivityById(6); // example
    }
}
