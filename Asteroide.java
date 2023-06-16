import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Asteroide {
    public int x, y, h, w;
    public int velocidade;
    public Image imagem;

    public Asteroide(int x, int y, int h, int w, int velocidade) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.velocidade = velocidade;
        try {
            imagem = ImageIO.read(Objects.requireNonNull(getClass().getResource("./imgs/asteroide.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desenhar(Graphics g2d) {
            g2d.drawImage(imagem, x, y, 40, 40, null);
    }
    public void mover() {
        this.y += velocidade;
    }
}
