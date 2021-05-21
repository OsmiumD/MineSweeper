package xyz.view;

import xyz.model.Player;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class ScoreBoard extends JComponent {
    //private final int[][] scoreBoard;//[0:得分; 1:失分][players]
    private final Player[] players;
    private static Font font;
    private final byte playerCount;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/FrozenNeutra.otf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.PLAIN, 20);
    }

    public ScoreBoard(byte playerCount) {
        setSize(200, 40 * 2 * playerCount + 40);
        setLayout(null);
        this.setFont(font);
        this.playerCount = playerCount;
        //scoreBoard = new int[2][playerCount];
        players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player((byte) i);
        }
    }


    public ScoreBoard(byte playerCount, Player[] players) {
        this(playerCount);
        for (int i = 0; i < 2; i++) {
            System.arraycopy(players, 0, this.players, 0, playerCount);
        }
    }
/*
    public ScoreBoard(byte playerCount, int[][] scoreBoard) {
        this(playerCount);
        for (int i = 0; i < 2; i++) {
            System.arraycopy(scoreBoard[i], 0, this.scoreBoard[i], 0, playerCount);
        }
    }

 */

    @Override
    public void paint(Graphics g) {
        //窗口内容
        super.paint(g);
        int y = 40;
        for (int i = 0; i < playerCount; i++) {
            g.drawString("Player" + (i + 1) + "-score:", 40, y);
            //g.drawString("" + scoreBoard[0][i], 180, y);
            g.drawString("" + players[i].getScoreCnt(), 180, y);
            y += 40;
        }
        y += 20;
        for (int i = 0; i < playerCount; i++) {
            g.drawString("Player" + (i + 1) + "-turnover:", 40, y);
            //g.drawString("" + scoreBoard[1][i], 180, y);
            g.drawString("" + players[i].getTurnoverCnt(), 180, y);
            y += 40;
        }
    }

    public void goal(byte playerId) {
        //scoreBoard[0][playerId]++;
        players[playerId].goal();
    }

    public void lose(byte playerId) {
        //scoreBoard[1][playerId]++;
        players[playerId].lose();
    }
    public void turnover(byte playerId) {
        players[playerId].turnover();
    }
/*
    public int[][] getScoreBoard() {
        return scoreBoard;
    }

 */

    public Player[] getPlayers() {
        return players;
    }
}
