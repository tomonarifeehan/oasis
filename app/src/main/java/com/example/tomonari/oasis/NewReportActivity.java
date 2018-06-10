package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;

public class NewReportActivity extends AppCompatActivity {
    User user = new User();
    private Spinner waterType;
    private Spinner waterCondition;
    private Spinner overallCondition;
    private EditText waterLocationLatitude;
    private EditText waterLocationLongitude;
    private EditText waterVirusPPM;
    private EditText waterContaminantPPM;
    private TextView reportTitle;
    private TextView contaminantTitle;
    private TextView waterTypeAndVirusPPMTitle;
    private TextView waterConditionAndOverallConditionTitle;
    private Switch switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        user = (User) getIntent().getSerializableExtra("USER");

        //Shared UI
        switchButton = (Switch) findViewById(R.id.report_switch);
        reportTitle = (TextView) findViewById(R.id.text_report_title);
        waterLocationLatitude = (EditText) findViewById(R.id.text_latitude);
        waterLocationLongitude = (EditText) findViewById(R.id.text_longitude);
        waterTypeAndVirusPPMTitle = (TextView) findViewById(R.id.title_water_type_and_virus_ppm);
        waterConditionAndOverallConditionTitle = (TextView) findViewById(R.id.title_water_condition_and_overall_condition);

        //Water Source Report UI
        waterType = (Spinner) findViewById(R.id.spinner_water_type);
        waterCondition = (Spinner) findViewById(R.id.spinner_water_condition);

        //Water Purity Report UI
        overallCondition = (Spinner) findViewById(R.id.spinner_overall_condition);
        waterVirusPPM = (EditText) findViewById(R.id.text_virus_ppm);
        contaminantTitle = (TextView) findViewById(R.id.title_contaminant_ppm);
        waterContaminantPPM = (EditText) findViewById(R.id.text_contaminant_ppm);

        SpinnerAdapter adaptWaterCondition = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                WaterSourceReport.legalConditions);
        SpinnerAdapter adaptWaterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                WaterSourceReport.legalTypes);
        SpinnerAdapter adaptOverallCondition = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                WaterPurityReport.legalOverallConditions);

        waterType.setAdapter(adaptWaterType);
        waterCondition.setAdapter(adaptWaterCondition);
        overallCondition.setAdapter(adaptOverallCondition);

        Toolbar toolbar = (Toolbar) findViewById(R.id.new_report_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewReportActivity.this, HomeActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                NewReportActivity.this.finish();
            }
        });

        switchButton.setChecked(false);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Updates UI for Water Purity Report
                    reportTitle.setText("Water Purity Report");

                    waterConditionAndOverallConditionTitle.setText("Overall Condition");
                    waterCondition.setVisibility(View.INVISIBLE);
                    overallCondition.setVisibility(View.VISIBLE);

                    waterTypeAndVirusPPMTitle.setText("Virus PPM");
                    waterType.setVisibility(View.INVISIBLE);
                    waterVirusPPM.setVisibility(View.VISIBLE);

                    contaminantTitle.setVisibility(View.VISIBLE);
                    waterContaminantPPM.setVisibility(View.VISIBLE);
                } else {
                    //Updates UI for Water Source Report
                    reportTitle.setText("Water Source Report");

                    waterConditionAndOverallConditionTitle.setText("Water Condition");
                    overallCondition.setVisibility(View.INVISIBLE);
                    waterCondition.setVisibility(View.VISIBLE);

                    waterTypeAndVirusPPMTitle.setText("Water Type");
                    waterVirusPPM.setVisibility(View.INVISIBLE);
                    waterType.setVisibility(View.VISIBLE);

                    contaminantTitle.setVisibility(View.INVISIBLE);
                    waterContaminantPPM.setVisibility(View.INVISIBLE);

                }
            }
        });
    }
}
