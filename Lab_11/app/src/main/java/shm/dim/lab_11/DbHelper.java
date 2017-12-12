package shm.dim.lab_11;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentsDb.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS FACULTY ( "
                + "IDFACULTY       INTEGER PRIMARY KEY, "
                + "FACULTY         TEXT    UNIQUE, "
                + "DEAN            TEXT    NOT NULL, "
                + "OFFICETIMETABLE TEXT    NOT NULL);"
        );

        db.execSQL("CREATE TABLE IF NOT EXISTS GROUP_ ("
                + "IDGROUP INTEGER PRIMARY KEY, "
                + "FACULTY TEXT    NOT NULL, "
                + "COURSE  INTEGER CHECK (COURSE > 0 AND COURSE < 7), "
                + "NAME    TEXT    NOT NULL, "
                + "HEAD    TEXT    DEFAULT(0), "
                + "FOREIGN KEY(FACULTY) REFERENCES FACULTY(FACULTY) "
                + "ON DELETE CASCADE ON UPDATE CASCADE);"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS STUDENT ("
                + "IDGROUP   INTEGER NOT NULL, "
                + "IDSTUDENT INTEGER PRIMARY KEY, "
                + "NAME      TEXT    NOT NULL, "
                + "BIRTHDATE DATE    NOT NULL, "
                + "ADDRESS   TEXT    NOT NULL, "
                + "FOREIGN KEY(IDGROUP) REFERENCES GROUP_(IDGROUP) "
                + "ON DELETE CASCADE ON UPDATE CASCADE);"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS SUBJECT ("
                + "IDSUBJECT INTEGER PRIMARY KEY, "
                + "SUBJECT   TEXT    NOT NULL);"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS PROGRESS ("
                + "IDSTUDENT INTEGER NOT NULL, "
                + "IDSUBJECT INTEGER NOT NULL, "
                + "EXAMDATE  DATE    NOT NULL, "
                + "MARK      INTEGER CHECK (MARK >= 0 AND MARK <= 10), "
                + "TEACHER   TEXT    NOT NULL, "
                + "FOREIGN KEY(IDSUBJECT) REFERENCES SUBJECT(IDSUBJECT) "
                + "ON DELETE CASCADE ON UPDATE CASCADE, "
                + "FOREIGN KEY(IDSTUDENT) REFERENCES STUDENT(IDSTUDENT) "
                + "ON DELETE CASCADE ON UPDATE CASCADE);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE FACULTY;");
        db.execSQL("DROP TABLE GROUP_");
        db.execSQL("DROP TABLE STUDENT;");
        db.execSQL("DROP TABLE SUBJECT");
        db.execSQL("DROP TABLE PROGRESS;");
        onCreate(db);
    }


    public void addFaculty(SQLiteDatabase db, int idFaculty, String nameFaculty, String dean, String timetable ) {
        ContentValues cv = new ContentValues();
        cv.put("IDFACULTY", idFaculty);
        cv.put("FACULTY", nameFaculty);
        cv.put("DEAN", dean);
        cv.put("OFFICETIMETABLE", timetable);
        db.insert("FACULTY", null, cv);
        cv.clear();
    }

    public void addGroup(SQLiteDatabase db, int idGroup, String faculty, int course, String name, String head) {
        ContentValues cv = new ContentValues();
        cv.put("IDGROUP", idGroup);
        cv.put("FACULTY", faculty);
        cv.put("COURSE", course);
        cv.put("NAME", name);
        cv.put("HEAD", head);
        db.insert("GROUP_", null, cv);
        cv.clear();
    }

    public void addStudent(SQLiteDatabase db, int idStudent, int idGroup, String name, String birthDate, String address) {
        ContentValues cv = new ContentValues();
        cv.put("IDSTUDENT", idStudent);
        cv.put("IDGROUP", idGroup);
        cv.put("NAME", name);
        cv.put("BIRTHDATE", birthDate);
        cv.put("ADDRESS", address);
        db.insert("STUDENT", null, cv);
        cv.clear();
    }

    public void addSubject(SQLiteDatabase db, int idSubject, String name){
        ContentValues cv = new ContentValues();
        cv.put("IDSUBJECT", idSubject);
        cv.put("SUBJECT", name);
        db.insert("SUBJECT", null, cv);
        cv.clear();
    }

    public void addProgress(SQLiteDatabase db, int idStudent, int idSubject ,String examDate, int mark, String teacher){
        ContentValues cv = new ContentValues();
        cv.put("IDSTUDENT", idStudent);
        cv.put("IDSUBJECT", idSubject);
        cv.put("EXAMDATE", examDate);
        cv.put("MARK", mark);
        cv.put("TEACHER", teacher);
        db.insert("PROGRESS", null, cv);
        cv.clear();
    }
}