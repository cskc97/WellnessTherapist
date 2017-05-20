package apps.everythingforward.com.wellnesstherapist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;
import com.nguyenhoanglam.imagepicker.activity.ImagePicker;
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FillDetails extends AppCompatActivity {

    public static int REQUEST_CODE_PICKER=0;

    ParseUser currentUser;


    @BindView(R.id.image)
    ImageView therapistProfileImage;

    @BindView(R.id.bioTV)EditText bio;

    @BindView(R.id.save)ButtonFlat saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.image)void pickImage()
    {
        ImagePicker.create(this)
                .folderMode(true) // folder mode (false by default)
                .folderTitle("Folder") // folder selection title
                .imageTitle("Tap to select") // image selection title
                .single() // single mode

                .limit(1) // max images can be selected (999 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                 // original selected images, used in multi mode
                .start(REQUEST_CODE_PICKER); // start image picker activity with request cod
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES);

            Picasso.with(this).load(images.get(0).getPath()).fit().centerInside().into(therapistProfileImage);


            // do your logic ....
        }
    }

    @OnClick(R.id.save)void onSumbit()
    {


        Bitmap bitmap = ((BitmapDrawable) therapistProfileImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        currentUser=ParseUser.getCurrentUser();

        ParseFile therapistImage = new ParseFile(currentUser.getUsername()+"_image", imageInByte);


        String userName,userEmail,userDescription;
        userName=currentUser.getString("name");
        userEmail=currentUser.getEmail();
        userDescription=bio.getText().toString();

        ParseObject object = new ParseObject(Utility.THERAPIST_CLASSNAME);
        object.put(Utility.THERAPIST_USERNAME,userEmail);
        object.put(Utility.THERAPIST_NAME,userName);
        object.put(Utility.THERAPIST_DESCRIPTION,userDescription);
        object.put(Utility.THERAPIST_IMAGE,therapistImage);
        object.saveInBackground();


        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null)
                {
                    Toast.makeText(FillDetails.this, "Saved!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FillDetails.this,TherapistProfile.class));
                }
                else
                {
                    Toast.makeText(FillDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });








    }

    }
