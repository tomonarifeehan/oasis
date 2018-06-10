package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.Random;

public class NewReportActivity extends AppCompatActivity implements View.OnClickListener {
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
        findViewById(R.id.button_submit).setOnClickListener(this);

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

    public void onClick(View v) {
        int i = v.getId();
        final boolean reportBoolean = switchButton.isChecked();

        if (i == R.id.button_submit) {
            if (!reportBoolean) {
                WaterSourceReport ws = compileWaterSourceReport();
                addWaterSourceReport(ws);
            } else {
                WaterPurityReport wp = compileWaterPurityReport();
                addWaterPurityReport(wp);
            }
        }
    }

    public void addWaterSourceReport(WaterSourceReport sourceReport) {
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_source_reports))
                .child(Integer.toString(sourceReport.getReportNumber()))
                .setValue(sourceReport)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void addWaterPurityReport(WaterPurityReport purityReport) {
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_purity_reports))
                .child(Integer.toString(purityReport.getReportNumber()))
                .setValue(purityReport)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public WaterPurityReport compileWaterPurityReport() {
        int reportNumber = getWaterPurityCount() + 1;
        Date currentDate = new Date();
        String location = waterLocationLatitude.getText().toString() + "," + waterLocationLongitude.getText().toString();
        OverallCondition condition = (OverallCondition) overallCondition.getSelectedItem();
        int vPPM = Integer.parseInt(waterVirusPPM.getText().toString());
        int cPPM = Integer.parseInt(waterContaminantPPM.getText().toString());
        String submittedBy = user.getName();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return new WaterPurityReport(reportNumber, currentDate, location, condition, submittedBy, vPPM, cPPM, uid);
    }

    private WaterSourceReport compileWaterSourceReport() {

        int reportNumber = getWaterSourceCount() + 1;
        Date currentDate = new Date();
        String location = waterLocationLatitude.getText().toString() + "," + waterLocationLongitude.getText().toString();
        WaterType type = (WaterType) waterType.getSelectedItem();
        WaterCondition condition = (WaterCondition) waterCondition.getSelectedItem();
        String submittedBy = user.getName();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return new WaterSourceReport(reportNumber, currentDate, location, type, condition, submittedBy, uid);
    }

    private int getWaterSourceCount() {
        Random r = new Random();
        return r.nextInt(1000);
    }

    private int getWaterPurityCount() {
        Random r = new Random();
        return r.nextInt(1000);
    }
}
