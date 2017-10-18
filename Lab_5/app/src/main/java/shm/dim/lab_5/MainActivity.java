package shm.dim.lab_5;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity {

    private File file;
    private EditText editText_setKey, editText_setValue,
                     editText_getKey, editText_getValue;
    private TextView textView_textOnFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        file = new File(this.getFilesDir(), "Lab.txt");
        file.delete();

        if(!existBase(file))
            createFile(file);

        HashTable.initLineToFile(file);
        textView_textOnFile.setText(HashTable.readDataFromFile(file));
    }

    // Инициализировать все используемые View
    private void initViews() {
        editText_setKey   = (EditText) findViewById(R.id.editText_setKey);
        editText_setValue = (EditText) findViewById(R.id.editText_setValue);
        editText_getKey   = (EditText) findViewById(R.id.editText_getKey);
        editText_getValue = (EditText) findViewById(R.id.editText_getValue);
        textView_textOnFile = (TextView) findViewById(R.id.textView_textOnFile);
    }

    // Проверка наличия файла в Internal памяти
    private boolean existBase(File file) {
        boolean rc;
        if(rc = file.exists())
            Log.d("Log_05", "Файл " + file.getName() + " существует");
        else
            Log.d("Log_05", "Файл " + file.getName() + " не найден");
        return rc;
    }

    // Создание файла в Internal памяти
    private void createFile(File file) {
        try {
            file.createNewFile();
            Log.d("Log_05", "Файл " + file.getName() + " создан");
        }
        catch (IOException e) {
            Log.d("Log_05", e.getMessage());
        }
    }

    // Обработчики кнопок
    public void onClick_Save(View view) {
        HashTable.insertItem(editText_setKey.getText().toString(),
                             editText_setValue.getText().toString(),
                             file);
        editText_setKey.setText("");
        editText_setValue.setText("");
        textView_textOnFile.setText(new String(HashTable.readDataFromFile(file)));
    }
    public void onClick_GetValue(View view) throws FileNotFoundException {
        editText_getValue.setText(HashTable.getValueOnKey(editText_getKey.getText().toString(), file));
    }
}
