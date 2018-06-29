package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewReportActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner viewingOptionSpinner;
    private SpinnerAdapter adaptReportOptions;
    private ListView listView;
    private BottomNavigationView bottomNav;

    User user = new User();
    private List<String> reportOptions = new ArrayList<>();
    private List<WaterSourceReport> sourceReportList = new ArrayList<>();
    private List<WaterPurityReport> purityReportList = new ArrayList<>();
    private List<String> sourceReportTitles = new ArrayList<>();
    private List<String> purityReportTitles = new ArrayList<>();
    private List<String> sourceReportNames = new ArrayList<>();
    private List<String> purityReportNames = new ArrayList<>();

    private static final String TAG = "ViewReportActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        user = (User) getIntent().getSerializableExtra("USER");

        toolbarSetup();
        spinnerSetup();
        listViewSetup();
        bottomNav();

        clearReportLists();
        getMySourceReports();
    }

    public void toolbarSetup() {
        toolbar = (Toolbar) findViewById(R.id.view_ws_toolbar);
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

    public void spinnerSetup() {
        reportOptions.addAll(Arrays.asList("Water Source Reports", "Water Purity Reports"));
        viewingOptionSpinner = (Spinner) findViewById(R.id.spinner_report_options);
        adaptReportOptions = new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.reportOptions);
        viewingOptionSpinner.setAdapter(adaptReportOptions);
        viewingOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (viewingOptionSpinner.getSelectedItem().toString().equals("Water Source Reports")) {
                    clearReportLists();
                    int tabId = getSelectedItem(bottomNav);
                    if (tabId == R.id.action_my_reports) {
                        getMySourceReports();
                    } else {
                        getAllSourceReports();
                    }
                } else if (viewingOptionSpinner.getSelectedItem().toString().equals("Water Purity Reports")) {
                    clearReportLists();
                    int tabId = getSelectedItem(bottomNav);
                    if (tabId == R.id.action_my_reports) {
                        getMyPurityReports();
                    } else {
                        getAllPurityReports();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }
        });
    }

    public void listViewSetup() {
        listView = (ListView) findViewById(R.id.report_list);
    }

    public void bottomNav() {
        bottomNav = (BottomNavigationView) findViewById(R.id.view_ws_bottom_navbar);
        bottomNav.setOnNavigationItemSelectedListener(
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_all_reports:
                        Toast.makeText(ViewReportActivity.this, "All Reports", Toast.LENGTH_SHORT).show();
                        clearReportLists();
                        if (viewingOptionSpinner.getSelectedItemPosition() == 0) {
                            getAllSourceReports();
                        }
                        viewingOptionSpinner.setSelection(0);
                        break;
                    case R.id.action_empty:
                        Intent intent = new Intent(ViewReportActivity.this, HistoricalReportParametersActivity.class);
                        intent.putExtra("USER", user);
                        startActivity(intent);
                        ViewReportActivity.this.finish();
                        break;
                    case R.id.action_my_reports:
                        Toast.makeText(ViewReportActivity.this, "My Reports", Toast.LENGTH_SHORT).show();
                        clearReportLists();
                        if (viewingOptionSpinner.getSelectedItemPosition() == 0) {
                            getMySourceReports();
                        }
                        viewingOptionSpinner.setSelection(0);
                        break;
                    default:
                        break;
                }
                return true;
                }
            });
    }

    public void getMySourceReports() {
        clearReportLists();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("source_reports")
                        .orderByKey()
                        .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot snap : templateSnapshot.getChildren()){
                        WaterSourceReport wsReport = (WaterSourceReport) snap.getValue(WaterSourceReport.class);
                        sourceReportList.add(wsReport);
                        sourceReportTitles.add("" + wsReport.getReportNumber());
                        sourceReportNames.add("" + wsReport.getSubmittedBy());
                        ListAdapter adapter = new ArrayAdapter<String>(ViewReportActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, sourceReportTitles) {
                            @Override
                            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                                text1.setText("Water Source Report");
                                text2.setText("Report Number: " + sourceReportTitles.get(position)
                                        + "                        Submitted By: " + sourceReportNames.get(position)
                                );
                                return view;
                            }
                        };
                        listView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.toString());
            }
        });
    }

    public void getMyPurityReports() {
        clearReportLists();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("purity_reports")
                        .orderByKey()
                        .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot snap : templateSnapshot.getChildren()){
                        WaterPurityReport wpReport = (WaterPurityReport) snap.getValue(WaterPurityReport.class);
                        purityReportList.add(wpReport);
                        purityReportTitles.add("" + wpReport.getReportNumber());
                        purityReportNames.add("" + wpReport.getSubmittedBy());

                        ListAdapter adapter = new ArrayAdapter<String>(
                                ViewReportActivity.this, android.R.layout.simple_list_item_2,
                                android.R.id.text1, purityReportTitles) {

                            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                                text1.setText("Water Purity Report");
                                text2.setText("Report Number: " + purityReportTitles.get(position)
                                        + "                        Submitted By: " + purityReportNames.get(position)
                                );
                                return view;
                            }
                        };
                        listView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.toString());
            }
        });
    }

    public void getAllSourceReports() {
        clearReportLists();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("source_reports");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot snap : templateSnapshot.getChildren()){
                        WaterSourceReport wsReport = (WaterSourceReport) snap.getValue(WaterSourceReport.class);
                        sourceReportList.add(wsReport);
                        sourceReportTitles.add("" + wsReport.getReportNumber());
                        sourceReportNames.add("" + wsReport.getSubmittedBy());
                        ListAdapter adapter = new ArrayAdapter<String>(ViewReportActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, sourceReportTitles) {
                            @Override
                            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                                text1.setText("Water Source Report");
                                text2.setText("Report Number: " + sourceReportTitles.get(position)
                                        + "                        Submitted By: " + sourceReportNames.get(position)
                                );
                                return view;
                            }
                        };
                        listView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.toString());
            }
        });
    }

    public void getAllPurityReports() {
        clearReportLists();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("purity_reports");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()) {
                    for(DataSnapshot snap : templateSnapshot.getChildren()){
                        WaterPurityReport wpReport = (WaterPurityReport) snap.getValue(WaterPurityReport.class);
                        purityReportList.add(wpReport);
                        purityReportTitles.add("" + wpReport.getReportNumber());
                        purityReportNames.add("" + wpReport.getSubmittedBy());
                        ListAdapter adapter = new ArrayAdapter<String>(
                                ViewReportActivity.this, android.R.layout.simple_list_item_2,
                                android.R.id.text1, purityReportTitles) {

                            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                                text1.setText("Water Purity Report");
                                text2.setText("Report Number: " + purityReportTitles.get(position)
                                              + "                        Submitted By: " + purityReportNames.get(position)
                                );
                                return view;
                            }
                        };
                        listView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.toString());
            }
        });
    }

    public void clearReportLists() {
        sourceReportList.clear();
        purityReportList.clear();

        sourceReportTitles.clear();
        purityReportTitles.clear();

        sourceReportNames.clear();
        purityReportNames.clear();
    }

    public int getSelectedItem(BottomNavigationView bottomNavigationView) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.isChecked()) {
                return menuItem.getItemId();
            }
        }
        return 0;
    }
}
