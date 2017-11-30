package shm.dim.lab_10.activitys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import shm.dim.lab_10.R;
import shm.dim.lab_10.database.DbHelper;
import shm.dim.lab_10.database.DbSchema;

public class AddStudentActivity extends AppCompatActivity {

    private Spinner mIdGroup;
    private EditText mIdStudent, mNameStudent;
    private DbHelper dbHelper;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();

        initViews();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }


    void initViews() {
        mIdGroup = (Spinner)findViewById(R.id.id_group);

        ArrayAdapter<String> adapterIdGroups = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getIdGroups());
        adapterIdGroups.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIdGroup.setAdapter(adapterIdGroups);

        mIdStudent = (EditText) findViewById(R.id.id_student);
        mNameStudent = (EditText) findViewById(R.id.name_student);
    }

    private ArrayList<String> getIdGroups() {
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + DbSchema.GroupsTable.Columns.ID_GROUP + " FROM " + DbSchema.GroupsTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int idGroup = cursor.getColumnIndex(DbSchema.GroupsTable.Columns.ID_GROUP);
            do {
                data.add(cursor.getString(idGroup));
            } while (cursor.moveToNext());
        }
        return data;
    }



    private boolean viewIsEmpty() {
        return mIdStudent.getText().toString().isEmpty() ||
               mNameStudent.getText().toString().isEmpty();
    }

    protected void insert(int idGroup, int idStudent, String name) {
        ContentValues newValues = new ContentValues();
        newValues.put(DbSchema.StudentsTable.Columns.ID_GROUP, idGroup);
        newValues.put(DbSchema.StudentsTable.Columns.ID_STUDENT, idStudent);
        newValues.put(DbSchema.StudentsTable.Columns.NAME, name);
        database.insert(DbSchema.StudentsTable.TABLE_NAME, null, newValues);
    }

    public void onClickAdd(View view) {
        if(!viewIsEmpty()) {
            insert(new Integer(mIdGroup.getSelectedItem().toString()),
                   new Integer(mIdStudent.getText().toString()),
                   mNameStudent.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Заполните все необходимые поля", Toast.LENGTH_SHORT).show();
    }
}