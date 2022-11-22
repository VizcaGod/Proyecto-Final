package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Damas {

    private static final int boardSize = 10;
    private Player[] players;
    private int currentPlayer;
    private String[][] board;

    public Damas(){
        players = new Player[]{new Player("0"), new Player("1")};
        initializeBoard();
    }

    private void initializeBoard(){
        board = new String[boardSize][boardSize];
        var module = 0;
        for(var row: board) Arrays.fill(row, "");
        for (var row = 0; row < 4; row++){
            for(var column = 0; column < boardSize; column++){
                if (column%2 == module) board[row][column] = "0";
                if (column%2 != module) board[boardSize-row-1][column] = "1";
            }
            module = (module + 1) % 2;
        }

    }

    public String[][] getBoard(){
        return board;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }

    public void changeCurrentPlayer(){
        currentPlayer = (currentPlayer + 1) % 2;
    }

    public ArrayList<int[]> getPossibleMoves(int x, int y){
        var delta = currentPlayer == 0 ? 1 : -1;
        var moves = new ArrayList<int[]>();
        if (boardSize > y+delta && y+delta >= 0 ) {
            if (x+1 < boardSize && board[y+delta][x+1].equals("")) moves.add(new int[]{x+1, y+delta});
            if (x-1 >= 0 && board[y+delta][x-1].equals("")) moves.add(new int[]{x-1, y+delta});
        }
        return moves;
    }

    public void move(int x, int y, int finalX, int finalY){
        var pawn = board[y][x];
        board[y][x] = "";
        board[finalY][finalX] = pawn;
        changeCurrentPlayer();
    }

    public static void main(String[] args){
        var game = new Damas();
        for(var row: game.getBoard()){
            for(var cell: row){
                System.out.print((cell == null  ? "" : cell) + " ");
            }
            System.out.println();
        }
        System.out.println(game.getPossibleMoves(1,3).size());
        for (var move: game.getPossibleMoves(1,3)){
            System.out.println(move[0] + " " + move[1]);
        }
    }
}
