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

public class AddStudentActivity extends AppCompatActivity {

    private EditText mIdGroup, mIdStudent, mNameStudent;
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

    void initViews() {
        mIdGroup = (EditText) findViewById(R.id.id_group);
        mIdStudent = (EditText) findViewById(R.id.id_student);
        mNameStudent = (EditText) findViewById(R.id.name_student);
    }

    private boolean viewIsEmpty() {
        return mIdGroup.getText().toString().isEmpty() ||
               mIdStudent.getText().toString().isEmpty() ||
               mNameStudent.getText().toString().isEmpty();
    }

    public void onClickAdd(View view) {
        if(!viewIsEmpty()) {
            insert(new Integer(mIdGroup.getText().toString()),
                   new Integer(mIdStudent.getText().toString()),
                   mNameStudent.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "Заполните все необходимые поля", Toast.LENGTH_SHORT).show();
    }

    protected void insert(int idGroup, int idStudent, String name) {
        ContentValues newValues = new ContentValues();
        newValues.put(DbSchema.StudentsTable.Columns.ID_GROUP, idGroup);
        newValues.put(DbSchema.StudentsTable.Columns.ID_STUDENT, idStudent);
        newValues.put(DbSchema.StudentsTable.Columns.NAME, name);
        database.insert(DbSchema.StudentsTable.TABLE_NAME, null, newValues);
    }
}