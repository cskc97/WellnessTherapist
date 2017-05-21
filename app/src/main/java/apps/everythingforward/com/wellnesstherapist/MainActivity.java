package apps.everythingforward.com.wellnesstherapist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);











    }


    @Override
    protected void onResume() {
        super.onResume();

        if(ParseUser.getCurrentUser()==null) {
            ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
            builder.setAppLogo(R.drawable.applogo100x100);
            startActivityForResult(builder.build(), 0);




        }
        else {


            checkIfInDB();
        }







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0)
        {
        //    Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();
        }


    }

    private void checkIfInDB()
    {
        Log.e("MainActivity","CheckIfInDB");
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Utility.THERAPIST_CLASSNAME);
        query.whereEqualTo(Utility.THERAPIST_USERNAME,ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.isEmpty())
                {
                    fillDetails();



                }
                else
                {
                    therapistPortal();



                }


            }
        });




    }

    private void fillDetails()
    {
        startActivity(new Intent(MainActivity.this,FillDetails.class));

    }

    private void therapistPortal()
    {
        startActivity(new Intent(MainActivity.this,TherapistProfile.class));
    }
}
