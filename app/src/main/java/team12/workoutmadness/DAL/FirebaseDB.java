package team12.workoutmadness.DAL;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseDB {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public FirebaseAuth getFirebaseAuth(){
       return  firebaseAuth;
    }
    public FirebaseUser getFirebaseUser(){
        return firebaseAuth.getCurrentUser();
    }

}
