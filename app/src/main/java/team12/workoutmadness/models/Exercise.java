package team12.workoutmadness.models;

import java.util.ArrayList;

import team12.workoutmadness.models.Set;

public class Exercise {
    private String name;
    private ArrayList<Set> sets;

    public Exercise(String name, ArrayList<Set> sets) {
        this.name = name;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // Map<Weight,Reps>
    public ArrayList<Set> getSets() {
        return sets;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }
}
