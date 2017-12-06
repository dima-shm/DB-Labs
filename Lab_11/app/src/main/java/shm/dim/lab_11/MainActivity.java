package shm.dim.lab_11;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import shm.dim.lab_11.database.DbHelper;

public class MainActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    @Override
    protected void onStop() {
        dbHelper.close();
        super.onStop();
    }


    protected void initDatabase() {

    }
}