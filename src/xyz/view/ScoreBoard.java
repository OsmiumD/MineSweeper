package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class ScoreBoard extends JComponent {
    private final int[][] scoreBoard;//[0:得分; 1:失分][players]
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
        scoreBoard = new int[2][playerCount];
    }

    public ScoreBoard(byte playerCount, int[][] scoreBoard) {
        this(playerCount);
        for (int i = 0; i < 2; i++) {
            System.arraycopy(scoreBoard[i], 0, this.scoreBoard[i], 0, playerCount);
        }
    }

    @Override
    public void paint(Graphics g) {
        //窗口内容
        super.paint(g);
        int y = 40;
        for (int i = 0; i < playerCount; i++) {
            g.drawString("Player" + (i + 1) + "-score:", 40, y);
            g.drawString("" + scoreBoard[0][i], 180, y);
            y += 40;
        }
        y += 20;
        for (int i = 0; i < playerCount; i++) {
            g.drawString("Player" + (i + 1) + "-lose:", 40, y);
            g.drawString("" + scoreBoard[1][i], 180, y);
            y += 40;
        }
    }

    public void goal(int player) {
        scoreBoard[0][player]++;
    }

    public void lose(int player) {
        scoreBoard[1][player]++;
    }

    public int[][] getScoreBoard() {
        return scoreBoard;
    }
}
