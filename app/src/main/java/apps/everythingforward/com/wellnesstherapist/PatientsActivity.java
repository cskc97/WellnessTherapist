package apps.everythingforward.com.wellnesstherapist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import apps.everythingforward.com.wellnesstherapist.adapters.PatientsAdapter;

import static android.view.View.GONE;

public class PatientsActivity extends AppCompatActivity {

    RecyclerView recyclerView;


    List<ParseObject> passData;

    ProgressBarCircularIndeterminate progressBarCircularIndeterminate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Patients");

        progressBarCircularIndeterminate = (ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndeterminate);




        passData = new ArrayList<ParseObject>();
        recyclerView = (RecyclerView)findViewById(R.id.patientsRV);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getPatientsTry();
    }



    private void getPatientsTry()
    {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Connections");
        query.whereEqualTo(Utility.CONNECTION_THERAPISTUSERNAME, ParseUser.getCurrentUser().getEmail());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    progressBarCircularIndeterminate.setVisibility(GONE);
                    for (ParseObject object : objects) {

                        String email = object.getString(Utility.CONNECTION_USERUSERNAME);

                        Log.e("PatientsActivity",email);


                        List<ParseUser> objectArrayList;


                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        query.whereEqualTo("email", email);

                        try {
                            Log.e("PatientsActivity",String.valueOf(query.count()));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }


                        try {
                            objectArrayList = query.find();

                            if(!objectArrayList.isEmpty()) {
                            //    Toast.makeText(PatientsActivity.this, objectArrayList.get(0).getString(Utility.USER_NAME), Toast.LENGTH_SHORT).show();
                                passData.add(objectArrayList.get(0));
                            }
                            else
                            {
                                Toast.makeText(PatientsActivity.this, "Therapist List is empty", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }


                    }


                    if (!passData.isEmpty()) {

                        PatientsAdapter adapter = new PatientsAdapter(passData);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    } else {
                        Toast.makeText(PatientsActivity.this, "You Don't Have Any Connections", Toast.LENGTH_SHORT).show();
                    }


                }
                else
                {
                    Toast.makeText(PatientsActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });









    }






//    }
//
//    private void getPatients()
//    {
//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Connections");
//        query.whereEqualTo(Utility.CONNECTION_THERAPISTUSERNAME, ParseUser.getCurrentUser().getEmail());
//
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e==null)
//                {
//                    if(!objects.isEmpty())
//                    {
//                        for (ParseObject object : objects)
//                        {
//                            ParseQuery<ParseObject> userQuery = new ParseQuery<ParseObject>("User");
//                            userQuery.whereEqualTo(Utility.USER_EMAIL,object.getString(Utility.CONNECTION_USERUSERNAME));
//                            try {
//                                List<ParseObject> data = userQuery.find();
//                                passData.add(data.get(0));
//
//
//                            } catch (ParseException e1) {
//                                e1.printStackTrace();
//                            }
//
//
//                        }
//                        PatientsAdapter adapter = new PatientsAdapter(passData);
//                        recyclerView.setAdapter(adapter);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//
//
//
//
//
//
//                    }
//                    else
//                    {
//                        Toast.makeText(PatientsActivity.this, "You have no patients", Toast.LENGTH_SHORT).show();
//                    }
//
//
//
//
//                }
//
//
//            }
//        });
//
//
//
//    }
//




}
