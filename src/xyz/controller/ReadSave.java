package xyz.controller;

import xyz.model.*;
import xyz.view.ScoreBoard;
import xyz.view.start.StartFrame;

import java.io.*;

public class ReadSave implements Serializable {
    /*
     * 文件存储格式：
     * (gameState) (steps) (stepCnt) (sequenceOpen(0=false, 1=true))
     * (playerCount) (currentPlayer) //next n rows
     * (player#n score) (player#n lose)
     * (row) (col) (mineNum) (remainedMineNum)//next row*col
     * (grid[0][0].getNum()) ...
     */
    //可能要修改！

    private final Board board;
    private final int row, col;
    private final Player[] players;
    private final GameControllerData data;

    public ReadSave(Board board, GameController controller, ScoreBoard scoreBoard) {
        this.board = board;
        row = board.getRow();
        col = board.getColumn();
        players = scoreBoard.getPlayers();
        data = controller.saveCurrentStatus();
    }

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
        startFrame.initGame(row, col, board, players, data);
    }


}
