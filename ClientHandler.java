import java.net.*; 
import java.io.*; 
import java.util.*;

public class ClientHandler extends Thread { 
    
   Socket socket; 
   DataInputStream in; 
   DataOutputStream out;
   String name;
   InetAddress inet;
   int count = 0;
   GameHandler[] games = new GameHandler[5];
   protected static InetAddress[] hosts = new InetAddress[5];
   protected static Vector handlers = new Vector ();
    
   public ClientHandler (String name, Socket socket, InetAddress inet) throws IOException { 
      this.name = name;
      this.socket = socket; 
      this.inet = inet;
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
   } 
    
   public void run () { 

      try { 
         handlers.addElement (this); 

         while (true) { 
            //out.writeUTF("this big ol string");
            
            String isHost = in.readUTF();
            if(isHost.equals("1")){
                System.out.println ("New game from " + this.inet);
                hosts[count] = this.inet;
                broadcast(String.valueOf(count));
        
            }else{
                games[count] = new GameHandler("name", hosts[0],this.inet,count, 3031, 3032); 
                games[count].start(); 
                System.out.println("Client " + this.inet+" joined game "+ count);
                count++;
                broadcast(String.valueOf("true"));
            }
         } 

      } catch (IOException ex) { 
         System.out.println("-- Connection to user lost.");
      } finally { 
         handlers.removeElement (this); 
         broadcast(name +" left");
         try { 
            this.socket.close();
         } catch (IOException ex) { 
            System.out.println("-- Socket to user already closed ?");
         }  
      }
   }
    

   protected static void broadcast (String message) { 
      synchronized (handlers) { 
         Enumeration e = handlers.elements (); 
         while (e.hasMoreElements()) { 
            ClientHandler handler = (ClientHandler) e.nextElement(); 
            try { 
               System.out.println(message);
               handler.out.writeUTF(message);
               handler.out.flush();
            } catch (IOException ex) { 
               handler.stop (); 
            } 
         }
      }
   } 
}

