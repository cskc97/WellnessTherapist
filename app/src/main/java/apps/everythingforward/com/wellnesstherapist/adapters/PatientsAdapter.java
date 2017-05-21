package apps.everythingforward.com.wellnesstherapist.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import apps.everythingforward.com.wellnesstherapist.R;
import apps.everythingforward.com.wellnesstherapist.Utility;

/**
 * Created by santh on 5/21/2017.
 */



public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {



    ArrayList<ParseObject> data;

    public PatientsAdapter(List<ParseObject> objectList)
    {

        data = (ArrayList<ParseObject>)objectList;
    }






    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        RelativeLayout layout;




        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.patientName);
            layout = (RelativeLayout)itemView.findViewById(R.id.relLayout);



        }
    }

    @Override
    public PatientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patientitem,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(PatientsAdapter.ViewHolder holder, int position) {

        final String name = data.get(position).getString(Utility.USER_NAME);
        final String email = data.get(position).getString(Utility.USER_EMAIL);
        final ViewHolder holderFinal = holder;







        holderFinal.name.setText(name);

        final int positionVal = position;




        holderFinal.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                    new LovelyTextInputDialog(holderFinal.layout.getContext())
                            .setTopColorRes(R.color.primary_dark)
                            .setTitle("Message:")
                            .setMessage("Go ahead and ask your connection something!")

                            .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                                @Override
                                public void onTextInputConfirmed(String text) {


                                    Calendar calendar =Calendar.getInstance();
                                    String timeStamp=calendar.getTime().toString();



                                    ParseObject object = new ParseObject("Messages");

                                    object.put(Utility.MESSAGES_FROM,ParseUser.getCurrentUser().getEmail());
                                    object.put(Utility.MESSAGES_TO,email);
                                    object.put(Utility.MESSAGE_CONTENT,text);
                                    object.put(Utility.MESSAGE_TIME,timeStamp);
                                    object.put(Utility.MESSAGE_SENDERNAME,ParseUser.getCurrentUser().getString("name"));
                                    object.saveInBackground();
                                    Toast.makeText(holderFinal.layout.getContext(), "Sent message!", Toast.LENGTH_SHORT).show();



                                }
                            })
                            .show();




                return false;
            }
        });





    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
