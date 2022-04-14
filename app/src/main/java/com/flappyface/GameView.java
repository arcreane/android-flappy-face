package com.flappyface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameView extends View {
    private Bird m_bird;
    private Handler m_handler;
    private Runnable m_runnable;

    public GameView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
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
    }
}
