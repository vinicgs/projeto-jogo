import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;

public class Game extends JPanel {
    private Nave nave;
    private Stack<Asteroide> asteroidesDisponiveis;
    private ArrayList<Asteroide> asteroidesNaTela;
    private boolean k_direita = false;
    private boolean k_esquerda = false;
    private final ArrayList<Tiro> tiros;// array de tiros da nave
    private int pontuacao;
    private int dificuldade = 1;

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
                    case KeyEvent.VK_SPACE -> tiros.add(new Tiro(nave.x + 23, nave.y));
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


        nave = new Nave(Principal.LARGURA_TELA / 2 - 55 / 2);//posição inicial da nave no centro da tela
        tiros = new ArrayList<>(); //array de tiros da nave (inicialmente vazio)
        asteroidesDisponiveis = new Stack<>(); // pilha de asteroides disponiveis
        for (int i = 0; i < 10; i++) { // cria 10 asteroides e adiciona na pilha
            asteroidesDisponiveis.push(new Asteroide(0, 0, 55, 55, 2));
        }
        asteroidesNaTela = new ArrayList<>(10); // lista de asteroides que estão na tela
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
            long tempoAnterior = System.currentTimeMillis(); // tempo anterior do sistema
            long tempoAtual = 0; // tempo atual do sistema
            long deltaTime = 0; // diferença de tempo entre o tempo atual e o tempo anterior

            while (true) {
                    // cria um novo asteroide a cada 1 segundo (1000 milisegundos) e adiciona na lista de asteroides do jogo
                    tempoAtual = System.currentTimeMillis(); // atualiza o tempo atual
                    deltaTime = tempoAtual - tempoAnterior; // calcula a diferença de tempo
                    tratadorDeEventos(); // trata os eventos de teclado
                    update(deltaTime); // atualiza o jogo
                    repaint(); // redesenha o jogo
                    tempoAnterior = tempoAtual; // atualiza o tempo anterior
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
            asteroide.x = (int) (Math.random() * (Principal.LARGURA_TELA - 55)); // posição aleatoria do asteroide
            asteroide.y = -55; // posição inicial do asteroide
            asteroidesNaTela.add(asteroide);
        }
        //aumenta em 1a velocidade dos asteroides a cada 20pontos


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
    public void checarColisoes(){//checa colisoes dos tiros com os asteroides
        // e remove os asteroides e os tiros que colidiram da tela
        for(int i=0;i<asteroidesNaTela.size();i++){
            Asteroide asteroide = asteroidesNaTela.get(i);
            if(asteroide.y > Principal.ALTURA_TELA){
                asteroidesDisponiveis.push(asteroide);
                asteroidesNaTela.remove(asteroide);
                pontuacao--;
            }
        }

        //checa colisoes da nave com os asteroides
        for(int i=0;i<asteroidesNaTela.size();i++) {
            Asteroide asteroide = asteroidesNaTela.get(i);
            if (nave.x + nave.w/2 < asteroide.x + asteroide.w &&
                nave.x + nave.w/2 > asteroide.x &&
                nave.y + nave.h/2 < asteroide.y + asteroide.h &&
                nave.h/2 + nave.y > asteroide.y) {
                JOptionPane.showMessageDialog(null, "Você perdeu! Sua pontuação foi: " + pontuacao);
                nave = null;
                asteroidesNaTela.remove(asteroide); // remove o asteroide da tela
                asteroidesDisponiveis.push(asteroide); // adiciona o asteroide na pilha de asteroides disponiveis
                //começa o jogo novamente
                nave = new Nave(Principal.LARGURA_TELA / 2 - 25); // cria a nave
                tiros.clear();
                asteroidesDisponiveis.clear();
                for (int j = 0; j < 10; j++) {
                    asteroidesDisponiveis.push(new Asteroide(0, 0, 55, 55, 2));
                }
                asteroidesNaTela.clear();
                pontuacao = 0;
                break;

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
                    //destroi o asteroide e o tiro que colidiram
                    asteroidesDisponiveis.push(asteroide);
                    asteroidesNaTela.remove(asteroide);
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
        Image fundo = new ImageIcon("imgs/fundo.jpeg").getImage();
        g.drawImage(fundo, 0, 0, Principal.LARGURA_TELA, Principal.ALTURA_TELA, null);
        g.drawImage(nave.imagem, nave.x, nave.y, nave.w, nave.h , null);
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
