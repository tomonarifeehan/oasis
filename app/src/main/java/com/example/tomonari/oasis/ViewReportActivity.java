package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ViewReportActivity extends AppCompatActivity {

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        user = (User) getIntent().getSerializableExtra("USER");

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_ws_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewReportActivity.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                ViewReportActivity.this.finish();
            }
        });
    }
}
