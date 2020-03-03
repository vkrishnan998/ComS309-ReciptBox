package com.example.vignesh.textrecognitionimage.Utils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.vignesh.textrecognitionimage.Utils.ChatMessage;
import com.example.vignesh.textrecognitionimage.Activities.LoginActivity;
import com.example.vignesh.textrecognitionimage.R;

import java.util.ArrayList;
import java.util.List;


/**
 * The ChatArrayAdapter positions the chat messages as they are received and sets them on the proper side of the screen
 */
public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private Context context;

    private String username;
    /**
     * Adds a new chat object on top of the current list of chat message listt
     */
    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    /**
     * Adds a new chat object on top of the current list of chat message listt
     */
    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    /**
     * Gets the size of the chat message list
     */
    public int getCount() {
        return this.chatMessageList.size();
    }

    /**
     * Gets the index of the item in the chat message list
     */
    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    /**
     * Displays message on the respective side of the screen according to the user it's sending to
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        username = LoginActivity.user.getUsername();


        ChatMessage chatMessageObj = getItem(position);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if(!ChatActivity.otherUser.equals(username) || chatMessageObj.left) {
//            row = inflater.inflate(R.layout.right, parent, false);
//
//        }
//        else{
//            row = inflater.inflate(R.layout.left, parent, false);
//        }
        chatMessageObj.message.toLowerCase();
        if(chatMessageObj.message.contains(username)){
            row = inflater.inflate(R.layout.right, parent, false);
        }
        else {
            row = inflater.inflate(R.layout.left, parent, false);
        }


//
//        if (chatMessageObj.left) {
//            row = inflater.inflate(R.layout.right, parent, false);
//        }else{
//            row = inflater.inflate(R.layout.left, parent, false);
//        }
        chatText = (TextView) row.findViewById(R.id.msgr);
        chatText.setText(chatMessageObj.message);
        return row;
    }
}