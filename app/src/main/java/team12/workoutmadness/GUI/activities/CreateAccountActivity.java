package team12.workoutmadness.GUI.activities;

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

import team12.workoutmadness.BLL.BLLManager;
import team12.workoutmadness.R;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth mAuth = BLLManager.getInstance(null).getFirebaseAuth();
    private EditText emailInput, passwordInput, password2Input;
    private Button btnCancel,btnCreate;
    private Toast tstSuccess,tstFail,tstEmpty,tstNoMatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        setViews();
        setUpToasts();
        setButtons();
    }


    //Getting access to .xml elements
    private void setViews() {
        emailInput = findViewById(R.id.editEmail);
        passwordInput = findViewById(R.id.editPassword);
        password2Input = findViewById(R.id.editPassword2);
        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnCreate);
    }
    //This method sets buttons behaviour
    private void setButtons() {
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
    //This method will try to create new user
    private void tryCreate() {
        if(fieldIsEmpty(emailInput) || fieldIsEmpty(passwordInput) || fieldIsEmpty(password2Input))
            tstEmpty.show();
         else {
             String password = passwordInput.getText().toString();
             String password2 = password2Input.getText().toString();

            if(password.equals(password2)){
                String email = emailInput.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).
                        addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
            } else
                tstNoMatch.show();
        }
    }
    //This method sets up toast for later display
    private void setUpToasts() {
        tstSuccess = Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT);
        tstFail = Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT);
        tstEmpty = Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT);
        tstNoMatch = Toast.makeText(this, "The password is not equal", Toast.LENGTH_SHORT);
    }

    //Simple method to check if input filed is empty
    private boolean fieldIsEmpty(EditText et){
        return !et.getText().toString().isEmpty();
    }

}
