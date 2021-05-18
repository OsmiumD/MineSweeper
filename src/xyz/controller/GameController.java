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
    private boolean cheatMode;//false:关闭， true:开启
    private byte gameState;//0:还没开始；1:正在进行；2:已结束; 在GameController.judgeWinner()中用到
    private final byte steps;//一个player可以走的步数
    private byte stepCount;//给steps计数
    private final byte playerCount;
    private final boolean sequenceOpen;

    public GameController(BoardComponent component, Board board, ScoreBoard scoreBoard, byte playerCount, byte steps, boolean sequenceOpen) {
        this.view1 = component;
        this.view2 = scoreBoard;
        this.model = board;
        this.steps = steps;
        this.sequenceOpen = sequenceOpen;
        this.playerCount = playerCount;
        view1.registerListener(this);
        initialGameState();
    }

    public void initialGameState() {
        cheatMode = false;
        currentPlayer = 0;
        int num;
        BoardLocation location;
        gameState = 0;
        stepCount = 0;
        for (int row = 0; row < model.getRow(); row++) {
            for (int col = 0; col < model.getColumn(); col++) {
                location = new BoardLocation(row, col);
                num = model.getNumAt(location);
                view1.setItemAt(location, num);
            }
        }

        view1.repaint();
    }

    public void nextPlayer() {
        stepCount++;
        if (stepCount == steps) {
            stepCount = 0;
            currentPlayer = (currentPlayer == 0) ? 1 : 0;
        }
    }

    @Override
    public void onPlayerLeftClick(BoardLocation location, SquareComponent component) {
        if (gameState == 0) {
            model.randomLandMine(location);
            model.iniItem();
            gameState = 1;
        }
        if (!model.isValidClick(location, 1)) {
            System.out.print("\nInvalid Click!");
            return;
        }
        printMessage(location, "left");
        if (gameState == 0) {
            //TODO: 首发不触雷
            gameState = 1;
        }

        // demo里的，先不删
        /*
        Square clickedGrid = model.getGridAt(location);
        clickedGrid.setOpened(true);
        view1.setItemAt(location, clickedGrid.getNum());
        view2.goal(currentPlayer);
        repaintAll();
        nextPlayer();
        */

        Square clickedGrid = model.getGridAt(location);
        model.openGrid(location);

        if (clickedGrid.hasLandMine()) {
            view2.lose(currentPlayer);
        }
        view1.setItemAt(location, clickedGrid.getNum());
        repaintAll();
        if (model.isAllGridClicked()) {
            gameState = 2;//全部open，游戏结束
        }
        judgeWinner();
        nextPlayer();
    }

    @Override
    public void onPlayerRightClick(BoardLocation location, SquareComponent component) {
        if (gameState == 0 || !model.isValidClick(location, 3)) {
            System.out.print("\nInvalid Click!");
            return;
        }
        printMessage(location, "right");

        Square clickedGrid = model.getGridAt(location);
        model.openGrid(location);

        if (clickedGrid.hasLandMine()) {
            // 有雷，则插旗；加分
            model.flagGrid(location);
            view2.goal(currentPlayer);
        } else {
            // 没雷，则正常翻开；扣分
            view2.lose(currentPlayer);
            // TODO: project描述2.2中要在这里”提示：标记错误“
        }
        view1.setItemAt(location, clickedGrid.getNum());
        repaintAll();
        if (model.isAllGridClicked()) {
            gameState = 2;//全部open，游戏结束
        }
        judgeWinner();
        nextPlayer();
    }

    @Override
    // 开作弊模式，显示所有（实际做的是：未open的）格子的数字
    // 关作弊模式，取消未open格子的数字
    public void onPlayerMidClick(BoardLocation location, SquareComponent component) {
        if (gameState == 0 || !model.isValidClick(location, 2)) {
            System.out.print("\nInvalid Click!");
            return;
        }
        //printMessage(location, "middle");
        if (!cheatMode) {
            cheatMode = true;
            System.out.print("\nCheating Mode: On.");
            for (int i = 0; i < model.getRow(); i++) {
                for (int j = 0; j < model.getColumn(); j++) {
                    if (!model.getGrid()[i][j].isOpened()) {
                        view1.setItemAt(model.getGrid()[i][j].getLocation(), model.getGrid()[i][j].getNumberOfLandMine());
                    }
                    if (model.getGrid()[i][j].hasLandMine()) {
                        view1.setItemAt(model.getGrid()[i][j].getLocation(), 9);
                    }
                }
            }
        } else {
            cheatMode = false;
            System.out.print("\nCheating Mode: Off.");
            for (int i = 0; i < model.getRow(); i++) {
                for (int j = 0; j < model.getColumn(); j++) {
                    if (!model.getGrid()[i][j].isOpened()) {
                        view1.setItemAt(model.getGrid()[i][j].getLocation(), model.getGrid()[i][j].getNum());
                        // 这句话的意思参照了initialGameState()
                    }
                    if (model.getGrid()[i][j].isFlag()) {
                        view1.setItemAt(model.getGrid()[i][j].getLocation(), 11);
                    }
                }
            }
        }
        repaintAll();
    }

    private void printMessage(BoardLocation location, String str) {
        int row_in_message = location.getRow();
        int column_in_message = location.getColumn();
        String format = "\nOn Player %d %s click at (%d, %d), ";
        System.out.printf(format, currentPlayer, str, row_in_message + 1, column_in_message + 1);
    }

    private void repaintAll() {
        view1.repaint();
        view2.repaint();
    }

    /**
     * 判断是否能结束游戏：
     * ①游戏已结束：是否平局？
     * ②分数差距过大
     */
    private void judgeWinner() {
        boolean winnerIsDetermined = false;
        //以下：一般判断（所有的格子都open）
        if (gameState == 2) {
            if (view2.getScoreBoard()[0][0] > view2.getScoreBoard()[0][1]) {
                winnerIsDetermined = true;
                //winner: player_0
            } else if (view2.getScoreBoard()[0][0] < view2.getScoreBoard()[0][1]) {
                winnerIsDetermined = true;
                //winner: player_1
            } else if (view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][1]) {
                winnerIsDetermined = true;
                //winner: player_0
            } else if (view2.getScoreBoard()[1][0] > view2.getScoreBoard()[1][1]) {
                winnerIsDetermined = true;
                //winner: player_1
            } else {
                winnerIsDetermined = true;
                System.out.print("\nTie Game! No Winner!");
                //平局
            }
        }
        //以下：提前结束
        if (view2.getScoreBoard()[0][0] - view2.getScoreBoard()[0][1] > model.getRemainderMineNum()) {
            winnerIsDetermined = true;
            //winner: player_0
        } else if (view2.getScoreBoard()[0][0] - view2.getScoreBoard()[0][1] == model.getRemainderMineNum() &&
                view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][1]) {
            winnerIsDetermined = true;
            //winner: player_0
        } else if (view2.getScoreBoard()[0][1] - view2.getScoreBoard()[0][0] > model.getRemainderMineNum()) {
            winnerIsDetermined = true;
            //winner: player_1
        } else if (view2.getScoreBoard()[0][1] - view2.getScoreBoard()[0][0] == model.getRemainderMineNum() &&
                view2.getScoreBoard()[1][1] < view2.getScoreBoard()[1][0]) {
            winnerIsDetermined = true;
            //winner: player_1
        }
        System.out.printf("\njudge winner, gameState is %d", gameState);
        if (winnerIsDetermined) {
            System.out.print("\nGame Ended.\n");
            //TODO:取消（所有？至少返回按键不要）Listener注册；显示输赢
            //view1.unregisterListener(this);
        }
    }

    public int remainMineNum() {
        return model.getRemainderMineNum();
    }

    public int getGameState() {
        return gameState;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public byte getSteps() {
        return steps;
    }

    public byte getStepCount() {
        return stepCount;
    }

    public byte getPlayerCount() {
        return playerCount;
    }

    public boolean isSequenceOpen() {
        return sequenceOpen;
    }
}
