package com.akshaykant.com.fuse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.akshaykant.com.fuse.font.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    RobotoTextView digitizeButton;

    @BindView(R.id.card_number)
    EditText cardNumber;

    @BindView(R.id.card_number_month)
    EditText c_month;

    @BindView(R.id.card_number_year)
    EditText c_year;

    @BindView(R.id.card_number_cvv)
    EditText c_cvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        mContext = this;

        /*Butterknife Binding*/
        ButterKnife.bind(this);

        digitizeButton = (RobotoTextView) findViewById(R.id.digitize_button);

        digitizeButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.digitize_button) {

            cardNumber.getText().toString();
            c_month.getText().toString();
            c_year.getText().toString();
            c_cvv.getText().toString();

            Intent intent = new Intent(mContext, PinActivity.class);
            //intent.putExtra("activity", "PinActivity");
            startActivity(intent);
            finish();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
