package shm.dim.lab_6_contacts;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity {

    private File filePublic, filePrivate;
    private TextView tvFile;
    private EditText etSecondName, etFirstName, etPhoneNumber, etBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        filePublic = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "ContactsPublic.json");
        filePrivate  = new File(super.getExternalFilesDir(Environment.DIRECTORY_DCIM), "ContactsPrivate.json");
        //filePublic.delete();
        //filePrivate.delete();
    }

    // Инициализировать все используемые View
    private void initViews() {
        tvFile = (TextView)findViewById(R.id.tvFile);
        etSecondName = (EditText)findViewById(R.id.etSecondName);
        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
        etBirthDate = (EditText)findViewById(R.id.etBirthDate);
    }

    // Очистить текст во всех используемых EditView
    private void clearTextOnEditView() {
        etSecondName.setText(""); etFirstName.setText(""); etPhoneNumber.setText(""); etBirthDate.setText("");
    }

    // Обработчики нажатия клавиш сохранения
    public void onClick_SavePublic(View view) {
        Person person = new Person(etSecondName.getText().toString(), etFirstName.getText().toString(),
                etPhoneNumber.getText().toString(), etBirthDate.getText().toString());
        clearTextOnEditView();
        FileManager.writeToFile(person, filePublic);
        tvFile.setText(FileManager.readFromFile(filePublic));
    }
    public void onClick_SavePrivate(View view) {
        Person person = new Person(etSecondName.getText().toString(), etFirstName.getText().toString(),
                etPhoneNumber.getText().toString(), etBirthDate.getText().toString());
        clearTextOnEditView();
        FileManager.writeToFile(person, filePrivate);
        tvFile.setText(FileManager.readFromFile(filePrivate));
    }
}