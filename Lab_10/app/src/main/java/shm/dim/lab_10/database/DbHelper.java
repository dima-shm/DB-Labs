package shm.dim.lab_10.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import shm.dim.lab_10.database.DbSchema.GroupsTable;
import shm.dim.lab_10.database.DbSchema.StudentsTable;

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
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS " + GroupsTable.TABLE_NAME + " ( "
                + GroupsTable.Columns.ID_GROUP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GroupsTable.Columns.FACULTY + " TEXT NOT NULL, "
                + GroupsTable.Columns.COURSE + " INTEGER NOT NULL CHECK (" + GroupsTable.Columns.COURSE + " > 0 AND " + GroupsTable.Columns.COURSE + " < 7), "
                + GroupsTable.Columns.NAME + " TEXT NOT NULL, "
                + GroupsTable.Columns.HEAD + " TEXT NOT NULL);");

        database.execSQL("CREATE TABLE IF NOT EXISTS " + StudentsTable.TABLE_NAME + " ( "
                + StudentsTable.Columns.ID_GROUP + " INTEGER NOT NULL, "
                + StudentsTable.Columns.ID_STUDENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StudentsTable.Columns.NAME + " TEXT NOT NULL, "
                + "CONSTRAINT " + StudentsTable.Columns.FK_CONSTRAINT + " FOREIGN KEY("
                + StudentsTable.Columns.ID_GROUP + ") REFERENCES " + GroupsTable.TABLE_NAME + "("
                + StudentsTable.Columns.ID_GROUP + ") ON DELETE CASCADE ON UPDATE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE " + StudentsTable.TABLE_NAME + ";");
        database.execSQL("DROP TABLE " + GroupsTable.TABLE_NAME + ";");
        onCreate(database);
    }
}