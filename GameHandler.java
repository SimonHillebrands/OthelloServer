import java.net.*; 
import java.io.*; 
import java.util.*;

public class GameHandler extends Thread { 
    
   Socket hostSocket; 
   Socket guestSocket;

   DataInputStream hostIn; 
   DataOutputStream hostOut;

   DataInputStream guestIn; 
   DataOutputStream guestOut;

   boolean guestJoined;

   String name;
   int id;

   Othello game;

    
   public GameHandler (String name, InetAddress host, InetAddress guest, int id, int port1, int port2) throws IOException { 
      this.name = name;
      //this.hostSocket = host;
      this.id = id; 
      this.game = new Othello();

      Socket hostSocket = new Socket(host, port1);
      Socket guestSocket = new Socket(guest, port2);

      hostIn = new DataInputStream(hostSocket.getInputStream());
      hostOut = new DataOutputStream(hostSocket.getOutputStream());
      hostOut.writeUTF(game.getPossibleBoardString());

      guestIn = new DataInputStream(guestSocket.getInputStream());
      guestOut = new DataOutputStream(guestSocket.getOutputStream());

      guestOut.writeUTF(game.getBoardString());
   } 
    
   public void run () { 

      try { 
         while (true) { 
               driver();           
         } 

      } catch (IOException ex) { 
         System.out.println("-- Connection to user lost.");
      } finally { 
         try { 
            this.hostSocket.close();
            if(guestJoined){
               this.guestSocket.close();
            }
            
         } catch (IOException ex) { 
            System.out.println("-- Socket to user already closed ?");
         }  
      }
   }
   public void join(Socket socket)throws IOException { 
      this.guestSocket = socket;
      this.guestJoined = true;
      guestIn = new DataInputStream(socket.getInputStream());
      guestOut = new DataOutputStream(socket.getOutputStream());

      guestOut.writeUTF(game.getBoardString());
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
         System.out.println(line);
         if(game.isValidMove(row,col)){
            game.placeDisk(row,col);

            game.prepareNextTurn();
            hostOut.writeUTF(game.string());
            guestOut.writeUTF(game.string());

            System.out.println(game.string());
            
         }
      }
      System.out.println(game.string());
      if(game.whosTurn() == -1){
         hostOut.writeUTF(game.getPossibleBoardString());
         guestOut.writeUTF(game.getBoardString());
      }else{
         hostOut.writeUTF(game.getBoardString());
         guestOut.writeUTF(game.getPossibleBoardString());         
      }
      
   }
}
