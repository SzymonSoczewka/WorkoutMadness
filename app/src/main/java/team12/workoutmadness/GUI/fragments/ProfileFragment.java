package team12.workoutmadness.GUI.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;

import team12.workoutmadness.BE.Profile;
import team12.workoutmadness.BLL.BLLManager;
import team12.workoutmadness.GUI.activities.LoginActivity;
import team12.workoutmadness.R;

public class ProfileFragment extends Fragment {
    private static final String TAG = "PROFILE_FRAGMENT";
    private NumberPicker heightPicker, weightPicker;
    private int height,weight;
    private EditText armInput,chestInput,hipsInput,waistInput,thighsInput,calvesInput;
    private TextView txtPBmi,nameTag;
    private Button btnSave;
    private Button btnSignout;
    private BLLManager manager = BLLManager.getInstance(null);
    private TextView bmiDetails;
    private Profile profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        profile = manager.getProfile();
        setViews(view);
        setButtons();
        loadProfile();
        setPickers();
        calculateBMI();

        return view;
    }

    private void setButtons() {
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
    }

    private void signOut() {
        final FirebaseAuth mAuth = manager.getFirebaseAuth();
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void loadProfile(){
        if(manager.getProfile() != null) {
            height = profile.getHeight();
            weight = profile.getWeight();
            heightPicker.setValue(height);
            weightPicker.setValue(weight);
            armInput.setText(String.valueOf(profile.getArm()));
            chestInput.setText(String.valueOf(profile.getChest()));
            hipsInput.setText(String.valueOf(profile.getHips()));
            waistInput.setText(String.valueOf(profile.getWaist()));
            thighsInput.setText(String.valueOf(profile.getThighs()));
            calvesInput.setText(String.valueOf(profile.getCalves()));
            calculateBMI();
        } else {
            weight = 70;
            height = 170;
            calculateBMI();
        }
    }
    private void saveProfile() {
        if(profile != null) {
            setProfileAttributes(profile);
            manager.updateProfile(profile);
        }
        else {
            profile = new Profile(1);
            setProfileAttributes(profile);
            manager.addProfile(profile);
        }
    }

    private void setProfileAttributes(Profile profile) {
        profile.setHeight(heightPicker.getValue());
        profile.setWeight(weightPicker.getValue());
        System.out.println("pickers: "+heightPicker.getValue()+" "+weightPicker.getValue());
        profile.setArm(Integer.parseInt(textOrZero(armInput.getText().toString())));
        profile.setChest(Integer.parseInt(textOrZero(chestInput.getText().toString())));
        profile.setHips(Integer.parseInt(textOrZero(hipsInput.getText().toString())));
        profile.setWaist(Integer.parseInt(textOrZero(waistInput.getText().toString())));
        profile.setThighs(Integer.parseInt(textOrZero(thighsInput.getText().toString())));
        profile.setCalves(Integer.parseInt(textOrZero(calvesInput.getText().toString())));
    }

    private void setViews(View view) {
        heightPicker = view.findViewById(R.id.height_picker);
        weightPicker = view.findViewById(R.id.weight_picker);
        txtPBmi = view.findViewById(R.id.txtPBmi);
        btnSignout = view.findViewById(R.id.btnSignout);
        nameTag = view.findViewById(R.id.nameTag);
        nameTag.setText(manager.getFirebaseUsername());
        bmiDetails = view.findViewById(R.id.bmi_details);
        btnSave = view.findViewById(R.id.btn_save_profile);
        armInput = view.findViewById(R.id.arm_input);
        chestInput = view.findViewById(R.id.chest_input);
        hipsInput = view.findViewById(R.id.hips_input);
        waistInput = view.findViewById(R.id.waist_input);
        thighsInput = view.findViewById(R.id.thighs_input);
        calvesInput = view.findViewById(R.id.calves_input);
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
    }

    private void calculateBMI() {
        double heightInM = height / 100.0;
        double result = (weight / Math.pow(heightInM, 2));
        DecimalFormat bmiFormat = new DecimalFormat("###.###");
        txtPBmi.setText(bmiFormat.format(result));
        setDescription(result);
    }

    @SuppressLint("SetTextI18n")
    private void setDescription(double result) {

        if(result<16){
            bmiDetails.setText("STARVATION");
            bmiDetails.setTextColor(Color.parseColor("#B40503"));
        }
         else if(result >= 16  && result <= 16.99) {
            bmiDetails.setText("EMACIATION");
            bmiDetails.setTextColor(Color.parseColor("#C51912"));
        }
         else if(result >= 17 && result <= 18.49) {
            bmiDetails.setText("UNDERWEIGHT");
            bmiDetails.setTextColor(Color.parseColor("#D24031"));
        }
         else if(result >= 18.5 && result <24.99){
            bmiDetails.setText("NORMAL WEIGHT");
            bmiDetails.setTextColor(Color.parseColor("#03B24A"));
        } else if(result >= 25 && result <=29.99) {
            bmiDetails.setText("OVERWEIGHT");
            bmiDetails.setTextColor(Color.parseColor("#D95440"));
        }
         else if(result >= 30 && result <= 34.99) {
            bmiDetails.setText("CLASS 1 OBESITY");
            bmiDetails.setTextColor(Color.parseColor("#D24031"));
        }
         else if(result >= 35 && result <= 39.99) {
            bmiDetails.setText("CLASS 2 OBESITY");
            bmiDetails.setTextColor(Color.parseColor("#C51912"));
        }
         else{
            bmiDetails.setText("CLASS 3 OBESITY");
            bmiDetails.setTextColor(Color.parseColor("#B40503"));
         }
    }

    private String textOrZero(String string){
        if(!string.isEmpty())
            return string;
            else return "0";
    }
}
