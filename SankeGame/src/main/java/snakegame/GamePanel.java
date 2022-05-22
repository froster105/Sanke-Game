package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    // the dimensions of screen
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    // size of one unit
    static final int UNIT_SIZE = 25;
    // total game units
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    // this will take care of the speed of the game
    static final int DELAY = 80;

    // this will store the position of the snakein x and y coordinate
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    // initial size of the array
    int bodyParts = 5;
    int applesEaten;
    int appleX;
    int appleY;
    // the movement direction of the snake when the game starts
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        // this will give the panel a size of 600X600
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        // MyKeyAdapter will listen to the which key I have pressed
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(running){
            //y1 is the starting point at the x axis so it will be zero
            //y2 is the end point it will be screen height
            //x1 and x2 change as i*UNIT_SIZE
//            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//
//            }
            //this will give apple the red color
            g.setColor(Color.red);
            //this will generate the apple at different location
            g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    //to set color of snake head
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
//                    g.setColor of the snake(Color.RED);
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }else{
        gameOver(g);
        }
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score:"+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());
        
    }

    public void newApple() {
        //unit_size is multiplied because we want to have apple in full unit size and not half
        appleX=random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY=random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;

    }
    //this method will move the snake 
    public void move() {
        for(int i=bodyParts;i>0;i--){
            //shifting the index by one of the snake
        x[i]=x[i-1];
        y[i]=y[i-1];
        }
        switch(direction){
        case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
        case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
        case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
        case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }
    }

    public void checkApples() {
        if((x[0]==appleX)&&(y[0]==appleY)){
        bodyParts++;
        applesEaten++;
        newApple();
        }
    }

    public void checkCollision() {
        //this check if head collidies with body
        for(int i=bodyParts;i>0;i--){
        if((x[0]==x[i])&&y[0]==y[i]){
        running=false;
        }
        }
        //this will check if head touches left border
        if(x[0]<0){
        running =false;}
        //right border
         if(x[0]>SCREEN_WIDTH){
        running =false;}
         //top border
          if(y[0]<0){
        running =false;}
          //bottom border
           if(y[0]>SCREEN_HEIGHT){
        running =false;}
        //to stop timer after collision   
        if(!running){
        timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Railway",Font.BOLD,75));
        //instance of font metrics for giving text
        FontMetrics metrics=getFontMetrics(g.getFont());
        //this will give position at center of the scree
        g.drawString("Game Over",(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(running){
        move();
        checkApples();
        checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
         switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                if(direction!='R'){
                direction='L';
                }
                break;
        case KeyEvent.VK_RIGHT:
        if(direction!='L'){
            direction='R';
        }
        break;
        
        case KeyEvent.VK_UP:
        if(direction!='D'){
            direction='U';
        }
        break;
        case KeyEvent.VK_DOWN:
        if(direction!='U'){
            direction='D';
        }
        break;
            }
        }

       
    }

    public static void main(String[] args) {
        new GamePanel();
    }
}

