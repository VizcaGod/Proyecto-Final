package presentation;

import domain.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {

    private static final int boardSize = 10;
    private JButton[][] cells;
    private JPanel gameBoard;
    private JPanel[] playersInfo;
    private final Color morao = new Color(162, 51, 255);

    public GamePanel() {
        prepareElements();
    }

    private void prepareElements() {
        setPanelsPreferences();
        cells = new JButton[boardSize][boardSize];
        gameBoard = new JPanel(new GridLayout(boardSize, boardSize));
        playersInfo = new JPanel[]{new JPanel(), new JPanel()};
        add(wrapPanel(playersInfo[0]), BorderLayout.NORTH);
        add(gameBoard, BorderLayout.CENTER);
        add(wrapPanel(playersInfo[1]), BorderLayout.SOUTH);
        addButtons();
        addPlayersInfo();
        gameBoard.setBackground(morao);
    }

    private void setPanelsPreferences() {
        setLayout(new BorderLayout());
        setBorder(new LineBorder(Color.black));
    }

    private JPanel wrapPanel(JPanel panelToWrap) {
        var wrapper = new JPanel(new GridBagLayout());
        //wrapper.setBorder(new LineBorder(Color.red));
        wrapper.add(panelToWrap);
        wrapper.setBackground(morao);
        return wrapper;
    }

    private void addButtons() {
        var emptyBorder = BorderFactory.createEmptyBorder();
        var emptyMargin = new Insets(0, 0, 0, 0);
        gameBoard.setPreferredSize(new Dimension(300, 300));
        for (var row = 0; row < boardSize; row++) {
            for (var column = 0; column < boardSize; column++) {
                var button = new JButton("");
                button.setBorder(emptyBorder);
                button.setMargin(emptyMargin);
                button.setBackground(getCellBackgroundColor(row, column));
                button.setEnabled(false);
                cells[row][column] = button;
                gameBoard.add(button);
            }
        }
    }

    private Color getCellBackgroundColor(int row, int column) {
        return ((row % 2 == 1 && column % 2 == 1) || (row % 2 == 0 && column % 2 == 0))
                ? Color.white
                : new Color(162, 51, 255);
    }

    public void addPawns(String[][] pawns) {
        for (var row = 0; row < boardSize; row++) {
            for (var column = 0; column < boardSize; column++) {
                if (pawns[row][column].equals("0")) cells[row][column].setText("O");
                else if (pawns[row][column].equals("1")) cells[row][column].setText("X");
            }
        }
    }

    public void activePlayerPawns(String[][] pawns, Player currentPlayer) {
        for (var row = 0; row < boardSize; row++) {
            for (var column = 0; column < boardSize; column++) {
                if (pawns[row][column].equals(currentPlayer.getId())) cells[row][column].setEnabled(true);
                else cells[row][column].setEnabled(false);
                cells[row][column].setBackground(getCellBackgroundColor(row, column));
                cells[row][column].setName("");
            }
        }
    }

    public boolean cellIsPossibleMove(int x, int y){
        return cells[y][x].getName().contains("possibleMove");
    }

    public int[] getCellMoveSource(int x, int y){
        var cellName = cells[y][x].getName().split(";");
        var source = cellName.length > 0 ? new int[]{Integer.valueOf(cellName[1]), Integer.valueOf(cellName[2])} : null;
        return source;
    }

    public void activePossibleMove(String[][] pawns, Player currentPlayer, ArrayList<int[]> moves, int[] source) {
        activePlayerPawns(pawns, currentPlayer);
        for (var move : moves) {
            cells[move[1]][move[0]].setEnabled(true);
            cells[move[1]][move[0]].setBackground(new Color(194,122,255));
            cells[move[1]][move[0]].setName("possibleMove;"+source[0]+";"+source[1]);
        }
    }

    private void addPlayersInfo() {
        playersInfo[0].add(new JLabel("Player 1 Info"));
        playersInfo[1].add(new JLabel("Player 2 Info"));
        playersInfo[0].setBackground(morao);
        playersInfo[1].setBackground(morao);
    }

    public JButton[][] getCells() {
        return cells;
    }


    public void movePawn(int x, int y, int finalX, int finalY) {
        var pawn = cells[y][x].getText();
        cells[y][x].setText("");
        cells[finalY][finalX].setText(pawn);
    }
}
