package flappy.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import java.util.logging.LogRecord;

public class GameView extends View {
    private Bird m_bird;
    private Handler m_handler;
    private Runnable m_runnable;
    private ArrayList<Pipe> m_pipes;
    private int mi_sumPipes, mi_distance;

    public GameView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
        initBird();
        initPipe();
        m_handler = new Handler();
        m_runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    private void initPipe() {
        mi_sumPipes = 6;
        mi_distance = 300*Configs.SCREEN_HEIGHT/1920;
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

    public void draw(Canvas canvas){
        super.draw(canvas);
        m_bird.draw(canvas);
        for (int i = 0; i < mi_sumPipes; i++) {
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
        m_handler.postDelayed(m_runnable, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            m_bird.setDrop(-15);
        }
        return true;
    }
}
