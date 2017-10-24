package shm.dim.lab_6_contacts;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class FileManager extends AppCompatActivity {

    // Проверка наличия файла в External памяти
    private static boolean exist(File file) {
        boolean rc;
        if(rc = file.exists())
            Log.d("Log_06", "Файл " + file.getName() + " существует");
        else
            Log.d("Log_06", "Файл " + file.getName() + " не найден");
        return rc;
    }

    // Создание файла в External памяти
    private static void create(File file) {
        try {
            file.createNewFile();
            Log.d("Log_06", "Файл " + file.getName() + " создан");
        }
        catch (IOException e) {
            Log.d("Log_06", e.getMessage());
        }
    }

    // Запись в файл
    public static void write(Person person, File f) {
        if(!exist(f)) {
            create(f);
        }
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(person);
        try (RandomAccessFile raf = new RandomAccessFile(f, "rw")) {
            raf.skipBytes((int)f.length());
            raf.writeBytes(str + "\n");
            Log.d("Log_06", "Данные записаны в файл " + f.getName());
        }
        catch (IOException e) {
            Log.d("Log_06", e.getMessage());
        }
    }

    // Прочитать из файла
    public static String read(File f) {
        String str = new String();
        StringBuilder sb = new StringBuilder();
        Gson gson = new GsonBuilder().create();
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            int b = raf.read();
            do{
                str += "{" + raf.readLine();
                gson.fromJson(str, Person.class);
                sb.append(str + "\n");
                str = new String();
                if ( b == 123)      // Если символ ""
                    b = raf.read(); // Пропускаем этот символ и читаем дальше
            }while (b != -1);
        }
        catch (IOException e) {
            Log.d("Log_06", "Не удалось прочитать данные из файла " + f.getName());
        }
        Log.d("Log_06", "Данные из файла " + f.getName() + " прочитанны");
        return sb.toString();
    }
}