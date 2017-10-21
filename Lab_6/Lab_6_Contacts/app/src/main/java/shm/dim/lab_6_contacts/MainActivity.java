package shm.dim.lab_6_contacts;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

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

    // Диалоговое окно для сообщения о создании файла
    private void createDialogMsg(String msg) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(msg).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Log.d("Log_06", "Сообщение об ошибке");
                    }
                });
        AlertDialog ad = b.create();
        ad.show();
    }

    // Проверка формата введенного номера телефона
    private boolean checkFormatPhoneNumber(String str) {
        String reg = "^[+][0-9]{10,13}$";
        return str.matches(reg);
    }

    // Проверка формата введенной даты рождения
    private boolean checkFormatDate(String str) {
        String DATE_FORMAT = "dd.MM.yyyy";
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        return df.parse(str, new ParsePosition(0)) != null;
    }

    // Обработчики нажатия клавиш сохранения
    public void onClick_SavePublic(View view) {
        Person person = new Person(etSecondName.getText().toString(), etFirstName.getText().toString(),
                etPhoneNumber.getText().toString(), etBirthDate.getText().toString());
        clearTextOnEditView();
        if(checkFormatPhoneNumber(person.getPhoneNumber()) && checkFormatDate(person.getBirthDate())) {
            FileManager.writeToFile(person, filePublic);
            tvFile.setText(FileManager.readFromFile(filePublic));
        }
        else
            createDialogMsg("Неверный формат телефона и даты рождения");
    }
    public void onClick_SavePrivate(View view) {
        Person person = new Person(etSecondName.getText().toString(), etFirstName.getText().toString(),
                etPhoneNumber.getText().toString(), etBirthDate.getText().toString());
        clearTextOnEditView();
        if(checkFormatPhoneNumber(person.getPhoneNumber()) && checkFormatDate(person.getBirthDate())) {
            FileManager.writeToFile(person, filePrivate);
            tvFile.setText(FileManager.readFromFile(filePrivate));
        }
        else
            createDialogMsg("Неверный формат телефона и даты рождения");
    }
}