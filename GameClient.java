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
   private DataInputStream in;
   private DataOutputStream out;
   boolean turn;
   String[][] board;
   public GameClient(String server,int port,boolean host,int id) {
      



      // gui = new GameFrame("Othello");
      // 
      // gui.input.addKeyListener (new EnterListener(this,gui));
      // gui.addWindowListener(new ExitListener(this));


      gui = new OthelloGUI(this);
      board = new String[8][8];

      this.turn = host;
      try {
         ServerSocket wsocket = new ServerSocket(port);
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
         
         while (true) {
            String[] str = in.readUTF().split("");
            int counter = 0;
            boolean flag = false;
            for(int i = 0; i<8;i++){ 
               for(int j = 0; j<8;j++){
                  board[i][j] = str[counter];

                 // gui.output.append(" "+board[i][j]);

                  if(board[i][j].equals("P")){
                     flag = true;
                  }
                  counter++; 
               }

            }
            gui.setBoard(board);
            if(flag){
               this.turn = true;
            }else{
               this.turn = false;
            }
            
         }
      }	catch (Exception e)	{ 
         e.printStackTrace();
      }
   }
   
   protected void sendTextToGame(int row, int col) {
      try {
         if(this.turn){
            // String[] splt = str.split(" ");
            // int row = Integer.parseInt(splt[0]);
            // int col = Integer.parseInt(splt[1]);
            // row--;
            // col--;
            String r = String.valueOf(row+1);
            String c = String.valueOf(col+1);
            if(board[row][col].equals("P")){
               out.writeUTF(r + " "+ c);
            }else{
              // gui.output.append("Invalid move!");
            }
         }else{
            //gui.output.append("\n It's not your turn!");
         }
         
      } catch (IOException e) {
         System.out.println("Something went wrong!!!");
         //e.printStackTrace();
      }
   }
   // protected void sendTextToGame(String str) {
   //    try {
   //       if(this.turn){
   //          String[] splt = str.split(" ");
   //          int row = Integer.parseInt(splt[0]);
   //          int col = Integer.parseInt(splt[1]);
   //          row--;
   //          col--;
   //          if(board[row][col].equals("P")){
   //             out.writeUTF(str);
   //          }else{
   //             gui.output.append("Invalid move!");
   //          }
   //       }else{
   //          gui.output.append("\n It's not your turn!");
   //       }
         
   //    } catch (IOException e) {
   //       System.out.println("Something went wrong!!!");
   //       //e.printStackTrace();
   //    }
   // }

   protected void disconnect() {
      try {
         socket.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
		
   public static void main (String args[])throws IOException {
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 


      boolean clientgo = true;
      String server = "127.0.0.1";
      int port = 3030;

      Socket ControlSocket= new Socket(server, port);
      DataOutputStream outToServer = new DataOutputStream(ControlSocket.getOutputStream()); 

      DataInputStream inFromServer = new DataInputStream(ControlSocket.getInputStream());
      String[] gameList = new String[5];

      boolean startC = false;
      boolean startJ = false;
      

      System.out.println("Welcome to the game");
      while(clientgo){
         String sentence = inFromUser.readLine();
         StringTokenizer tokens = new StringTokenizer(sentence);

         //String in = inFromServer.readUTF();
        // System.out.println(in);
         if(sentence.startsWith("create")){
            try{
               outToServer.writeUTF("1");
               //startC = true;
                GameClient c = new GameClient(server,port+1,true,0);
            }catch (Exception e)	{ 
               e.printStackTrace();
               System.out.println("Connection Failed");
               clientgo = false;
            }
            }
            else if(sentence.startsWith("join")){
            try{
               outToServer.writeUTF("0");
               GameClient c = new GameClient(server,port+2,false,0);
               //startJ = true;
            }catch (Exception e)	{ 
               e.printStackTrace();
               System.out.println("Connection Failed");
               clientgo = false;
            }
            }
            else{
            if(sentence.equals("close")){
               clientgo = false;
               System.out.println("Bye Bye!");
            }else{
               System.out.print("wut");
            }
         }
      }       
   }
}

