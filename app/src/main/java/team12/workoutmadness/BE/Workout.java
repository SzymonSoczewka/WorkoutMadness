package team12.workoutmadness.BE;

import java.io.Serializable;
import java.util.ArrayList;

public class Workout implements Serializable {


    private int id;
    private String name;
    private ArrayList<Day> days;
    public Workout(int id, String name, ArrayList<Day> days){
        this.id = id;
        this.name = name;
        this.days = days;
    }
    public Workout(){}

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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void updateDay(Day updatedDay){
        for(int i=0; i<days.size(); i++){
            if(days.get(i).getName().equals(updatedDay.getName())) {
                days.get(i).setName(updatedDay.getName());
                days.get(i).setExercises(updatedDay.getExercises());
                break;
            }
        }
    }
}
