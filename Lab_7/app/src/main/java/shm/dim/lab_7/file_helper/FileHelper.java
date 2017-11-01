package shm.dim.lab_7.file_helper;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import shm.dim.lab_7.notes.Note;
import shm.dim.lab_7.notes.Notes;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class FileHelper {

    // Проверка наличия файла
    public static boolean exist(File file) {
        boolean rc;
        if(rc = file.exists())
            Log.d("Log_07", "Файл " + file.getName() + " существует");
        else
            Log.d("Log_07", "Файл " + file.getName() + " не найден");
        return rc;
    }

    // Создание файла
    public static void create(File file) {
        try {
            file.createNewFile();
            Log.d("Log_07", "Файл " + file.getName() + " создан");
        }
        catch (IOException e) {
            Log.d("Log_07", e.getMessage());
        }
    }

    // Запись в файл заметки
    public static void write(Notes notes, File file) {
        try (FileOutputStream fos = new  FileOutputStream(file, true)) {
            Gson gson = new Gson();
            String json = gson.toJson(notes);
            fos.write(json.getBytes());
        }
        catch (IOException e) {
            Log.d("Log_07", e.getMessage());
        }
    }
}