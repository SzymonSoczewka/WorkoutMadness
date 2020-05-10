package team12.workoutmadness.DAL;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import team12.workoutmadness.BE.Day;
import team12.workoutmadness.BE.Workout;

public class FirebaseDB {
    private Day selectedDay = null;
    private Workout currentWorkout = null;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void setWorkout(Workout workout) {
        //currentWorkout = workout;
        db.collection("workouts").add(workout)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("FirebaseDB", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FirebaseDB", "Error adding document", e);
                    }
                });
    }
    public Workout getCurrentWorkout(){
        final List<Workout> workouts = new ArrayList<>();
        db.collection("workouts").whereEqualTo("userID",firebaseAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("FirebaseDB", "Listen failed.", e);
                            return;
                        }


                        for(DocumentChange dc: value.getDocumentChanges()){
                            DocumentSnapshot documentSnapshot = dc.getDocument();
                            System.out.println("DZIALA TO?");
                            workouts.add(documentSnapshot.toObject(Workout.class));
                        }
                        Log.d("FirebaseDB", "Current size: " + workouts.size());
                    }
                });
        if(workouts.size()>0)
        return workouts.get(0);
        else return null;
    }

    public void setSelectedDay(Day day) {
        selectedDay = day;
    }

    public void updateDay(Day day) {
        currentWorkout.updateDay(day);
    }

    public Day getSelectedDay() {
        return selectedDay;
    }

    public FirebaseAuth getFirebaseAuth(){
       return  firebaseAuth;
    }
    public FirebaseUser getFirebaseUser(){
        return firebaseAuth.getCurrentUser();
    }

}
