package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegNameActivity extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private EditText nameField;

    private static final String TAG = "RegNameActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_name);

        //Get User
        user = (User) getIntent().getSerializableExtra("USER");
        Log.d(TAG, user.toString());

        //EditText
        nameField = (EditText) findViewById(R.id.reg_text_name);

        //Buttons
        findViewById(R.id.cancel_button).setOnClickListener(this);
        findViewById(R.id.reg_button_continue).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.reg_button_continue) {
            user.setName(nameField.getText().toString());
            Log.d(TAG, user.toString());
            Intent intent = new Intent(this, RegEmailActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            this.finish();
        } else if (i == R.id.cancel_button) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            this.finish();
        }
    }
}
