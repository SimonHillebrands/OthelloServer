import java.net.*; 
import java.io.*; 
import java.util.*;

public class ClientHandler extends Thread { 
    
   Socket socket; 
   DataInputStream in; 
   DataOutputStream out;
   String name;
   InetAddress inet;
   int counter = 0;
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
                  counter++;
                  games.add(new GameHandler("name", hosts.get(gameID),this.inet,counter, 3030+ (gameID*2)+1, 3030+((gameID+1)*2))); 
                  games.get(games.size()-1).start(); 
                  System.out.println("Client " + this.inet+" joined game "+ gameID);
                  available.set(gameID,false);
                }


            }else if(command.equals("list:")){
               String str = "";
               for(int i = counter;i<available.size();i++){
                  str+= "Game : " + (i)+"\n";
               }
               out.writeUTF(str);
            }
            else if (command.startsWith("over")){
               int id =Integer.parseInt(command.substring(5,command.length()));
               counter++;

               System.out.println("Game " + id +" is over");
               
            }
            else if(command.equals("close")){
               this.socket.close();
               System.out.println("User "+this.inet+" left");
            }
         } 

      } catch (IOException ex) { 
         System.out.println("-- Connection to user lost.");
         ex.printStackTrace();

      } finally { 
         try { 
            this.socket.close();
         } catch (IOException ex) { 
            System.out.println("-- Socket to user already closed ?");
         }  
      }
   }
}

