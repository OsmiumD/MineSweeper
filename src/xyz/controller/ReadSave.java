package xyz.controller;

import xyz.model.*;
import xyz.view.ScoreBoard;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReadSave implements Serializable{
    /*
     * 文件存储格式：
     * (gameState) (steps) (stepCnt) (sequenceOpen(0=false, 1=true))
     * (playerCount) (currentPlayer) //next n rows
     * (player#n score) (player#n lose)
     * (row) (col) (mineNum) (remainedMineNum)//next row*col
     * (grid[0][0].getNum()) ...
     */

    Board board;
    ScoreBoard scoreBoard;
    GameController controller;

    ReadSave(Board board, ScoreBoard scoreBoard, GameController controller) {
        this.board = board;
        this.scoreBoard = scoreBoard;
        this.controller = controller;
    }

    public static String currentTime() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return time.format(fmt);
    }
}
