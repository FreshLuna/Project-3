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
                        || filters.getWeekdays().contains(a.getWeekday()))
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

//class Main {
//    public static void main(String[] args) {
//        // Load activities from JSON
//        List<Activity> activities = DataLoader.loadActivities();
//
//        // Print all activities
//        System.out.println("All activities:");
//        activities.forEach(System.out::println);
//
//        // Example filter criteria
//        List<String> locations = Arrays.asList();   // adjust to match your JSON
//        List<String> ageGroups = Arrays.asList("15-30");         // adjust to match your JSON
//        List<Long> dateTimes = Arrays.asList();
//        List<String> genderGroups = Arrays.asList("All");     // adjust to match your JSON
//        List<String> tags = Arrays.asList("climbing", "combat", "relaxation");    // adjust to match your JSON
//
//        // Filter activities
//        List<Activity> filtered = FilterActivities.filterActivities(
//                activities,
//                locations,
//                dateTimes,
//                ageGroups,
//                genderGroups,
//                tags
//        );
//
//        // Print filtered results
//        System.out.println("\nFiltered activities:");
//        if (filtered.isEmpty()) {
//            System.out.println("No activities matched the criteria.");
//        } else {
//            filtered.forEach(System.out::println);
//        }
//    }
//
//}
