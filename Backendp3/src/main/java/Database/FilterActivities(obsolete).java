package Database;

import Classes.Activity;
import Classes.Filter;

import java.util.*;
import java.util.stream.Collectors;

public class FilterActivities {

    public static List<Activity> filterActivities(
            List<Activity> activities,
            Filter filters
    ) {
        if (filters == null) return activities;

        return activities.stream()
                .filter(a -> filters.getLocations() == null || filters.getLocations().isEmpty()
                        || filters.getLocations().contains(a.getLocation()))
                .filter(a -> filters.getWeekdays() == null || filters.getWeekdays().isEmpty()
                        || filters.getWeekdays().contains(a.getWeekdays()))
                .filter(a -> filters.getAges() == null || filters.getAges().isEmpty()
                        || filters.getAges().contains(a.getAgeGroup()))
                .filter(a -> filters.getGenders() == null || filters.getGenders().isEmpty()
                        || filters.getGenders().contains(a.getGenderGroup()))
                .filter(a -> {
                    if (filters.getTags() == null || filters.getTags().isEmpty()) return true;
                    for (String tag : filters.getTags()) {
                        if (a.getTags().contains(tag)) return true;
                    }
                    return false;
                })
                .sorted(Comparator.comparingLong(Activity::getDateAndTime))
                .collect(Collectors.toList());
    }
}

class Main {
    public static void main(String[] args) {
        // Load activities from JSON
        List<Activity> activities = DataLoader.loadActivities();

        // Example filter criteria
        List<String> locations = Arrays.asList();
        List<String> ageGroups = Arrays.asList();
        List<String> genderGroups = Arrays.asList();
        List<String> tags = Arrays.asList("climbing", "combat", "indoor");

        // Create Filter object
        Filter filter = new Filter();
        filter.setLocations(locations);
        filter.setWeekdays(new ArrayList<>());
        filter.setAges(ageGroups);
        filter.setGenders(genderGroups);
        filter.setTags(tags);

        // Filter activities
        List<Activity> filtered = FilterActivities.filterActivities(activities, filter);

        // Print filtered results
        System.out.println("\nFiltered activities:");
        if (filtered.isEmpty()) {
            System.out.println("No activities matched the criteria.");
        } else {
            filtered.forEach(System.out::println);
        }
    }

}
