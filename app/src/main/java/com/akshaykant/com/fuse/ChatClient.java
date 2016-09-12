package com.akshaykant.com.fuse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.akshaykant.com.fuse.adapter.MyAdapter;
import com.akshaykant.com.fuse.font.RobotoTextView;
import com.akshaykant.com.fuse.view.FloatLabeledEditText;

import java.util.ArrayList;
import java.util.Date;


public class ChatClient extends AppCompatActivity implements View.OnClickListener {

    Context mContext;

    ImageButton sendButton;
    EditText messageText;
    RecyclerView messageList;

    RecyclerView.Adapter mAdapter = null;
    ArrayList<Message> messages = null;
    int in_index = 0;

    Toolbar toolbar;

    PopupWindow popupWindow;
    String friendName;

    RobotoTextView payButton;
    RobotoTextView cancelButton;
    FloatLabeledEditText transactionPin;
    ImageView imageView;
    TextView confirmText;

    String amount;
    String bot;

    Message message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_client);

        mContext = this;

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
        friendName = in.getStringExtra(getString(R.string.friend));

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
                    message = new Message("", messString, true, new Date());
                    messages.add(message);
                    messageList.scrollToPosition(messages.size() - 1);
                    mAdapter.notifyDataSetChanged();
                    sendMessage();
                    if (message.getMessage() != null & message.getMessage().length() > 8) {
                        bot = message.getMessage().substring(0, 8);
                        amount = message.getMessage().substring(8, message.getMessage().length());
                        if (bot.equalsIgnoreCase("FUSE PAY")) {

                            popupInit();

                        }
                    }
                    message = null;
                    messageText.setText("");

                }

                break;

            case R.id.accept_pay:
                popupWindow.dismiss();
                message = null;
                messageText.setText("");
                String str = "Congratulation! Amount " + amount + " successfully transferred" +
                        " to " + friendName;
                message = new Message("", str, true, new Date());
                messages.add(message);
                messageList.scrollToPosition(messages.size() - 1);
                mAdapter.notifyDataSetChanged();
                sendMessage();
                break;

            case R.id.cancel_pay:
                popupWindow.dismiss();
                break;
            default:
                break;
        }
    }

    private void sendMessage() {

        String[] incoming = {"Hey, How's it going?",
                "Super! You have to pay Rs.2000 for yesterday lunch. Just a reminder :-)",
                "Anytime.", "",
                "Great, I received the amount in a blink",
                "Ok, see you then!"};

        if (in_index < incoming.length) {
            Message message = new Message("John", incoming[in_index], false, new Date());
            messages.add(message);
            in_index++;
            messageList.scrollToPosition(messages.size() - 1);
            mAdapter.notifyDataSetChanged();
        }
    }


    public void popupInit() {


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup, (ViewGroup) findViewById(R.id.popUpPay));
        popupWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 20, 20);


        payButton = (RobotoTextView) layout.findViewById(R.id.accept_pay);
        cancelButton = (RobotoTextView) layout.findViewById(R.id.cancel_pay);
        transactionPin = (FloatLabeledEditText) layout.findViewById(R.id.confirm_transaction_pin);
        imageView = (ImageView) layout.findViewById(R.id.imageView1);
        confirmText = (TextView) layout.findViewById(R.id.confirmTitle);


        payButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        confirmText.setText("PAY AMOUNT " + amount + " TO " + friendName);

    }

}
