package Database;

import Classes.Activity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GetTagsForFilter {

    // Generic method to get unique values from activities (single-value fields)
    public static List<String> getUniqueValues(java.util.function.Function<Activity, String> mapper) {
        List<Activity> activities = DataLoader.loadActivities();

        // Use stream to map and collect unique values
        Set<String> unique = activities.stream()
                .map(mapper)
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toSet());

        // Return sorted list
        return unique.stream().sorted().collect(Collectors.toList());
    }

    // Special method for tags
    public static List<String> getTags() {
        List<Activity> activities = DataLoader.loadActivities();

        return activities.stream()
                .flatMap(a -> a.getTags().stream())  // flatten List<String> into Stream<String>
                .filter(s -> s != null && !s.isEmpty())
                .distinct()                          // remove duplicates
                .sorted()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        // Test getUniqueValues with different fields

        // Locations
        List<String> locations = GetTagsForFilter.getUniqueValues(Activity::getLocation);
        System.out.println("Locations:");
        locations.forEach(System.out::println);

        System.out.println("\nWeekdays:");
        List<String> weekdays = GetTagsForFilter.getUniqueValues(Activity::getWeekdays);
        weekdays.forEach(System.out::println);

        System.out.println("\nAges:");
        List<String> ages = GetTagsForFilter.getUniqueValues(Activity::getAgeGroup);
        ages.forEach(System.out::println);

        System.out.println("\nGenders:");
        List<String> genders = GetTagsForFilter.getUniqueValues(Activity::getGenderGroup);
        genders.forEach(System.out::println);

        System.out.println("\nTags:");
        List<String> tags = GetTagsForFilter.getTags();
        tags.forEach(System.out::println);
    }
}