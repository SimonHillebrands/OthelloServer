import java.awt.event.*;

public class ExitListener extends WindowAdapter {

   GameClient client;

   public ExitListener(GameClient client) {
      this.client = client;
   }
      
   public void windowClosing(WindowEvent e) {
      client.disconnect();
      System.exit(0);
   }
}
