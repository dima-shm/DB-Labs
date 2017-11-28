package shm.dim.lab_10;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

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

        mGroups = (GridView) findViewById(R.id.gridViewGroups);
        mStudents = (GridView) findViewById(R.id.gridViewStudents);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        insertIntoGroups(7, "ИТ", 3, "ПОИБМС", "Корсун Максим");
        insertIntoGroups(2, "ПИМ", 2, "ИД", "Петров Петр");

        ArrayAdapter<String> groupsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                getTitleForGroupsTable());
        groupsAdapter.addAll(getAllFromGroupsTable());
        mGroups.setAdapter(groupsAdapter);

        insertIntoStudents(7, 2122, "Прохоров Иван");
        insertIntoStudents(7, 2123, "Трофимова Катя");
        insertIntoStudents(7, 2124, "Игнатенко Виктор");

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

    protected void insertIntoGroups(Integer id, String faculty, int course, String name, String head) {
        ContentValues newValues = new ContentValues();
        newValues.put(DbSchema.GroupsTable.Columns.ID_GROUP, id);
        newValues.put(DbSchema.GroupsTable.Columns.FACULTY, faculty);
        newValues.put(DbSchema.GroupsTable.Columns.COURSE, course);
        newValues.put(DbSchema.GroupsTable.Columns.NAME, name);
        newValues.put(DbSchema.GroupsTable.Columns.HEAD, head);
        database.insert(DbSchema.GroupsTable.TABLE_NAME, null, newValues);
    }

    protected void insertIntoStudents(Integer idGroup, int idStudent, String name) {
        ContentValues newValues = new ContentValues();
        newValues.put(DbSchema.StudentsTable.Columns.ID_GROUP, idGroup);
        newValues.put(DbSchema.StudentsTable.Columns.ID_STUDENT, idStudent);
        newValues.put(DbSchema.StudentsTable.Columns.NAME, name);
        database.insert(DbSchema.StudentsTable.TABLE_NAME, null, newValues);
    }

    protected ArrayList<String> getTitleForGroupsTable() {
        ArrayList<String> data = new ArrayList<>(Arrays.asList(DbSchema.GroupsTable.Columns.ID_GROUP,
                DbSchema.GroupsTable.Columns.FACULTY,
                DbSchema.GroupsTable.Columns.COURSE,
                DbSchema.GroupsTable.Columns.NAME,
                DbSchema.GroupsTable.Columns.HEAD));
        return data;
    }

    protected ArrayList<String> getTitleForStudentsTable() {
        ArrayList<String> data = new ArrayList<>(Arrays.asList(DbSchema.StudentsTable.Columns.ID_GROUP,
                DbSchema.StudentsTable.Columns.ID_STUDENT,
                DbSchema.StudentsTable.Columns.NAME));
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
        Toast.makeText(this, "Добавить группу", Toast.LENGTH_SHORT).show();
    }

    protected void addStudent() {
        Toast.makeText(this, "Добавить группу", Toast.LENGTH_SHORT).show();
    }
}