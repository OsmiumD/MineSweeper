package xyz.view;

import xyz.model.Player;

import xyz.GameUtil;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class ScoreBoard extends JComponent {
    private final int[][] scoreBoard;//[0:得分; 1:失分][players]
    private static final Font font= GameUtil.getFont().deriveFont(Font.PLAIN, 20);
    private final byte playerCount;

    public ScoreBoard(byte playerCount) {
        setSize(220, 40 * 2 * playerCount + 40);
        setLayout(null);
        this.setFont(font);
        this.playerCount = playerCount;
        scoreBoard = new int[2][playerCount];
    }

    @Override
    public void paintComponent(Graphics g) {
        //窗口内容
        super.paintComponent(g);
        int y = 40;
        for (int i = 0; i < playerCount; i++) {
            g.drawString("Player" + (i + 1) + "-score:", 40, y);
            g.drawString("" + scoreBoard[0][i], 180, y);
            //g.drawString("" + players[i].getScoreCnt(), 180, y);
            y += 40;
        }
        y += 20;
        for (int i = 0; i < playerCount; i++) {
            g.drawString("Player" + (i + 1) + "-turnover:", 40, y);
            g.drawString("" + scoreBoard[1][i], 200, y);
            //g.drawString("" + players[i].getTurnoverCnt(), 200, y);
            y += 40;
        }
    }

    public void update(Player[] players){
        for (int i = 0; i < playerCount; i++) {
            scoreBoard[0][i]=players[i].getScoreCnt();
            scoreBoard[1][i]=players[i].getTurnoverCnt();
        }
    }
}
