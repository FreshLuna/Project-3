package Database;

import Classes.Activity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static Classes.Filter.fieldExtractors;

public class GetTagsForFilter {

    // Generic method to get unique values from activities (single-value fields)
    public static List<String> getUniqueValues(String fieldName, List<Activity> activities) {
        Function<Activity, List<String>> extractor = fieldExtractors.get(fieldName);
        if (extractor == null) return List.of(); // field not found

        // Flatten lists and collect unique, sorted
        return activities.stream()
                .map(extractor)
                .flatMap(List::stream)
                .filter(s -> s != null && !s.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}