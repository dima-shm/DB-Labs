package shm.dim.lab_4;

import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity {

    private File file;
    private EditText editText_secondName, editText_firstName;
    private TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        file = new File(this.getFilesDir(), "Base_Lab.txt");
        file.delete();

        if(!existBase(file)) {
            createDialogMsg();
            createFile(file);
        }

        textView4.setText(readDataFromFile());
    }

    // Инициализировать все используемые View
    private void initViews() {
        editText_secondName = (EditText)findViewById(R.id.editText_secondName);
        editText_firstName = (EditText)findViewById(R.id.editText_firstName);
        textView4 = (TextView)findViewById(R.id.textView4);
    }

    // Проверка наличия файла в Internal памяти
    private boolean existBase(File file) {
        boolean rc;
        if(rc = file.exists())
            Log.d("Log_04", "Файл " + file.getName() + " существует");
        else
            Log.d("Log_04", "Файл " + file.getName() + " не найден");
        return rc;
    }

    // Диалоговое окно для сообщения о создании файла
    private void createDialogMsg() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Создается файл " + file.getName()).setPositiveButton("Да",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Log.d("Log_04", "Создание файла " + file.getName());
                    }
        });
        AlertDialog ad = b.create();
        ad.show();
    }

    // Создание файла в Internal памяти
    private void createFile(File file) {
        try {
            file.createNewFile();
            Log.d("Log_04", "Файл " + file.getName() + " создан");
        }
        catch (IOException e) {
            Log.d("Log_04", e.getMessage());
        }
    }

    // Обработчик нажатия клаиши ввода
    public void onClick_Enter(View view) {
        writeLineToFile(editText_secondName.getText().toString(), editText_firstName.getText().toString());
        textView4.setText(readDataFromFile());
    }

    // Запись строку в файл
    private void writeLineToFile(String sname, String fname) {
        String str = sname + " " + fname + ";\r\n";
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(str);
            Log.d("Log_04", "Данные записаны");
        }
        catch (IOException e) {
            Log.d("Log_04", e.getMessage());
        }
    }

    // Прочитать данные из файла
    private String readDataFromFile() {
        char buf[] = new char[(int) file.length()];
        try (FileReader fr = new FileReader(file)){
            fr.read(buf);
            Log.d("Log_04", "Получил текст " + new String(buf));
        }
        catch (IOException e) {
            Log.d("Log_04", "Не удалось прочитать из файла " + file.getName());
        }
        Log.d("Log_04", new String(buf));
        return new String(buf);
    }
}