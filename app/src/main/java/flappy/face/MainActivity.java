package flappy.face;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static TextView m_txt_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Configs.SCREEN_WIDTH = dm.widthPixels;
        Configs.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_main);
        m_txt_score = findViewById(R.id.txt_score);
    }
}