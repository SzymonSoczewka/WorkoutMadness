package team12.workoutmadness.Entity;

public class Set {
    private Double weight;
    private Integer reps;
    public Set(Integer reps){
        this.reps = reps;
    }

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
