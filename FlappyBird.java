import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener,KeyListener  {
    int broadWidth = 360;
    int broaHeight = 640;      

    // Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;


    //bird
    int birdX = broadWidth/8;
    int birdY = broaHeight/2;
int birdWidth = 34;
int birdHeight = 24;

class Bird{
int x = birdX;
 
int y = birdY;
int width = birdWidth;
int height = birdHeight;
Image img;

Bird(Image img){
    this.img = img;
}

}

//pipes
int pipeX = broadWidth;
int pipeY = 0;
int pipeWidth = 64; //scaled by 1/6
int pipeHeight = 512;

class Pipe{
    int x = pipeX;
    int y = pipeY;
    int width  = pipeWidth;
    int height = pipeHeight;
    Image img;
    boolean passed = false;


    Pipe(Image img){
        this.img = img;
    }
}

//game logic

Bird bird;
Timer gameLoop;
int gravity = 1;
int velocityY = 0;
int velocityX = -4; //Move pipe to the left
ArrayList<Pipe>pipes;
double score = 0;
double highScore = 0; // 


Timer placePipesTimer;
boolean gameOver = false;
    public FlappyBird() {
        setPreferredSize(new Dimension(broadWidth, broaHeight));
        setBackground(Color.BLUE);
setFocusable(true);
addKeyListener(this);

        // Load images properly into separate variables
        backgroundImg = new ImageIcon(App.class.getResource("/images/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(App.class.getResource("/images/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(App.class.getResource("/images/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(App.class.getResource("/images/bottompipe.png")).getImage();
//Bird
        bird = new Bird(birdImg);
pipes = new ArrayList<Pipe>();
Random random = new Random();


        //place pipes timer
placePipesTimer = new Timer(1500, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e){
        PlacePipes();
    }
});

placePipesTimer.start();

        ///game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void PlacePipes(){

        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = broaHeight/4;
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);


        Pipe bottompipe = new Pipe(bottomPipeImg);
bottompipe.y = topPipe.y + pipeHeight + openingSpace;
pipes.add(bottompipe);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
       // System.err.println("draw");
        g.drawImage(backgroundImg, 0, 0, broadWidth, broaHeight, null);


        g.drawImage(bird.img, bird.x, bird.y, bird.width,bird.height,null);

        //pipes
for(int i = 0;i<pipes.size();i++){
    Pipe pipe  = pipes.get(i);
    g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width,pipe.height,null);
}
 
if(bird.y >broaHeight)
{
    gameOver = true;
}
//score
g.setColor(Color.white);
g.setFont(new Font("Arial" , Font.PLAIN,32));
if(gameOver){
    g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
}
else{
    g.drawString(String.valueOf((int) score), 10, 35);
}
g.drawString("High Score: " + (int)highScore, 10, 70);

    }


    public void move(){
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y,0);

        //pipe
        for(int i =0;i<pipes.size();i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
            if(!pipe.passed && bird.x >pipe.x + pipe.width){
pipe.passed = true;
 score += 0.5;
            }
            if(collision(bird, pipe)){
                gameOver = true;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            if (score > highScore) {
                highScore = score;
            }
            placePipesTimer.stop();
            gameLoop.stop();
        }
        
    }
 public boolean collision (Bird a,Pipe b){
return a.x<b.x + b.width &&
a.x + a.width > b.x &&
a.y < b.y + b.height &&
a.y + a.height > b.y;
 }
   
    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_SPACE){
velocityY = -9;
if(gameOver){
    bird.y = birdX;
    velocityY = 0;
    pipes.clear();
    score = 0;
    gameOver = false;
    gameLoop.start();
    placePipesTimer.start();

}
       }
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
