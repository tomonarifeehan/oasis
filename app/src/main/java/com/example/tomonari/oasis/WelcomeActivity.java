package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.logo).setOnClickListener(this);

        GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.oasis);

    }

    @Override
    public void onClick(View v) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        this.finish();
    }
}