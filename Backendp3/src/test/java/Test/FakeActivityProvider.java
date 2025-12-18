package Test;

import Classes.Activity;
import Controller.ActivityProvider;

import java.util.List;

public class FakeActivityProvider implements ActivityProvider {
    private Activity activity;

    public FakeActivityProvider(Activity activity) {
        this.activity = activity;
    }
    @Override
    public Activity getActivity(String name) {
        if (activity.getActivityNameAndID().equals(name)) {
            return activity;
        }
        return null;
    }
}
