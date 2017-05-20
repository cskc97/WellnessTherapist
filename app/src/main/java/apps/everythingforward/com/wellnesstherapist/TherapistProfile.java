package apps.everythingforward.com.wellnesstherapist;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.parse.FindCallback;
import com.parse.Parse;
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
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_profile);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




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

        setUpDrawer();
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



    private void setUpDrawer()
    {






        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("My Profile");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("My Patients");

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)

                .withHeaderBackground(getResources().getDrawable(R.drawable.headerbg))
                .addProfiles(
                        new ProfileDrawerItem().withName(profileNameStr).withEmail(ParseUser.getCurrentUser().getEmail())
                )
                .build();


//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2

                )

                .build();
    }
}
