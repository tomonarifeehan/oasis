package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ReportDetailsActivity extends AppCompatActivity {

    User user;
    String type;
    WaterSourceReport wsReport = new WaterSourceReport();
    WaterPurityReport wpReport = new WaterPurityReport();

    private static final String TAG = "ReportDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        TextView reportNumber = (TextView) findViewById(R.id.val_reportNum);
        TextView reportDate = (TextView) findViewById(R.id.val_reportDate);
        TextView reportLocation = (TextView) findViewById(R.id.val_reportLoc);
        TextView reportWaterConditionLabel = (TextView) findViewById(R.id.label_reportWC);
        TextView reportWaterCondition = (TextView) findViewById(R.id.val_reportWC);
        TextView reportWaterTypeLabel = (TextView) findViewById(R.id.label_reportWT);
        TextView reportWaterType = (TextView) findViewById(R.id.val_reportWT);
        TextView reportAuthor = (TextView) findViewById(R.id.val_reportAuth);
        TextView reportContaminantPPM = (TextView) findViewById(R.id.val_reportContaminantPPM);
        TextView reportContaminantPPMLabel = (TextView) findViewById(R.id.label_reportContaminantPPM);
        TextView title = (TextView) findViewById(R.id.label_report);

        user = (User) getIntent().getSerializableExtra("USER");
        type = (String) getIntent().getExtras().getString("TYPE");
        if (type.equals("source")) {
            wsReport = (WaterSourceReport) getIntent().getExtras().getSerializable("REPORT");
            title.setText("Water Source Report #" + Integer.toString(wsReport.getReportNumber()));
            reportNumber.setText(Integer.toString(wsReport.getReportNumber()));
            reportDate.setText(wsReport.getDate().toString());
            reportLocation.setText(wsReport.getLocation());
            reportWaterCondition.setText(wsReport.getWaterCondition().toString());
            reportWaterType.setText(wsReport.getWaterType().toString());
            reportAuthor.setText(user.getName());
        } else {
            wpReport = (WaterPurityReport) getIntent().getExtras().getSerializable("REPORT");
            title.setText("Water Purity Report #" + Integer.toString(wpReport.getReportNumber()));
            reportNumber.setText(Integer.toString(wpReport.getReportNumber()));
            reportDate.setText(wpReport.getDate().toString());
            reportLocation.setText(wpReport.getLocation());
            reportWaterConditionLabel.setText("Overall Condition");
            reportWaterCondition.setText(wpReport.getOverallCondition().toString());
            reportWaterTypeLabel.setText("Virus PPM");
            reportWaterType.setText(Integer.toString(wpReport.getVirusPPM()));
            reportContaminantPPM.setVisibility(View.VISIBLE);
            reportContaminantPPMLabel.setVisibility(View.VISIBLE);
            reportContaminantPPM.setText(Integer.toString(wpReport.getContaminantPPM()));
            reportAuthor.setText(user.getName());
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_report_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportDetailsActivity.this, ViewReportActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
                ReportDetailsActivity.this.finish();
            }
        });
    }
}
