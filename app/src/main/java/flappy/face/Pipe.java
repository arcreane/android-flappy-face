package flappy.face;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Pipe extends BaseObject {
    public static int mi_speed;

    public Pipe(float x, float y, int width, int height) {
        super(x, y, width, height);
        mi_speed = 10*Configs.SCREEN_WIDTH/1080;
    }

    public void draw(Canvas canvas) {
        mf_x -= mi_speed;
        canvas.drawBitmap(m_bm, mf_x, mf_y, null);
    }

    public void randomY() {
        Random r = new Random();
        mf_y = r.nextInt((0+mi_height/4)+1)-mi_height/4;
    }

    @Override
    public void setBm(Bitmap p_bm) {
        m_bm = Bitmap.createScaledBitmap(p_bm, mi_width, mi_height, true);
    }
}
