import java.io.*; 
public class Driver{
public static void main (String args[]) throws IOException { 
    BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
    Othello game = new Othello();
    while(true){
        if(!game.isValidMoveAvailable()){
        game.prepareNextTurn();
        } else {
            String line = reader.readLine();

        String[] tokens = line.split(" ");
        int row = Integer.parseInt(tokens[0]);
        int col = Integer.parseInt(tokens[1]);

        row--;
        col--;
        System.out.println(line);
        if(game.isValidMove(row,col)){
            game.placeDisk(row,col);

            game.prepareNextTurn();
            
        }
        }
        System.out.println(game.string());
    }
 }
}