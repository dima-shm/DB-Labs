package shm.dim.lab_9.db_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int    DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lab_DB";
    public static final String TABLE_SIMPLETABLE = "SimpleTable";

    public static final String KEY_ID = "_id";
    public static final String KEY_F= "f";
    public static final String KEY_T = "t";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_SIMPLETABLE + "(" + KEY_ID
                + " integer primary key autoincrement," + KEY_F + " real not null," + KEY_T + " text not null" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_SIMPLETABLE);

        onCreate(db);
    }

}