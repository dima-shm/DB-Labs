package shm.dim.lab_7.file_helper;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import shm.dim.lab_7.note.Note;

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
    public static void write(ArrayList<Note> notes, File file) {
        try (FileOutputStream fos = new  FileOutputStream(file, true)) {
            Gson gson = new Gson();
            String json = gson.toJson(notes);
            fos.write(json.getBytes());
        }
        catch (IOException e) {
            Log.d("Log_07", e.getMessage());
        }
    }

    // Считать заметки из файла
    public static ArrayList<Note> read(File file) {
        ArrayList<Note> notes = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            String json = new String (bytes);
            notes = new Gson().fromJson(json, new TypeToken<ArrayList<Note>>() {}.getType());
        }
        catch (IOException e) {
            Log.d("Log_07", e.getMessage());
        }
        return notes;
    }
}