package team12.workoutmadness.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Workout implements Serializable {
    private String name;
    private ArrayList<Day> days;
    public Workout(String name, ArrayList<Day> days){
        this.name = name;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }
}
