package team12.workoutmadness.GUI.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import team12.workoutmadness.R;

public class BodyPartsFragment extends Fragment {
    private Button btnAbs,btnArms,btnShoulders,btnBack,btnLegs;
    private Button btnHips,btnChest,btnExtra,btnButtocks;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.body_parts, container, false);
        setViews(view);
        setButtons();
        return view;
    }

    private void setViews(View view) {
         btnAbs = view.findViewById(R.id.btn_abs);
         btnArms = view.findViewById(R.id.btn_arms);
         btnShoulders = view.findViewById(R.id.btn_shoulders);
         btnBack = view.findViewById(R.id.btn_back);
         btnLegs = view.findViewById(R.id.btn_legs);
         btnButtocks = view.findViewById(R.id.btn_buttocks);
         btnHips = view.findViewById(R.id.btn_hips);
         btnChest = view.findViewById(R.id.btn_chest);
         btnExtra = view.findViewById(R.id.btn_extra);
    }

    private void setButtons() {
    btnAbs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                String ABS_URL = "https://www.freetrainers.com/exercise/musclegroup/abs/";
                openBrowser(ABS_URL);
        }
    });
    btnArms.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                String ARMS_URL = "https://www.freetrainers.com/exercise/musclegroup/arms/";
                openBrowser(ARMS_URL);
        }
    });
    btnShoulders.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                String SHOULDERS_URL = "https://www.freetrainers.com/exercise/musclegroup/shoulders/";
                openBrowser(SHOULDERS_URL);
        }
    });
    btnBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                String BACK_URL = "https://www.freetrainers.com/exercise/musclegroup/back/";
                openBrowser(BACK_URL);
        }
    });
    btnLegs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                String LEGS_URL = "https://www.freetrainers.com/exercise/musclegroup/legs/";
                openBrowser(LEGS_URL);
        }
    });
        btnButtocks.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                String BUTTOCKS_URL = "https://www.freetrainers.com/exercise/musclegroup/buttocks/";
                openBrowser(BUTTOCKS_URL);
        }
    });
        btnHips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String HIPS_URL = "https://www.freetrainers.com/exercise/musclegroup/hips/";
                openBrowser(HIPS_URL);
            }
        });
        btnChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CHEST_URL = "https://www.freetrainers.com/exercise/musclegroup/chest/";
                openBrowser(CHEST_URL);
            }
        });
        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EXTRA_EXERCISES_URL = "https://www.freetrainers.com/exercise/musclegroup/extra/";
                openBrowser(EXTRA_EXERCISES_URL);
            }
        });
    }
    private void openBrowser(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
