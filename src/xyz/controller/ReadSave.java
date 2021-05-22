package xyz.controller;

import xyz.model.*;
import xyz.view.ScoreBoard;
import xyz.view.start.StartFrame;

import java.io.*;

public class ReadSave implements Serializable {
    //可能要修改！
    //那个储存格式根本没用，删了

    private final Board board;
    private final int row, col;
    private final GameControllerData data;

    public ReadSave(Board board, GameController controller) {
        this.board = board;
        row = board.getRow();
        col = board.getColumn();
        data = controller.saveCurrentStatus();
    }

    /**
     * 测试用方法
     */
    public void print() {
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (board.getGrid()[j][i].hasLandMine()) System.out.print("9 ");
                else System.out.print(board.getGrid()[j][i].getNumberOfLandMine() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void resumeGame(StartFrame startFrame) {
        startFrame.initGame(row, col, board, data);
    }

}
