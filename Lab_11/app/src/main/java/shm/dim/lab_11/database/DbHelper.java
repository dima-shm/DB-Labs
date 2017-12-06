package shm.dim.lab_11.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Random;

import static shm.dim.lab_11.database.DbSchema.*;

public class DbHelper extends SQLiteOpenHelper {

    String LOG_TAG = "MyLog";

    private static final String DATABASE_NAME = "StudentsDb.db";
    private static final int DATABASE_VERSION = 1;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS " + FacultyTable.TABLE_NAME + " ( "
                + FacultyTable.Columns.ID_FACULTY + " INTEGER PRIMARY KEY, "
                + FacultyTable.Columns.FACULTY + " TEXT NOT NULL, "
                + FacultyTable.Columns.DEAN + " TEXT NOT NULL, "
                + FacultyTable.Columns.OFFICE_TIME_TABLE + " TEXT NOT NULL);");

        database.execSQL("CREATE TABLE IF NOT EXISTS " + GroupTable.TABLE_NAME + " ( "
                + GroupTable.Columns.ID_GROUP + " INTEGER NOT NULL PRIMARY KEY, "
                + GroupTable.Columns.FACULTY + " TEXT NOT NULL, "
                + GroupTable.Columns.COURSE + " INTEGER NOT NULL CHECK (" + GroupTable.Columns.COURSE + " > 0 AND " + GroupTable.Columns.COURSE + " < 7), "
                + GroupTable.Columns.NAME + " TEXT NOT NULL, "
                + GroupTable.Columns.HEAD + " TEXT NOT NULL, "
                + "CONSTRAINT " + GroupTable.Columns.FK_CONSTRAINT + " FOREIGN KEY("
                + GroupTable.Columns.FACULTY + ") REFERENCES " + FacultyTable.TABLE_NAME + "("
                + FacultyTable.Columns.FACULTY + ") ON DELETE CASCADE ON UPDATE CASCADE);");

        database.execSQL("CREATE TABLE IF NOT EXISTS " + StudentTable.TABLE_NAME + " ( "
                + StudentTable.Columns.ID_STUDENT + " INTEGER NOT NULL PRIMARY KEY, "
                + StudentTable.Columns.ID_GROUP + " INTEGER NOT NULL, "
                + StudentTable.Columns.NAME + " TEXT NOT NULL, "
                + StudentTable.Columns.BIRTHDATE + "TEXT NOT NULL, "
                + StudentTable.Columns.ADDRESS + " TEXT NOT NULL, "
                + "CONSTRAINT " + StudentTable.Columns.FK_CONSTRAINT + " FOREIGN KEY("
                + StudentTable.Columns.ID_GROUP + ") REFERENCES " + GroupTable.TABLE_NAME + "("
                + GroupTable.Columns.ID_GROUP + ") ON DELETE CASCADE ON UPDATE CASCADE);");

        database.execSQL("CREATE TABLE IF NOT EXISTS " + SubjectTable.TABLE_NAME + " ( "
                + SubjectTable.Columns.ID_SUBJECT + " INTEGER NOT NULL PRIMARY KEY, "
                + SubjectTable.Columns.SUBJECT + " TEXT NOT NULL);");

        database.execSQL("CREATE TABLE IF NOT EXISTS " + ProgressTable.TABLE_NAME + " ( "
                + ProgressTable.Columns.ID_STUDENT + " INTEGER NOT NULL, "
                + ProgressTable.Columns.ID_SUBJECT + " INTEGER NOT NULL, "
                + ProgressTable.Columns.EXAMDATE + " TEXT NOT NULL, "
                + ProgressTable.Columns.MARK + " INTEGER NOT NULL, "
                + ProgressTable.Columns.TEACHER + " TEXT NOT NULL, "
                + "CONSTRAINT " + ProgressTable.Columns.FK_STUDENT_CONSTRAINT + " FOREIGN KEY("
                + ProgressTable.Columns.ID_STUDENT + ") REFERENCES " + StudentTable.TABLE_NAME + "("
                + StudentTable.Columns.ID_STUDENT+ ") ON DELETE CASCADE ON UPDATE CASCADE), "
                + "CONSTRAINT " + ProgressTable.Columns.FK_SUBJECT_CONSTRAINT + " FOREIGN KEY("
                + ProgressTable.Columns.ID_SUBJECT + ") REFERENCES " + SubjectTable.TABLE_NAME + "("
                + SubjectTable.Columns.ID_SUBJECT + ") ON DELETE CASCADE ON UPDATE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE " + FacultyTable.TABLE_NAME + ";");
        database.execSQL("DROP TABLE " + GroupTable.TABLE_NAME + ";");
        database.execSQL("DROP TABLE " + StudentTable.TABLE_NAME + ";");
        database.execSQL("DROP TABLE " + SubjectTable.TABLE_NAME + ";");
        database.execSQL("DROP TABLE " + ProgressTable.TABLE_NAME + ";");
        onCreate(database);
    }


    public void addFaculty( int id, String name, String dean, String timetable ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //faculty
        cv.put("IDFACULTY", id);
        cv.put("FACULTY", name);
        cv.put("DEAN", dean);
        cv.put("OFFICETIMETABLE", timetable);
        long rowID = db.insert("faculty", null, cv);
        if (rowID == -1) {
            Log.d(LOG_TAG,"Sorry, such ID already exists");
            // Log.d(LOG_TAG, "Sorry, empty field\n");
        } else {
            Log.d(LOG_TAG, "row faculty inserted, ID = " + rowID);
            // toast("row inserted, ID = " + rowID);
        }
        cv.clear();
    }

    public void addGroup(int id, String faculty, int course, String name, String head){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //group
        cv.put("IDGROUP", id);
        cv.put("FACULTY", faculty);
        cv.put("COURSE", course);
        cv.put("NAME", name);
        cv.put("HEAD", head);
        Long rowID = db.insert("groups", null, cv);
        if (rowID == -1) {
            Log.d(LOG_TAG,"Sorry, such ID already exists");
            // Log.d(LOG_TAG, "Sorry, empty field\n");
        } else {
            Log.d(LOG_TAG, "row group inserted, ID = " + rowID);
            // toast("row inserted, ID = " + rowID);
        }
        cv.clear();
    }

    public void addStudent(int idStudent, int idGroup, String name, String birthDate, String address){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //students
        cv.put("IDSTUDENT", idStudent);
        cv.put("IDGROUP", idGroup);
        cv.put("NAME", name);
        cv.put("BIRTHDATE", birthDate);
        cv.put("ADDRESS", address);
        Long rowID = db.insert("students", null, cv);
        if (rowID == -1) {
            Log.d(LOG_TAG,"Sorry, such ID already exists");
            // Log.d(LOG_TAG, "Sorry, empty field\n");
        } else {
            Log.d(LOG_TAG, "row student student inserted, ID = " + rowID);
            // toast("row inserted, ID = " + rowID);
        }
        cv.clear();
    }

    public void addSubject(int id, String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //subject
        cv.put("IDSUBJECT", id);
        cv.put("SUBJECT", name);
        Long rowID = db.insert("subject", null, cv);
        if (rowID == -1) {
            Log.d(LOG_TAG,"Sorry, such ID already exists");
            // Log.d(LOG_TAG, "Sorry, empty field\n");
        } else {
            Log.d(LOG_TAG, "row subject inserted, ID = " + rowID);
            // toast("row inserted, ID = " + rowID);
        }
        cv.clear();
    }

    public void addProgress(int idStudent, int idSubject, int idFaculty ,String examDate, String teacher){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        //progress


        cv.put("IDSTUDENT", idStudent);
        cv.put("IDSUBJECT", idSubject);
        cv.put("IDFACULTY", idFaculty);
        cv.put("EXAMDATE", examDate);
        cv.put("MARK", generateMark());
        cv.put("TEACHER", teacher);
        Long rowID = db.insert("progress", null, cv);
        if (rowID == -1) {
            Log.d(LOG_TAG,"Sorry, such ID already exists");
            // Log.d(LOG_TAG, "Sorry, empty field\n");
        } else {
            Log.d(LOG_TAG, "row progress inserted, ID = " + rowID);
            // toast("row inserted, ID = " + rowID);
        }
        cv.clear();
    }

    private int generateMark(){
        Random rand = new Random();
        int randomNum = rand.nextInt((10 - 0) + 1) + 0;
        return randomNum;
    }
}