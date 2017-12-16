package shm.dim.lab_6_birthdays;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MainActivity extends AppCompatActivity {

    private File filePublic, filePrivate;
    private EditText etSecondName, etFirstName, etPhoneNumber, etBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        filePublic = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "ContactsPublic.json");
        filePrivate  = new File(super.getExternalFilesDir(Environment.DIRECTORY_DCIM), "ContactsPrivate.json");
    }

    // Инициализировать все используемые View
    private void initViews() {
        etSecondName = (EditText)findViewById(R.id.etSecondName);
        etFirstName = (EditText)findViewById(R.id.etFirstName);
        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
        etBirthday = (EditText)findViewById(R.id.etBirthday);
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

    // Проверка формата введенной даты рождения
    private boolean checkFormatDate(String str) {
        String DATE_FORMAT = "dd.MM.yyyy";
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        return df.parse(str, new ParsePosition(0)) != null;
    }

    // Получить из файла объект по дню его рождения
    public Person getPersonFromFile(String birthDate, File f) {
        Person person = null;
        String str = new String();
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            int b = raf.read();
            do{
                str += "{" + raf.readLine();
                JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
                if(birthDate.equals(jsonObject.get("birthday").getAsString())) {
                    person = new Person(jsonObject.get("secondName").getAsString(),
                            jsonObject.get("firstName").getAsString(),
                            jsonObject.get("phoneNumber").getAsString(),
                            jsonObject.get("birthday").getAsString());
                    break;
                }
                str = new String();
                if ( b == 123)
                    b = raf.read();
            }while (b != -1);
        }
        catch (IOException e) {
            Log.d("Log_06", e.getMessage());
            Log.d("Log_06", "Не удалось прочитать данные из файла " + f.getName());
            createDialogMsg("Не удалось прочитать данные из файла ContactsPrivate.json");
        }
        Log.d("Log_06", "Данные из файла " + f.getName() + " прочитанны");
        return person;
    }

    // Обработчики нажатия клавиш сохранения
    public void onClick_GetPublic(View view) {
        Person person = getPersonFromFile(etBirthday.getText().toString(), filePublic);
        if(checkFormatDate(person.getBirthday())) {
            etSecondName.setText(person.getSecondName());
            etFirstName.setText(person.getFirstName());
            etPhoneNumber.setText(person.getPhoneNumber());
        }
        else
            checkFormatDate("Неверный формат даты рождения");
    }
    public void onClick_GetPrivate(View view) {
        Person person = getPersonFromFile(etBirthday.getText().toString(), filePrivate);
    }
}