package src;
import java.util.ArrayList;
import java.util.Random;


public class GameLogic {

    private GameBoard board;

   
    private static final int[][] WIN_PATTERNS = {
        {0, 1, 2}, 
        {3, 4, 5}, 
        {6, 7, 8}, 
        {0, 3, 6}, 
        {1, 4, 7}, 
        {2, 5, 8}, 
        {0, 4, 8}, 
        {2, 4, 6}  
    };

    public GameLogic() {
        board = new GameBoard();
    }

    /** Mereset papan untuk game baru. */
    public void resetBoard() {
        board.reset();
    }

  
    public boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= 9) {
            return false;
        }
        if (!board.isEmpty(index)) {
            return false; // sel sudah terisi
        }
        board.setCell(index, symbol);
        return true;
    }

 
    public boolean checkWinner(char symbol) {
        for (int[] pattern : WIN_PATTERNS) {
            int a = pattern[0];
            int b = pattern[1];
            int c = pattern[2];
            if (board.getCell(a) == symbol &&
                board.getCell(b) == symbol &&
                board.getCell(c) == symbol) {
                return true;
            }
        }
        return false;
    }

 
    public boolean isDraw() {
        for (int i = 0; i < 9; i++) {
            if (board.isEmpty(i)) {
                return false;
            }
        }
        return true;
    }


    public int computerMove() {
        // 1. Coba menang
        int winMove = findBestMove('O');
        if (winMove != -1) return winMove;

        // 2. Blok pemain
        int blockMove = findBestMove('X');
        if (blockMove != -1) return blockMove;

        // 3. Ambil tengah
        if (board.isEmpty(4)) return 4;

        // 4. Ambil sudut acak
        int[] corners = {0, 2, 6, 8};
        ArrayList<Integer> freeCorners = new ArrayList<>();
        for (int c : corners) {
            if (board.isEmpty(c)) freeCorners.add(c);
        }
        if (!freeCorners.isEmpty()) {
            Random rnd = new Random();
            return freeCorners.get(rnd.nextInt(freeCorners.size()));
        }

        // 5. Sel kosong mana saja
        ArrayList<Integer> freeCells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board.isEmpty(i)) freeCells.add(i);
        }
        if (!freeCells.isEmpty()) {
            Random rnd = new Random();
            return freeCells.get(rnd.nextInt(freeCells.size()));
        }

        return -1; // tidak ada sel kosong
    }

    /**
     * Mencari gerakan terbaik untuk simbol tertentu (menang atau blok).
     */
    private int findBestMove(char symbol) {
        for (int[] pattern : WIN_PATTERNS) {
            int a = pattern[0];
            int b = pattern[1];
            int c = pattern[2];

            // Cek apakah 2 sel terisi simbol dan 1 sel kosong
            if (board.getCell(a) == symbol && board.getCell(b) == symbol && board.isEmpty(c)) return c;
            if (board.getCell(a) == symbol && board.getCell(c) == symbol && board.isEmpty(b)) return b;
            if (board.getCell(b) == symbol && board.getCell(c) == symbol && board.isEmpty(a)) return a;
        }
        return -1;
    }

    /** Mengembalikan objek GameBoard. */
    public GameBoard getBoard() {
        return board;
    }
}