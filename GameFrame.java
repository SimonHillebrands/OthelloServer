import java.awt.*;

public class GameFrame extends Frame { 

   protected TextArea output; 
   protected TextField input;

   protected Thread listener;
  
   public GameFrame (String title){
      super (title); 
    
      setLayout (new BorderLayout ()); 
      add ("Center", output = new TextArea ()); 
      output.setEditable (false); 
      add ("South", input = new TextField ()); 
    
      pack (); 
      show (); 
      input.requestFocus (); 
   }
  
   public static void main (String args[]) { 
      new GameFrame("Game "); 
   }
}

