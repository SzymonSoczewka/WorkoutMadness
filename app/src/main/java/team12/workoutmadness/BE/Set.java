package team12.workoutmadness.BE;

import java.io.Serializable;

public class Set implements Serializable {
    private Double weight;
    private Integer reps;
    public Set(Integer reps){
        this.reps = reps;
    }
    public Set(){}
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }
}
