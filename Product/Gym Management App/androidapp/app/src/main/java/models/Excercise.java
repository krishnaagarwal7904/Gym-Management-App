package models;

public class Excercise {

    String bodyPart,description,exercise;

    public Excercise() {
    }

    public Excercise(String bodyPart, String description, String exercise) {
        this.bodyPart = bodyPart;
        this.description = description;
        this.exercise = exercise;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
