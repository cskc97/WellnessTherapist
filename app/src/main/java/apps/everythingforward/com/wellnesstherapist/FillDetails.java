package apps.everythingforward.com.wellnesstherapist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.io.File;
import java.util.ArrayList;


import apps.everythingforward.com.wellnesstherapist.models.TherapistProfileDB;
import apps.everythingforward.com.wellnesstherapist.models.TherapistProfileDBEntityManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FillDetails extends AppCompatActivity {

    public static int REQUEST_CODE_PICKER=0;

    ParseUser currentUser;



    ImageView therapistProfileImage;

    EditText bio;

    ButtonFlat saveButton;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_details);

        therapistProfileImage = (ImageView)findViewById(R.id.image);
        bio = (EditText)findViewById(R.id.bioTV);
        saveButton = (ButtonFlat)findViewById(R.id.save);

        therapistProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();

            }
        });

       saveButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onSumbit();
           }
       });
    }

    void pickImage()
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




            path = images.get(0).getPath();

            Toast.makeText(this, path, Toast.LENGTH_LONG).show();

            Uri uri = Uri.fromFile(new File(path));




            Picasso.with(this).load(uri).fit().centerCrop().into(therapistProfileImage);





            // do your logic ....
        }
    }

    void onSumbit()
    {



        Bitmap bitmap = ((BitmapDrawable) therapistProfileImage.getDrawable()).getBitmap();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        Log.e("onSumbit",String.valueOf(imageInByte));
        System.out.println(imageInByte);

        currentUser=ParseUser.getCurrentUser();

        ParseFile therapistImage = new ParseFile(currentUser.getEmail()+"_image.jpg", imageInByte);


        String userName,userEmail,userDescription;
        userName=currentUser.getString("name");
        userEmail=currentUser.getEmail();
        userDescription=bio.getText().toString();

        ParseObject object = new ParseObject(Utility.THERAPIST_CLASSNAME);
        object.put(Utility.THERAPIST_USERNAME,userEmail);
        object.put(Utility.THERAPIST_NAME,userName);
        object.put(Utility.THERAPIST_DESCRIPTION,userDescription);
        object.put(Utility.THERAPIST_IMAGE,therapistImage);


        TherapistProfileDBEntityManager manager = new TherapistProfileDBEntityManager();
        manager.add(new TherapistProfileDB(ParseUser.getCurrentUser().getEmail(),path));





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
