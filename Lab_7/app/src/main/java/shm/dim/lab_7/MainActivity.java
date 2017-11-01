package shm.dim.lab_7;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import java.io.File;
import java.util.Calendar;

import shm.dim.lab_7.file_helper.FileHelper;
import shm.dim.lab_7.notes.Note;
import shm.dim.lab_7.date.SelectedDate;
import shm.dim.lab_7.notes.Notes;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity {

    private File file;

    private EditText etNote;
    private Button buttonAdd, buttonChange, buttonRemove;
    private CalendarView calendar;

    private SelectedDate selectedDate;

    private Notes notes = new Notes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        file = new File(this.getFilesDir(), "Note.json");
        file.delete();
        checkFile();

        setCurrentDate();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendar, int year, int month, int day) {
                setSelectedDate(year, month, day);
                setDisplayFormatButtons(false);
                Note note;
                if((note = notes.findByDate(selectedDate)) != null) {
                    setDisplayFormatButtons(true);
                    etNote.setText(note.getText());
                }
            }
        });
    }

    // Инициализировать все используемые View
    private void initViews() {
        etNote = (EditText)findViewById(R.id.etNote);
        buttonAdd = (Button)findViewById(R.id.buttonAddNote);
        buttonChange = (Button)findViewById(R.id.buttonChangeNote);
        buttonRemove = (Button)findViewById(R.id.buttonRemoveNote);
        calendar = (CalendarView)findViewById(R.id.calendarView);
    }

    // Проверка наличия файла и создание его в случае отсутсвия
    private void checkFile() {
        if(!FileHelper.exist(file))
            FileHelper.create(file);
    }

    // Установить текущую дату как выбраную
    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        selectedDate = new SelectedDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    // Установить выбранную дату
    private void setSelectedDate(int year, int month, int day) {
        selectedDate = new SelectedDate(year, month, day);
    }

    // Создание и отображение сообщения в диалоге
    private void createDialogMsg(String msg) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(msg).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                    }
                });
        AlertDialog ad = b.create();
        ad.show();
    }

    // Установить формат отображения кнопок
    private void setDisplayFormatButtons(boolean b) {
        etNote.setText("");
        if (b) {
            buttonAdd.setVisibility(View.GONE);
            buttonChange.setVisibility(View.VISIBLE);
            buttonRemove.setVisibility(View.VISIBLE);
        } else {
            buttonAdd.setVisibility(View.VISIBLE);
            buttonChange.setVisibility(View.GONE);
            buttonRemove.setVisibility(View.GONE);
        }
    }

    // Обработчикик кнопок
    public void onClick_Add(View v) {
        Note note = new Note(etNote.getText().toString(), selectedDate.toString());
        notes.add(note);
        FileHelper.write(notes, file);
        setDisplayFormatButtons(true);
        etNote.setText(note.getText());
        createDialogMsg("Заметка добавлена");
    }
    public void onClick_Change(View v) {
        Note note;
        if((note = notes.findByDate(selectedDate)) != null) {
            notes.remove(note);
            notes.add(new Note(etNote.getText().toString(), selectedDate.toString()));
        }
        FileHelper.write(notes, file);
        createDialogMsg("Заметка изменена");
    }
    public void onClick_Remove(View v) {
        Note note;
        if((note = notes.findByDate(selectedDate)) != null)
            notes.remove(note);
        FileHelper.write(notes, file);
        setDisplayFormatButtons(false);
        etNote.setText("");
        createDialogMsg("Заметка была удалена");
    }
}
