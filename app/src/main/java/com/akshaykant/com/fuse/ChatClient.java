package com.akshaykant.com.fuse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.akshaykant.com.fuse.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.Date;


public class ChatClient extends AppCompatActivity implements View.OnClickListener {

    ImageButton sendButton;
    EditText messageText;
    RecyclerView messageList;

    RecyclerView.Adapter mAdapter = null;
    ArrayList<Message> messages = null;
    int in_index = 0;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_client);

        sendButton = (ImageButton) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);

        messageText = (EditText) findViewById(R.id.messageText);

        // messages = new ArrayList<String>();
        messages = new ArrayList<Message>();

        // mAdapter = new ArrayAdapter<String>(this, R.layout.mymessage, R.id.mymessageTextView, messages);
        mAdapter = new MyAdapter(this, messages);


        // ACTIVATING RECYCLER VIEW
        messageList = (RecyclerView) findViewById(R.id.messageList);
        messageList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        messageList.setLayoutManager(llm);
        messageList.setAdapter(mAdapter);


        //ACTIVATING THE TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        // get the friend's name from the Intent
        Intent in = getIntent();
        String friendName = in.getStringExtra(getString(R.string.friend));

        // Set the toolbar title to friend's name. This is a little quirk
        // that once you set the toolbar to be an ActionBar, you have to
        // use this approach to set the title
        getSupportActionBar().setTitle(friendName);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sendButton:

                String messString = messageText.getText().toString();
                if (!messString.equals("")) {
                    Message message = new Message("", messString, true, new Date());
                    messages.add(message);
                    messageList.scrollToPosition(messages.size() - 1);
                    mAdapter.notifyDataSetChanged();
                    sendMessage();
                    message = null;
                    messageText.setText("");

                }

                break;

            default:
                break;
        }
    }

    private void sendMessage() {

        String[] incoming = {"Hey, How's it going?",
                "Super! Let's do lunch tomorrow",
                "How about Mexican?",
                "Great, I found this new place around the corner",
                "Ok, see you at 12 then!"};

        if (in_index < incoming.length) {
            Message message = new Message("John", incoming[in_index], false, new Date());
            messages.add(message);
            in_index++;
            messageList.scrollToPosition(messages.size() - 1);
            mAdapter.notifyDataSetChanged();
        }
    }
}
