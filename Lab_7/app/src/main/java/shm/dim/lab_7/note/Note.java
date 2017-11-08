package shm.dim.lab_7.note;

public class Note {

    private String text, date;

    public Note() {
    }

    public Note(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return text + " " + date + "\n";
    }
}