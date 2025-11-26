package Events;
import java.time.LocalDate; // import the LocalDate class
import java.time.format.DateTimeFormatter;
import Classes.Activity;
import static java.lang.Integer.parseInt;


public class Expired { //returns true if event is expired
    public Boolean expired(Activity inputActivity) {
        LocalDate currentDate = LocalDate.now(); // Create a date object
        DateTimeFormatter FormattedDate = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        int currentDateInt = parseInt(currentDate.format(FormattedDate));
        return inputActivity.DateAndTime > currentDateInt;
    }
}
