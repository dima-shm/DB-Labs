package shm.dim.lab_5;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;

import shm.dim.lab_5.file_manager.FileManager;
import shm.dim.lab_5.hash_table.HashTable;
import shm.dim.lab_5.item.Item;

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

        if(!FileManager.existFile(file))
            FileManager.createFile(file);

        FileManager.initLineToFile(file);

        textView_textOnFile.setText(FileManager.readFile(file));
    }

    // Инициализировать все используемые View
    private void initViews() {
        editText_setKey   = (EditText) findViewById(R.id.editText_setKey);
        editText_setValue = (EditText) findViewById(R.id.editText_setValue);
        editText_getKey   = (EditText) findViewById(R.id.editText_getKey);
        editText_getValue = (EditText) findViewById(R.id.editText_getValue);
        textView_textOnFile = (TextView) findViewById(R.id.textView_textOnFile);
    }

    // Обработчики кнопок
    public void onClick_Save(View view) throws FileNotFoundException {
        Item item = new Item(editText_setKey.getText().toString(),
                             editText_setValue.getText().toString());
        HashTable.add(item, file);
        editText_setKey.setText("");
        editText_setValue.setText("");
        textView_textOnFile.setText(FileManager.readFile(file));
    }
    public void onClick_GetValue(View view) throws FileNotFoundException {
        Item item = new Item();
        item.setKey(editText_getKey.getText().toString());
        if((item = HashTable.find(item, file)) != null)
            editText_getValue.setText(item.getValue());
        else
            editText_getValue.setText("Значение не найдено");
    }
}