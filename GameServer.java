import java.net.*; 
import java.io.*; 
import java.util.*;

public class GameServer { 

   public GameServer (int port) throws IOException { 
      ServerSocket server = new ServerSocket(port); 
      int count = 0;
      GameHandler[] games = new GameHandler[5];
      Socket[] hosts = new Socket[5];
      while (true) { 
         Socket client = server.accept(); 
         DataInputStream in = new DataInputStream(client.getInputStream());
         String isHost = in.readUTF();
         //System.out.println(isHost.valueOf("1"));
         if(isHost.equals("1")){
            System.out.println ("New game from " + client.getInetAddress());
            hosts[count] = client;
            // games[count] = new GameHandler("name", client,count); 
            // games[count].start(); 
           // count++;
         }else{
            games[count] = new GameHandler("name", hosts[0],client,count); 
            games[count].start(); 
            //games[count].join(client);
            System.out.println("Client " + client.getInetAddress()+" joined game "+ count);
         }
      } 
   }
  
   public static void main (String args[]) throws IOException { 
      new GameServer(3030); 
   }
}
