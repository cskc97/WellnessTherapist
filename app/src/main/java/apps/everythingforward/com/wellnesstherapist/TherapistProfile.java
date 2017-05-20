package apps.everythingforward.com.wellnesstherapist;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import apps.everythingforward.com.wellnesstherapist.models.TherapistProfileDBEntityManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TherapistProfile extends AppCompatActivity {

    ImageView profileImage;
    TextView profileName;
    TextView profileDescrption;


    String email;
    String imagePath;
    String profileNameStr;
    String profileDescriptionStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_profile);


        profileName = (TextView)findViewById(R.id.therapistProfileName);
        profileDescrption = (TextView)findViewById(R.id.therapistProfileDescription);
        profileImage = (ImageView)findViewById(R.id.therapistProfileImage);











    }

    @Override
    protected void onResume() {
        super.onResume();

        TherapistProfileDBEntityManager manager = new TherapistProfileDBEntityManager();
        imagePath = manager.select().asList().get(0).getImagePath();
        Uri uri = Uri.fromFile(new File(imagePath));
        Picasso.with(getApplicationContext()).load(uri).fit().centerCrop().into(profileImage);

        getNameAndDesc();
    }

    private void getNameAndDesc()
    {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(Utility.THERAPIST_CLASSNAME);
        query.whereEqualTo(Utility.THERAPIST_USERNAME, ParseUser.getCurrentUser().getEmail());

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    profileNameStr = objects.get(0).getString(Utility.THERAPIST_NAME);
                    profileDescriptionStr = objects.get(0).getString(Utility.THERAPIST_DESCRIPTION);
                    profileName.setText(profileNameStr);
                    profileDescrption.setText(profileDescriptionStr);


                }

            }
        });



    }
}
