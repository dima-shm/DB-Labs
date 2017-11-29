package shm.dim.lab_10;

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

import shm.dim.lab_10.database.DbHelper;
import shm.dim.lab_10.database.DbSchema;

public class AddGroupActivity extends AppCompatActivity {

    private EditText mIdGroup, mFaculty, mCourse, mNameGroup;
    private Spinner mHeadGroup;
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();

        initViews();
    }

    void initViews() {
        mIdGroup = (EditText) findViewById(R.id.id_group);
        mFaculty = (EditText) findViewById(R.id.faculty);
        mCourse = (EditText) findViewById(R.id.course);
        mNameGroup = (EditText) findViewById(R.id.name_group);
        mHeadGroup = (Spinner)findViewById(R.id.head_group);
        initSpinner();
    }

    private ArrayList<String> getStudent() {
        ArrayList<String> data = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT " + DbSchema.StudentsTable.Columns.NAME + " FROM " + DbSchema.StudentsTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int name = cursor.getColumnIndex(DbSchema.StudentsTable.Columns.NAME);
            do {
                data.add(cursor.getString(name));
            } while (cursor.moveToNext());
        }
        return data;
    }

    void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getStudent());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHeadGroup.setAdapter(adapter);
    }

    private boolean viewIsEmpty() {
        return mFaculty.getText().toString().isEmpty() ||
               mCourse.getText().toString().isEmpty() ||
               mNameGroup.getText().toString().isEmpty();
    }

    public void onClickAdd(View view) {
        if(!viewIsEmpty()) {
            insert(mIdGroup.getText().toString().isEmpty() ? null : new Integer(mIdGroup.getText().toString()),
                   mFaculty.getText().toString(),
                   new Integer(mCourse.getText().toString()),
                   mNameGroup.getText().toString(),
                   mHeadGroup.getSelectedItem().toString().isEmpty() ? "" : mHeadGroup.getSelectedItem().toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Заполните все необходимые поля", Toast.LENGTH_SHORT).show();
    }

    protected void insert(Integer id, String faculty, int course, String name, String head) {
        ContentValues newValues = new ContentValues();
        newValues.put(DbSchema.GroupsTable.Columns.ID_GROUP, id);
        newValues.put(DbSchema.GroupsTable.Columns.FACULTY, faculty);
        newValues.put(DbSchema.GroupsTable.Columns.COURSE, course);
        newValues.put(DbSchema.GroupsTable.Columns.NAME, name);
        newValues.put(DbSchema.GroupsTable.Columns.HEAD, head);
        database.insert(DbSchema.GroupsTable.TABLE_NAME, null, newValues);
    }
}