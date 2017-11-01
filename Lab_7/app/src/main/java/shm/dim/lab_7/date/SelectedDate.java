package shm.dim.lab_7.date;

public class SelectedDate {
    private int year, month, day;

    public SelectedDate() {
    }

    public SelectedDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        return year + " " + month + " "  + day;
    }
}