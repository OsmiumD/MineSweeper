package xyz;

import xyz.controller.GameController;
import xyz.model.Board;
import xyz.view.BoardComponent;
import xyz.view.GameFrame;
import xyz.view.ScoreBoard;
import xyz.view.StartFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartFrame startFrame = new StartFrame();
            startFrame.setVisible(true);
        });
    }
}
