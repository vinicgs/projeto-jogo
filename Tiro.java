import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tiro {
    private final int x;
    private int y;
    private final int velocidade;

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

    public boolean colisao(Asteroide asteroide) {
        int x1 = asteroide.getPosX();
        int y1 = asteroide.getPosY();
        int raio1 = asteroide.getRaio();

        int x2 = this.x;
        int y2 = this.y;
        int raio2 = 5;

        int dx = x1 - x2;
        int dy = y1 - y2;
        int distancia = (int) Math.sqrt(dx * dx + dy * dy);

        return distancia < raio1 + raio2;
    }
}
