import java.util.ArrayList;

public class Othello{
	int[][] board= new int[8][8];
	int size = 8;
	int turn;
	ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
	public Othello(){
		this.size = 8;
		this.turn = -1;
		intializeBoard();
	}
	void intializeBoard(){
		int center = this.size/2;
		for(int i =0; i<this.size;i++){
			for(int j = 0; j<this.size; j++){
				board[i][j] = 0;
			}
		}
		board[center-1][center-1] = -1;
		board[center][center] = -1;
		board[center-1][center] = 1;
		board[center][center-1] = 1;
		possibleMoves = generatePossibleMoves();
	}
  	boolean isValidRow(int row,int col,int[] dir,boolean flip){
  		row = row+dir[0];
  		col = col+dir[1];
		if(row >= this.size || col>= this.size || row<0 || col <0){
			return false;
		}
		if(this.board[row][col] == 0){
			return false;
		}
		if(this.turn == this.board[row][col]){
			return true;
		}
		if(this.isValidRow(row,col,dir,flip)){
			if(flip){
				this.board[row][col] *=-1;
			}
			return true;
		}
		return false;  		
  	}
	boolean checkRows(int row, int col,boolean flip){
		if(board[row][col] != 0){
			return false;
		}
		boolean flag = false;
		int[] dir1 = new int[]{0,1,1,0,-1,-1,1,-1};
		int[] dir2 = new int[]{1,0,1,-1,0,-1,-1,1}; 
		for(int i = 0;i<8;i++){
			if(!(row+dir1[i] >= this.size || col+dir2[i]>= this.size || row+dir1[i]<0 || col+dir2[i] <0)){
				if(this.board[row+dir1[i]][col+dir2[i]] == this.turn*-1){
					if(this.isValidRow(row,col,new int[]{dir1[i],dir2[i]},flip)){
						flag = true;

					}

				}
			}
		}
		return flag;	
	}
	boolean isValidMove(int row, int col){
		return checkRows(row,col,false);
	}
	boolean isValidMoveAvailable(){
		for(int i = 0;i<possibleMoves.size();i++){
			// System.out.print(possibleMoves.get(i)[0]+1);
			// System.out.print(" , ");
			// System.out.println(possibleMoves.get(i)[1]+1);
		}
		if(possibleMoves.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	void placeDisk(int row, int col){
		boolean flag = false;
		for (int i = 0; i < possibleMoves.size(); i++) {
			int[] pos = possibleMoves.get(i);
    	  if(row == pos[0] && col == pos[1]){
    	  	flag = true;
    	  	break;
    	  }
    	}
    	if(!flag){
    		return;
    	}
		checkRows(row,col,true);
		this.board[row][col] = this.turn;
	}
	void prepareNextTurn(){
		this.turn*=-1;
		possibleMoves = generatePossibleMoves();

	}
	ArrayList<int[]> generatePossibleMoves(){
		ArrayList<int[]> temp = new ArrayList<int[]>(); 
		for(int i =0; i<this.size;i++){
			for(int j = 0; j<this.size; j++){
				if(isValidMove(i,j)){
					temp.add(new int[]{i,j});
				}
			}
		}
		return temp;
	}
	boolean isBoardFull() {
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size ; j++){
				if(this.board[i][j] == 0)
					return false;
			}
		}

		return true;
	}
	boolean isGameOver(){
		if(possibleMoves.size() == 0 || isBoardFull()){
			return true;
		}
		return false;
	}
	int whosTurn(){
		return this.turn;
	}
	int checkWinner() {
		int Bcount = 0;
		int Wcount = 0;
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size ; j++){
				if(this.board[i][j] == -1)
					Bcount++;
				if(this.board[i][j] == 1)
					Wcount++;			
			}
		}
		if(Bcount>Wcount){
			return -1;
		}
		if(Wcount>Bcount){
			return 1;
		}else{
			return 0;
		}
		
	}
	int[][] getBoard(){
		return this.board;
	}
	int[][] getPossibleBoard(){
		int[][] possibleBoard = new int[8][8];
		for(int i =0; i<this.size;i++){
			for(int j = 0; j<this.size; j++){
				possibleBoard[i][j] = board[i][j];
			}
		}
		for(int i = 0; i<possibleMoves.size();i++){
			int[] loc = possibleMoves.get(i);
			possibleBoard[loc[0]][loc[1]] = 2;
		}
		return possibleBoard;
	}
	String getBoardString(){
		String str = "";
		for(int i = 0;i<this.size;i++){
			for(int j = 0; j<this.size;j++){
				if(this.board[i][j] == -1){
					str+="B";
				}else if(this.board[i][j] == 1){
					str+="W";
				}else{
					str+="-";
				}
			}
		}

		return str;
	}
	String getPossibleBoardString(){
		String str = "";
		int[][] b = getPossibleBoard();
		for(int i = 0;i<this.size;i++){
			for(int j = 0; j<this.size;j++){
				if(b[i][j] == -1){
					str+="B";
				}else if(b[i][j] == 1){
					str+="W";
				}else if(b[i][j] == 2){
					str+="P";
				}else{
					str+="-";
				}
			}
		}

		return str;
	}
	String getGameStatus(){
		if(isGameOver()){
			prepareNextTurn();
			if(isGameOver()){
				int winner = checkWinner();
				String ret = "0";
				if(winner == -1){
					ret = "2";
				}else if(winner == 1){
					ret = "1";
				}else if(winner == 0){
					ret = "3";
				}	
				return ret;
			}
			return "0";
		}else{
			return "0";
		}
	}
	String string() {
		String str = "\n ";
		for (int i = 0; i < this.size; i++) {
			str += " " + (i+1);
		}
		str += "\n";
		for (int i = 0; i < this.size; i++) {
			str += i+1 + " ";
			for(int j=0;j<this.size;j++){
				String place;
				if(this.board[i][j] == 1){
					place = "W ";
				}
				else if(this.board[i][j] == -1){
					place = "B ";
				}else{
					place = "- ";
				}
				str+= place;
			}
			str += "\n";
		}
		return str;
	}

}