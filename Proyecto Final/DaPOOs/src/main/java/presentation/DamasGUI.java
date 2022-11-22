package presentation;

import domain.Damas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DamasGUI extends JFrame {

    private GamePanel gamePanel;
    private Damas game;
    private final Color morao = new Color(162, 51, 255);

    public DamasGUI(){
        game = new Damas();
        prepareElements();
        prepareActions();
    }

    private void prepareElements() {
        setWindowPreferences();
        gamePanel = new GamePanel();
        add(gamePanel);
        gamePanel.addPawns(game.getBoard());
        gamePanel.setBackground(morao);
        gamePanel.activePlayerPawns(game.getBoard(), game.getCurrentPlayer());
    }

    private void setWindowPreferences(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.height / 2, screenSize.height / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void prepareActions(){
        var rowIndex = 0;
        for (var row: gamePanel.getCells()){
            var columnIndex = 0;
            for(var button: row){
                var x = columnIndex;
                var y = rowIndex;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectPawn(x, y);
                    }
                });
                columnIndex += 1;
            }
            rowIndex++;
        }
    }

    private void selectPawn(int x, int y){
        if (gamePanel.cellIsPossibleMove(x,y)){
            var move = gamePanel.getCellMoveSource(x,y);
            game.move(move[0], move[1], x, y);
            gamePanel.movePawn(move[0], move[1], x, y);
            gamePanel.activePlayerPawns(game.getBoard(), game.getCurrentPlayer());
        }else
            gamePanel.activePossibleMove(game.getBoard(), game.getCurrentPlayer(), game.getPossibleMoves(x, y), new int[]{x,y});
    }

    public static void main(String[] args){
        var theGame = new DamasGUI();
        theGame.setVisible(true);
    }
}
