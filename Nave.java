import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Nave {
    public int velX;
    private int x, y;
    int velocidade = 5;
    public BufferedImage imagem;

    public Nave(int x) {
        this.x = x;
        //posição de inicio
        this.y = 400;
        try {
            imagem = ImageIO.read(Objects.requireNonNull(getClass().getResource("./imgs/nave.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mover() {
        x += velX;
        if (x < 0) {
            x = 0;
        }
        int largura = 55;
        if (x > 640 - largura) {
            x = 640 - largura;
        }
        if (y < 0) {
            y = 0;
        }
        int altura = 55;
        if (y > 480 - altura) {
            y = 480 - altura;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void resetPosition() {
        this.x = 300;
        this.y = 400;
    }

    public boolean colisao(Asteroide asteroide) {

        int x1 = asteroide.getPosX();
        int y1 = asteroide.getPosY();
        int raio1 = asteroide.getRaio();
        int x2 = this.x;
        int y2 = this.y;
        int raio2 = 25;
        int distancia = (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        return distancia < raio1 + raio2;
    }
}


