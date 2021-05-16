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

    public GameController (BoardComponent component, Board board, ScoreBoard scoreBoard) {
        this.view1 = component;
        this.view2 = scoreBoard;
        this.model = board;
        view1.registerListener(this);
        initialGameState();
    }

    public void initialGameState () {
        cheatMode = false;
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
        if (model.getGameState() == 0){
            model.setGameState((byte) 1);
            //TODO: 首发不触雷
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
        clickedGrid.setOpened(true);

        if (clickedGrid.hasLandMine()) {
            view2.lose(currentPlayer);
        }
        view1.setItemAt(location, clickedGrid.getNum());
        repaintAll();
        if (model.isAllGridOpened()) {
            model.setGameState((byte) 2);//全部open，游戏结束
        }
        judgeWinner();
        nextPlayer();
    }

    @Override
    public void onPlayerRightClick(BoardLocation location, SquareComponent component) {
        printMessage(location, "right");

        Square clickedGrid = model.getGridAt(location);
        clickedGrid.setOpened(true);

        if (clickedGrid.hasLandMine()) {
            // 有雷，则插旗；加分
            clickedGrid.setFlag(true);
            view2.goal(currentPlayer);
        }else {
            // 没雷，则正常翻开；扣分
            view2.lose(currentPlayer);
            // TODO: project描述2.2中要在这里”提示：标记错误“
        }
        view1.setItemAt(location, clickedGrid.getNum());
        repaintAll();
        if (model.isAllGridOpened()) {
            model.setGameState((byte) 2);//全部open，游戏结束
        }
        judgeWinner();
        nextPlayer();
    }

    @Override
    // 开作弊模式，显示所有（实际做的是：未open的）格子的数字
    // 关作弊模式，取消未open格子的数字
    public void onPlayerMidClick(BoardLocation location, SquareComponent component) {
        printMessage(location, "middle");
        if (!cheatMode) {
            cheatMode = true;
            System.out.print("\nCheating Mode: On.");
            for (int i = 0; i < model.getRow(); i++) {
                for (int j = 0; j < model.getColumn(); j++) {
                    if (!model.getGrid()[i][j].isOpened()) {
                        view1.setItemAt(model.getGrid()[i][j].getLocation(), model.getGrid()[i][j].getNumberOfLandMine());
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
                }
            }
        }
        if (model.isAllGridOpened()) {
            model.setGameState((byte) 2);//全部open，游戏结束
        }
        judgeWinner();
        repaintAll();
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
        boolean winnerIsDetermined = false;
        //以下：一般判断（所有的格子都open）
        if (model.getGameState() == 2) {
            if (view2.getScoreBoard()[0][0] > view2.getScoreBoard()[0][1]) {
                winnerIsDetermined = true;
                //winner: player_0
            }else if (view2.getScoreBoard()[0][0] < view2.getScoreBoard()[0][1]) {
                winnerIsDetermined = true;
                //winner: player_1
            }else if (view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][1]) {
                winnerIsDetermined = true;
                //winner: player_0
            }else if (view2.getScoreBoard()[1][0] > view2.getScoreBoard()[1][1]) {
                winnerIsDetermined = true;
                //winner: player_1
            }else {
                winnerIsDetermined = true;
                System.out.print("\nTie Game! No Winner!");
                //平局
            }
        }
        //以下：提前结束
        if (view2.getScoreBoard()[0][0] - view2.getScoreBoard()[0][1] > model.getRemainderMineNum()) {
            winnerIsDetermined = true;
            //winner: player_0
        }else if (view2.getScoreBoard()[0][0] - view2.getScoreBoard()[0][1] == model.getRemainderMineNum() &&
                  view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][1]) {
            winnerIsDetermined = true;
            //winner: player_0
        }else if (view2.getScoreBoard()[0][1] - view2.getScoreBoard()[0][0] > model.getRemainderMineNum()) {
            winnerIsDetermined = true;
            //winner: player_1
        }else if (view2.getScoreBoard()[0][1] - view2.getScoreBoard()[0][0] == model.getRemainderMineNum() &&
                  view2.getScoreBoard()[1][1] < view2.getScoreBoard()[1][0]) {
            winnerIsDetermined = true;
            //winner: player_1
        }
        System.out.printf("\njudge winner, gameState is %d", model.getGameState());
        if (winnerIsDetermined) {
            System.out.print("\nGame Ended.\n");
            //TODO:取消（所有？至少按键不要）Listener注册；显示输赢
        }
    }
}
