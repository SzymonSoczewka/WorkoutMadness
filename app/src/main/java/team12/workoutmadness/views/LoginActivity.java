package team12.workoutmadness.views;

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

import team12.workoutmadness.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN_ACTIVITY";

    private FirebaseAuth mAuth;

    EditText email;
    EditText password;
    Button btnLogin;
    Button btnSignup;

    Toast tstSuccess;
    Toast tstFail;
    Toast tstEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        CreateToasts();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString();
                String p = password.getText().toString();
                if(!e.isEmpty() && !p.isEmpty())
                    TryLogin(e, p);
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

    private boolean TryLogin(String email, String password) {
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
        return false;
    }

    private void SignIn() {
        tstSuccess.show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void CreateToasts() {
        tstSuccess = Toast.makeText(this, "Successfully signed in", Toast.LENGTH_SHORT);
        tstFail = Toast.makeText(this, "Wrong password or username", Toast.LENGTH_SHORT);
        tstEmpty = Toast.makeText(this, "Please fill in both fields and try again", Toast.LENGTH_SHORT);
    }
}
