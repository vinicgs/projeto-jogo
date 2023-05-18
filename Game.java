import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Game extends JPanel {
    private final Nave nave;

    private final Asteroide asteroide;

    private ArrayList<Asteroide> asteroides;
    private boolean k_direita = false;
    private boolean k_esquerda = false;
    private final ArrayList<Tiro> tiros;
    private int pontuacao;

    public Game() {
        setFocusable(true);
        setLayout(null);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE -> tiros.add(new Tiro(nave.getX() + 35, nave.getY()));
                    case KeyEvent.VK_LEFT -> k_esquerda = true;
                    case KeyEvent.VK_RIGHT -> k_direita = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> k_esquerda = false;
                    case KeyEvent.VK_RIGHT -> k_direita = false;
                }
            }
        });

        nave = new Nave(100);
        tiros = new ArrayList<>();
        asteroides = new ArrayList<>();
        pontuacao = 0;
        asteroide = new Asteroide(0, 0, 0, 0);


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                gameloop();
            }
        });
        t1.start();
    }

    public void gameloop(){
            long asteroidCreationDelay = 1000; // Delay in milliseconds
            long lastAsteroidCreationTime = System.currentTimeMillis();

            while (true) {
                handlerEvents();

                // cria um novo asteroide a cada 1 segundo (1000 milisegundos) e adiciona na lista de asteroides do jogo
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastAsteroidCreationTime >= asteroidCreationDelay) {
                    asteroides.add(new Asteroide((int) (Math.random() * (Principal.LARGURA_TELA - 50)), 0, 5, 2));
                    lastAsteroidCreationTime = currentTime;
                }


                for (Asteroide asteroide : asteroides) {
                    asteroide.mover();
                    if (asteroide.getPosY() > 600) {
                        asteroides.remove(asteroide);
                        pontuacao++;
                    }
                }

                update();
                repaint();

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    public void handlerEvents() {
        if (k_esquerda && !k_direita) {
            nave.velX = -nave.velocidade;
        } else if (k_direita && !k_esquerda) {
            nave.velX = nave.velocidade;
        } else {
            nave.velX = 0;
        }
    }

    public void update() {
        handlerEvents(); // to update the ship
        nave.mover();
//        asteroides.add(new Asteroide((int) (Math.random() * 800) , 0, 5, 2));
//        asteroide.mover();

        for (int i = 0; i < tiros.size(); i++) {
            Tiro tiro = tiros.get(i);
            tiro.mover();
            if (tiro.getPosY()) {
                tiros.remove(i);
                i--;
            }
        }


    }


    @Override
    protected void paintComponent(Graphics g) {
        // desenha o fundo
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        // desenha a nave
        g.drawImage(nave.imagem, nave.getX(), nave.getY(), null);

        g.setColor(Color.WHITE);
        g.drawString("Pontuação: " + pontuacao, 20, 20);

        for (Asteroide asteroide : asteroides) {
            asteroide.desenhar(g);
        }

        // desenha os tiros
        for (Tiro tiro : tiros) {
            tiro.desenhar(g);
        }
    }
}
