package shm.dim.lab_5.file_manager;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class FileManager {

    // Проверка наличия файла в External памяти
    public static boolean existFile(File file) {
        boolean rc;
        if(rc = file.exists())
            Log.d("Log_05", "Файл " + file.getName() + " существует");
        else
            Log.d("Log_05", "Файл " + file.getName() + " не найден");
        return rc;
    }

    // Создание файла в External памяти
    public static void createFile(File file) {
        try {
            file.createNewFile();
            Log.d("Log_05", "Файл " + file.getName() + " создан");
        }
        catch (IOException e) {
            Log.d("Log_05", e.getMessage());
        }
    }

    // Записать в файл 10 строк
    public static void initLineToFile(File file){
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            for(int i = 0; i < 50; i++)
                raf.writeBytes("                                    \n");
        } catch (IOException e){
            Log.d("Log_05", e.getMessage());
        }
    }

    // Запись в файл строку
    public static void writeToFile(String str, int line, File file) {
        try (RandomAccessFile wRaf = new RandomAccessFile(file, "rw")) {
            String s;
            try ( RandomAccessFile rRaf = new RandomAccessFile(file, "rw")) {
                rRaf.skipBytes(37*line);
                s = rRaf.readLine();
            }
            wRaf.skipBytes(37*line);
            wRaf.writeBytes(">" + str.concat(s));
            Log.d("Log_05", "Данные: " + str + " записаны в файл: " + file.getName() + "; в строку: " + line);
        }
        catch (IOException e) {
            Log.d("Log_05", e.getMessage());
        }
    }

    // Прочитать файл
    public static String readFile(File file) {
        String str = new String();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            int b = raf.read();
            while(b != -1) {
                str += (char)b;
                b = raf.read();
            }
            Log.d("Log_05", "Данные из файла " + file.getName() + " прочитаны");
        }
        catch (IOException e) {
            Log.d("Log_05", e.getMessage());
        }
        return str;
    }
}