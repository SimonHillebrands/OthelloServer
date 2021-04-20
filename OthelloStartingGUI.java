import javax.swing.*;
import java.awt.*;

public class OthelloStartingGUI {
    public static void main (String[] args){
        //The background frame for the first JFrame. This is for where the player can choose to join or create a game
        JFrame frame = new JFrame ("Othello");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(156, 175, 111));
        frame.setVisible(true);

        Container cp = frame.getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 50));

        //This sets up the first button on the left, the "create a game button"
        JButton newGameButton = new JButton();
        newGameButton.setText("Create Game");
        newGameButton.setVerticalAlignment(SwingConstants.CENTER);
        newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        newGameButton.setPreferredSize(new Dimension(200, 100));
        newGameButton.setBackground(new Color(109, 130, 173));

        cp.add(newGameButton);


        //this is just a filler spot to ensure the buttons are in the correct area
        JLabel filler = new JLabel(" ", SwingConstants.CENTER);
        filler.setOpaque(false);
        filler.setPreferredSize(new Dimension(200, 100));

        cp.add(filler);

        //this is setting up the second button, the Join a Game Button

        JButton joinGameButton = new JButton();
        joinGameButton.setText("Join A Game");
        joinGameButton.setVerticalAlignment(SwingConstants.CENTER);
        joinGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        joinGameButton.setPreferredSize(new Dimension(200, 100));
        joinGameButton.setBackground(new Color(109, 130, 173));

        cp.add(joinGameButton);


        //this sets up a text area that acts as a filler as well as gives information
        JLabel filler1 = new JLabel("This is the game Othello.", SwingConstants.CENTER);
        filler1.setOpaque(true);
        filler1.setPreferredSize(new Dimension(350, 100));
        filler1.setBackground(new Color(156, 175, 111));
        filler1.setOpaque(true);
        cp.add(filler1);

        //this sets up the quit button

        JButton quitButton = new JButton();
        quitButton.setText("Quit");
        quitButton.setVerticalAlignment(SwingConstants.CENTER);
        quitButton.setHorizontalAlignment(SwingConstants.CENTER);
        quitButton.setPreferredSize(new Dimension(200, 100));
        quitButton.setBackground(new Color(109, 130, 173));

        cp.add(quitButton);

        //this sets up a text area that says who it was created by
        JLabel createdBy = new JLabel("Created by Simon and Rebecca", SwingConstants.CENTER);
        createdBy.setOpaque(true);
        createdBy.setPreferredSize(new Dimension(350, 100));
        createdBy.setBackground(new Color(156, 175, 111));
        cp.add(createdBy);
    }
}
