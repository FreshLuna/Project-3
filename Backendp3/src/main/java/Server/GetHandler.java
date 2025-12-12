package Server;
// import Database.ActivityService;
import Classes.Activity;
import Database.GetTagsForFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import static Database.ActivitySorter.*;
import static Database.DatabaseToStringReader.fileReader;
import static Database.GetSingularActivityFromId.findActivityById;
import static Database.GetTagsForFilter.getUniqueValues;

public class GetHandler {
    public String handle(HttpExchange exchange) throws Exception {
        // we retrieve the full path the user attempts to access
        String path = exchange.getRequestURI().getPath();
        if(path.startsWith("/server/activities/")){
            String idStr = path.substring("/server/activities/".length());
            int id = Integer.parseInt(idStr);
            return(findActivityById(id));

        }
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(path);


        // switch handles the different possible paths, write full path for now
        // since im to lazy to cut off the server part mwuhahahhaha!!! >:3
        return switch (path) {
            case "/server/users" -> "List of users";
            case "/server/status" -> "Server is running";
            case "/server/activities" -> getUpcomingActivities();
            case "/server/popularActivities" -> getPopularActivities();
            case "/server/newActivities" -> getNewActivities();
            case "/server" -> fileReader("activities.json");
            case "/server/filter/locations" -> mapper.writeValueAsString(GetTagsForFilter.getUniqueValues(Activity::getLocation));
            case "/server/filter/weekdays" -> mapper.writeValueAsString(GetTagsForFilter.getUniqueValues(Activity::getWeekday));
            case "/server/filter/ages" -> mapper.writeValueAsString(GetTagsForFilter.getUniqueValues(Activity::getAgeGroup));
            case "/server/filter/genders" -> mapper.writeValueAsString(GetTagsForFilter.getUniqueValues(Activity::getGenderGroup));
            case "/server/filter/tags" -> mapper.writeValueAsString(GetTagsForFilter.getTags());


            default -> "Unknown GET path: " + path;
        };

    }
}