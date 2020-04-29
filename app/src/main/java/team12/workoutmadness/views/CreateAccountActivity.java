package team12.workoutmadness.views;

import android.content.Intent;
import android.os.Bundle;
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

public class CreateAccountActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText email;
    EditText name;
    EditText password;
    EditText password2;

    Button btnCancel;
    Button btnCreate;

    Toast tstSuccess;
    Toast tstFail;
    Toast tstEmpty;
    Toast tstNoMatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editEmail);
        name = findViewById(R.id.editName);
        password = findViewById(R.id.editPassword);
        password2 = findViewById(R.id.editPassword2);

        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnCreate);

        SetUpToasts();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryCreate();
            }
        });
    }

    private void tryCreate() {
        if(email.getText().toString().isEmpty() || name.getText().toString().isEmpty() || password.getText().toString().isEmpty() || password2.getText().toString().isEmpty()){
            tstEmpty.show();
        } else {
            if(password.getText().toString().equals(password2.getText().toString())){
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            tstSuccess.show();
                            finish();
                        } else {
                            tstFail.show();
                        }
                    }
                });
            } else {
                tstNoMatch.show();
            }
        }
    }

    private void SetUpToasts() {
        tstSuccess = Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT);
        tstFail = Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);
        tstEmpty = Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT);
        tstNoMatch = Toast.makeText(this, "The password is not equal", Toast.LENGTH_SHORT);
    }


}
