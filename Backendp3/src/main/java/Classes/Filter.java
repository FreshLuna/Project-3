package Classes;
import java.util.ArrayList;
import java.util.List;

public class Filter {

    private List<String> locations = new ArrayList<>();
    private List<String> weekdays = new ArrayList<>();
    private List<String> ages = new ArrayList<>();
    private List<String> genders = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    // Getters and setters
    public List<String> getLocations() { return locations; }
    public void setLocations(List<String> locations) { this.locations = locations; }

    public List<String> getWeekdays() { return weekdays; }
    public void setWeekdays(List<String> weekdays) { this.weekdays = weekdays; }

    public List<String> getAges() { return ages; }
    public void setAges(List<String> ages) { this.ages = ages; }

    public List<String> getGenders() { return genders; }
    public void setGenders(List<String> genders) { this.genders = genders; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
