import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Asteroide {
    public int posX;
    public int posY;
    public int x, y, h, w;

    public int velocidade;
    public BufferedImage imagem;

    public Asteroide(int x, int y, int h, int w, int velocidade) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.velocidade = velocidade;
        try {
            imagem = ImageIO.read(Objects.requireNonNull(getClass().getResource("./imgs/tiro.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desenhar(Graphics g2d) {

            g2d.drawImage(imagem, x, y, 80, 80, null);

    }

    public void mover() {

            this.y += velocidade;

    }
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }



}
