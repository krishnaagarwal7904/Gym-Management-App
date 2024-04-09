package models;

public class Histrory {

    String id,date,time,plan;

    public Histrory() {
    }

    public Histrory(String id, String date, String time, String plan) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.plan = plan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
