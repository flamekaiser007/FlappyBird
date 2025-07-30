import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int broadWidth = 360;
        int broaHeight = 640;
         JFrame frame = new JFrame("Flappy Bird");
         frame.setVisible(true);
frame.setResizable(false);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(broadWidth,broaHeight);
frame.setLocationRelativeTo(null);


FlappyBird flappyBird = new FlappyBird();
frame.add(flappyBird);
frame.pack();
frame.setVisible(true);
flappyBird.requestFocus();

    }
}
