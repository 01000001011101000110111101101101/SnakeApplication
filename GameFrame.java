import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){

        this.add(new GamePanel());
        this.setTitle("Snake Game"); // Title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        this.pack(); //To fit all of the components
        this.setVisible(true); //To be visible
        this.setLocationRelativeTo(null); //To set it at the center of the screen
    }
}
