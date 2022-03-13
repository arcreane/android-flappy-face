package flappy.face;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class BaseObject {
    protected float mf_x,mf_y;
    protected int mi_width, mi_height;
    protected Rect m_rect;
    protected Bitmap m_bm;

    public BaseObject() {
    }

    public BaseObject(float x, float y, int width, int height) {
        mf_x = x;
        mf_y = y;
        mi_width = width;
        mi_height = height;
    }

    public float getX() {
        return mf_x;
    }

    public void setX(float p_x) {
        mf_x = p_x;
    }

    public float getY() {
        return mf_y;
    }

    public void setY(float p_y) {
        mf_y = p_y;
    }

    public int getWidth() {
        return mi_width;
    }

    public void setWidth(int p_width) {
        mi_width = p_width;
    }

    public int getHeight() {
        return mi_height;
    }

    public void setHeight(int p_height) {
        mi_height = p_height;
    }

    public Bitmap getBm() {
        return m_bm;
    }

    public void setBm(Bitmap p_bm) {
        m_bm = p_bm;
    }

    public Rect getRect() {
        return new Rect((int)mf_x, (int)mf_y, (int)mf_x+mi_width, (int)mf_y+mi_height);
    }

    public void setRect(Rect p_rect) {
        m_rect = p_rect;
    }
}
