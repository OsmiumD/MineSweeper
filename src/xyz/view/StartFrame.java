package xyz.view;

import xyz.controller.GameController;
import xyz.model.Board;

import javax.swing.*;
import java.util.ArrayList;

public class StartFrame extends JFrame {
    private final int MAX_BOARD_WIDTH=800;
    private final int MAX_BOARD_HEIGHT=500;

    public StartFrame() {
        setTitle("Select");
        setSize(100, 400);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JButton offline = new JButton("offline");
        offline.setSize(80, 20);
        offline.setLocation(90, 50);
        add(offline);

        JButton customize=new JButton("customize");
        customize.setSize(120,20);
        customize.setLocation(100,150);
        customize.addActionListener(e->initGame(40,1,10));
        add(customize);
        ArrayList<JButton> difButtons = initDifButton();

        offline.addActionListener(e -> {
            offline.setVisible(false);
            for (JButton button : difButtons) {
                button.setVisible(true);
            }
        });
    }

    private BoardComponent initBoardComponent(int row, int col) {
        double ratioBoard=((double) MAX_BOARD_WIDTH)/((double) MAX_BOARD_HEIGHT);
        int width, height;
        if(col*ratioBoard>row){
            height=MAX_BOARD_HEIGHT;
            width=height/col*row;
        }else{
            width=MAX_BOARD_WIDTH;
            height=width/row*col;
        }
        return new BoardComponent(row, col, width, height);
    }

    private Board initBoard(int row, int col, int mineNum) {
        return new Board(row, col, mineNum);
    }

    private void initGame(Difficulty difficulty) {
        initGame(difficulty.getRow(), difficulty.getCol(), difficulty.getMine());
    }

    private void initGame(int row, int col, int mineNum) {
        int width = 50, height = 50;
        BoardComponent boardComponent = initBoardComponent(row, col);
        width += boardComponent.getWidth();
        height += boardComponent.getHeight();
        Board board = initBoard(row, col, mineNum);
        ScoreBoard scoreBoard = new ScoreBoard();
        width += scoreBoard.getWidth();
        height=Math.max(scoreBoard.getHeight(), height);
        scoreBoard.setLocation(boardComponent.getWidth()-20, 0);
        GameController gameController = new GameController(boardComponent, board, scoreBoard);

        GameFrame gameFrame = new GameFrame();
        gameFrame.setSize(width, height);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.add(boardComponent);
        gameFrame.add(scoreBoard);
        gameFrame.setVisible(true);
        setVisible(false);
    }

    private ArrayList<JButton> initDifButton() {
        ArrayList<JButton> difButtons = new ArrayList<>();
        int y = 50;
        for (Difficulty dif : Difficulty.values()) {
            JButton button = new JButton(dif.toString());
            button.setSize(80, 20);
            button.setLocation(90, y);
            button.setVisible(false);
            button.addActionListener(e -> initGame(dif));
            y += 100;
            add(button);
            difButtons.add(button);
        }
        return difButtons;
    }
}
