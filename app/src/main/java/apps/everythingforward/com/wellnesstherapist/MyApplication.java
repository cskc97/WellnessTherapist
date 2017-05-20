package apps.everythingforward.com.wellnesstherapist;

import android.app.Application;

import com.parse.Parse;

import fr.xebia.android.freezer.Freezer;

/**
 * Created by santh on 5/20/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
        Freezer.onCreate(this);
    }
}
