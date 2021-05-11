package xyz.view;

import xyz.controller.GameController;
import xyz.model.Board;

import javax.swing.*;

public class StartFrame extends JFrame {
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
        JButton easy = new JButton("easy");
        easy.setSize(80,20);
        easy.setLocation(90,50);
        easy.setVisible(false);
        add(easy);
        System.out.println("Easy");

        offline.addActionListener(e->{
            offline.setVisible(false);
            easy.setVisible(true);
        });

        easy.addActionListener(e->{
            BoardComponent boardComponent = new BoardComponent(10, 10, 500, 500);
            Board board = new Board(10, 10);
            ScoreBoard scoreBoard = new ScoreBoard();
            scoreBoard.setLocation(500,0);
            GameController gameController = new GameController(boardComponent, board, scoreBoard);

            GameFrame gameFrame = new GameFrame();
            gameFrame.add(boardComponent);
            gameFrame.add(scoreBoard);
            gameFrame.setVisible(true);
            setVisible(false);
        });
    }
}
