package shm.dim.lab_7.notes;

import java.util.ArrayList;

import shm.dim.lab_7.date.SelectedDate;

public class Notes {

    private ArrayList<Note> notes;

    public Notes() {
        notes = new ArrayList<Note>();
    }

    public Notes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public void setNotes(ArrayList<Note> studList){
        this.notes = notes;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void add (Note note) {
        notes.add(note);
    }

    public void remove (Note note) {
        notes.remove(note);
    }

    public Note findByDate(SelectedDate selectedDate) {
        for (Note n : notes)
            if (n.getDate().equals(selectedDate.toString()))
                return n;
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Note n : notes)
            result.append(n.toString()).append("\n");
        return result.toString();
    }
}