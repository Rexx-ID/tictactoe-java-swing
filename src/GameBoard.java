package src;

public class GameBoard {
    private char[] board;

    public GameBoard() {
        board = new char[9];
        reset();
    }

    
    public void reset() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    
    public char[] getBoard() {
        return board;
    }

    
    public char getCell(int index) {
        return board[index];
    }

    
    public void setCell(int index, char symbol) {
        board[index] = symbol;
    }

    
    public boolean isEmpty(int index) {
        return board[index] == ' ';
    }
}