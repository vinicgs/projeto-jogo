import java.awt.Dimension;

import javax.swing.JFrame;

public class Principal{

    public static final int LARGURA_TELA = 640;
    public static final int ALTURA_TELA = 480;

    public static void main(String[] args){
        // cria o objeto que representa a janela do jogo
        JFrame janela = new JFrame("STARBURST");

        Game game = new Game();
        game.setPreferredSize(new Dimension(LARGURA_TELA,ALTURA_TELA) );
        janela.add(game); // coloca a tela de desenho dentro da janela
        
        //janela.setSize(LARGURA_TELA,ALTURA_TELA);
        janela.setResizable(false); // impedir o redimensionamento da janela
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocation(300, 100);
        janela.setVisible(true);

        janela.pack(); // faz a janela inflar (redimensionar) para caber os seus componentes internos

    }
}