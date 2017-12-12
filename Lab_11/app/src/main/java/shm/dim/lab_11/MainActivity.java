package shm.dim.lab_11;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private EditText mDateWith, mDateOn;
    private String dateWith, dateOn;
    private GridView mResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDateWith = findViewById(R.id.EditText_with);
        mDateOn = findViewById(R.id.EditText_on);
        mResult =  findViewById(R.id.GridView_result);

        findViewById(R.id.button_best_students).setOnClickListener(this);
        findViewById(R.id.button_students_with_mark_below_4).setOnClickListener(this);
        findViewById(R.id.button_comparative_analysis_for_groups).setOnClickListener(this);
        findViewById(R.id.button_comparison_on_faculty).setOnClickListener(this);

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        //initDatabase();
    }


    protected void initDatabase() {
        dbHelper.addFaculty(db, 1,"ФИТ", "Шиман","8:00 – 15:00");
        dbHelper.addFaculty(db, 2,"ИЭФ", "Перов","8:00 – 15:00");
        dbHelper.addGroup(db, 1,"ФИТ", 3, "ПОИБМС", "Иванов");
        dbHelper.addGroup(db, 2,"ИЭФ", 3, "NET", "Смирнов");
        dbHelper.addStudent(db, 1, 1, "Иванов", "1998-11-11","ул.Садовая 12");
        dbHelper.addStudent(db, 2, 1, "Сидоров", "1998-02-12","просп.Рокосовского 14");
        dbHelper.addStudent(db, 3, 1, "Петров", "1987-01-09","ул.Иванова 2");
        dbHelper.addStudent(db, 4, 2, "Смирнов", "1991-11-10","ул.Горовца 11");
        dbHelper.addStudent(db, 5, 2, "Азаренко", "1995-06-11","просп.Машерова 114а");
        dbHelper.addStudent(db, 6, 2, "Брусевич", "1997-15-07","ул.Седых 9");
        dbHelper.addSubject(db, 1, "ОАиП");
        dbHelper.addSubject(db, 2, "Бизнес");
        dbHelper.addProgress(db, 1,1,"2010-06-06", 6, "Преподаватель");
        dbHelper.addProgress(db, 2,1,"2010-06-06", 4, "Преподаватель");
        dbHelper.addProgress(db, 3,1,"2010-06-06", 7, "Преподаватель");
        dbHelper.addProgress(db, 4,1,"2017-06-06", 2, "Преподаватель");
        dbHelper.addProgress(db, 5,1,"2017-06-06", 2, "Преподаватель");
        dbHelper.addProgress(db, 6,1,"2017-06-06", 6, "Преподаватель");
        dbHelper.addProgress(db, 1,2,"2014-06-06", 5, "Преподаватель2");
        dbHelper.addProgress(db, 2,2,"2014-06-06", 9, "Преподаватель2");
        dbHelper.addProgress(db, 3,2,"2014-06-06", 3, "Преподаватель2");
        dbHelper.addProgress(db, 4,2,"2016-06-06", 7, "Преподаватель2");
        dbHelper.addProgress(db, 5,2,"2016-06-06", 4, "Преподаватель2");
        dbHelper.addProgress(db, 6,2,"2016-06-06", 4, "Преподаватель2");
    }

    private boolean formatDateIsCorrect(String date) {
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        return df.parse(date, new ParsePosition(0)) != null;
    }

    public boolean getPeriod(){
        if(formatDateIsCorrect(mDateWith.getText().toString()) &&
                formatDateIsCorrect(mDateOn.getText().toString())) {
            dateWith = mDateWith.getText().toString();
            dateOn = mDateOn.getText().toString();
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Неверный формат даты", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void selectBestStudentsForPeriod() {
        if (getPeriod()) {
            mResult.setNumColumns(2);
            mResult.setAdapter(null);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            adapter.add("NAME");
            adapter.add("MARK");

            String query = "select NAME, MARK from STUDENT, PROGRESS "
                    +"where STUDENT.IDSTUDENT = PROGRESS.IDSTUDENT "
                    + "and EXAMDATE BETWEEN '" + dateWith + "' and '" + dateOn + "' "
                    + "and MARK >= 6 "
                    +"order by MARK desc";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("NAME");
                int markIndex = cursor.getColumnIndex("MARK");
                do {
                    adapter.add(cursor.getString(nameIndex));
                    adapter.add(cursor.getString(markIndex));
                    Log.d("LOG", cursor.getString(nameIndex));
                    Log.d("LOG", cursor.getString(markIndex));
                } while (cursor.moveToNext());
            }
            mResult.setAdapter(adapter);
            cursor.close();
        }
    }

    public void selectStudentsWhereMarkLessThenFour() {
        if(getPeriod()) {
            mResult.setNumColumns(2);
            mResult.setAdapter(null);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            adapter.add("NAME");
            adapter.add("MARK");

            String query = "select NAME, MARK from STUDENT, PROGRESS "
                    + "where STUDENT.IDSTUDENT = PROGRESS.IDSTUDENT "
                    + "and EXAMDATE BETWEEN '" + dateWith + "' and '" + dateOn + "' "
                    + "and MARK < 4";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex("NAME");
                int markIndex = cursor.getColumnIndex("MARK");
                do {
                    adapter.add(cursor.getString(nameIndex));
                    adapter.add(cursor.getString(markIndex));
                } while (cursor.moveToNext());
            }
            mResult.setAdapter(adapter);
            cursor.close();
        }
    }

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
        mResult.setNumColumns(2);
        mResult.setAdapter(null);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        getPeriod();
        String query = "Select SUBJECT, round(avg(MARK), 1) avg_mark  from subjectProgress "
                + "WHERE EXAMDATE BETWEEN '" + dateWith + "' and '" + dateOn + "' "
                + "Group by SUBJECT";
        Cursor c = db.rawQuery(query, null);
        adapter.add("Subject id");
        adapter.add("Mark");

        if (c.moveToFirst()) {

            int IDSubjectColIndex = c.getColumnIndex("SUBJECT");
            int MARKColIndex = c.getColumnIndex("avg_mark");

            do {
                adapter.add(c.getString(IDSubjectColIndex));
                adapter.add(c.getString(MARKColIndex));
            } while (c.moveToNext());
            mResult.setAdapter(adapter);
        }
        c.close();
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_best_students:
                selectBestStudentsForPeriod();
                break;
            case R.id.button_students_with_mark_below_4:
                selectStudentsWhereMarkLessThenFour();
                break;
            case R.id.button_comparative_analysis_for_groups:
                selectAvgMarkByGroups();
                break;
            case R.id.button_comparison_on_faculty:
                selectAvgMarkByFaculty();
                break;
        }
    }
}