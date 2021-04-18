import java.net.*; 
import java.io.*; 
import java.util.*;

public class GameServer { 
   
   public GameServer (int port) throws IOException { 
      ServerSocket server = new ServerSocket(port); 
      // int count = 0;
      // GameHandler[] games = new GameHandler[5];
      // InetAddress[] hosts = new InetAddress[5];
      while (true) { 
         Socket client = server.accept(); 
         // clients.addElement(client);
         //DataInputStream in = new DataInputStream(client.getInputStream());
         // DataOutputStream out = new DataOutputStream(client.getOutputStream());
         System.out.println ("New client name from " + client.getInetAddress());
         ClientHandler c = new ClientHandler ("name", client, client.getInetAddress()); 
         c.start(); 
      } 
   }
   // protected static void broadcast (String message) { 
   //    synchronized (clients) { 
   //       Enumeration e = clients.elements(); 
   //       while (e.hasMoreElements()) { 
   //          Socket c = (Socket) e.nextElement();
   //          try { 
   //             DataOutputStream out = new DataOutputStream( c.getOutputStream());
   //             out.writeUTF(message);
   //             out.flush();
   //          } catch (IOException ex) { 
   //             System.out.println("uh oh");
   //          } 
   //       }
   //    }
   // } 
  
   public static void main (String args[]) throws IOException { 
      new GameServer(3030); 
   }
}
