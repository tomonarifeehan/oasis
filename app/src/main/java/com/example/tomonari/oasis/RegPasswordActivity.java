package com.example.tomonari.oasis;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    User user;
    private EditText passwordField;
    private static final String TAG = "Registration";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_password);

        //Get User
        user = (User) getIntent().getSerializableExtra("USER");
        Log.d(TAG, user.toString());

        //EditText
        passwordField = (EditText) findViewById(R.id.reg_text_password);

        //Buttons
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);
        findViewById(R.id.reg_button_signup).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.reg_button_signup) {
            user.setPassword(passwordField.getText().toString());
            Log.d(TAG, user.toString());
            registerNewEmail(user.getEmail(), user.getPassword());
        }
    }

    public void registerNewEmail(final String email, String password){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            //Toast feedback.
                            Toast.makeText(RegPasswordActivity.this, "Successfully Registered",
                                    Toast.LENGTH_LONG).show();
                            //Send verification email.
                            sendVerificationEmail();
                            //Sign out new user.
                            FirebaseAuth.getInstance().signOut();
                            //Redirect the new user to the login screen.
                            redirectLoginScreen();
                        }
                        if (!task.isSuccessful()) {
                            //Toast feedback.
                            Toast.makeText(RegPasswordActivity.this, "Unable to Register",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void redirectLoginScreen(){
        Intent intent = new Intent(RegPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegPasswordActivity.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RegPasswordActivity.this, "Couldn't Verification Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
