package flappy.face;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Bird extends BaseObject {
    private ArrayList<Bitmap> m_arrsBms = new ArrayList<>();
    private int mi_count, mi_vFlap, mi_idCurrentBitmap;
    public Bird() {
        mi_count = 0;
        mi_vFlap = 5;
        mi_idCurrentBitmap = 0;
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
        return m_arrsBms.get(mi_idCurrentBitmap);
    }
}
