package xyz.view.start;

import xyz.GameUtil;
import xyz.controller.GameController;
import xyz.controller.GameControllerData;
import xyz.controller.ReadSave;
import xyz.model.Board;
import xyz.model.MachinePlayer;
import xyz.model.Player;
import xyz.view.*;
import xyz.view.music.MusicPlayer;

import javax.swing.*;
import java.io.*;

public class StartFrame extends JFrame {

    private final PlayerSettingPanel playerSettingPanel;
    private final AvatarPanel avatarPanel;

    public StartFrame() {
        setTitle("Select");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JButton offline = new JButton("Play");
        offline.setSize(80, 20);
        offline.setLocation(60, 50);

        JButton customize = new JButton("customize");
        customize.setSize(120, 20);
        customize.setLocation(40, 220);

        JButton customizeStart = new JButton("start");
        customizeStart.setSize(80, 20);
        customizeStart.setLocation(60, 300);

        JButton loadGame = new JButton("Load Game");
        loadGame.setSize(120, 20);
        loadGame.setLocation(40, 210);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(null);
        startPanel.setSize(200, 400);
        startPanel.add(offline);
        startPanel.add(loadGame);
        add(startPanel);

        JPanel difSelectPanel = new JPanel();
        ComponentBackButton difBack = new ComponentBackButton(startPanel, difSelectPanel);
        difSelectPanel.setLayout(null);
        difSelectPanel.setSize(200, 400);
        difSelectPanel.add(difBack);
        difSelectPanel.add(customize);
        initDifButton(difSelectPanel);
        difSelectPanel.setVisible(false);
        add(difSelectPanel);

        CustomizeDifPanel customizeDifPanel = new CustomizeDifPanel();
        customizeDifPanel.add(new ComponentBackButton(difSelectPanel, customizeDifPanel));
        customizeDifPanel.setVisible(false);
        customizeDifPanel.add(customizeStart);
        add(customizeDifPanel);

        playerSettingPanel = new PlayerSettingPanel();
        add(playerSettingPanel);

        avatarPanel = new AvatarPanel();
        avatarPanel.setPlayerNum((byte) 2);
        add(avatarPanel);

        offline.addActionListener(e -> {
            startPanel.setVisible(false);
            difSelectPanel.setVisible(true);
        });

        customize.addActionListener(e -> {
            customizeDifPanel.setVisible(true);
            difSelectPanel.setVisible(false);
        });

        customizeStart.addActionListener(e -> {
            if (customizeDifPanel.isDataAvailable()) {
                initGame(customizeDifPanel.getCol(), customizeDifPanel.getRow(), customizeDifPanel.getMineNum());
            }
        });

        loadGame.addActionListener(e -> {
            FileNameFrame loadFrame = new FileNameFrame();
            loadFrame.setTitle("Load Game");
            loadFrame.setVisible(true);
            loadFrame.addActionListener(e1 -> {
                loadGame(loadFrame.getFile());
                loadFrame.dispose();
            });
        });

        playerSettingPanel.addPlayerListener(e -> {
            avatarPanel.setPlayerNum(playerSettingPanel.getPlayerCount());
            avatarPanel.repaint();
        });
    }

    private void loadGame(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ReadSave rs = (ReadSave) ois.readObject();
            rs.resumeGame(this);
        } catch (Exception ex) {
            GameUtil.showMessage("Error", "Load Game Failed");
        }
    }

    private BoardComponent initBoardComponent(int row, int col) {
        int MAX_BOARD_WIDTH = 1000;
        int MAX_BOARD_HEIGHT = 600;
        double ratioBoard = ((double) MAX_BOARD_WIDTH) / ((double) MAX_BOARD_HEIGHT);
        int width, height;
        if (col * ratioBoard > row) {
            height = MAX_BOARD_HEIGHT;
            width = height / col * row;
        } else {
            width = MAX_BOARD_WIDTH;
            height = width / row * col;
        }
        return new BoardComponent(row, col, width, height);
    }

    private Board initBoard(int row, int col, int mineNum) {
        return new Board(row, col, mineNum);
    }

    private void initGame(Difficulty difficulty) {
        initGame(difficulty.getRow(), difficulty.getCol(), difficulty.getMine());
    }

    private void initGame(int row, int col, int mineNum) {
        BoardComponent boardComponent = initBoardComponent(row, col);
        Board board = initBoard(row, col, mineNum);
        Player[] players = new Player[playerSettingPanel.getPlayerCount()];
        for (byte i = 0; i < playerSettingPanel.getPlayerCount(); i++) {
            if (avatarPanel.isMachine(i)) {
                players[i] = new MachinePlayer(i);
                players[i].setAvatar((byte) 4);
            } else {
                players[i] = new Player(i);
                players[i].setAvatar(avatarPanel.getAvatar(i));
            }

        }
        GameControllerData data = new GameControllerData((byte) 0, (byte) 0, playerSettingPanel.getStep(), (byte) 0, playerSettingPanel.getPlayerCount(), playerSettingPanel.isSequenceOpen(), players);
        initGame(boardComponent, board, data);
    }

    public void initGame(int row, int col, Board board, GameControllerData data) {
        BoardComponent boardComponent = initBoardComponent(row, col);
        initGame(boardComponent, board, data);
    }

    private void initGame(BoardComponent boardComponent, Board board, GameControllerData data) {
        int width = 0, height = 30;
        boardComponent.setLocation(0, height);
        width += boardComponent.getWidth();
        height += boardComponent.getHeight();

        ScoreBoard scoreBoard = new ScoreBoard(data.getPlayerCount());
        scoreBoard.setLocation(width - 20, 40);
        width += scoreBoard.getWidth();
        height = Math.max(scoreBoard.getHeight(), height);

        GameInfoComponent infoComponent = new GameInfoComponent();
        infoComponent.setLocation(0, height);
        height += infoComponent.getHeight();
        width = Math.max(width, infoComponent.getWidth());

        GameController gameController;
        gameController = new GameController(boardComponent, board, scoreBoard, infoComponent, data);

        GameFrame gameFrame = new GameFrame();

        width += 50;
        height += 40;

        FrameBackButton gameBack = new FrameBackButton(this, gameFrame);
        gameBack.setSize(80, 20);
        gameBack.setLocation(0, 0);

        JButton save = new JButton("Save");
        save.setSize(80, 20);
        save.setLocation(80, 0);
        save.addActionListener(e -> gameController.saveGame());

        JButton reset = new JButton("Reset");
        reset.setSize(80, 20);
        reset.setLocation(160, 0);
        reset.addActionListener(e -> gameController.resetGame());

        MusicPlayer BGM = new MusicPlayer(GameUtil.getRoot() + "view\\music\\BGM.mp3");
        BGM.setLoop(true);
        JToggleButton music = new JToggleButton("Music");
        music.setSize(80, 20);
        music.setLocation(240, 0);
        music.addActionListener(e -> {
            if (music.isSelected()) {
                BGM.play();
            } else {
                BGM.stop();
            }
        });
        gameBack.addStop(BGM);

        JButton undo = new JButton("Undo");
        undo.setSize(80, 20);
        undo.setLocation(480, 0);
        undo.addActionListener(e -> gameController.undo());

        JToggleButton cheat = new JToggleButton("Cheat");
        cheat.setSize(80, 20);
        cheat.setLocation(400, 0);
        cheat.addActionListener(e -> {

        });

        BGComponent bg = new BGComponent();
        gameFrame.addBG(bg);
        JButton texture = new JButton();
        if (GameUtil.getTexture() == 0) texture.setText("STD");
        if (GameUtil.getTexture() == 1) texture.setText("MC");
        texture.setSize(80, 20);
        texture.setLocation(320, 0);
        texture.addActionListener(e -> {
            GameUtil.changeTexture();
            bg.repaint();
            if (GameUtil.getTexture() == 0) texture.setText("STD");
            if (GameUtil.getTexture() == 1) texture.setText("MC");
            gameController.renewTexture();
        });

        cheat.addActionListener(e -> {
            if(cheat.isSelected()){
                gameController.enableCheatMode();
            }else{
                gameController.disableCheatMode();
            }
        });

        gameFrame.setSize(width, height);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.add(boardComponent);
        gameFrame.add(scoreBoard);
        gameFrame.add(infoComponent);
        gameFrame.add(gameBack);
        gameFrame.add(save);
        gameFrame.add(reset);
        gameFrame.add(music);
        gameFrame.add(texture);
        gameFrame.add(undo);
        gameFrame.add(cheat);
        gameFrame.setVisible(true);
        setVisible(false);
    }

    private void initDifButton(JComponent component) {
        int y = 40;
        for (Difficulty dif : Difficulty.values()) {
            JButton button = new JButton(dif.toString());
            button.setSize(80, 20);
            button.setLocation(60, y);
            button.addActionListener(e -> initGame(dif));
            y += 60;
            component.add(button);
        }
    }
}