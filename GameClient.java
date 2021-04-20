import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import javax.swing.*;

public class GameClient {
		
   ///public GameFrame gui;
   public OthelloGUI gui;

   private Socket socket;
   private ServerSocket wsocket;
   private DataInputStream in;
   private DataOutputStream out;
   boolean turn;
   String winner;
   String[][] board;
   boolean isRunning;
   public GameClient(String server,int port,boolean host,int id) {
      
      gui = new OthelloGUI(this);
      board = new String[8][8];
      isRunning = true;

      this.turn = host;
      try {
         wsocket = new ServerSocket(port);
         socket = wsocket.accept();

         in = new DataInputStream(socket.getInputStream());
         out = new DataOutputStream(socket.getOutputStream());

         // String b;
         // if(host){
         //    b = "1";
         // }else{
         //    b = "0";
         // }
         // out.writeUTF(b);
         
         while (isRunning) {
            String[] str = in.readUTF().split("");
            winner = str[0];
            
            int counter = 1;
            boolean flag = false;
            for(int i = 0; i<8;i++){ 
               for(int j = 0; j<8;j++){
                  board[i][j] = str[counter];
                  if(board[i][j].equals("P")){
                     flag = true;
                  }
                  counter++; 
               }

            }
            gui.setBoard(board);
            if(winner.equals("2")){
               isRunning = false;
               gui.declareWinnerBlackPopup();
            }else if(winner.equals("1")){
               isRunning = false;
               gui.declareWinnerWhitePopup();
            }else if(winner.equals("3")){
               isRunning = false;
               gui.declareTiePopup();
            }

            if(flag){
               this.turn = true;
            }else{
               this.turn = false;
            }
            
         }
      }	catch (Exception e)	{ 
         //e.printStackTrace();
      }
      disconnect();
   }
   
   protected void sendTextToGame(int row, int col) {
      try {
         if(this.turn){
            String r = String.valueOf(row+1);
            String c = String.valueOf(col+1);
            if(board[row][col].equals("P")){
               out.writeUTF(r + " "+ c);
            }else{
               gui.invalidMovePopup();
            }
         }else{
            gui.notTurnTurnPopup();
         }
         
      } catch (IOException e) {
         System.out.println("Something went wrong!!!");
         //e.printStackTrace();
      }
   }
   protected void disconnect() {
      try {
         wsocket.close();
         socket.close();
         gui.quit();
         this.isRunning = false;
      } catch (IOException e) {
         //e.printStackTrace();
      }
   }
		
   public static void main (String args[])throws IOException {
      

      boolean clientgo = true;
      String server = "127.0.0.1";
      int serverPort = 3030;
      String sentence;
      int port;


      System.out.println("Welcome to the Othello game \n Commands:\nconnect servername port# connects to a specified server \nlist: lists available games with the gameID\n create creates a new game\njoin gameID Joins the game with the id gameID\n close terminates the program");
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 

      sentence = inFromUser.readLine();
      StringTokenizer tokens = new StringTokenizer(sentence);
      if(sentence.startsWith("connect")){
         tokens.nextToken(); //skip the connect command
         port = Integer.parseInt(tokens.nextToken());
         Socket ControlSocket= new Socket(server, serverPort);

         DataOutputStream outToServer = new DataOutputStream(ControlSocket.getOutputStream()); 
         DataInputStream inFromServer = new DataInputStream(ControlSocket.getInputStream());

         System.out.println("You are connected to the server\n");

         while(clientgo){
            sentence = inFromUser.readLine();
            tokens = new StringTokenizer(sentence);

            if(sentence.startsWith("list:")){
               outToServer.writeUTF(tokens.nextToken());
               String games = inFromServer.readUTF();
               System.out.println(games);
            }
            else if(sentence.startsWith("create")){
                  try{
                     outToServer.writeUTF(tokens.nextToken());
                     String id = inFromServer.readUTF();
                     System.out.println("Your game has been created with ID: " + id);
                     int port1 = port+ (Integer.parseInt(id)*2)+1;
                     GameClient c = new GameClient(server, port1,true,0);
                     outToServer.writeUTF("over " +id );
                  }catch (Exception e)	{ 
                     e.printStackTrace();
                     System.out.println("Connection Failed");
                     //clientgo = false;
                  }
                  }
               else if(sentence.startsWith("join")){
                  try{
                     outToServer.writeUTF(tokens.nextToken());
                     String id = tokens.nextToken();
                     outToServer.writeUTF(id);
                     String status = inFromServer.readUTF();
                     if(status.equals("0")){
                        System.out.println("This game does not exist");
                     }else if(status.equals("1")){
                        System.out.println("This game is already full");
                     }else if(status.equals("2")){
                        System.out.println("You have joined the game");
                        int port1 = port+ (Integer.parseInt(id)+1)*2;
                        GameClient c = new GameClient(server,port1,false,0);
                        //outToServer.writeUTF("over");
                        //outToServer.writeUTF("0");
                     }

                     }catch (Exception e)	{ 
                        e.printStackTrace();
                        System.out.println("Connection Failed");
                       // clientgo = false;
                     }
                  }
                  else{
                     if(sentence.equals("close")){
                        outToServer.writeUTF("close");
                        clientgo = false;
                        ControlSocket.close();
                        System.out.println("Bye Bye!");
                     }else{
                        System.out.print("wut\n");
                     }
               }
         }       
      }
   }
}

