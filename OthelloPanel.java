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
    //private Othello game;
    private int [][] board1 = new int[8][8];

    public OthelloPanel(JMenuItem pQuitItem) {
        quitItem = pQuitItem;
        listen = new ButtonListener();
        setLayout(new BorderLayout());
        panel1 = new JPanel();
        createBoard(8, 8);
        add(panel1, BorderLayout.CENTER);
        quitItem.addActionListener(listen);
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == quitItem) {
                System.exit(1);
            }
            if (e.getSource() == newGameItem) {
                //game = new Othello();
                displayBoard(board1);
                //reset();
            }

            for(int row = 0; row<8; row++){
                for(int col = 0; col<8; col++){
                    if(board[row][col] == e.getSource()){

                    }
                }
            }
            displayBoard(board1);

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


    private void displayBoard(int [][] b){
        this.board1 = b;
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if(b[i][j] == -1){
                    board[i][j].setBackground(Color.BLACK);
                }
                else if(b[i][j] == 1){
                    board[i][j].setBackground(Color.WHITE);
                }
                else if(b[i][j] == 2){
                    board[i][j].setBackground(Color.RED);
                }
                else{
                    board[i][j].setBackground(Color.GREEN);
                }
            }
        }
    }

}
