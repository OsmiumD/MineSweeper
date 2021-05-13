package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class ScoreBoard extends JComponent {
    private final int[][] scoreBoard;//[0:得分; 1:失分][players]
    private static Font font;

    static {
        try {
            font = Font.createFont( Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/FrozenNeutra.otf") );
        } catch(Exception e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.PLAIN, 20);
    }

    public ScoreBoard () {
        setSize(200, 200);
        setLayout(null);
        setBackground(Color.BLACK);
        this.setFont(font);
        scoreBoard = new int[2][2];
    }

    @Override
    public void paint(Graphics g) {
        //窗口内容
        super.paint(g);
        g.drawString("Player1-score:", 40, 40);
        g.drawString("Player2-score:", 40, 80);
        g.drawString("Player1-lose:", 40, 120);
        g.drawString("Player2-lose:", 40, 160);

        g.drawString("" + scoreBoard[0][0], 180, 40);
        g.drawString("" + scoreBoard[0][1], 180, 80);
        g.drawString("" + scoreBoard[1][0], 180, 120);
        g.drawString("" + scoreBoard[1][1], 180, 160);
    }

    public void goal (int player) {
        scoreBoard[0][player] ++;
    }

    public void lose (int player) {
        scoreBoard[1][player] ++;
    }

    public int[][] getScoreBoard() {
        return scoreBoard;
    }
}
