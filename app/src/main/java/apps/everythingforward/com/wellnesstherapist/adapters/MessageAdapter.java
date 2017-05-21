package apps.everythingforward.com.wellnesstherapist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.Card;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import apps.everythingforward.com.wellnesstherapist.R;
import apps.everythingforward.com.wellnesstherapist.Utility;

/**
 * Created by santh on 5/21/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    ArrayList<ParseObject> data;


    public MessageAdapter(List<ParseObject> objects)
    {
        data =(ArrayList<ParseObject>)objects;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV;
        Card messageCard;
        TextView timeStamp;

        public ViewHolder(View itemView) {
            super(itemView);
            messageTV = (TextView) itemView.findViewById(R.id.messageTV);
            messageCard=(Card)itemView.findViewById(R.id.messageCard);
            timeStamp = (TextView)itemView.findViewById(R.id.timeStamp);

        }
    }


    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagesitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        final ViewHolder holderFinal = holder;
        final String fromEmail = data.get(position).getString(Utility.MESSAGES_FROM);
        final String fromName = data.get(position).getString(Utility.MESSAGE_SENDERNAME);
        String timeStamp = data.get(position).getString(Utility.MESSAGE_TIME);
        final String messageContent = data.get(position).getString(Utility.MESSAGE_CONTENT);

        holderFinal.messageTV.setText(fromName);
        holderFinal.timeStamp.setText(timeStamp);

        holderFinal.messageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialStyledDialog.Builder(holderFinal.messageCard.getContext())
                        .setTitle(fromName)
                        .setDescription(messageContent)
                        .setStyle(Style.HEADER_WITH_TITLE)
                        .setHeaderColor(R.color.primary_dark)
                        .withDialogAnimation(true)
                        .withIconAnimation(true)
                        .setScrollable(true)
                        .setCancelable(true)
                        //.setStyle(Style.HEADER_WITH_TITLE)
                        .show();
            }
        });

        holderFinal.messageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new LovelyTextInputDialog(holderFinal.messageCard.getContext())
                        .setTopColorRes(R.color.accent)
                        .setTitle("Message:")
                        .setMessage("Reply to this message")
                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                                    @Override
                                    public void onTextInputConfirmed(String text) {

                                        Calendar calendar =Calendar.getInstance();
                                        String timeStamp=calendar.getTime().toString();

                                        ParseObject object = new ParseObject("Messages");

                                        object.put(Utility.MESSAGES_FROM, ParseUser.getCurrentUser().getEmail());
                                        object.put(Utility.MESSAGES_TO,fromEmail);
                                        object.put(Utility.MESSAGE_CONTENT,text);
                                        object.put(Utility.MESSAGE_TIME,timeStamp);
                                        object.put(Utility.MESSAGE_SENDERNAME,ParseUser.getCurrentUser().getString("name"));
                                        object.saveInBackground();
                                        Toast.makeText(holderFinal.messageCard.getContext(), "Sent message!", Toast.LENGTH_SHORT).show();
                                    }
                                }).show();

                /*
                ParseObject object = new ParseObject("Messages");

                object.put(Utility.MESSAGES_FROM, ParseUser.getCurrentUser().getEmail());
                object.put(Utility.MESSAGES_TO,email);
                object.put(Utility.MESSAGE_CONTENT,text);
                object.put(Utility.MESSAGE_TIME,timeStamp);
                object.put(Utility.MESSAGE_SENDERNAME,ParseUser.getCurrentUser().getString("name"));
                object.saveInBackground();
                Toast.makeText(holderFinal.layout.getContext(), "Sent message!", Toast.LENGTH_SHORT).show();

                */


            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
