package team12.workoutmadness.GUI.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import team12.workoutmadness.BLL.BLLManager;
import team12.workoutmadness.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN_ACTIVITY";



    EditText emailInput, passwordInput;
    Button btnLogin, btnSignup;
    Toast tstSuccess, tstFail, tstEmpty;
    FirebaseAuth mAuth = BLLManager.getInstance(null).getFirebaseAuth();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setViews();
        CreateToasts();
        setButtons();
    }


    //Getting access to .xml elements
    private void setViews() {
        emailInput = findViewById(R.id.editEmail);
        passwordInput = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
    }
    //This method configures button behaviour
    private void setButtons() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                if(!email.isEmpty() && !password.isEmpty())
                    TryLogin(email, password);
                else
                    tstEmpty.show();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
    //This method communicates with the database and validates is user's credentials are valid
    private void TryLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Sign in success");
                    SignIn();
                } else {
                    tstFail.show();
                }
            }
        });
    }
    //When user is successfully logged in, new Activity is opened
    private void SignIn() {
        tstSuccess.show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    //This method initializes different Toasts to notify user during his login process
    private void CreateToasts() {
        tstSuccess = Toast.makeText(this, "Successfully signed in", Toast.LENGTH_SHORT);
        tstFail = Toast.makeText(this, "Wrong password or username", Toast.LENGTH_SHORT);
        tstEmpty = Toast.makeText(this, "Please fill in both fields and try again", Toast.LENGTH_SHORT);
    }
}
