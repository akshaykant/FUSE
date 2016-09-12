package com.akshaykant.com.fuse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akshaykant.com.fuse.font.RobotoTextView;
import com.akshaykant.com.fuse.view.FloatLabeledEditText;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String TAG = MainActivity.class.getSimpleName();
    Context mContext;
    String mobileNum;
    String aadharNum;
    String otpNum;
    ProgressDialog pDialog;

    @BindView(R.id.mobile_number_layout)
    LinearLayout mobileNumberLayout;

    FloatLabeledEditText mobileNumber;

    @BindView(R.id.aadhar_number_layout)
    LinearLayout aadharNumberLayout;

    FloatLabeledEditText aadharNumber;

    @BindView(R.id.otp_layout)
    LinearLayout otpLayout;

    FloatLabeledEditText otp;

    /* RobotoTextView nextButton;
 */
    RobotoTextView onboardButton;

    RobotoTextView submitButton;

    RobotoTextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mContext = this;

        /*Butterknife Binding*/
        ButterKnife.bind(this);

        mobileNumber = (FloatLabeledEditText) findViewById(R.id.mobile_number);
        aadharNumber = (FloatLabeledEditText) findViewById(R.id.aadhar_number);
        otp = (FloatLabeledEditText) findViewById(R.id.otp);

        // nextButton = (RobotoTextView) findViewById(R.id.next_button);
        onboardButton = (RobotoTextView) findViewById(R.id.onboard_button);
        submitButton = (RobotoTextView) findViewById(R.id.submit_button);
        login = (RobotoTextView) findViewById(R.id.login);

        //nextButton.setOnClickListener(this);
        onboardButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

/*
        if (v.getId() == R.id.next_button) {

            mobileNumberLayout.setVisibility(View.GONE);
            //aadharNumberLayout.setVisibility(View.VISIBLE);
            otpLayout.setVisibility(View.GONE);
            //nextButton.setVisibility(View.GONE);
            onboardButton.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.GONE);

            mobileNum = mobileNumber.getText().toString();


        }
        else*/

        if (v.getId() == R.id.onboard_button) {
            mobileNumberLayout.setVisibility(View.GONE);
            aadharNumberLayout.setVisibility(View.GONE);
            otpLayout.setVisibility(View.VISIBLE);
            //nextButton.setVisibility(View.GONE);
            onboardButton.setVisibility(View.GONE);
            submitButton.setVisibility(View.VISIBLE);
            mobileNum = mobileNumber.getText().toString();
            aadharNum = aadharNumber.getText().toString();
            login.setVisibility(View.GONE);

            //Volley Request
            // Tag used to cancel the request
            String tag_json_obj = "onboard_json_obj_req";

            String url = "http://192.168.1.104/aadhar/authorise";

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Please Wait! OnBoarding you in...");
            pDialog.show();

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            pDialog.hide();

                            mobileNumberLayout.setVisibility(View.GONE);
                            aadharNumberLayout.setVisibility(View.GONE);
                            otpLayout.setVisibility(View.VISIBLE);
                            onboardButton.setVisibility(View.GONE);
                            submitButton.setVisibility(View.VISIBLE);
                            login.setVisibility(View.GONE);
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    // hide the progress dialog
                    pDialog.hide();

                    mobileNumberLayout.setVisibility(View.GONE);
                    aadharNumberLayout.setVisibility(View.GONE);
                    otpLayout.setVisibility(View.VISIBLE);
                    onboardButton.setVisibility(View.GONE);
                    submitButton.setVisibility(View.VISIBLE);
                    login.setVisibility(View.GONE);
                }
            }) {

                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("aadhar", "12345");
                    return params;
                }

            };

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        } else if (v.getId() == R.id.submit_button) {

            otpNum = otp.getText().toString();

            //Volley Request
            // Tag used to cancel the request
            String tag_json_obj = "otp_json_obj_req";

            String url = "http://192.168.1.104/aadhar/get_user_data";

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            pDialog.hide();

                            Intent intent = new Intent(mContext, CardDetailsActivity.class);

                            startActivity(intent);
                            finish();

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    // hide the progress dialog
                    pDialog.hide();
                    Intent intent = new Intent(mContext, CardDetailsActivity.class);
                    Toast.makeText(MainActivity.this, "Successful! Enter default card details.", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("otp", otpNum);

                    return params;
                }

            };

// Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}



