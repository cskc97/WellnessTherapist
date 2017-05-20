package apps.everythingforward.com.wellnesstherapist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ParseUser currentTherapist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(ParseUser.getCurrentUser()==null) {
            ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
            builder.setAppLogo(R.drawable.applogo100x100);


            startActivityForResult(builder.build(), 0);
        }

        currentTherapist= ParseUser.getCurrentUser();





    }


    @Override
    protected void onResume() {
        super.onResume();



    }


    private void checkIfInDB()
    {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(Utility.THERAPIST_CLASSNAME);
        query.whereEqualTo(Utility.THERAPIST_USERNAME,currentTherapist.getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.isEmpty())
                {




                }
                else
                {




                }


            }
        });




    }

    private void fillDetails()
    {

    }
}
