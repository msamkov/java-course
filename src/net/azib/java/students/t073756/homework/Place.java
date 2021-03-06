package net.azib.java.students.t073756.homework;

public class Place {

    private int start;
    private int end;

    public Place(int start) {
        this.start = start;
        this.end = start;
    }

    public void incrementEnd() {
        end++;
    }

    @Override
    public String toString() {
        return start + (end > start ? "-" + end : "");
    }
}
