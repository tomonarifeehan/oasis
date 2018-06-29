package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class HistoricalReportActivity extends AppCompatActivity {
    private User user;
    private String latitude, longitude, selectedPPM, selectedLocation;
    private int selectedYear;
    private GraphView graph;
    private TextView janText, febText, marText, aprText, mayText, junText, julText, augText, sepText, octText, novText, decText;

    private List<Integer> january = new ArrayList<>();
    private List<Integer> february = new ArrayList<>();
    private List<Integer> march = new ArrayList<>();
    private List<Integer> april = new ArrayList<>();
    private List<Integer> may = new ArrayList<>();
    private List<Integer> june = new ArrayList<>();
    private List<Integer> july = new ArrayList<>();
    private List<Integer> august = new ArrayList<>();
    private List<Integer> september = new ArrayList<>();
    private List<Integer> october = new ArrayList<>();
    private List<Integer> november = new ArrayList<>();
    private List<Integer> december = new ArrayList<>();

    private static final String TAG = "HistReportActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_report);

        user = (User) getIntent().getSerializableExtra("USER");
        latitude = getIntent().getExtras().getString("LATITUDE");
        longitude = getIntent().getExtras().getString("LONGITUDE");
        selectedPPM = getIntent().getExtras().getString("PPM");
        selectedYear = Integer.parseInt(getIntent().getExtras().getString("YEAR"));
        selectedLocation = latitude + "," + longitude;

        Toolbar toolbar = (Toolbar) findViewById(R.id.graph_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(HistoricalReportActivity.this, HistoricalReportParametersActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
            HistoricalReportActivity.this.finish();
            }
        });
        latitude = getIntent().getExtras().getString("LATITUDE");
        longitude = getIntent().getExtras().getString("LONGITUDE");
        selectedPPM = getIntent().getExtras().getString("PPM");
        selectedYear = Integer.parseInt(getIntent().getExtras().getString("YEAR"));
        selectedLocation = latitude + "," + longitude;

        graph = (GraphView) findViewById(R.id.graph);
        GraphView graphView = (GraphView) findViewById(R.id.graph);
        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Month");
        gridLabel.setVerticalAxisTitle("PPM");
        graphView.getViewport().setMaxX(13);
        graphView.getViewport().setXAxisBoundsManual(true);
        gridLabel.setVerticalAxisTitle(selectedPPM);

        //Month PPM Textviews
        janText = (TextView) findViewById(R.id.january);
        febText = (TextView) findViewById(R.id.february);
        marText = (TextView) findViewById(R.id.march);
        aprText = (TextView) findViewById(R.id.april);
        mayText = (TextView) findViewById(R.id.may);
        junText = (TextView) findViewById(R.id.june);
        julText = (TextView) findViewById(R.id.july);
        augText = (TextView) findViewById(R.id.august);
        sepText = (TextView) findViewById(R.id.september);
        octText = (TextView) findViewById(R.id.october);
        novText = (TextView) findViewById(R.id.november);
        decText = (TextView) findViewById(R.id.december);

        getAllPurityReports();
    }

    public void getAllPurityReports() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("purity_reports");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot templateSnapshot : dataSnapshot.getChildren()){
                    for(DataSnapshot snap : templateSnapshot.getChildren()){
                        WaterPurityReport wpReport = (WaterPurityReport) snap.getValue(WaterPurityReport.class);
                        Date databaseDate = wpReport.getDate();
                        if (selectedYear == databaseDate.getYear() && selectedLocation.equals("" + wpReport.getLocation())) {
                            if (databaseDate.toString().contains("Jan")) {
                                Log.d("JAN", "JAN");
                                january.add(wpReport.getVirusPPM());
                                january.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Feb")) {
                                Log.d("FEB", "FEB");
                                february.add(wpReport.getVirusPPM());
                                february.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Mar")) {
                                Log.d("MAR", "MAR");
                                march.add(wpReport.getVirusPPM());
                                march.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Apr")) {
                                Log.d("APR", "APR");
                                april.add(wpReport.getVirusPPM());
                                april.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("May")) {
                                Log.d("MAY", "MAY");
                                may.add(wpReport.getVirusPPM());
                                may.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Jun")) {
                                Log.d("JUN", "JUN");
                                june.add(wpReport.getVirusPPM());
                                june.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Jul")) {
                                Log.d("JUL", "JUL");
                                july.add(wpReport.getVirusPPM());
                                july.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Aug")) {
                                Log.d("AUG", "AUG");
                                august.add(wpReport.getVirusPPM());
                                august.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Sep")) {
                                Log.d("SEP", "SEP");
                                september.add(wpReport.getVirusPPM());
                                september.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Oct")) {
                                Log.d("OCT", "OCT");
                                october.add(wpReport.getVirusPPM());
                                october.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Nov")) {
                                Log.d("NOV", "NOV");
                                november.add(wpReport.getVirusPPM());
                                november.add(wpReport.getContaminantPPM());
                            } else if (databaseDate.toString().contains("Dec")) {
                                Log.d("DEC", "DEC");
                                december.add(wpReport.getVirusPPM());
                                december.add(wpReport.getContaminantPPM());
                            }
                        }
                    }
                }
                TextView graphParams = (TextView) findViewById(R.id.textview_parameters);
                graphParams.setText("\nLocation: " + latitude + ", " + longitude);
                janText.setText("January: " + calculateAverage(january));
                febText.setText("February: " + calculateAverage(february));
                marText.setText("March: " + calculateAverage(march));
                aprText.setText("April: " + calculateAverage(april));
                mayText.setText("May: " + calculateAverage(may));
                junText.setText("June: " + calculateAverage(june));
                julText.setText("July: " + calculateAverage(july));
                augText.setText("August: " + calculateAverage(august));
                sepText.setText("September: " + calculateAverage(september));
                octText.setText("October: " + calculateAverage(october));
                novText.setText("November: " + calculateAverage(november));
                decText.setText("December: " + calculateAverage(december));

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                    new DataPoint(0, calculateAverage(january)),
                    new DataPoint(1, calculateAverage(february)),
                    new DataPoint(2, calculateAverage(march)),
                    new DataPoint(3, calculateAverage(april)),
                    new DataPoint(4, calculateAverage(may)),
                    new DataPoint(5, calculateAverage(june)),
                    new DataPoint(6, calculateAverage(july)),
                    new DataPoint(7, calculateAverage(august)),
                    new DataPoint(8, calculateAverage(september)),
                    new DataPoint(9, calculateAverage(october)),
                    new DataPoint(10, calculateAverage(november)),
                    new DataPoint(11, calculateAverage(december))
                });
                graph.addSeries(series);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.toString());
            }
        });
    }

    public static double calculateAverage(List<Integer> list) {
        double sum = 0;
        if (list != null) {
            if (list.size() == 1) {
                return list.get(0);
            }
            for (Integer item : list) {
                sum += item;
            }
            return Math.round(sum / list.size());
        }
        return sum;
    }
}
