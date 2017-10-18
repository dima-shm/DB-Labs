package shm.dim.lab_3;


import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView textViewAbsolute,
            textViewName,
            textViewPath,
            textViewReadWrite,
            textViewExternal;
    private File file;
    private String m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        m = Environment.getExternalStorageState();
    }

    // Инициализировать все используемые View
    private void initViews() {
        textViewAbsolute = (TextView) findViewById(R.id.textViewAbsolute);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewPath = (TextView) findViewById(R.id.textViewPath);
        textViewReadWrite = (TextView) findViewById(R.id.textViewReadWrite);
        textViewExternal = (TextView) findViewById(R.id.textViewExternal);
    }

    // Установить текс во все используемые View
    private void setTextOnTextViews(String absolute,
                                    String name,
                                    String path,
                                    boolean read,
                                    boolean write,
                                    boolean external) {
        textViewAbsolute.setText(absolute);
        textViewName.setText(name);
        textViewPath.setText(path);
        textViewReadWrite.setText(((read==true) ? "yes":"no") + '/' + ((write==true) ? "yes":"no"));
        textViewExternal.setText(external== true ? "mounted":"not mounted");

    }

    // Обработчики кнопок
    public void onButtonClick_GetFilesDir(View view) {
        file = super.getFilesDir();
        setTextOnTextViews(file.getAbsolutePath(), file.getName(), file.getPath(),
                            file.canRead(), file.canWrite(), Environment.MEDIA_MOUNTED.equals(m));
    }
    public void onButtonClick_GetCacheDir(View view) {
        file = super.getCacheDir();
        setTextOnTextViews(file.getAbsolutePath(), file.getName(), file.getPath(),
                file.canRead(), file.canWrite(), Environment.MEDIA_MOUNTED.equals(m));
    }
    public void onButtonClick_GetExternalFielsDir(View view) {
        file = super.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        setTextOnTextViews(file.getAbsolutePath(), file.getName(), file.getPath(),
                file.canRead(), file.canWrite(), Environment.MEDIA_MOUNTED.equals(m));
    }
    public void onButtonClick_GetExternalCacheDir(View view) {
        file = super.getExternalCacheDir();
        setTextOnTextViews(file.getAbsolutePath(), file.getName(), file.getPath(),
                file.canRead(), file.canWrite(), Environment.MEDIA_MOUNTED.equals(m));
    }
    public void onButtonClick_GetExternalStoregeDir(View view) {
        file = Environment.getExternalStorageDirectory();
        setTextOnTextViews(file.getAbsolutePath(), file.getName(), file.getPath(),
                file.canRead(), file.canWrite(), Environment.MEDIA_MOUNTED.equals(m));
    }
    public void onButtonClick_GetExternalStoregePublicDir(View view) {
        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        setTextOnTextViews(file.getAbsolutePath(), file.getName(), file.getPath(),
                file.canRead(), file.canWrite(), Environment.MEDIA_MOUNTED.equals(m));
    }
}