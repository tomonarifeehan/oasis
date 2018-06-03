package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private User user;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabLogout;
    private FloatingActionButton fabAdmin;

    /**
     * OnCreate method required to load activity and loads everything that
     * is needed for the page while setting the view.
     *
     *
     * @param savedInstanceState Takes in a bundle that may contain an object
     *                           for use within this class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //noinspection ChainedMethodCall
//        user = (User) getIntent().getSerializableExtra("USER");

        dataSetup();
        uiSetup();
    }

    /**
     * OnClick method that will listen for clicks on the
     * view.
     *
     *
     * @param v Takes in a view.
     */
    @Override
    public void onClick(View v) {
    }

    /**
     * Sets up user information to be displayed
     *
     */
    private void dataSetup() {
        TextView nameField = (TextView) findViewById(R.id.name_field);
        TextView emailField = (TextView) findViewById(R.id.email_field);
        TextView accountTypeField = (TextView) findViewById(R.id.account_field);

//        nameField.setText("Name:  " + user.getName());
//        emailField.setText("Email:  " + user.getEmail());
        //noinspection ChainedMethodCall
//        accountTypeField.setText("Account Type:  " + user.getAccountType().toString());
    }

    /**
     * Sets up all of the necessary ui for this screen.
     *
     */
    private void uiSetup() {
        fabSetup();
        bottomBar();

    }

    /**
     * Fab setup
     *
     */
    private void fabSetup() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fabEdit = (FloatingActionButton) findViewById(R.id.fabEdit);
        fabLogout = (FloatingActionButton) findViewById(R.id.fabLogout);
        fabAdmin = (FloatingActionButton) findViewById(R.id.fabAdmin);

        fab.setOnClickListener(new View.OnClickListener() {
            boolean open = false;
            @Override
            public void onClick(View v) {
                if (!open) {
                    fabEdit.show();
                    fabLogout.show();
                    fabAdmin.show();
//                    if (user.getAccountType() == AccountType.ADMINISTRATOR) {
//                        fabAdmin.show();
//                    }
                    open = true;
                } else {
                    fabEdit.hide();
                    fabLogout.hide();
                    fabAdmin.hide();
                    open = false;
                }
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editUser = new Intent(HomeActivity.this, LoginActivity.class);
                editUser.putExtra("USER", user);
                startActivity(editUser);
                HomeActivity.this.finish();
            }
        });

        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                HomeActivity.this.finish();
            }
        });

        fabAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(adminIntent);
                HomeActivity.this.finish();
            }
        });
    }

    private void bottomBar() {
        BottomNavigationView botNavbar
                = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        botNavbar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_viewMap:
                                Intent mapIntent
                                        = new Intent(HomeActivity.this, LoginActivity.class);
                                mapIntent.putExtra("USER", user);
                                startActivity(mapIntent);
                                HomeActivity.this.finish();
                                break;
                            case R.id.action_newReport:
                                Intent newReportIntent
                                        = new Intent(HomeActivity.this, LoginActivity.class);
                                newReportIntent.putExtra("USER", user);
                                startActivity(newReportIntent);
                                HomeActivity.this.finish();
                                break;
                            case R.id.action_viewReports:
                                Intent viewReportIntent
                                        = new Intent(
                                        HomeActivity.this, LoginActivity.class);
                                viewReportIntent.putExtra("USER", user);
                                startActivity(viewReportIntent);
                                HomeActivity.this.finish();
                                break;
                        }
                        return true;
                    }

                });
    }
}
