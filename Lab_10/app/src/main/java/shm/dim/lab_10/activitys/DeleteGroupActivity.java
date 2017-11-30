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

public class DeleteGroupActivity extends AppCompatActivity {

    private Spinner mIdGroup;
    private DbHelper dbHelper;
    private SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_group);

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
        mIdGroup = (Spinner)findViewById(R.id.id_group);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getGroupsId());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIdGroup.setAdapter(adapter);
    }

    private ArrayList<String> getGroupsId() {
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


    public void onClickDelete(View view) {
        database.delete(DbSchema.GroupsTable.TABLE_NAME,
                DbSchema.GroupsTable.Columns.ID_GROUP + "= ?",
                new String[]{mIdGroup.getSelectedItem().toString()});
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}