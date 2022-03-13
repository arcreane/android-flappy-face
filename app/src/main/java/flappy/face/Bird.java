package flappy.face;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public class Bird extends BaseObject {
    private ArrayList<Bitmap> m_arrsBms = new ArrayList<>();
    private int mi_count, mi_vFlap, mi_idCurrentBitmap;
    private float mf_drop;

    public Bird() {
        mi_count = 0;
        mi_vFlap = 5;
        mi_idCurrentBitmap = 0;
        mf_drop = 0;
    }

    public void draw(Canvas canvas) {
        drop();
        canvas.drawBitmap(this.getBm(), mf_x, mf_y, null);
    }

    private void drop() {
        mf_drop += 0.6;
        mf_y += mf_drop;

    }

    public ArrayList<Bitmap> getArrsBms() {
        return m_arrsBms;
    }

    public void setArrsBms(ArrayList<Bitmap> p_arrsBms) {
        m_arrsBms = p_arrsBms;
        for (int i = 0; i < p_arrsBms.size(); i++) {
            m_arrsBms.set(i, Bitmap.createScaledBitmap(m_arrsBms.get(i), mi_width, mi_height, true));
        }
    }

    @Override
    public Bitmap getBm() {
        mi_count++;
        if (mi_count == mi_vFlap) {
            for (int i = 0; i < m_arrsBms.size(); i++) {
                if (i == m_arrsBms.size()-1) {
                    mi_idCurrentBitmap = 0;
                    break;
                } else if(mi_idCurrentBitmap == i) {
                    mi_idCurrentBitmap = i+1;
                    break;
                }
            }
            mi_count = 0;
        }
        if (mf_drop < 0) {
            Matrix matrix =  new Matrix();
            matrix.postRotate(-25);
            return Bitmap.createBitmap(m_arrsBms.get(mi_idCurrentBitmap), 0, 0, m_arrsBms.get(mi_idCurrentBitmap).getWidth(), m_arrsBms.get(mi_idCurrentBitmap).getHeight(), matrix, true);
        } else if (mf_drop > 0) {
            Matrix matrix =  new Matrix();
            if(mf_drop < 70) {
                matrix.postRotate(-25+(mf_drop*2));
            } else {
                matrix.postRotate(45);
            }
            return Bitmap.createBitmap(m_arrsBms.get(mi_idCurrentBitmap), 0, 0, m_arrsBms.get(mi_idCurrentBitmap).getWidth(), m_arrsBms.get(mi_idCurrentBitmap).getHeight(), matrix, true);
        }
        return m_arrsBms.get(mi_idCurrentBitmap);
    }

    public float getDrop() {
        return mf_drop;
    }

    public void setDrop(float p_drop) {
        mf_drop = p_drop;
    }
}
