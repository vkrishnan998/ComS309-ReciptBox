package com.example.vignesh.textrecognitionimage.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import com.example.vignesh.textrecognitionimage.Model.User;
import java.util.List;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.view.View;

import com.example.vignesh.textrecognitionimage.R;

public class SelectChatUserIdActivity extends AppCompatActivity {
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_chat_user_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        spinner = findViewById(R.id.spinner1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<User> userList = new ArrayList<>();
        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
//                android.R.layout.simple_spinner_item, userList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

//        Button selectChatUser = (Button) findViewById(R.id.startChat);
//        selectChatUser.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent myIntent = new Intent(view.getContext(), ChatActivity.class);
//                startActivityForResult(myIntent, 0);
//            }
//        });


    }
    public void getSelectedUser(View v){
        User user = (User) spinner.getSelectedItem();
    }

}
