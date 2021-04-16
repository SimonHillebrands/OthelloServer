import java.awt.event.*;

public class EnterListener extends KeyAdapter {
   
   GameClient client;
   GameFrame gui;

   public EnterListener (GameClient client, GameFrame gui) {
      this.client = client;
      this.gui = gui;
   }   

   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode()==KeyEvent.VK_ENTER) {
          client.sendTextToGame(gui.input.getText());
          gui.input.setText("");
      }
   }
}
