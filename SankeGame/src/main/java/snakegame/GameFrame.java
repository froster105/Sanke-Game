
package snakegame;

import javax.swing.JFrame;


public class GameFrame extends JFrame {
    GameFrame(){
        //this will add the game panel to the frmae
        this.add(new GamePanel());
        this.setTitle("Snake");
        //when we close this will exit the game
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);;
    }
    public static void main(String[] args) {
        new GameFrame();
    }
}
