package com.akshaykant.com.fuse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.akshaykant.com.fuse.adapter.MyArrayAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ContactsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "ContactsActivity";

    Toolbar toolbar;
    String[] names;

    ListView friendView;

    MyArrayAdapter myArrayAdapter = null;
    List<FriendInfo> friendInfoList = null;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mContext = this;

        // If you are using a ListView widget, then your activity should implement
        // the onItemClickListener. Then you should set the OnItemClickListener for
        // the ListView.
        friendView = (ListView) findViewById(R.id.friendListView);
        friendView.setOnItemClickListener(this);

        // Start the AsyncTask to process the Json string in the background and then initialize the listview

        FriendsProcessor mytask = new FriendsProcessor();

        mytask.execute("friendsjson.txt");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String pos = "" + adapterView.getItemIdAtPosition(position);
        String name = null;

        switch (pos) {
            case "0":
                name = "NISHTHA";
                break;
            case "1":
                name = "AYUSH";
                break;
            case "2":
                name = "AKSHAY";
                break;
            case "3":
                name = "PALAK";
                break;
            case "4":
                name = "RINGO";
                break;
            case "5":
                name = "PAUL";
                break;
            case "6":
                name = "JOHN";
                break;
            case "7":
                name = "GEORGE";
                break;

            default:
                name = "SEQUOIA";
                break;
        }
        Intent mIntent = new Intent(this, ChatClient.class);
        mIntent.putExtra(getString(R.string.friend), name);
        startActivity(mIntent);
    }

    // This class processes the Json string and converts it into a list of FriendInfo objects
    // We make use of the Gson library to do this automatically
    private void processFriendInfo(String infoString) {

        // Create a new Gson object
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        // Use the Gson library to automatically process the string and convert it into
        // the list of FriendInfo objects. The use of the library saves you the need for
        // writing your own code to process the Json string
        friendInfoList = new ArrayList<FriendInfo>();
        friendInfoList = Arrays.asList(gson.fromJson(infoString, FriendInfo[].class));
    }


    // This AsyncTask processes the Json string by reading it from a file in the assets folder and
    // then converts the string into a list of FriendInfo objects. You will also see the use of
    // a progress dialog to show that work is being processed in the background.

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

    public class FriendInfo {
        public int id;
        public String name;
        public String statusMsg;
        public String imageURL;
    }

    private class FriendsProcessor extends AsyncTask<String, Void, Integer> {

        ProgressDialog progressDialog;
        int delay = 2000; // ms

        public FriendsProcessor() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Show the progress dialog on the screen
            progressDialog = ProgressDialog.show(mContext, "Wait!", "It is Cloudy");
        }

        // This method is executed in the background and will return a result to onPostExecute
        // method. It receives the file name as input parameter.
        @Override
        protected Integer doInBackground(String... strings) {

            // Open an input stream to read the file
            InputStream inputStream;
            BufferedReader in;

            // this try/catch is used to create a simulated delay for doing the background
            // processing so that you can see the progress dialog on the screen. If the
            // data to be processed is large, then you don't need this.
            try {
                // Pretend downloading takes a long time
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Now we read the file, line by line and construct the
            // Json string from the information read in.
            try {

                /*This string[0] essentially is the set of strings that got delivered as parameters for the doInBackground.
                In this case, since we passed only one string, which is the name of the file, we only have one single parameter coming in,
                and that is accessed by specifying it as string[0].*/
                inputStream = mContext.getAssets().open(strings[0]);
                in = new BufferedReader(new InputStreamReader(inputStream));

                String readLine;
                StringBuffer buf = new StringBuffer();

                while ((readLine = in.readLine()) != null) {
                    buf.append(readLine);
                }

                //Convert the read  in information  to a JSON  string.
                String infoString = buf.toString();

                //now process the string  using  the method  that we  implemented in the previous exercise.
                processFriendInfo(infoString);

                if (null != in) {
                    in.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return (Integer) 1;
        }

        // This method will be executed on the main UI thread and can access the UI and update
        // the listview. We dismiss the progress dialog after updating the listview.

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            myArrayAdapter = new MyArrayAdapter(mContext, friendInfoList);
            friendView.setAdapter((ListAdapter) myArrayAdapter);

            progressDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            progressDialog.dismiss();
        }
    }
}
