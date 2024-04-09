package models;

public class BegEx {

    String name,reps;

    public BegEx() {
    }

    public BegEx(String name, String reps) {
        this.name = name;
        this.reps = reps;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }
}
