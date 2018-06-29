package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoricalReportParametersActivity extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private Toolbar toolbar;
    private EditText latitude, longitude;
    private Spinner ppmSpinner, yearSpinner;
    private final List<String> ppmOptions = new ArrayList<>();
    private final List<Integer> yearOptions = new ArrayList<>();

    private static final String TAG = "HistReportParamActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_report_parameters);

        user = (User) getIntent().getSerializableExtra("USER");
        toolbar = (Toolbar) findViewById(R.id.historical_report_toolbar);
        latitude = (EditText) findViewById(R.id.text_latitude);
        longitude = (EditText) findViewById(R.id.text_longitude);
        ppmSpinner = (Spinner) findViewById(R.id.spinner_ppm);
        yearSpinner = (Spinner) findViewById(R.id.spinner_year);
        findViewById(R.id.submit_button).setOnClickListener(this);

        //Virus PPM Spinner
        ppmOptions.add("Virus PPM");
        ppmOptions.add("Contaminant PPM");
        SpinnerAdapter adaptPPM = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ppmOptions);
        ppmSpinner.setAdapter(adaptPPM);

        //Year Spinner
        for (int i = 0; i <= 10; i++) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            yearOptions.add(currentYear - i);
        }
        SpinnerAdapter adaptYear = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearOptions);
        yearSpinner.setAdapter(adaptYear);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(HistoricalReportParametersActivity.this, ViewReportActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            HistoricalReportParametersActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.submit_button) {
            String selectedLatitude = latitude.getText().toString();
            String selectedLongitude = longitude.getText().toString();
            String selectedPPM = ppmSpinner.getSelectedItem().toString();
            String selectedYear = yearSpinner.getSelectedItem().toString();
            Intent intent = new Intent(this, HistoricalReportActivity.class);
            intent.putExtra("USER", user);
            intent.putExtra("LATITUDE", selectedLatitude);
            intent.putExtra("LONGITUDE", selectedLongitude);
            intent.putExtra("PPM", selectedPPM);
            intent.putExtra("YEAR", selectedYear);
            startActivity(intent);
            HistoricalReportParametersActivity.this.finish();
        }
    }
}