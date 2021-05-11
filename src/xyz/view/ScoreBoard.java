package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class ScoreBoard extends JComponent {
    private final int[][] scoreBoard;
    private static Font font;

    static {
        try {
            font = Font.createFont( Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/FrozenNeutra.otf") );
        } catch(Exception e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.PLAIN, 30);
    }

    public ScoreBoard () {
        setSize(400, 360);
        setLayout(null);
        setBackground(Color.BLACK);
        this.setFont(font);
        scoreBoard = new int[2][2];
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("Player1-score:", 40, 80);
        g.drawString("Player2-score:", 40, 160);
        g.drawString("Player1-lose:", 40, 240);
        g.drawString("Player2-lose:", 40, 320);

        g.drawString("" + scoreBoard[0][0], 300, 80);
        g.drawString("" + scoreBoard[0][1], 300, 160);
        g.drawString("" + scoreBoard[1][0], 300, 240);
        g.drawString("" + scoreBoard[1][1], 300, 320);
    }

    public void goal (int player) {
        scoreBoard[0][player] ++;
    }

    public void lose (int player) {
        scoreBoard[1][player] ++;
    }
}
