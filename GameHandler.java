import java.net.*; 
import java.io.*; 

public class GameHandler extends Thread { 
    
   Socket hostSocket; 
   Socket guestSocket;

   DataInputStream hostIn; 
   DataOutputStream hostOut;

   DataInputStream guestIn; 
   DataOutputStream guestOut;

   String name;
   int id;

   boolean isOver;

   Othello game;

    
   public GameHandler (String name, InetAddress host, InetAddress guest, int id, int port1, int port2) throws IOException { 
      this.name = name;
      this.id = id; 
      isOver = false;
      this.game = new Othello();

      hostSocket = new Socket(host, port1);
      guestSocket = new Socket(guest, port2);

      hostIn = new DataInputStream(hostSocket.getInputStream());
      hostOut = new DataOutputStream(hostSocket.getOutputStream());
      hostOut.writeUTF("0"+game.getPossibleBoardString());

     // System.out.println(game.getPossibleBoardString());

      guestIn = new DataInputStream(guestSocket.getInputStream());
      guestOut = new DataOutputStream(guestSocket.getOutputStream());

      guestOut.writeUTF("0"+game.getBoardString());
   } 
    
   public void run () { 

      try { 
         while (!isOver) { 
               driver();           
         } 

      } catch (IOException ex) { 
         System.out.println("-- Users disconnected.");
      } finally { 
         try { 
            this.hostSocket.close();
            this.guestSocket.close();
            
         } catch (Exception e) { 
           // System.out.println("-- Socket to user already closed ");
         }  
      }
   }
   boolean running(){
      return isOver;
   }
   void driver() throws IOException {
      
      String line;
      if(!game.isValidMoveAvailable()){
         game.prepareNextTurn();
      } else {
         if(game.whosTurn() == -1){
            line = hostIn.readUTF();
         } else{
            line = guestIn.readUTF();
         }
         String[] tokens = line.split(" ");
         int row = Integer.parseInt(tokens[0]);
         int col = Integer.parseInt(tokens[1]);

         row--;
         col--;
         if(game.isValidMove(row,col)){
            game.placeDisk(row,col);

            game.prepareNextTurn();
            String w = game.getGameStatus();
            // hostOut.writeUTF(w + game.string());
            // guestOut.writeUTF(w + game.string());

            //System.out.println(game.string());
            
         }
      }
      String w = game.getGameStatus();
      if(!w.equals("0")){
         isOver = true;
      }
      if(game.whosTurn() == -1){
         hostOut.writeUTF(w + game.getPossibleBoardString());
         guestOut.writeUTF(w + game.getBoardString());
      }else{
         hostOut.writeUTF(w + game.getBoardString());
         guestOut.writeUTF(w + game.getPossibleBoardString());         
      }
      
   }
}
