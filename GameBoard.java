
public class GameBoard {
    private char[] board;

    public GameBoard() {
        board = new char[9];
        reset();
    }

    /** Mengosongkan semua sel papan. */
    public void reset() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    /** Mengembalikan seluruh array papan. */
    public char[] getBoard() {
        return board;
    }

    /** Mengambil simbol pada sel tertentu. */
    public char getCell(int index) {
        return board[index];
    }

    /** Mengisi sel tertentu dengan simbol. */
    public void setCell(int index, char symbol) {
        board[index] = symbol;
    }

    /** Mengecek apakah sel tertentu masih kosong. */
    public boolean isEmpty(int index) {
        return board[index] == ' ';
    }
}