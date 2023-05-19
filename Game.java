import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;

public class Game extends JPanel {
    private final Nave nave;
    private Stack<Asteroide> asteroidesDisponiveis;
    private ArrayList<Asteroide> asteroidesNaTela;
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
                    case KeyEvent.VK_SPACE -> tiros.add(new Tiro(nave.x + 35, nave.y));
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
        asteroidesDisponiveis = new Stack<>();
        for (int i = 0; i < 10; i++) {
            asteroidesDisponiveis.push(new Asteroide(0, 0, 55, 55, 2));
        }
        asteroidesNaTela = new ArrayList<>(10);
        pontuacao = 0;

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                gameloop();
            }
        });
        t1.start();
    }

    public void gameloop(){
            long tempoAnterior = System.currentTimeMillis();
            long tempoAtual = 0;
            long deltaTime = 0;

            while (true) {
                    // cria um novo asteroide a cada 1 segundo (1000 milisegundos) e adiciona na lista de asteroides do jogo
                    tempoAtual = System.currentTimeMillis();
                    deltaTime = tempoAtual - tempoAnterior;
                    tratadorDeEventos();
                    update(deltaTime);
                    repaint();
                    tempoAnterior = tempoAtual;
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

    public void tratadorDeEventos() {
        if (k_esquerda && !k_direita) {
            nave.velX = -nave.velocidade;
        } else if (k_direita && !k_esquerda) {
            nave.velX = nave.velocidade;
        } else {
            nave.velX = 0;
        }
    }

    long accTempo = 0; // acumulador de tempo
    long intervaloTempo = 1000; // gera o asteroid a cada 1 segundo
    public void update(long deltaTime) {
        // relogio para gerar um novo asteroide na tela
        accTempo += deltaTime;
        if(accTempo >= intervaloTempo){
            accTempo = 0;
            //gerar um novo asteroide
            Asteroide asteroide = asteroidesDisponiveis.pop();
            asteroide.x = (int) (Math.random() * (Principal.LARGURA_TELA - 50));
            asteroide.y = -50;
            asteroidesNaTela.add(asteroide);
        }
        nave.mover(); // move a nave

        // para cada asteroide na lista de asteroides  move o asteroide
        for (Asteroide asteroide : asteroidesNaTela) {
            asteroide.mover();
        }
        // para cada tiro na lista de tiros move o tiro
        for (int i = 0; i < tiros.size(); i++) {
            Tiro tiro = tiros.get(i);
            tiro.mover();
            if (tiro.getPosY()) {
                tiros.remove(i);
                i--;
            }
        }
        checarColisoes();
    }
    public void checarColisoes(){
        //checa colisoes dos asteroides com a parte de baixo da tela
        for(int i=0;i<asteroidesNaTela.size();i++){
            Asteroide asteroide = asteroidesNaTela.get(i);
            if(asteroide.y > Principal.ALTURA_TELA){
                asteroidesDisponiveis.push(asteroide);
                asteroidesNaTela.remove(asteroide);
                pontuacao--;
            }
        }

        //checa colisoes dos tiros com os asteroides
        for(int i=0;i<asteroidesNaTela.size();i++) {
            Asteroide asteroide = asteroidesNaTela.get(i);
            if (nave.x < asteroide.x + asteroide.w &&
                    nave.x + nave.w > asteroide.x &&
                    nave.y < asteroide.y + asteroide.h &&
                    nave.h + nave.y > asteroide.y) {
                System.out.println("colidiu" + asteroide);
            }
        }
        //checa colisoes dos tiros com os asteroides
        for(int i=0;i<asteroidesNaTela.size();i++) {
            Asteroide asteroide = asteroidesNaTela.get(i);
                for (Tiro tiro : tiros) {
                if (tiro.x < asteroide.x + asteroide.w &&
                        tiro.x > asteroide.x &&
                        tiro.y < asteroide.y + asteroide.h &&
                        tiro.y > asteroide.y) {
                    tiros.remove(tiro);
                    asteroidesDisponiveis.push(asteroide);
                    asteroidesNaTela.remove(asteroide);
                    System.out.println("colidiu" + asteroide + " " + tiro);
                    pontuacao++;
                    break;

                }
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
        g.drawImage(nave.imagem, nave.x, nave.y, null);

        //desenha a pontuação
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Pontuação: " + pontuacao, 20, 20);

        // desenha os asteroides
        for (Asteroide asteroide : asteroidesNaTela) {
            asteroide.desenhar(g);
        }

        // desenha os tiros
        for (Tiro tiro : tiros) {
            tiro.desenhar(g);
        }
    }
}
