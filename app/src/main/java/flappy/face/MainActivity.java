package flappy.face;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    public static TextView m_txt_score, m_txt_best_score, m_txt_score_over;
    public static RelativeLayout m_rl_game_over;
    public static Button m_btn_start, m_btn_media;
    private GameView m_gv;
    private MediaPlayer m_media_player;


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
        m_btn_media = findViewById(R.id.btn_media);
        m_gv = findViewById(R.id.game_view);
        m_btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_gv.setStart(true);
                m_txt_score.setVisibility(v.VISIBLE);
                m_btn_start.setVisibility(v.INVISIBLE);
            }
        });
        m_rl_game_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_btn_start.setVisibility(v.VISIBLE);
                m_rl_game_over.setVisibility(v.INVISIBLE);
                m_gv.setStart(false);
                m_gv.reset();
            }
        });
        m_btn_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(galleryIntent);
            }
        });
        m_media_player = MediaPlayer.create(this, R.raw.sillychipsong);
        m_media_player.setLooping(true);
        m_media_player.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        m_media_player.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        m_media_player.pause();
    }
}