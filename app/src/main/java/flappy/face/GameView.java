package flappy.face;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private Bird m_bird;
    private Handler m_handler;
    private Runnable m_runnable;
    private ArrayList<Pipe> m_pipes;
    private int mi_sumPipes, mi_distance;
    private int mi_score, mi_best_score = 0;
    private boolean mb_start;
    private Context m_context;
    private int mi_sound_jump;
    private float mf_volume;
    private boolean mb_loaded_sound;
    private SoundPool m_soundPool;

    public GameView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        m_context = context;
        SharedPreferences sp = context.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
        if (sp != null) {
            mi_best_score = sp.getInt("BestScore", 0);
        }
        mi_score = 0;
        mb_start  = false;
        initBird();
        initPipe();
        m_handler = new Handler();
        m_runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
            m_soundPool = builder.build();
        } else {
            m_soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        m_soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mb_loaded_sound = true;
            }
        });
        mi_sound_jump = m_soundPool.load(context, R.raw.jump_02, 1);
    }

    private void initPipe() {
        mi_sumPipes = 6;
        mi_distance = 400*Configs.SCREEN_HEIGHT/1920;
        m_pipes = new ArrayList<>();
        for (int i = 0; i < mi_sumPipes; i++) {
            if (i < mi_sumPipes/2) {
                m_pipes.add(new Pipe(Configs.SCREEN_WIDTH+i*((Configs.SCREEN_WIDTH+200*Configs.SCREEN_WIDTH/1080)/(mi_sumPipes/2)), 0, 200*Configs.SCREEN_WIDTH/1080, Configs.SCREEN_HEIGHT/2));
                m_pipes.get(m_pipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe2));
                m_pipes.get(m_pipes.size()-1).randomY();
            } else {
                m_pipes.add(new Pipe(m_pipes.get(i-mi_sumPipes/2).getX(), m_pipes.get(i-mi_sumPipes/2).getY()+m_pipes.get(i-mi_sumPipes/2).getHeight()+ mi_distance, 200*Configs.SCREEN_WIDTH/1080, Configs.SCREEN_HEIGHT/2));
                m_pipes.get(m_pipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.pipe1));
            }
        }
    }

    private void initBird() {
        m_bird = new Bird();
        m_bird.setWidth(100*Configs.SCREEN_WIDTH/1080);
        m_bird.setHeight(100*Configs.SCREEN_HEIGHT/1920);
        m_bird.setX(100*Configs.SCREEN_WIDTH/1080);
        m_bird.setY(Configs.SCREEN_HEIGHT/2-m_bird.getHeight()/2);

        ArrayList<Bitmap> arrBms = new ArrayList<>();
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird1));
        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.bird2));
        m_bird.setArrsBms(arrBms);
    }

    private void triggerFakeNotif() {
        if (Math.random() < 0.001) {
            MainActivity.m_fake_notification.setVisibility(VISIBLE);
            MainActivity.m_fake_notification.setHeight((int) (Configs.SCREEN_HEIGHT/3));
            MainActivity.m_fake_notification.setText("Lorem ipsum dolor sit amet");
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        if (mb_start) {
            triggerFakeNotif();
            m_bird.draw(canvas);
            for (int i = 0; i < mi_sumPipes; i++) {
                //Check game over event, if bird hit a pipe, game over
                if (m_bird.getRect().intersect(m_pipes.get(i).getRect())
                        || m_bird.getY()-m_bird.getHeight() < 0
                        || m_bird.getY() > Configs.SCREEN_HEIGHT) {
                    Pipe.mi_speed = 0;
                    MainActivity.m_txt_score_over.setText(MainActivity.m_txt_score.getText());
                    MainActivity.m_txt_best_score.setText("Best: "+mi_best_score);
                    MainActivity.m_txt_score.setVisibility(INVISIBLE);
                    MainActivity.m_rl_game_over.setVisibility(VISIBLE);
                    MainActivity.m_fake_notification.setHeight(0);
                }
                //Condition to increment score and high score
                if (m_bird.getX()+m_bird.getWidth() > m_pipes.get(i).getX()+m_pipes.get(i).getWidth()/2
                        && m_bird.getX()+m_bird.getWidth() <= m_pipes.get(i).getX()+m_pipes.get(i).getWidth()/2+Pipe.mi_speed
                        && i < mi_sumPipes/2) {
                    mi_score++;
                    if (mi_score > mi_best_score) {
                        mi_best_score = mi_score;
                        SharedPreferences sp = m_context.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("bestScore", mi_best_score);
                        editor.apply();
                    }
                    MainActivity.m_txt_score.setText(""+mi_score);
                }
                //Generate pipes
                if(m_pipes.get(i).getX() < -m_pipes.get(i).getWidth()) {
                    m_pipes.get(i).setX(Configs.SCREEN_WIDTH);
                    if (i < mi_sumPipes/2) {
                        m_pipes.get(i).randomY();
                    } else {
                        m_pipes.get(i).setY(m_pipes.get(i-mi_sumPipes/2).getY()+m_pipes.get(i-mi_sumPipes/2).getHeight() + mi_distance);
                    }
                }
                m_pipes.get(i).draw(canvas);
            }
        } else {
            if (m_bird.getY() > Configs.SCREEN_HEIGHT/2){
                m_bird.setDrop(-15*Configs.SCREEN_HEIGHT/1920);
            }
            m_bird.draw(canvas);
        }
        m_handler.postDelayed(m_runnable, 10);
    }

    //When click, play a sound and bird jump
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            m_bird.setDrop(-15);
            if (mb_loaded_sound) {
                int stream_id = m_soundPool.play(mi_sound_jump, (float)0.5, (float)0.5, 1, 0, 1f);
            }
        }
        return true;
    }

    public boolean isStarted() {
        return mb_start;
    }

    public void setStart(boolean p_start) {
        mb_start = p_start;
    }

    public void reset() {
        MainActivity.m_txt_score.setText("0");
        mi_score = 0;
        initPipe();
        initBird();
    }
}
