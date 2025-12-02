package Database;
import java.util.List;

public class FilterRequest {
    private List<String> selectedTags;

    public List<String> getSelectedTags() {
        return selectedTags;
    }
    public void setSelectedTags(List<String> selectedTags) {
        this.selectedTags = selectedTags;
    }
}
