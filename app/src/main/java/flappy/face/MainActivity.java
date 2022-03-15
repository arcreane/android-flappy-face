package flappy.face;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static TextView m_txt_score, m_txt_best_score, m_txt_score_over;
    public static RelativeLayout m_rl_game_over;
    public static Button m_btn_start;
    private GameView m_gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Configs.SCREEN_WIDTH = dm.widthPixels;
        Configs.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.activity_main);
        m_txt_score = findViewById(R.id.txt_score);
        m_txt_best_score = findViewById(R.id.txt_best_over);
        m_txt_score_over = findViewById(R.id.txt_score_over);
        m_rl_game_over = findViewById(R.id.game_over_screen);
        m_btn_start = findViewById(R.id.btn_start);
        m_gv = findViewById(R.id.game_view);
    }
}