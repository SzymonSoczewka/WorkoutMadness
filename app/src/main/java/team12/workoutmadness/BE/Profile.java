package team12.workoutmadness.BE;

public class Profile {

    private int id;
    private int height;
    private int weight;
    private int arm;
    private int chest;
    private int hips;
    private int waist;
    private int thighs;
    private int calves;

    public Profile(int id) {
        this.id = id;
    }
    public int getArm() {
        return arm;
    }

    public void setArm(int arm) {
        this.arm = arm;
    }

    public int getChest() {
        return chest;
    }

    public void setChest(int chest) {
        this.chest = chest;
    }

    public int getHips() {
        return hips;
    }

    public void setHips(int hips) {
        this.hips = hips;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getThighs() {
        return thighs;
    }

    public void setThighs(int thighs) {
        this.thighs = thighs;
    }

    public int getCalves() {
        return calves;
    }

    public void setCalves(int calves) {
        this.calves = calves;
    }
    public int getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
