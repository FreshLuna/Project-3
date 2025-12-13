package Events;

import Classes.Activity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static Config.FilePaths.ACTIVITIES_FOLDER;

public class Modified {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File FILE = new File(ACTIVITIES_FOLDER);

    public static void updateActivityById(int id, Activity updates) throws IOException, IllegalAccessException {

        List<Activity> activities = mapper.readValue(FILE, new TypeReference<List<Activity>>() {});
        boolean found = false;

        for (Activity a : activities) {
            if (a.getActivityID() == id) {
                found = true;

                Field[] fields = Activity.class.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true); // allow access to private fields

                    Object newValue = field.get(updates); // value from the update object
                    if (newValue == null) continue; // skip null for objects

                    Class<?> type = field.getType();

                    if (type == int.class && (Integer) newValue == 0) continue; // skip default int
                    if (type == long.class && (Long) newValue == 0L) continue; // skip default long
                    if (type == boolean.class) {
                        field.set(a, newValue); // always update boolean
                        continue;
                    }

                    // update all other non-null fields
                    field.set(a, newValue);
                }

                break;
            }
        }

        if (!found) {
            System.out.println("Activity with ID " + id + " not found.");
            return;
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(FILE, activities);
        System.out.println("Activity with ID " + id + " has been partially updated.");
    }
    //public static void Server.main(String[] args) throws Exception {
    //    Activity updated = new Activity();
    //    updated.ActivityCapacity = 20;
    //
    //    updateActivityById(2, updated); // example
    //}

}
