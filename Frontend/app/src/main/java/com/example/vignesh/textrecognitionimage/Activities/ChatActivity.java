package com.example.vignesh.textrecognitionimage.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.KeyEvent;
import android.widget.AbsListView;
import android.database.DataSetObserver;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;

import android.widget.EditText;
import android.widget.TextView;

import com.example.vignesh.textrecognitionimage.R;
import com.example.vignesh.textrecognitionimage.Utils.ChatArrayAdapter;
import com.example.vignesh.textrecognitionimage.Utils.ChatMessage;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import android.widget.ListView;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * For users to chat with other users
 */
public class ChatActivity extends AppCompatActivity {

    public static Context applicationContext;

    private static String username;

    private WebSocketClient client;
    private TextView chatMessage;

    private Button sendChatMessage;
    private EditText messageContent;
    private TextView chat;
    private ListView listView;
    private ChatArrayAdapter chatArrayAdapter;
    private boolean side = false;
    private TextView usernameOfOtherUser;

    private String messages = "";

    /**
     * Creates chat activity UI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //change this back
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        applicationContext = getApplicationContext();

        //displays the username of the other user
//        usernameOfOtherUser = (TextView) findViewById(R.id.username);

        //button you click on to send a message
        sendChatMessage = (Button) findViewById(R.id.sendMessage);

        listView = (ListView) findViewById(R.id.msgview);

        //chat = (TextView) findViewById(R.id.messageText);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.right);
        listView.setAdapter(chatArrayAdapter);
        //the conversation between users
        //chat = (TextView) findViewById(R.id.messageText);

        // gets username of user and connects to the web socket with the username
        username = LoginActivity.user.getUsername();
        connectToWebSocket(username);

        messageContent = (EditText) findViewById(R.id.chatText);
        messageContent.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage(messageContent.getText().toString());
                }
                return false;
            }
        });

        sendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                System.out.println(messageContent.getText());
                sendMessage(arg0 , messageContent);
            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    /**
     * Connects to the web socket
     *
     * @param username this is the username of the user sending the message
     */
    public void connectToWebSocket(final String username) {
        Draft[] drafts = {new Draft_6455()};

        try {
            Log.d("Socket:", "Trying socket");
            String url = "ws://proj309-pp-08.misc.iastate.edu:8080/websocket/" + username;
            client = new WebSocketClient(new URI(url), (Draft) drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                }

                @Override
                public void onMessage(String s) {
                    Log.d("", "run() returned: " + s);

                    if(!s.equals("")){
                        String message = "";
                        sendChatMessage(s);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                }
            };

        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }

        client.connect();
    }

    /**
     * To send a message to another user on the chat
     *
     * @param view
     * @param message the message the user is trying to send
     */
    public void sendMessage(View view, EditText message) {
        try {

            String text = message.getText().toString();
            text = LoginActivity.user.getUsername() +  ": " + text;
            client.send(text);

            if (message.length() > 1) {
                chat.setText(messageContent.getText().toString());
                String b = chat.getText().toString();
                Log.d("CHAT", "Chat box content is " + b);
                message.getText().clear();
            }


        } catch (Exception e) {
            Log.d("ExceptionSendMessage:", e.getMessage().toString());
        }
    }

    /**
     * Sends message to the backend for processing
     * @param s the message being sent
     * @return true or false for success
     */
    private boolean sendChatMessage(String s) {
        chatArrayAdapter.add(new ChatMessage(side, s));
        messageContent.setText("");
        side = !side;
        return true;
    }

}

