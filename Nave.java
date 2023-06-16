import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Nave {
    public int velX;
    public int x, y;
    public int h;
    public int w;
    int velocidade = 5;
    public BufferedImage imagem;

    public Nave(int x) {
        this.x = x;
        //posição de inicio
        this.y = 400;
        this.h = 55;
        this.w = 55;
        try {
            imagem = ImageIO.read(Objects.requireNonNull(getClass().getResource("./imgs/nave.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mover() {
        this.x += velX;
        if (this.x < 0) {
            this.x = 0;
        }
        if (this.x > Principal.LARGURA_TELA - 55) {
            this.x = Principal.LARGURA_TELA - 55;
        }
    }


}


