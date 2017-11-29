package shm.dim.lab_10.activitys;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

import shm.dim.lab_10.R;
import shm.dim.lab_10.database.DbHelper;
import shm.dim.lab_10.database.DbSchema;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DbHelper dbHelper;
    private SQLiteDatabase database;
    private GridView mGroups, mStudents;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add_group:
                addGroup();
                return true;
            case R.id.add_student:
                addStudent();
                return true;
            case R.id.select_head:
                selectHead();
                return true;
            case R.id.delete_group:
                deleteGroup();
                return true;
            case R.id.delete_student:
                deleteStudent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        database = dbHelper.getWritableDatabase();

        mGroups = (GridView) findViewById(R.id.gv_groups);
        mStudents = (GridView) findViewById(R.id.gv_students);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayAdapter<String> groupsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getTitleForGroupsTable());
        groupsAdapter.addAll(getAllFromGroupsTable());
        mGroups.setAdapter(groupsAdapter);

        ArrayAdapter<String> studentsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getTitleForStudentsTable());
        studentsAdapter.addAll(getAllFromStudentsTable());
        mStudents.setAdapter(studentsAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }


    protected ArrayList<String> getTitleForGroupsTable() {
        ArrayList<String> data = new ArrayList<>(Arrays.asList(DbSchema.GroupsTable.Columns.ID_GROUP,
                DbSchema.GroupsTable.Columns.FACULTY,
                DbSchema.GroupsTable.Columns.COURSE,
                DbSchema.GroupsTable.Columns.NAME,
                DbSchema.GroupsTable.Columns.HEAD));
        return data;
    }

    protected ArrayList<String> getAllFromGroupsTable() {
        ArrayList<String> data = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DbSchema.GroupsTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int idGroup = cursor.getColumnIndex(DbSchema.GroupsTable.Columns.ID_GROUP);
            int faculty = cursor.getColumnIndex(DbSchema.GroupsTable.Columns.FACULTY);
            int course = cursor.getColumnIndex(DbSchema.GroupsTable.Columns.COURSE);
            int name = cursor.getColumnIndex(DbSchema.GroupsTable.Columns.NAME);
            int head = cursor.getColumnIndex(DbSchema.GroupsTable.Columns.HEAD);
            do {
                data.add(cursor.getString(idGroup));
                data.add(cursor.getString(faculty));
                data.add(cursor.getString(course));
                data.add(cursor.getString(name));
                data.add(cursor.getString(head));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }

    protected ArrayList<String> getTitleForStudentsTable() {
        ArrayList<String> data = new ArrayList<>(Arrays.asList(DbSchema.StudentsTable.Columns.ID_GROUP,
                DbSchema.StudentsTable.Columns.ID_STUDENT,
                DbSchema.StudentsTable.Columns.NAME));
        return data;
    }

    protected ArrayList<String> getAllFromStudentsTable() {
        ArrayList<String> data = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DbSchema.StudentsTable.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int idGroup = cursor.getColumnIndex(DbSchema.StudentsTable.Columns.ID_GROUP);
            int idStudent = cursor.getColumnIndex(DbSchema.StudentsTable.Columns.ID_STUDENT);
            int name = cursor.getColumnIndex(DbSchema.StudentsTable.Columns.NAME);
            do {
                data.add(cursor.getString(idGroup));
                data.add(cursor.getString(idStudent));
                data.add(cursor.getString(name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }


    protected void addGroup() {
        Intent intent = new Intent(this, AddGroupActivity.class);
        startActivity(intent);
    }

    protected void addStudent() {
        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivity(intent);
    }

    protected void selectHead() {
        Intent intent = new Intent(this, SelectHeadActivity.class);
        startActivity(intent);
    }

    protected void deleteGroup() {
        Intent intent = new Intent(this, DeleteGroupActivity.class);
        startActivity(intent);
    }

    protected void deleteStudent() {
        Intent intent = new Intent(this, DeleteStudentActivity.class);
        startActivity(intent);
    }
}