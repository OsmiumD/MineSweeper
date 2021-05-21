package xyz.controller;

//import org.jetbrains.annotations.NotNull;

import xyz.GameUtil;
import xyz.listener.GameListener;
import xyz.model.*;
import xyz.view.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GameController implements GameListener {
    private final BoardComponent view1;
    private final ScoreBoard view2;
    private final Board model;
    private byte currentPlayer;
    private boolean cheatMode;//false:关闭， true:开启
    private byte gameState;//0:还没开始；1:正在进行；2:已结束; 在GameController.judgeWinner()中用到
    private final byte stepCount;//一个player可以走的步数
    private byte currentStep;//给steps计数
    private final byte playerCount;
    private final boolean sequenceOpen;

    public GameController(BoardComponent component, Board board, ScoreBoard scoreBoard, GameControllerData data) {
        this.view1 = component;
        this.view2 = scoreBoard;
        this.model = board;
        this.stepCount = data.getStepCount();
        this.sequenceOpen = data.isSequenceOpen();
        this.playerCount = data.getPlayerCount();
        this.currentPlayer = data.getCurrentPlayer();
        this.currentStep = data.getCurrentStep();
        this.gameState = data.getGameState();
        view1.registerListener(this);
        initialGameState();
    }

    public void initialGameState() {
        cheatMode=false;
        int num;
        BoardLocation location;
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
        currentStep++;
        if (currentStep == stepCount) {
            currentStep = 0;
            currentPlayer = (currentPlayer == (byte) 0) ? (byte) 1 : (byte) 0;
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
            System.out.println("Invalid Click!\n");
            return;
        }
        printMessage(location, "left");
        if (gameState == 0) {
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

        if (clickedGrid.hasLandMine()) {
            view2.lose(currentPlayer);
        }
        if (sequenceOpen) {
            sequenceOpen(location);
        } else {
            model.openGrid(location);
            view1.setItemAt(location, clickedGrid.getNum());
        }
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
            System.out.println("Invalid Click!");
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
            System.out.println("Invalid Click!");
            return;
        }
        //printMessage(location, "middle");
        if (!cheatMode) {
            cheatMode = true;
            System.out.println("Cheating Mode: On.");
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
            System.out.println("Cheating Mode: Off.");
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
        String format = "On Player %d %s click at (%d, %d), \n";
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
                System.out.println("Tie Game! No Winner!");
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
        System.out.printf("judge winner, gameState is %d\n", gameState);
        if (winnerIsDetermined) {
            System.out.println("Game Ended.\n");
            //TODO:取消（所有？至少返回按键不要）（返回键的Listener不是这样子的，可以取消）Listener注册；显示输赢
            //view1.unregisterListener(this);
        }
    }

    public void sequenceOpen(BoardLocation location) {
        if (!model.isLocationInBound(location)) return;
        Square grid = model.getGridAt(location);
        if (grid.isOpened()) return;
        model.openGrid(location);
        view1.setItemAt(location, grid.getNum());
        view1.repaint();
        if (grid.getNumberOfLandMine() != 0 || grid.hasLandMine()) return;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                sequenceOpen(new BoardLocation(location.getRow() + i, location.getColumn() + j));
            }
        }
    }

    public void saveGame() {
        File file = new File(System.getenv("APPDATA") + "\\MineSweeperJavaA\\" + GameUtil.currentTime() + ".msv");
        ReadSave rs = new ReadSave(model, this, view2);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GameUtil.showMessage("Message","Save Complete!");
    }

    public GameControllerData saveCurrentStatus() {
        return new GameControllerData(gameState, currentStep, stepCount, currentPlayer, playerCount, sequenceOpen);
    }
}
