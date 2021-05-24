package xyz.controller;

//import org.jetbrains.annotations.NotNull;

import xyz.GameUtil;
import xyz.listener.GameListener;
import xyz.model.*;
import xyz.view.*;
import xyz.view.music.MusicPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class GameController implements GameListener {
    private final BoardComponent view1;
    private final ScoreBoard view2;
    private final GameInfoComponent view3;
    private final Board model;
    private byte currentPlayerId;
    private boolean cheatMode;//false:关闭， true:开启
    private byte gameState;//0:还没开始；1:正在进行；2:已结束; 在GameController.judgeWinner()中用到
    private final byte stepCount;//一个player可以走的步数
    private byte currentStep;//给steps计数
    private final byte playerCount;
    private final boolean sequenceOpen;
    private byte remainTime;
    private final byte COUNTDOWN_TIME = (byte) 60;
    private Thread timer;
    private final Player[] players;

    public GameController(BoardComponent component, Board board, ScoreBoard scoreBoard, GameInfoComponent infoComponent, GameControllerData data) {
        this.view1 = component;
        this.view2 = scoreBoard;
        this.view3 = infoComponent;
        this.model = board;
        this.stepCount = data.getStepCount();
        this.sequenceOpen = data.isSequenceOpen();
        this.playerCount = data.getPlayerCount();
        this.currentPlayerId = data.getCurrentPlayerId();
        this.currentStep = data.getCurrentStep();
        this.gameState = data.getGameState();
        this.players = data.getPlayers();
        view1.registerListener(this);
        view1.registerGridListener(this);
        initialGameState();
    }

    public void initialGameState() {
        cheatMode = false;
        remainTime = COUNTDOWN_TIME;

        renewTexture();

        view3.setStep((byte) (stepCount - currentStep));
        view3.setPlayer(currentPlayerId);
        view3.setTime(remainTime);
        view3.setRemainMine(model.getRemainderMineNum());

        startTimer();
        repaintAll();
    }

    void nextPlayer() {
        currentStep++;
        remainTime = COUNTDOWN_TIME;
        if (currentStep == stepCount) {
            currentStep = 0;
            currentPlayerId = (byte) ((currentPlayerId + 1) % playerCount);//id从0开始
        }
        view3.setPlayer(currentPlayerId);
        view3.setStep((byte) (stepCount - currentStep));
        view3.repaint();
    }

    @Override
    public void onPlayerLeftClick(BoardLocation location, SquareComponent component) {
        if (gameState == 0) {
            model.randomLandMine(location);
            model.iniItem();
            gameState = 1;
        }
        if (!model.isValidClick(location, 1) || cheatMode || gameState == 2) {
            System.out.println("Invalid Click!");
            return;
        }
        printMessage(location, "left");
        players[currentPlayerId].getClickedLocations().add(location);

        Square clickedGrid = model.getGridAt(location);

        if (clickedGrid.hasLandMine()) {
            lose(currentPlayerId, location);
            MusicPlayer boom = new MusicPlayer("src\\xyz\\view\\music\\boom.mp3");
            boom.play();
            new Thread(new AnimationRunnable(GameUtil.getBoom(), view1.getGridAt(location))).start();
        }else{
            new MusicPlayer("src\\xyz\\view\\music\\open.mp3").play();
        }
        if (sequenceOpen) {
            sequenceOpen(location);
        } else {
            model.openGrid(location);
            view1.setItemAt(location, clickedGrid.getNum());
            view3.setRemainMine(model.getRemainderMineNum());
        }
        repaintAll();
        if (model.getRemainderMineNum() == 0) {
            gameState = 2;//全部open，游戏结束
        }
        judgeWinner();
        nextPlayer();
    }

    @Override
    public void onPlayerRightClick(BoardLocation location, SquareComponent component) {
        if (gameState == 0 || !model.isValidClick(location, 3) || cheatMode || gameState == 2) {
            System.out.println("Invalid Click!");
            return;
        }
        printMessage(location, "right");

        players[currentPlayerId].getClickedLocations().add(location);
        Square clickedGrid = model.getGridAt(location);
        model.openGrid(location);

        if (clickedGrid.hasLandMine()) {
            // 有雷，则插旗；加分
            model.flagGrid(location);
            goal(currentPlayerId, location);
        } else {
            // 没雷，则正常翻开；失误
            turnover(currentPlayerId, location);
            // TODO: project描述2.2中要在这里”提示：标记错误“
        }
        view1.setItemAt(location, clickedGrid.getNum());
        view3.setRemainMine(model.getRemainderMineNum());
        if (model.getRemainderMineNum() == 0) {
            gameState = 2;//全部open，游戏结束
        }
        judgeWinner();
        nextPlayer();
        repaintAll();
        machinePlayerMove();
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
        machinePlayerMove();
    }

    @Override
    public void mouseEnter(BoardLocation location, SquareComponent component) {
        Square enteredGrid = model.getGridAt(location);
        System.out.println("Mouse enter");
        view1.setItemAt(location, 12);
        repaintAll();
    }

    @Override
    public void mouseExit(BoardLocation location, SquareComponent component) {
        Square enteredGrid = model.getGridAt(location);
        view1.setItemAt(location, model.getNumAt(location));
        repaintAll();
    }

    private void printMessage(BoardLocation location, String str) {
        int row_in_message = location.getRow();
        int column_in_message = location.getColumn();
        String format = "On Player %d %s click at (%d, %d), \n";
        System.out.printf(format, currentPlayerId, str, row_in_message + 1, column_in_message + 1);
    }

    private void repaintAll() {
        view1.repaint();
        view2.update(players);
        view2.repaint();
        view3.repaint();
    }

    /**
     * 判断是否能结束游戏：
     * ①游戏已结束：是否平局？
     * ②分数差距过大
     */
    private void judgeWinner() {
        boolean winnerIsDetermined = false;
        SortedSet<Player> playersSortedSet = new TreeSet<>(Arrays.asList(players));

        //以下：一般判断（所有的格子都open）
        if (gameState == (byte) 2) {
            winnerIsDetermined = true;
            ArrayList<Player> winners = new ArrayList<>();
            Player topPlayer = playersSortedSet.first();
            for (Player player : playersSortedSet) {
                if (topPlayer.compareTo(player) == 0) {
                    winners.add(player);
                } else break;
            }
            if (winners.size() == playerCount) {
                System.out.println("Tie Game! No Winner!");
            } else {
                if (winners.size() == 1) System.out.print("WINNER: ");
                else System.out.print("WINNERs: ");
                for (Player player : winners) {
                    System.out.print(player);
                }
                System.out.println();
            }
        }

        //以下：提前结束
        else {
            Player p1 = playersSortedSet.first();
            Player p2 = p1;
            boolean tag = true;
            for (Player p :
                    playersSortedSet) {
                p2 = p;
                if (tag) tag = false;
                else break;
            }
            if (p1.getScoreCnt() - p2.getScoreCnt() > model.getRemainderMineNum()) {
                winnerIsDetermined = true;
                System.out.printf("WINNER: %s\n", p1);
                gameState = 2;
            }
        }

        System.out.printf("judge winner, gameState is %d\n", gameState);
        if (winnerIsDetermined) {
            System.out.println("Game Ended.");
            RankFrame rankFrame = new RankFrame(players);
            rankFrame.setVisible(true);
            rankFrame.repaint();
        } else {
            System.out.println("Game Continue.");
        }
    }

    public void sequenceOpen(BoardLocation location) {
        if (!model.isLocationInBound(location)) return;
        Square grid = model.getGridAt(location);
        if (grid.isOpened()) return;
        model.openGrid(location);
        view1.setItemAt(location, grid.getNum());
        view3.setRemainMine(model.getRemainderMineNum());
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
        ReadSave rs = new ReadSave(model, this);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GameUtil.showMessage("Message", "Save Complete!");
    }

    public GameControllerData saveCurrentStatus() {
        return new GameControllerData(gameState, currentStep, stepCount, currentPlayerId, playerCount, sequenceOpen, players);
    }

    public void resetGame() {
        if (gameState == 0) return;
        cheatMode = false;
        gameState = (byte) 1;
        currentPlayerId = (byte) 0;
        currentStep = (byte) 0;
        remainTime = COUNTDOWN_TIME;
        timer.interrupt();
        for (Player player : players) {
            player.remake();
        }

        //Board model的参数
        for (int i = 0; i < model.getRow(); i++) {
            for (int j = 0; j < model.getColumn(); j++) {
                model.getGrid()[i][j].setOpened(false);
                model.getGrid()[i][j].setFlag(false);
            }
        }
        model.setRemainderMineNum(model.getMineNum());

        //BoardComponent view1的参数
        BoardLocation location;
        for (int i = 0; i < model.getRow(); i++) {
            for (int j = 0; j < model.getColumn(); j++) {
                location = new BoardLocation(i, j);
                view1.setItemAt(location, 10);//10:closed
            }
        }

        //ScoreBoard view2在repaintAll()中reset，即.update(players)

        //GameInfoComponent view3的参数
        view3.setStep((byte) (stepCount - currentStep));
        view3.setPlayer(currentPlayerId);
        view3.setTime(remainTime);
        view3.setRemainMine(model.getRemainderMineNum());

        startTimer();
        repaintAll();
    }

    void tick() {
        remainTime--;
        if (remainTime <= 0) {
            nextPlayer();
        }
        view3.setTime(remainTime);
        view3.repaint();
    }

    private void startTimer() {
        timer = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Timer interrupted");
                }
                tick();
            }
        });
        timer.start();
    }

    private void machinePlayerMove() {
        if (players[currentPlayerId].getClass() == MachinePlayer.class) {
            MachinePlayer player = (MachinePlayer) players[currentPlayerId];
            player.move(model);
            BoardLocation clickedLocation = player.getClickLocation();
            SquareComponent clickedComponent = view1.getGridAt(clickedLocation);
            if (player.getClickType() == 1) {
                onPlayerLeftClick(clickedLocation, clickedComponent);
            } else {
                onPlayerRightClick(clickedLocation, clickedComponent);
            }
        }
    }
    public boolean undo() {
        timer.interrupt();

        byte playerIdToBe;
        byte stepToBe;
        if (currentStep != 0) {
            playerIdToBe = currentPlayerId;
            stepToBe = (byte) (currentStep - 1);
        } else {
            playerIdToBe = (byte) (currentPlayerId - 1);
            stepToBe = (byte) (stepCount - 1);
            if (playerIdToBe < 0) playerIdToBe += playerCount;
        }
        Player playerTobe = players[playerIdToBe];
        List<BoardLocation> clickedLocations = players[playerIdToBe].getClickedLocations();
        if (clickedLocations.size() == 0) return false;
        BoardLocation lastClickedLocation = clickedLocations.get(clickedLocations.size() - 1);
        Square lastClickedGrid = model.getGridAt(lastClickedLocation);

        lastClickedGrid.setOpened(false);
        if (lastClickedGrid.hasLandMine()) {
            model.setRemainderMineNum(model.getRemainderMineNum() + 1);
            if (lastClickedGrid.isFlag()) {
                playerTobe.unGoal();
                lastClickedGrid.setFlag(false);
            } else {
                playerTobe.unLose();
            }
        } else {
            if (playerTobe.getTurnoverOrNot().get(lastClickedLocation)) {
                playerTobe.unTurnover();
            }
        }

        view3.setStep(stepToBe);
        view3.setPlayer(playerIdToBe);
        view3.setTime(COUNTDOWN_TIME);
        view3.setRemainMine(model.getRemainderMineNum());

        startTimer();
        repaintAll();
        return true;
    }

    public void goal(byte playerId, BoardLocation location) {
        players[playerId].goal(location);
    }

    public void lose(byte playerId, BoardLocation location) {
        players[playerId].lose(location);
    }

    public void turnover(byte playerId, BoardLocation location) {
        players[playerId].turnover(location);
    }

    public void renewTexture() {
        BoardLocation location;
        for (int row = 0; row < model.getRow(); row++) {
            for (int col = 0; col < model.getColumn(); col++) {
                location = new BoardLocation(row, col);
                view1.setItemAt(location, model.getNumAt(location));
            }
        }
        view1.repaint();
    }
}
