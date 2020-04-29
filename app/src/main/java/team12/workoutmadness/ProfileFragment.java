package team12.workoutmadness;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class ProfileFragment extends Fragment {
    private static final String TAG = "PROFILE_FRAGMENT";
    private NumberPicker heightPicker, weightPicker, agePicker;
    private int height, weight, age, bmi;
    private TextView txtPBmi;
    private Button btnPSave;
    private Button btnSignout;

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

        heightPicker = (NumberPicker) view.findViewById(R.id.height_picker);
        weightPicker = (NumberPicker) view.findViewById(R.id.weight_picker);
        agePicker = (NumberPicker) view.findViewById(R.id.age_picker);
        txtPBmi = (TextView) view.findViewById(R.id.txtPBmi);
        btnSignout = view.findViewById(R.id.btnSignout);

        mAuth = FirebaseAuth.getInstance();

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        height = 172;
        weight = 67;
        age = 23;

        setPickers();
        calculateBMI();

        return view;
    }

    private void setPickers() {
        // Setting the height picker
        heightPicker.setMinValue(130);
        heightPicker.setMaxValue(220);
        heightPicker.setValue(height);
        NumberPicker.Formatter heightFormat = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value + " cm";
            }
        };
        heightPicker.setFormatter(heightFormat);
        heightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.i("Number", newVal + "");
                height = newVal;
                calculateBMI();
            }
        });

        // Setting the weight picker
        weightPicker.setMinValue(40);
        weightPicker.setMaxValue(150);
        weightPicker.setValue(weight);
        NumberPicker.Formatter weightFormat = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value + " kg";
            }
        };
        weightPicker.setFormatter(weightFormat);
        weightPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.i("Number", newVal + "");
                weight = newVal;
                calculateBMI();
            }
        });

        // Setting the age picker
        agePicker.setMinValue(10);
        agePicker.setMaxValue(80);
        agePicker.setValue(age);
        agePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.i("Number", newVal + "");
                age = newVal;
            }
        });
    }

    private void calculateBMI() {
        double heightInM = height / 100.0;
        double result = (weight / Math.pow(heightInM, 2));
        DecimalFormat bmiFormat = new DecimalFormat("###.###");
        txtPBmi.setText(bmiFormat.format(result));
    }

}
