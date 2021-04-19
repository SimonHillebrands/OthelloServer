import java.net.*; 
import java.io.*; 
import java.util.*;

public class GameServer { 
   
   public GameServer (int port) throws IOException { 
      ServerSocket server = new ServerSocket(port); 
      while (true) { 
         Socket client = server.accept(); 
         System.out.println ("New client from " + client.getInetAddress());
         ClientHandler c = new ClientHandler ("name", client, client.getInetAddress()); 
         c.start(); 
      } 
   } 
   public static void main (String args[]) throws IOException { 
      new GameServer(3030); 
   }
}
