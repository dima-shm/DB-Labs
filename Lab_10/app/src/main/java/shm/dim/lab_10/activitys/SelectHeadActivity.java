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

public class SelectHeadActivity extends AppCompatActivity {

    private Spinner mIdGroup, mHeadGroup;
    private DbHelper dbHelper;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_head);

        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();

        initViews();
    }


    void initViews() {
        mIdGroup = (Spinner)findViewById(R.id.id_group);
        mHeadGroup = (Spinner)findViewById(R.id.head_group);

        ArrayAdapter<String> adapterIdGroups = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getIdGroups());
        adapterIdGroups.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIdGroup.setAdapter(adapterIdGroups);

        ArrayAdapter<String> adapterNameStudents = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getNameStudents());
        adapterNameStudents.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHeadGroup.setAdapter(adapterNameStudents);
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

    private ArrayList<String> getNameStudents() {
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



    protected void update(int idGroup, String head) {
        ContentValues newValues = new ContentValues();
        newValues.put(DbSchema.GroupsTable.Columns.HEAD, head);
        database.update(DbSchema.GroupsTable.TABLE_NAME, newValues,
                DbSchema.GroupsTable.Columns.ID_GROUP + " = ?", new String[] {new Integer(idGroup).toString()});
    }

    public void onClickSelect(View view) {
        update(new Integer(mIdGroup.getSelectedItem().toString()), mHeadGroup.getSelectedItem().toString());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}