import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tiro {
    public int x, y;
    public final int velocidade;

    public Tiro(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocidade =  5;
    }
    public void mover() {
        this.y -= velocidade;
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.YELLOW); // cor do tiro
        g.fillRect(this.x, this.y, 2, 10); // desenha o tiro
    }

    public boolean getPosY() {
        return y < 0;
    }

}
