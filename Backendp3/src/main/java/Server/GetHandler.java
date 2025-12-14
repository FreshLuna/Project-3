package Server;

import Classes.Activity;
import Database.ActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

import static Database.ActivitySorter.*;
import static Database.GetTagsForFilter.getUniqueValues;


public class GetHandler {

    public String handle(HttpExchange exchange) throws Exception {
        // we retrieve the full path the user attempts to access
        String path = exchange.getRequestURI().getPath();
        System.out.println(path);

        ObjectMapper mapper = new ObjectMapper();

        List<Activity> activities = ActivityService.getAllActivities();
        //System.out.println(activities);

        if(path.startsWith("/server/activities/")){
            String idStr = path.substring("/server/activities/".length());
            int id = Integer.parseInt(idStr);
            return(singleExtractor(ActivityService.getActivityById(id)));

        }

        // switch handles the different possible paths, write full path for now
        // since im to lazy to cut off the server part mwuhahahhaha!!! >:3
        return switch (path) {
            case "/server/status" -> "Server is running";
            case "/server/activities" -> getUpcomingActivities(activities);
            case "/server/popularActivities" -> getPopularActivities(activities);
            case "/server/newActivities" -> getNewActivities(activities);
            case "/server/filter/locations" -> mapper.writeValueAsString(getUniqueValues("locations", activities));
            case "/server/filter/weekdays" -> mapper.writeValueAsString(getUniqueValues("weekdays", activities));
            case "/server/filter/ages" -> mapper.writeValueAsString(getUniqueValues("ages", activities));
            case "/server/filter/genders" -> mapper.writeValueAsString(getUniqueValues("genders", activities));
            case "/server/filter/tags" -> mapper.writeValueAsString(getUniqueValues("tags", activities));
            default -> "Unknown GET path: " + path;
        };

    }
}