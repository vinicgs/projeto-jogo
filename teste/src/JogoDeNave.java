import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JogoDeNave extends JFrame {
    private int larguraJanela = 800; // Largura da janela do jogo
    private int alturaJanela = 600; // Altura da janela do jogo
    private int navePosX = 400; // Posição inicial da nave no eixo X
    private int navePosY = 500; // Posição inicial da nave no eixo Y
    private int velocidadeNave = 5; // Velocidade de movimento da nave
    private int asteroides = 3; // Número de asteroides
    private int[] asteroidePosX; // Array para armazenar as posições X dos asteroides
    private int[] asteroidePosY; // Array para armazenar as posições Y dos asteroides
    private int[] velocidadeAsteroide; // Array para armazenar as velocidades dos asteroides

    public JogoDeNave() {
        setTitle("Jogo de Nave"); // Título da janela do jogo
        setSize(larguraJanela, alturaJanela); // Define o tamanho da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define o comportamento padrão ao fechar a janela
        setResizable(false); // Impede que a janela seja redimensionada

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                moverNave(e); // Chama o método para mover a nave quando uma tecla for pressionada
            }
        });

        asteroidePosX = new int[asteroides]; // Inicializa o array de posições X dos asteroides
        asteroidePosY = new int[asteroides]; // Inicializa o array de posições Y dos asteroides
        velocidadeAsteroide = new int[asteroides]; // Inicializa o array de velocidades dos asteroides

        for (int i = 0; i < asteroides; i++) {
            asteroidePosX[i] = (int) (Math.random() * (larguraJanela - 50)); // Define uma posição aleatória no eixo X para o asteroide
            asteroidePosY[i] = (int) (Math.random() * -alturaJanela); // Define uma posição aleatória acima da janela para o asteroide
            velocidadeAsteroide[i] = 1; // Define uma velocidade fixa para o asteroide
        }

        setVisible(true); // Torna a janela visível
    }

    public void moverNave(KeyEvent e) {
        int tecla = e.getKeyCode(); // Obtém o código da tecla pressionada

        if (tecla == KeyEvent.VK_LEFT && navePosX > 0) {
            navePosX -= velocidadeNave; // Move a nave para a esquerda se a tecla esquerda foi pressionada e a nave não está na borda esquerda da janela
        }

        if (tecla == KeyEvent.VK_RIGHT && navePosX < larguraJanela - 50) {
            navePosX += velocidadeNave; // Move a nave para a direita se a tecla direita foi pressionada e a nave não está na borda direita da janela
        }

        repaint(); // Redesenha a tela para atualizar a posição da nave
    }

    public void update() {
        for (int i = 0; i < asteroides; i++) {
            asteroidePosY[i] += velocidadeAsteroide[i]; // Move o asteroide para baixo com base em sua velocidade

            if (asteroidePosY[i] > alturaJanela) {
                asteroidePosX[i] = (int) (Math.random() * (larguraJanela - 50)); // Reposiciona o asteroide no eixo X quando ele sai da janela
                asteroidePosY[i] = (int) (Math.random() * -alturaJanela); // Reposiciona o asteroide acima da janela quando ele sai da janela
            }

            if (asteroidePosX[i] < navePosX + 40 && asteroidePosX[i] + 40 > navePosX && asteroidePosY[i] < navePosY + 40 && asteroidePosY[i] + 40 > navePosY) {
                // Verifica se houve colisão entre a nave e o asteroide
                JOptionPane.showMessageDialog(this, "Você perdeu!"); // Exibe uma mensagem informando que o jogador perdeu
                System.exit(0); // Encerra o jogo
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(navePosX, navePosY, 40, 40); // Desenha a nave como um retângulo preto

        for (int i = 0; i < asteroides; i++) {
            g2d.setColor(Color.RED);
            g2d.fillOval(asteroidePosX[i], asteroidePosY[i], 40, 40); // Desenha os asteroides como círculos vermelhos
        }

        update(); // Atualiza a posição dos asteroides

        try {
            Thread.sleep(10); // Adiciona um pequeno atraso para reduzir a velocidade dos asteroides
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        repaint(); // Redesenha a tela para atualizar a posição dos asteroides
    }

    public static void main(String[] args) {
        new JogoDeNave(); // Cria uma instância do jogo
    }
}