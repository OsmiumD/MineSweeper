package xyz.controller;

//import org.jetbrains.annotations.NotNull;
import xyz.listener.GameListener;
import xyz.model.*;
import xyz.view.*;

public class GameController implements GameListener {
    private final BoardComponent view1;
    private final ScoreBoard view2;
    private final Board model;
    private int currentPlayer;

    public GameController (BoardComponent component, Board board, ScoreBoard scoreBoard) {
        this.view1 = component;
        this.view2 = scoreBoard;
        this.model = board;
        view1.registerListener(this);
        initialGameState();
    }

    public void initialGameState () {
        currentPlayer = 0;
        int num;
        BoardLocation location;
        for (int row = 0; row < model.getRow(); row ++) {
            for (int col = 0; col < model.getColumn(); col ++) {
                location = new BoardLocation(row, col);
                num = model.getNumAt(location);
                view1.setItemAt(location, num);
            }
        }
        view1.repaint();
    }

    public void nextPlayer() {
        currentPlayer = currentPlayer == 0 ? 1 : 0;
    }

    @Override
    public void onPlayerLeftClick(BoardLocation location, SquareComponent component) {
        printMessage(location, "left");
        Square clickedGrid = model.getGridAt(location);
        clickedGrid.setOpened(true);
        view1.setItemAt(location, clickedGrid.getNum());
        view2.goal(currentPlayer);
        repaintAll();
        nextPlayer();
        // TODO: Implement the action after player click left Click
    }

    @Override
    public void onPlayerRightClick(BoardLocation location, SquareComponent component) {
        //printMessage(location, "right");
        nextPlayer();
        // TODO: Implement the action after player click right Click
    }

    @Override
    public void onPlayerMidClick(BoardLocation location, SquareComponent component) {
        printMessage(location, "middle");
        nextPlayer();
        // TODO: Implement the action after player click middle Click
    }

    private void printMessage (BoardLocation location, String str) {
        int row_in_message = location.getRow();
        int column_in_message = location.getColumn();
        String format = "\nOn Player %d %s click at (%d, %d), ";
        System.out.printf(format, currentPlayer, str, row_in_message + 1, column_in_message + 1);
    }

    private void repaintAll(){
        view1.repaint();
        view2.repaint();
    }

    /**
     * 判断是否能结束游戏：
     * ①游戏已结束：是否平局？
     * ②分数差距过大
     */
    private void judgeWinner() {
        //以下：一般判断
        if (model.isGameEnded()) {
            if (view2.getScoreBoard()[0][0] > view2.getScoreBoard()[0][1]) {
                //winner: player_0
            }else if (view2.getScoreBoard()[0][0] < view2.getScoreBoard()[0][1]) {
                //winner: player_1
            }else if (view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][1]) {
                //winner: player_0
            }else if (view2.getScoreBoard()[1][0] > view2.getScoreBoard()[1][1]) {
                //winner: player_1
            }else {
                //平局
            }
        }
        //以下：提前结束
        if (view2.getScoreBoard()[0][0] - view2.getScoreBoard()[0][1] > model.getRemainderMineNum()) {
            //winner: player_0
        }else if (view2.getScoreBoard()[0][0] - view2.getScoreBoard()[0][1] == model.getRemainderMineNum() &&
                  view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][1]) {
            //winner: player_0
        }else if (view2.getScoreBoard()[0][1] - view2.getScoreBoard()[0][0] > model.getRemainderMineNum()) {
            //winner: player_1
        }else if (view2.getScoreBoard()[0][1] - view2.getScoreBoard()[0][0] == model.getRemainderMineNum() &&
                  view2.getScoreBoard()[1][1] < view2.getScoreBoard()[1][0]) {
            //winner: player_1
        }
    }
}
