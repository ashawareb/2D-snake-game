package snake_2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
 
public class GameDrawer extends JPanel implements KeyListener, ActionListener{
 
    final int[] boardX = new int[484];
    final int[] boardY = new int[484];
 
    LinkedList<Position> snake = new LinkedList();
 
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;
 
    ImageIcon lookToRightImage = new ImageIcon(this.getClass().getResource("/images/face-look-right.jpg"));
    ImageIcon lookToLeftImage = new ImageIcon(this.getClass().getResource("/images/face-look-left.jpg"));
    ImageIcon lookToUpImage = new ImageIcon(this.getClass().getResource("/images/face-look-up.jpg"));
    ImageIcon lookToDownImage = new ImageIcon(this.getClass().getResource("/images/face-look-down.jpg"));
 
    ImageIcon snakeBodyImage = new ImageIcon(this.getClass().getResource("/images/body.png"));
 
    ImageIcon fruitImage = new ImageIcon(this.getClass().getResource("/images/fruit.png"));
 
    int lengthOfSnake = 3;
 
    int[] fruitXPos = {20, 40, 60, 80, 100, 120, 140, 160, 200, 220, 240, 260, 280, 300, 320, 340, 360, 380, 400, 420, 440, 460};
    int[] fruitYPos = {20, 40, 60, 80, 100, 120, 140, 160, 200, 220, 240, 260, 280, 300, 320, 340, 360, 380, 400, 420, 440, 460};
 
    Timer speedTimer;
 
    int delay = 100;
 
    int moves = 0;
 
    int totalScore = 0;
    int fruitEaten = 0;
    int scoreReverseCounter = 99;
 
    int bestScore = readBestScorefromTheFile();
 
    Random random = new Random();
 
    int xPos = random.nextInt(22);
    int yPos = 5+random.nextInt(17);
 
    boolean isGameOver = false;
 
    public GameDrawer()
    {
        setPreferredSize(new Dimension(750, 500));
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        speedTimer = new Timer(delay, this);
    }
 
    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }
 
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
    }
 
    @Override
    public void paint(Graphics g)
    {
        if(moves == 0)
        {
            boardX[2] = 40;
            boardX[1] = 60;
            boardX[0] = 80;
 
            boardY[2] = 100;
            boardY[1] = 100;
            boardY[0] = 100;
 
            scoreReverseCounter = 99;
            speedTimer.start();
        }
 
        if(totalScore > bestScore)
            bestScore = totalScore;
 
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 750, 500);
 
        g.setColor(Color.DARK_GRAY);
        for(int i=6; i<=482; i+=17)
            for(int j=6; j<=482; j+=17)
                g.fillRect(i, j, 13, 13);
 
        g.setColor(Color.BLACK);
        g.fillRect(20, 20, 460, 460);
 
        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.drawString("Snake 2D", 565, 35);
 
        g.setFont(new Font("Arial", Font.PLAIN, 13));
        g.drawString("+ "+scoreReverseCounter, 510, 222);
 
        g.setColor(Color.LIGHT_GRAY);
 
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("Developed by Shawareb", 530, 60);
 
        FontMetrics fm = g.getFontMetrics();
 
        g.setFont(new Font("Arial", Font.PLAIN, 18));
 
        g.drawString("Best Score", 576, 110);
        g.drawRect(550, 120, 140, 30);
        g.drawString(bestScore+"", 550+(142-fm.stringWidth(bestScore+""))/2, 142);
 
        g.drawString("Total Score", 573, 190);
        g.drawRect(550, 200, 140, 30);
        g.drawString(totalScore+"", 550+(142-fm.stringWidth(totalScore+""))/2, 222);
 
        g.drawString("Fruit Eaten", 575, 270);
        g.drawRect(550, 280, 140, 30);
        g.drawString(fruitEaten+"", 550+(142-fm.stringWidth(fruitEaten+""))/2, 302);
 
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Controls", 550, 360);
 
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Pause / Start : Space", 550, 385);
        g.drawString("lookTo Up : Arrow Up", 550, 410);
        g.drawString("lookTo Down : Arrow Down", 550, 435);
        g.drawString("lookTo Left : Arrow Left", 550, 460);
        g.drawString("lookTo Right : Arrow Right", 550, 485);
 
        lookToRightImage.paintIcon(this, g, boardX[0], boardY[0]);
 
        snake.clear();
 
        for(int i=0; i<lengthOfSnake; i++)
        {
            if(i==0 && left)
                lookToLeftImage.paintIcon(this, g, boardX[i], boardY[i]);
 
            else if(i==0 && right)
                lookToRightImage.paintIcon(this, g, boardX[i], boardY[i]);
 
            else if(i==0 && up)
                lookToUpImage.paintIcon(this, g, boardX[i], boardY[i]);
 
            else if(i==0 && down)
                lookToDownImage.paintIcon(this, g, boardX[i], boardY[i]);
 
            else if(i!=0)
                snakeBodyImage.paintIcon(this, g, boardX[i], boardY[i]);
 
            snake.add(new Position(boardX[i], boardY[i]));
        }
 
        if(scoreReverseCounter != 10)
            scoreReverseCounter--;
 
        for(int i=1; i<lengthOfSnake; i++)
        {
            if(boardX[i] == boardX[0] && boardY[i] == boardY[0])
            {
                if(right)
                    lookToRightImage.paintIcon(this, g, boardX[1], boardY[1]);
 
                else if(left)
                    lookToLeftImage.paintIcon(this, g, boardX[1], boardY[1]);
 
                else if(up)
                    lookToUpImage.paintIcon(this, g, boardX[1], boardY[1]);
 
                else if(down)
                    lookToDownImage.paintIcon(this, g, boardX[1], boardY[1]);
 
                isGameOver = true;
 
                speedTimer.stop();
 
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.BOLD, 50));
                g.drawString("Game Over", 110, 220);
 
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Press Space To Restart", 130, 260);
 
                writeBestScoreInTheFile();
            }
        }
 
        if((fruitXPos[xPos] == boardX[0]) && fruitYPos[yPos] == boardY[0])
        {
            totalScore += scoreReverseCounter;
            scoreReverseCounter = 99;
            fruitEaten++;
            lengthOfSnake++;
        }
 
        for(int i=0; i<snake.size(); i++)
        {
            if(snake.get(i).x == fruitXPos[xPos] && snake.get(i).y == fruitYPos[yPos]) {
                xPos = random.nextInt(22);
                yPos = random.nextInt(22);
            }
        }
 
        fruitImage.paintIcon(this, g, fruitXPos[xPos], fruitYPos[yPos]);
 
        g.dispose();
    }
 
 
    @Override
    public void keyPressed(KeyEvent e) {
 
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if(speedTimer.isRunning() && isGameOver == false)
                speedTimer.stop();

            else if(!speedTimer.isRunning() && isGameOver == false)
                speedTimer.start();
 
            else if(!speedTimer.isRunning() && isGameOver == true)
            {
                isGameOver = false;
                speedTimer.start();
                moves = 0;
                totalScore = 0;
                fruitEaten = 0;
                lengthOfSnake = 3;
                right = true;
                left = false;
                xPos = random.nextInt(22);
                yPos = 5+random.nextInt(17);
            }
        }
 
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            moves++;
            right = true;
 
            if(!left)
                right = true;
 
            else
            {
                right = false;
                left = true;
            }
 
            up = false;
            down = false;
        }
 
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            moves++;
            left = true;
 
            if(!right)
                left = true;
 
            else
            {
                left = false;
                right = true;
            }
 
            up = false;
            down = false;
        }
 
        else if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            moves++;
            up = true;
 
            if(!down)
                up = true;
 
            else
            {
                up = false;
                down = true;
            }
 
            left = false;
            right = false;
        }
 
        else if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            moves++;
            down = true;
 
            if(!up)
                down = true;
 
            else
            {
                up = true;
                down = false;
            }
 
            left = false;
            right = false;
        }
    }
 
 
    @Override
    public void actionPerformed(ActionEvent e) {
 
        if(right)
        {
            for(int i = lengthOfSnake-1; i>=0; i--)
                boardY[i+1] = boardY[i];
 
            for(int i = lengthOfSnake; i>=0; i--)
            {
                if(i==0)
                    boardX[i] = boardX[i] + 20;
 
                else
                    boardX[i] = boardX[i-1];
 
                if(boardX[i] > 460)
                    boardX[i] = 20;
            }
        }
 
        else if(left)
        {
            for(int i = lengthOfSnake-1; i>=0; i--)
                boardY[i+1] = boardY[i];
 
            for(int i = lengthOfSnake; i>=0; i--)
            {
                if(i==0)
                    boardX[i] = boardX[i] - 20;
 
                else
                    boardX[i] = boardX[i-1];
 
                if(boardX[i] < 20)
                    boardX[i] = 460;
            }
        }
 
        else if(up)
        {
            for(int i = lengthOfSnake-1; i>=0; i--)
                boardX[i+1] = boardX[i];
 
            for(int i = lengthOfSnake; i>=0; i--)
            {
                if(i==0)
                    boardY[i] = boardY[i] - 20;
 
                else
                    boardY[i] = boardY[i-1];
 
                if(boardY[i] < 20)
                    boardY[i] = 460;
            }
        }
 
        else if(down)
        {
            for(int i = lengthOfSnake-1; i>=0; i--)
                boardX[i+1] = boardX[i];
 
            for(int i = lengthOfSnake; i>=0; i--)
            {
                if(i==0)
                    boardY[i] = boardY[i] + 20;
 
                else
                    boardY[i] = boardY[i-1];
 
                if(boardY[i] > 460)
                    boardY[i] = 20;
            }
        }
 
        repaint();
    }
 
 
    @Override
    public void keyReleased(KeyEvent e) {
 
    }
 
 
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
 
    private void writeBestScoreInTheFile()
    {
        if(totalScore >= bestScore)
        {
            try {
                FileOutputStream fos = new FileOutputStream("./snake-game-best-score.txt");
                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                osw.write(bestScore+"");
                osw.flush();
                osw.close();
            }
            catch(IOException e) {
            }
        }
    }
 
 
    private int readBestScorefromTheFile()
    {
        try {
            InputStreamReader isr = new InputStreamReader( new FileInputStream("./snake-game-best-score.txt"), "UTF-8" );
            BufferedReader br = new BufferedReader(isr);
 
            String str = "";
            int c;
            while( (c = br.read()) != -1){
                if(Character.isDigit(c))
                    str += (char)c;
            }
            if(str.equals(""))
                str = "0";
 
            br.close();
            return Integer.parseInt(str);
        }
        catch(IOException e) {
        }
        return 0;
    }
 
}