package flappy.face;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static TextView m_txt_score, m_txt_best_score, m_txt_score_over, m_fake_notification;
    public static RelativeLayout m_rl_game_over;
    public static Button m_btn_start, m_btn_media;
    private GameView m_gv;
    private MediaPlayer m_media_player;
    private Handler m_handler = new Handler();
    private Runnable runnable;

    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

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
        m_fake_notification = findViewById(R.id.fake_notification);
        m_rl_game_over = findViewById(R.id.game_over_screen);
        m_btn_start = findViewById(R.id.btn_start);
        m_btn_media = findViewById(R.id.btn_media);
        m_gv = findViewById(R.id.game_view);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    ArrayList<Bitmap> arrBms = (ArrayList<Bitmap>) result.getData().getExtras().get("DATA");
                    m_gv.getM_bird().setArrsBms(arrBms);
                }
            }
        });

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
                if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                    activityResultLauncher.launch(galleryIntent);
                }
                else {
                    Toast.makeText(MainActivity.this, "There is no app that support this action",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        m_media_player = MediaPlayer.create(this, R.raw.sillychipsong);
        m_media_player.setLooping(true);
        m_media_player.start();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }


    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 12) {
                m_fake_notification.setHeight(0);
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        m_media_player.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
        m_media_player.pause();
    }
}