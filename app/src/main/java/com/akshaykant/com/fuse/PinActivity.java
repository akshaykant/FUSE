package com.akshaykant.com.fuse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.akshaykant.com.fuse.font.RobotoTextView;
import com.akshaykant.com.fuse.view.FloatLabeledEditText;

import butterknife.ButterKnife;

public class PinActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    String walletPin;
    String confirmWalletPin;
    String transactionPin;
    String confirmTransactionPin;


    FloatLabeledEditText mWalletPin;
    FloatLabeledEditText mConfirmWalletPin;
    FloatLabeledEditText mTransactionPin;
    FloatLabeledEditText mConfirmTransactionPin;

    RobotoTextView confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        mContext = this;

        /*Butterknife Binding*/
        ButterKnife.bind(this);

        mWalletPin = (FloatLabeledEditText) findViewById(R.id.wallet_pin);
        mConfirmWalletPin = (FloatLabeledEditText) findViewById(R.id.confirm_wallet_pin);
        mTransactionPin = (FloatLabeledEditText) findViewById(R.id.transaction_pin);
        mConfirmTransactionPin = (FloatLabeledEditText) findViewById(R.id.confirm_transaction_pin);


        confirmButton = (RobotoTextView) findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm_button) {

            mWalletPin.getText().toString();
            mConfirmWalletPin.getText().toString();
            mTransactionPin.getText().toString();
            mConfirmTransactionPin.getText().toString();

            Intent intent = new Intent(mContext, ContactsActivity.class);
            //intent.putExtra("activity", "ContactsActivity");
            startActivity(intent);
            finish();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(mContext, CardDetailsActivity.class);
        startActivity(intent);
        finish();

    }
}
