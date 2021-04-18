import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class OthelloGUI {


    public static void main (String[] args){
        JButton [][] board = new JButton[8][8];
        JPanel panel;
        JMenuItem quitItem;
        JMenuItem newGameItem;
        Othello game;
        JMenuBar menus;
        JMenu fileMenu;

        JFrame frame = new JFrame ("Othello");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);

        Container cp = frame.getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        //this is creating the option file at the top of the panel (like the little bar thing) with two options, quit and new game
        fileMenu = new JMenu ("File");
        newGameItem = new JMenuItem("new game");
        quitItem = new JMenuItem("quit");
        //adding these Menu Item buttons to the bar
        fileMenu.add(quitItem);
        fileMenu.add(newGameItem);
        menus = new JMenuBar();
        menus.add(fileMenu);
        frame.setJMenuBar(menus);

        panel = new JPanel();
        frame.add(panel, BorderLayout.CENTER);

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                board[i][j] = new JButton("");
                cp.add(board[i][j]);
            }
        }


    }



}
