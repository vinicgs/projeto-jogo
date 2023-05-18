import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Asteroide {
    private int posX;
    private int posY;
    private int x;
    private int y;
    private int raio;
    private int velocidade;
    private BufferedImage imagem;
    private int tempo = 0;



    public Asteroide(int x, int y, int raio, int velocidade) {
        // coordenada x aleatoria entre 0 e 800 - raio do asteroide
        // 800 = largura da tela do jogo (pixels)
        // - largura do asteroide (pixels).
        this.x = x;
        this.y =   y;
        this.raio = raio;
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

    public int getVelocidadeAsteroide() {

        while(true){
            tempo++;
            return velocidade = calcularVelocidade(tempo);
        }
    }

    public int calcularVelocidade(int tempo) {

        return 2 + tempo * 2;
    }

//    public void gerarPosicaoAleatoria() {
//        Random random = new Random();
//        // gera um número aleatório entre 0 e 800 - raio * 2
//        this.posY = random.nextInt(600 - raio * 2) + raio;
//    }

//    public void desenhar(Graphics g2d) {
//        g2d.drawImage(imagem, x, y, 30, 20, null);
//    }

    public void mover() {
        this.y += velocidade;
    }
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getRaio() {
        return raio;
    }

    public int getVelocidade() {
        return velocidade;
    }

}
