package shm.dim.lab_1;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Integer colorRed, colorGreen, colorBlue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);

        initColorsObj();
    }

    private void initColorsObj()
    {
        colorRed = ContextCompat.getColor(this, R.color.colorRed);
        colorGreen = ContextCompat.getColor(this, R.color.colorGreen);
        colorBlue = ContextCompat.getColor(this, R.color.colorBlue);
    }

    // Установить цвет TextView по коду цвета
    private void setTextViewColor(int colotNumber) {
        switch (colotNumber)
        {
            case 0: textView.setBackgroundColor(colorRed); break;
            case 1: textView.setBackgroundColor(colorGreen); break;
            case 2: textView.setBackgroundColor(colorBlue); break;
        }
    }

    // Обработчикик кнопок
    public void onClickButtonRed(View view){
        setTextViewColor(0);
    }
    public void onClickButtonGreen(View view){
        setTextViewColor(1);
    }
    public void onClickButtonBlue(View view){
        setTextViewColor(2);
    }

}