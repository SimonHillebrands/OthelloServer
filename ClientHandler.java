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
   protected static ArrayList<GameHandler> games = new ArrayList<GameHandler>();
   protected static ArrayList<InetAddress> hosts = new ArrayList<InetAddress>();
   protected static ArrayList<Boolean> available = new ArrayList<Boolean>();
    
   public ClientHandler (String name, Socket socket, InetAddress inet) throws IOException { 
      this.name = name;
      this.socket = socket; 
      this.inet = inet;
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
   } 
    
   public void run () { 
      try { 
         while (true) { 

            //inital command from client
            String command = in.readUTF();
            
            if(command.equals("create")){
                System.out.println ("New game from " + this.inet);
                hosts.add(this.inet);
                available.add(true);
                out.writeUTF(String.valueOf(hosts.size()-1));      
            }
            else if(command.equals("join")){
                int gameID = Integer.parseInt(in.readUTF());
                if(gameID>=hosts.size()){
                   //this game does not exist
                   out.writeUTF("0");
                }else if(!available.get(gameID)){
                   //this game is unavailable
                   out.writeUTF("1");
                }else{
                  //The game is found
                  out.writeUTF("2");
                  games.add(new GameHandler("name", hosts.get(gameID),this.inet,count, 3031, 3032)); 
                  games.get(games.size()-1).start(); 
                  System.out.println("Client " + this.inet+" joined game "+ gameID);
                  available.set(gameID,false);
                }


            }else if(command.equals("list:")){
               String str = "";
               for(int i = 0;i<available.size();i++){
                  str+= "Game : " + i;
               }
               out.writeUTF(str);
            }
         } 

      } catch (IOException ex) { 
         System.out.println("-- Connection to user lost.");
      } finally { 
         try { 
            this.socket.close();
         } catch (IOException ex) { 
            System.out.println("-- Socket to user already closed ?");
         }  
      }
   }
}

