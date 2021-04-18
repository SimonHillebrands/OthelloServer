import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class OthelloPanel extends JPanel{

    private JButton [][] board;
    private JPanel panel1;
    private int player;
    private ButtonListener listen;
    private JMenuItem quitItem;
    private JMenuItem newGameItem;
    private Othello game;


    public OthelloPanel(JMenuItem pQuitItem) {
        quitItem = pQuitItem;
        listen = new ButtonListener();


        setLayout(new BorderLayout());
        panel1 = new JPanel();

        createBoard(8, 8);
        add(panel1, BorderLayout.CENTER);
        game = new Othello();
        quitItem.addActionListener(listen);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == quitItem) {
                System.exit(1);
            }
            if (e.getSource() == newGameItem) {
                game = new Othello();
                displayBoard(8, 8);
                //reset();
            }

            displayBoard(8,8);

            }

    }

    private void createBoard(int r, int c){
        board = new JButton[r][c];
        panel1.setLayout(new GridLayout(r,c));

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                board[i][j] = new JButton("");
                board[i][j].addActionListener(listen);
                panel1.add(board[i][j]);
            }
        }
    }

    private void displayBoard(int r, int c){
        for(int row = 0; row < r; row++){
            for(int col = 0; col < c; col++){
                int d = game.getCell(row, col);
                if(d != 0){
                    board[row][col].setText("???" );// d.getPlayerNumber());
                }
                else
                    board[row][col].setText("");
            }
        }
    }

}
