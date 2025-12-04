package Events;
import java.time.LocalDate; // import the LocalDate class
import java.time.format.DateTimeFormatter;
import Classes.Activity;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;


public class Expired { //returns true if event is expired
    public Boolean expired(Activity inputActivity) {
        LocalDate currentDate = LocalDate.now(); // Create a date object
        DateTimeFormatter FormattedDate = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        long currentDateInt = parseLong(currentDate.format(FormattedDate));
        return inputActivity.getDateAndTime() > currentDateInt;
    }
}
