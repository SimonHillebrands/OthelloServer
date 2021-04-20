import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;  
import java.awt.event.WindowListener;  
import java.awt.event.WindowAdapter;


class OthelloGUI {
    OthelloPanel p;
    GameClient client;
    JFrame frame;

    public OthelloGUI(GameClient client){
        JButton [][] board = new JButton[8][8];
        JPanel panel;
        JMenuItem quitItem;
        JMenuItem newGameItem;
        JMenuBar menus;
        JMenu fileMenu;

        this.client = client;

        frame = new JFrame ("Othello");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
              client.disconnect();
            }
          });

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
        

        this.p = new OthelloPanel(quitItem, client);
        frame.add(p);
        frame.setSize(600,600);
        frame.setVisible(true);

    }
    public void quit(){
        frame.dispose();
    }
    public void setBoard(String[][] b){
        this.p.updateBoard(b);
    }
    public void invalidMovePopup(){
        JFrame e = new JFrame();
        JOptionPane.showMessageDialog(e, "Invalid Move", "Alert",JOptionPane.WARNING_MESSAGE);
    }
    public void notTurnTurnPopup(){
        JFrame e = new JFrame();
        JOptionPane.showMessageDialog(e, "It's not your turn!", "Alert",JOptionPane.WARNING_MESSAGE);
    }
    public void declareWinnerBlackPopup() {
        JFrame e = new JFrame();
        JOptionPane.showMessageDialog(e, "Black is the winner!!");
    }
    public void declareWinnerWhitePopup() {
        JFrame e = new JFrame();
        JOptionPane.showMessageDialog(e, "White is the winner!!");
    }
    public void declareTiePopup() {
        JFrame e = new JFrame();
        JOptionPane.showMessageDialog(e, "It's a TIE!!");
    }




    public static void main (String[] args){
    
        //new OthelloGUI(this);

    }



}