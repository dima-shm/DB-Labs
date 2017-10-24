package shm.dim.lab_5;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class HashTable {

    // Поулчить хеш-код
    public static int getHashCode(String key) {
        return (key.hashCode() % 10 * key.length());
    }

    // Добавить элемент
    public static void add(Item item, File file) throws FileNotFoundException {
        writeItemToFile(item, file);
    }

    // Найти элемент по ключу
    public static Item find(Item item, File file) throws FileNotFoundException {
        Item newItem = getItemFromFile(item.getKey(), file);
        if(newItem != null) {
            Log.d("Log_05", "Найдено значение: " + item.getValue() + " в файле: " + file.getName() + " в строке: " + item.getKey());
            return newItem;
        }
        else
            return null;
    }

    // Записать в файл элемент
    private static void writeItemToFile(Item item, File file) throws FileNotFoundException {
        int hash = getHashCode(item.getKey());
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(item);
        FileManager.writeToFile(str, hash, file);
    }

    // Получить из файла объект по его ключу
    private static Item getItemFromFile(String key, File f) {
        Item item = new Item();
        String str;
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            int b = 32;
            do{
                str = raf.readLine();
                if (str == null)
                    break;
                else if(!str.equals("                                    ")) {
                    str = str.trim();
                    if(str.indexOf("-") == 0)
                        str = str.substring(1);
                    String[] subStr = str.split("-");
                    JsonObject jsonObject;
                    for(int i = 0; i < subStr.length; i ++) {
                        jsonObject = new JsonParser().parse(subStr[i].toString()).getAsJsonObject();
                        if (key.equals(jsonObject.get("key").getAsString())) {
                            item = new Item(jsonObject.get("key").getAsString(),
                                    jsonObject.get("value").getAsString());
                            return item;
                        }
                    }
                }
                else {
                    item = null;
                }
                if (b == 123)       // Если символ ""
                    b = raf.read(); // Пропускаем этот символ и читаем дальше
            }while (b != -1);
        }
        catch (IOException e) {
            Log.d("Log_06", e.getMessage());
        }
        Log.d("Log_06", "Данные из файла " + f.getName() + " прочитанны");
        return item;
    }
}