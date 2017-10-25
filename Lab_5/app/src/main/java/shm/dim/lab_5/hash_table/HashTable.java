package shm.dim.lab_5.hash_table;

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

import shm.dim.lab_5.file_manager.FileManager;
import shm.dim.lab_5.item.Item;

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

    // Получить строковое представление объекта item в формате json
    private static String getJsonString(Item item) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(item);
    }

    // Получить объект в формате json из строки
    private static JsonObject getJsonObject(String str) {
        return new JsonParser().parse(str.toString()).getAsJsonObject();
    }

    // Получить объект item из json объекта
    private static Item getItemFromJsonObject(JsonObject jsonObject) {
        return new Item(jsonObject.get("key").getAsString(),
                        jsonObject.get("value").getAsString());
    }

    // Возвращает значение true, если значение поля "key" json оъекта равно значению строки
    private static boolean isEqualValueFieldKeyJsonObjectString(String str, JsonObject jsonObject) {
        return str.equals(jsonObject.get("key").getAsString());
    }

    // Получить отформатированные подстроки
    private static String[] getFormatSubstrings(String str) {
        str = str.trim();
        if(str.indexOf(">") == 0)
            str = str.substring(1);
        return str.split(">");
    }

    // Записать в файл элемент
    private static void writeItemToFile(Item item, File file) throws FileNotFoundException {
        int hash = getHashCode(item.getKey());
        String str = getJsonString(item);
        FileManager.writeToFile(str, hash, file);
    }

    // Получить из файла объект по его ключу
    private static Item getItemFromFile(String key, File f) {
        Item item;
        String str;
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            int b = 32;
            do{
                str = raf.readLine();
                if (str == null)
                    break;
                else if(!str.equals("                                    ")) {
                    String[] subStr = getFormatSubstrings(str);
                    JsonObject jsonObject;
                    for(int i = 0; i < subStr.length; i ++) {
                        jsonObject = getJsonObject(subStr[i]);
                        if (isEqualValueFieldKeyJsonObjectString(key, jsonObject)) {
                            item = getItemFromJsonObject(jsonObject);
                            return item;
                        }
                    }
                }
            }while (b != -1);
        }
        catch (IOException e) {
            Log.d("Log_05", e.getMessage());
        }
        Log.d("Log_05", "Данные из файла " + f.getName() + " прочитанны");
        return null;
    }
}