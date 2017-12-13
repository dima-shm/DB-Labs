package shm.dim.lab_11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //private DbHelper dbHelper;
    //private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_best_students).setOnClickListener(this);
        findViewById(R.id.button_students_with_mark_below_4).setOnClickListener(this);
        findViewById(R.id.button_comparative_analysis_for_groups).setOnClickListener(this);
        findViewById(R.id.button_comparison_on_faculty).setOnClickListener(this);

        //dbHelper = new DbHelper(this);
        //db = dbHelper.getWritableDatabase();
        //dbHelper.initDatabase(db);
    }


    /*
    public void selectAvgMarkByGroups(){
        if(getPeriod()) {
            mResult.setNumColumns(2);
            mResult.setAdapter(null);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            adapter.add("ID_GROUP");
            adapter.add("AVG_MARK");

            String query = "select IDGROUP, round(avg(MARK), 1) avg_mark from STUDENT, PROGRESS "
                    + "where STUDENT.IDSTUDENT = PROGRESS.IDSTUDENT "
                    + "and EXAMDATE BETWEEN '" + dateWith + "' and '" + dateOn + "' "
                    + "group by IDGROUP";
            Cursor cursor = db.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                int idGroupIndex = cursor.getColumnIndex("IDGROUP");
                int avgMarkIndex = cursor.getColumnIndex("avg_mark");
                do {
                    adapter.add(cursor.getString(idGroupIndex));
                    adapter.add(cursor.getString(avgMarkIndex));
                } while (cursor.moveToNext());
            }
            mResult.setAdapter(adapter);
            cursor.close();
        }
    }

    public void selectAvgMarkBySubject(){
        if(getPeriod()) {
            mResult.setNumColumns(2);
            mResult.setAdapter(null);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            adapter.add("ID_SUBJECT");
            adapter.add("AVG_MARK");

            String query = "select SUBJECT, round(avg(MARK), 1) avg_mark  from SUBJECT, PROGRESS "
                    + "where EXAMDATE BETWEEN '" + dateWith + "' and '" + dateOn + "' "
                    + "group by SUBJECT";
            Cursor cursor = db.rawQuery(query, null);


            if (cursor.moveToFirst()) {
                int IDSubjectColIndex = cursor.getColumnIndex("SUBJECT");
                int MARKColIndex = cursor.getColumnIndex("avg_mark");
                do {
                    adapter.add(cursor.getString(IDSubjectColIndex));
                    adapter.add(cursor.getString(MARKColIndex));
                } while (cursor.moveToNext());
            }
            mResult.setAdapter(adapter);
            cursor.close();
        }
    }

    //done facultyProgress
    public void selectAvgMarkByFaculty() {
        if(getPeriod()) {
            mResult.setNumColumns(2);
            mResult.setAdapter(null);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            adapter.add("FACULTY");
            adapter.add("AVG_MARK");

            String query = "select FACULTY, round(avg(MARK), 1) avg_mark from FACULTY, PROGRESS "
                    + "where EXAMDATE BETWEEN '" + dateWith + "' and '" + dateOn + "' "
                    + "group by FACULTY "
                    + "order by avg_mark asc";
            Cursor c = db.rawQuery(query, null);


            if (c.moveToFirst()) {
                int facultyIndex = c.getColumnIndex("FACULTY");
                int avgMarkIndex = c.getColumnIndex("avg_mark");
                do {
                    adapter.add(c.getString(facultyIndex));
                    adapter.add(c.getString(avgMarkIndex));
                } while (c.moveToNext());
            }
            mResult.setAdapter(adapter);
            c.close();
        }
    }

    //done studentsProgress
    public void selectAvgBySpecificStudent(){
        //mResult.setNumColumns(3);
        //mResult.setAdapter(null);
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        //getPeriod();
        //String query = "Select NAME, IDSUBJECT, round(avg(MARK), 1) avg_mark  from studentsProgress "
        //        + "WHERE EXAMDATE BETWEEN '" + dateWith + "' and '" + dateOn + "' and NAME = '"+ studentName.getText().toString() +"' "
        //        + "GROUP BY IDSUBJECT";
        //Cursor c = db.rawQuery(query, null);
        //adapter.add("Name");
        //adapter.add("Subject id");
        //adapter.add("Mark");
//
        //if (c.moveToFirst()) {
//
        //    int NAMEColIndex = c.getColumnIndex("NAME");
        //    int IDSUBJColIndex = c.getColumnIndex("IDSUBJECT");
        //    int MARKColIndex = c.getColumnIndex("avg_mark");
//
        //    do {
        //        adapter.add(c.getString(NAMEColIndex));
        //        adapter.add(c.getString(IDSUBJColIndex));
        //        adapter.add(c.getString(MARKColIndex));
        //    } while (c.moveToNext());
        //    mResult.setAdapter(adapter);
        //} else {
        //    toast("0 rows");
        //}
        //c.close();
    }

    //done studentsProgress
    public void selectAvgForEveryStudentBySubject(){
        mResult.setNumColumns(3);
        mResult.setAdapter(null);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        getPeriod();
        String query = "Select NAME, IDSUBJECT, round(avg(MARK), 1) avg_mark  from studentsProgress "
                + "WHERE EXAMDATE BETWEEN '" + dateWith + "' and '" + dateOn + "' "
                + "GROUP BY  NAME, IDSUBJECT";
        Cursor c = db.rawQuery(query, null);
        adapter.add("Name");
        adapter.add("Subject id");
        adapter.add("Mark");

        if (c.moveToFirst()) {

            int NAMEColIndex = c.getColumnIndex("NAME");
            int IDSUBJColIndex = c.getColumnIndex("IDSUBJECT");
            int MARKColIndex = c.getColumnIndex("avg_mark");

            do {
                adapter.add(c.getString(NAMEColIndex));
                adapter.add(c.getString(IDSUBJColIndex));
                adapter.add(c.getString(MARKColIndex));
            } while (c.moveToNext());
            mResult.setAdapter(adapter);
        }
        c.close();
    }

    //done studentsProgress
    public void selectAvgBySpecificGroup(){
        //mResult.setNumColumns(2);
        //mResult.setAdapter(null);
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        //getPeriod();
        //String query = "Select IDGROUP, round(avg(MARK), 1) avg_mark  from studentsProgress "
        //        + "WHERE EXAMDATE BETWEEN '" + dateWith + "' and '" + dateOn + "' and IDGROUP = " + groupID.getText().toString()
        //        + " Group by IDGROUP";
        //Cursor c = db.rawQuery(query, null);
        //adapter.add("Group id");
        //adapter.add("Mark");
//
        //if (c.moveToFirst()) {
//
        //    int IDGRColIndex = c.getColumnIndex("IDGROUP");
        //    int MARKColIndex = c.getColumnIndex("avg_mark");
//
        //    do {
        //        adapter.add("Group id: " + c.getString(IDGRColIndex));
        //        adapter.add("Mark: " + c.getString(MARKColIndex));
        //    } while (c.moveToNext());
        //    mResult.setAdapter(adapter);
        //} else{
        //    toast("0 rows");
        //}
        //c.close();
    }
*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_best_students:
                startActivity(new Intent(this, BestStudentsActivity.class));
                break;
            case R.id.button_students_with_mark_below_4:
                startActivity(new Intent(this, BadStudentsActivity.class));
                break;
            case R.id.button_comparative_analysis_for_groups:
                //selectAvgMarkByGroups();
                break;
            case R.id.button_comparison_on_faculty:
                //selectAvgMarkByFaculty();
                break;
        }
    }
}