package com.flappyface;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Bird extends BaseObject {
    private ArrayList<Bitmap> m_arrsBms = new ArrayList<>();

    public Bird() {

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.getBm(), mf_x, mf_y, null);
    }

    public ArrayList<Bitmap> getArrsBms() {
        return m_arrsBms;
    }

    public void setArrsBms(ArrayList<Bitmap> p_arrsBms) {
        m_arrsBms = p_arrsBms;
        for (int i = 0; i < p_arrsBms.size(); i++) {
            m_arrsBms.set(i, Bitmap.createScaledBitmap(this.m_arrsBms.get(i), mi_width, mi_height, true));
        }
    }

    @Override
    public Bitmap getBm() {
        return this.getArrsBms().get(0);
    }

}
