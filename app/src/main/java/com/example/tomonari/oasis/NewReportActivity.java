package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class NewReportActivity extends AppCompatActivity implements View.OnClickListener {
    User user = new User();
    private Spinner waterType, waterCondition, overallCondition;
    private EditText waterLocationLatitude, waterLocationLongitude, waterVirusPPM, waterContaminantPPM;
    private TextView reportTitle, contaminantTitle, waterTypeAndVirusPPMTitle, waterConditionAndOverallConditionTitle;
    private Switch switchButton;
    private int wsCount, wpCount, overallWsCount, overallWpCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        user = (User) getIntent().getSerializableExtra("USER");
        overallWsCount = getOverallSourceCount();
        overallWpCount = getOverallPurityCount();

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

        SpinnerAdapter adaptWaterCondition = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalConditions);
        SpinnerAdapter adaptWaterType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterSourceReport.legalTypes);
        SpinnerAdapter adaptOverallCondition = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, WaterPurityReport.legalOverallConditions);

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
                Toast.makeText(NewReportActivity.this, "Source Report Created", Toast.LENGTH_SHORT).show();
            } else {
                WaterPurityReport wp = compileWaterPurityReport();
                addWaterPurityReport(wp);
                Toast.makeText(NewReportActivity.this, "Purity Report Created", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addWaterSourceReport(WaterSourceReport sourceReport) {
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_source_reports))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Integer.toString(getOverallSourceCount() + 1))
                .setValue(sourceReport)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Count c = new Count(getWaterSourceCount() + 1, getWaterPurityCount(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                        updateWaterSourceCount(c);
                        OverallCount oc = new OverallCount(getOverallSourceCount() + 1, getOverallPurityCount());
                        updateOverallSourceCount(oc);
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
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(Integer.toString(getOverallPurityCount() + 1))
                .setValue(purityReport)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Count c = new Count(getWaterSourceCount(), getWaterPurityCount() + 1, FirebaseAuth.getInstance().getCurrentUser().getUid());
                        updateWaterPurityCount(c);
                        OverallCount oc = new OverallCount(getOverallSourceCount(), getOverallPurityCount() + 1);
                        updateOverallPurityCount(oc);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    public WaterPurityReport compileWaterPurityReport() {
        int reportNumber = getOverallPurityCount() + 1;
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
        int reportNumber = getOverallSourceCount() + 1;
        Date currentDate = new Date();
        String location = waterLocationLatitude.getText().toString() + "," + waterLocationLongitude.getText().toString();
        WaterType type = (WaterType) waterType.getSelectedItem();
        WaterCondition condition = (WaterCondition) waterCondition.getSelectedItem();
        String submittedBy = user.getName();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return new WaterSourceReport(reportNumber, currentDate, location, type, condition, submittedBy, uid);
    }

    private int getWaterSourceCount() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query1 = reference.child(getString(R.string.dbnode_count))
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Count count = (Count) singleSnapshot.getValue(Count.class);
                    wsCount = count.getSource_count();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return wsCount;
    }

    private int getWaterPurityCount() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query1 = reference.child(getString(R.string.dbnode_count))
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Count count = (Count) singleSnapshot.getValue(Count.class);
                    wpCount = count.getPurity_count();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return wpCount;
    }

    private int getOverallSourceCount() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query1 = reference.child(getString(R.string.dbnode_overall_count));
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OverallCount count = (OverallCount) dataSnapshot.getValue(OverallCount.class);
                overallWsCount = count.getSource_count();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return overallWsCount;
    }
    private int getOverallPurityCount() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query1 = reference.child(getString(R.string.dbnode_overall_count));
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                OverallCount count = (OverallCount) dataSnapshot.getValue(OverallCount.class);
                overallWpCount = count.getPurity_count();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return overallWpCount;
    }

    private void updateWaterSourceCount(Count count) {
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_count))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(count)
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

    private void updateWaterPurityCount(Count count) {
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_count))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(count)
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

    private void updateOverallSourceCount(OverallCount count) {
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_overall_count))
                .setValue(count)
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
    private void updateOverallPurityCount(OverallCount count) {
        FirebaseDatabase.getInstance().getReference()
                .child(getString(R.string.dbnode_overall_count))
                .setValue(count)
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
}
