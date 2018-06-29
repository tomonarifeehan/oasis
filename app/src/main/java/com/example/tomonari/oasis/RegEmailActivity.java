package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegEmailActivity extends AppCompatActivity implements View.OnClickListener {
    User user;
    private EditText emailField;
    private static final String TAG = "RegEmailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_email);

        //Get User
        user = (User) getIntent().getSerializableExtra("USER");
        Log.d(TAG, user.toString());

        //EditText
        emailField = (EditText) findViewById(R.id.reg_text_email);

        //Buttons
        findViewById(R.id.cancel_button).setOnClickListener(this);
        findViewById(R.id.reg_button_continue).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.reg_button_continue) {
            user.setEmail(emailField.getText().toString());
            Log.d(TAG, user.toString());
            Intent intent = new Intent(this, RegPasswordActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            this.finish();
        }
    }
}
