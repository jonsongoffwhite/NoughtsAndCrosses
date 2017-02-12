import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JHGWhite on 11/01/2017.
 */

public class State {

    public final Cell[][] board;

    public final Integer lastX;
    public final Integer lastY;

    // Initialise a fresh state in which the board is empty
    public State() {
        board = new Cell[NoughtsAndCrosses.DIMENSION][NoughtsAndCrosses.DIMENSION];

        for (int i = 0; i < NoughtsAndCrosses.DIMENSION; i++) {
            for (int j = 0; j < NoughtsAndCrosses.DIMENSION; j++) {
                board[i][j] = new Cell();
            }
        }

        lastX = null;
        lastY = null;
        //lastSymbol = null;
    }

    // Create a state from a pre-made board
    // Must specify what the last moves were
    // For testing purposes
    public State(Cell[][] board, Integer lastX, Integer lastY) {
        this.board = new Cell[NoughtsAndCrosses.DIMENSION][NoughtsAndCrosses.DIMENSION];

        for (int i = 0; i < NoughtsAndCrosses.DIMENSION; i++) {
            for (int j = 0; j < NoughtsAndCrosses.DIMENSION; j++) {
                this.board[i][j] = board[i][j].copy();
            }
        }

        this.lastX = lastX;
        this.lastY = lastY;
    }

    // Initialise a state from a previous state with one alteration
    public State(State prev, Symbol turn, int i, int j) {
        //Cell[][] temp = prev.board.clone();

        Cell[][] temp = new Cell[NoughtsAndCrosses.DIMENSION][NoughtsAndCrosses.DIMENSION];

        for (int k = 0; k < NoughtsAndCrosses.DIMENSION; k++) {
            for (int l = 0; l < NoughtsAndCrosses.DIMENSION; l++) {
                temp[k][l] = prev.board[k][l].copy();
            }
        }

        temp[i][j].setContents(turn);
        board = temp;
        lastX = i;
        lastY = j;
        //lastSymbol = turn;
    }

    public int emptyTileCount() {
        int emptyTiles = 0;
        for (Cell[] column : board) {
            for (Cell tile: column) {
                if (tile.getContents() == Symbol.EMPTY) {
                    emptyTiles ++;
                }
            }
        }
        return emptyTiles;
    }

    public List<Coordinate> getEmptyTiles() {
        List<Coordinate> emptyTiles = new ArrayList<>();

        for (int i = 0; i < NoughtsAndCrosses.DIMENSION; i++) {
            for (int j = 0; j < NoughtsAndCrosses.DIMENSION; j++) {
                if (board[i][j].getContents() == Symbol.EMPTY) {
                    emptyTiles.add(new Coordinate(i, j));
                }
            }
        }
        return emptyTiles;
    }

    // Private method to determine whether or not the board is full
    public boolean boardFilled() {
        for (Cell[] row : board) {
            for (Cell cell : row) {
                if (cell.getContents() == Symbol.EMPTY)
                    return false;
            }
        }
        return true;
    }

    public boolean checkForWinner(Symbol symbol) {

        // Column win
        int column = lastY;
        int rowCounter = 0;
        for (int i = 0; i < NoughtsAndCrosses.DIMENSION; i++) {
            if (board[i][column].getContents() == symbol) {
                rowCounter ++;
            }
        }

        if (rowCounter == NoughtsAndCrosses.DIMENSION)
            return true;

        // Row win
        int row = lastX;
        int columnCounter = 0;
        for (int j = 0; j < NoughtsAndCrosses.DIMENSION; j++) {
            if (board[row][j].getContents() == symbol) {
                columnCounter ++;
            }
        }

        if (columnCounter == NoughtsAndCrosses.DIMENSION)
            return true;

        // L - R Diagonal win
        if (lastX == lastY) {
            int diagCounter = 0;

            for (int ij = 0; ij < NoughtsAndCrosses.DIMENSION; ij++) {
                if (board[ij][ij].getContents() == symbol)
                    diagCounter ++;
            }

            if (diagCounter == NoughtsAndCrosses.DIMENSION)
                return true;
        }

        // R - L Diagonal win
        if (lastX + lastY == NoughtsAndCrosses.DIMENSION - 1) {
            int diagCounter = 0;

            for (int i = 0; i < NoughtsAndCrosses.DIMENSION; i++) {
                if (board[i][NoughtsAndCrosses.DIMENSION - 1 - i].getContents() == symbol) {
                    diagCounter++;
                }
            }

            if (diagCounter == NoughtsAndCrosses.DIMENSION)
                return true;
        }

        return false;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        for (Cell[] column : board) {
            for (Cell cell: column) {
                Symbol contents = cell.getContents();
                builder.append(contents == Symbol.EMPTY ? "_" : contents);
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

//        for (int i = 0; i < NoughtsAndCrosses.DIMENSION; i++) {
//            for (int j = 0; j < NoughtsAndCrosses.DIMENSION; i++) {
//                if (!board[i][j].equals(state.board[i][j])) {
//                    return false;
//                }
//            }
//        }
//        return true;
        
        return Arrays.deepEquals(board, state.board);

    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }


}
