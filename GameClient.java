import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import javax.swing.*;

public class GameClient {
		
   public GameFrame gui;

   private Socket socket;
   private DataInputStream in;
   private DataOutputStream out;
   boolean turn;
   String[][] board;
   public GameClient(String server,int port,boolean host,int id) {
      
      gui = new GameFrame("Othello");
      board = new String[8][8];
      gui.input.addKeyListener (new EnterListener(this,gui));
      gui.addWindowListener(new ExitListener(this));
      this.turn = host;
      try {

         socket = new Socket(server,port);

         in = new DataInputStream(socket.getInputStream());
         out = new DataOutputStream(socket.getOutputStream());

         String b;
         if(host){
            b = "1";
         }else{
            b = "0";
         }
         out.writeUTF(b);

         while (true) {
            String[] str = in.readUTF().split("");
            int counter = 0;
            boolean flag = false;
            for(int i = 0; i<8;i++){ 
               for(int j = 0; j<8;j++){
                  board[i][j] = str[counter];
                  gui.output.append(" "+board[i][j]);
                  if(board[i][j].equals("P")){
                     flag = true;
                  }
                  counter++; 
               }
               gui.output.append("\n");
            }
            gui.output.append("\n\n");
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
   

   protected void sendTextToGame(String str) {
      try {
         if(this.turn){
            String[] splt = str.split(" ");
            int row = Integer.parseInt(splt[0]);
            int col = Integer.parseInt(splt[1]);
            row--;
            col--;
            if(board[row][col].equals("P")){
               out.writeUTF(str);
            }else{
               gui.output.append("Invalid move!");
            }
         }else{
            gui.output.append("\n It's not your turn!");
         }
         
      } catch (IOException e) {
         System.out.println("Something went wrong!!!");
         //e.printStackTrace();
      }
   }

   protected void disconnect() {
      try {
         socket.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
		
   public static void main (String args[])throws IOException {
       BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
       String sentence = inFromUser.readLine();
       StringTokenizer tokens = new StringTokenizer(sentence);

       boolean clientgo = true;
       String server = "127.0.0.1";
       int port = 3030;
      // Socket ControlSocket= new Socket(server, port);
       System.out.println("Welcome to the game");
       while(clientgo){
         // DataOutputStream outToServer = new DataOutputStream(ControlSocket.getOutputStream()); 
         // DataInputStream inFromServer = new DataInputStream(new BufferedInputStream(ControlSocket.getInputStream()));

         if(sentence.startsWith("create")){
            try{
               GameClient c = new GameClient(server,port,true,0);
            }catch (Exception e)	{ 
               e.printStackTrace();
               System.out.println("Connection Failed");
               clientgo = false;
            }
         }
         else if(sentence.startsWith("join")){
            try{
               GameClient c = new GameClient(server,port,false,0);
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

