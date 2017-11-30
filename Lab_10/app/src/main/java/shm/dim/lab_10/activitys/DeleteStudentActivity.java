package shm.dim.lab_10.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import shm.dim.lab_10.R;
import shm.dim.lab_10.database.DbHelper;
import shm.dim.lab_10.database.DbSchema;

public class DeleteStudentActivity extends AppCompatActivity {

    private Spinner mIdStudent;
    private DbHelper dbHelper;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();

        initViews();
    }

    @Override
    protected void onStop() {
        dbHelper.close();
        super.onStop();
    }


    void initViews() {
        mIdStudent = (Spinner)findViewById(R.id.id_student);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getStudentsId());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIdStudent.setAdapter(adapter);
    }

    private ArrayList<String> getStudentsId() {
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + DbSchema.StudentsTable.Columns.ID_STUDENT + " FROM " + DbSchema.StudentsTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int idStudent = cursor.getColumnIndex(DbSchema.StudentsTable.Columns.ID_STUDENT);
            do {
                data.add(cursor.getString(idStudent));
            } while (cursor.moveToNext());
        }
        return data;
    }


    public void onClickDelete(View view) {
        database.delete(DbSchema.StudentsTable.TABLE_NAME,
                DbSchema.StudentsTable.Columns.ID_STUDENT + "= ?",
                new String[]{mIdStudent.getSelectedItem().toString()});
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}