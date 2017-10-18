package shm.dim.lab_5;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class HashTable {

    private static final int MAXLENGHTSTR = 10;

    // Поулчить хеш-код
    public static int getHashCode(String key) {
        return (key.hashCode() % MAXLENGHTSTR * key.length());
    }

    // Добавить элемент
    public static void insertItem(String key, String value, File file) {
        Item item = new Item(key, value);
        int hash = getHashCode(item.getKey());
        writeLineToFile(hash, item.getValue(), file);
    }

    // Получить значение по ключу
    public static String getValueOnKey(String key, File file) throws FileNotFoundException {
        int hash = getHashCode(key);
        String str;
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            raf.seek(11 * hash);
            str = raf.readLine();
            if(str != null) {
                Log.d("Log_05", "Найдено значение в файле: " + file.getName() + "; В строке: " + hash + "; Значение: " + str);
                return str;
            }
        } catch (IOException e){
            Log.d("Log_05", e.getMessage());
        }
        return "Значение не найдено";
    }

    // Получить преобразованную строку
    public static String getFormatString(String str) {
        String formatStr = new String();
        formatStr = formatStr.concat(str);
        if(str.length() < MAXLENGHTSTR) {
            for (int i = (MAXLENGHTSTR - str.length()); i != 0; i--)
                formatStr = formatStr.concat(" "); // Заполнить пробелами оставшееся пространство
        }
        return formatStr;
    }

    // Записать в файл значение
    public static void initLineToFile(File file){
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            for(int i = 0; i < 10; i++)
                raf.writeBytes("          " + "\n");
        } catch (IOException e){
            Log.d("Log_05", e.getMessage());
        }
    }

    // Записать в файл значение
    public static void writeLineToFile(int key, String value, File file){
        String str = getFormatString(value);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.seek(11 * key);
            raf.writeBytes(str + "\n");
            Log.d("Log_05", "Данные записаны в файл: " + file.getName() + "; В строку: " + key + "; Значение: " + str);
        } catch (IOException e){
            Log.d("Log_05", e.getMessage());
        }
    }

    // Прочитать данные из файла
    public static String readDataFromFile(File file){
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