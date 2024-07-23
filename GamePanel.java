import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if (running) {
            //GRID so it is easier for us to see things
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++){
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int j = 0; j < bodyParts; j++){
                if (j == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[j], y[j], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45,180,0));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[j], y[j], UNIT_SIZE, UNIT_SIZE);
                }

            }
            //After we draw all of the body parts for the snake
            g.setColor(Color.red);
            g.setFont(new Font("apple",Font.BOLD,40));
            FontMetrics fm = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fm.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }

    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
    public void move(){
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction){
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }

    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions(){
        //This checks if head colides with its body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;

            }
        }
        //This checks if head touches left border
        if (x[0] < 0) running = false;

        //This checks if head touches right border
        if (x[0] > SCREEN_WIDTH) running = false;

        //This checks if head touches top border
        if (y[0] < 0) running = false;

        //This checks if head touches botoom border
        if (y[0] > SCREEN_HEIGHT) running = false;

        if (running == false) {
            timer.stop();
        }

    }

    public void gameOver(Graphics g){
        //This will display the score
        g.setColor(Color.red);
        g.setFont(new Font("apple",Font.BOLD,40));
        FontMetrics fm1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - fm1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("apple",Font.BOLD,80));
        FontMetrics fm2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - fm2.stringWidth("Game over")) / 2, SCREEN_HEIGHT / 2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }

                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }

                case KeyEvent.VK_UP -> {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }

                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }
            }
        }
    }
}
