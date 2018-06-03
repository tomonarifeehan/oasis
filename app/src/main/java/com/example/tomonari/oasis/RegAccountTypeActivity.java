package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class RegAccountTypeActivity extends AppCompatActivity implements View.OnClickListener {
    private final User user = new User();
    private Spinner accTypeSpinner;
    private static final String TAG = "Registration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_account_type);

        //Buttons
        findViewById(R.id.cancel_button).setOnClickListener(this);
        findViewById(R.id.reg_button_continue).setOnClickListener(this);

        //Spinner
        accTypeSpinner = (Spinner) findViewById(R.id.reg_spin_acctype);
        SpinnerAdapter adaptAcc = new ArrayAdapter(this, android.R.layout.simple_spinner_item, User.legalClass);
        accTypeSpinner.setAdapter(adaptAcc);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.reg_button_continue) {
            user.setAccountType((AccountType) accTypeSpinner.getSelectedItem());
            Log.d(TAG, user.toString());
            Intent intent = new Intent(this, RegNameActivity.class);
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
