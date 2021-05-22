package xyz.view;

import xyz.GameUtil;

import javax.swing.*;
import java.awt.*;

public class GameInfoComponent extends JComponent {
    static Font font;
    private byte player, step, time;
    private int remainMine;

    static {
        font = GameUtil.getFont();
        font = font.deriveFont(Font.PLAIN, 20);
    }

    public GameInfoComponent() {
        setSize(550, 80);
        setLayout(null);
    }

    public void setPlayer(byte player) {
        this.player = player;
    }

    public void setStep(byte step) {
        this.step = step;
    }

    public void setTime(byte time) {
        this.time = time;
    }

    public void setRemainMine(int remainMine) {
        this.remainMine = remainMine;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        painting(g);
    }

    private void painting(Graphics g) {
        g.setFont(font);
        g.drawString("CurrentPlayer:", 0, 20);
        g.drawString("" + (player + 1), 140, 20);

        g.drawString("RemainingSteps:", 170, 20);
        g.drawString("" + step, 310, 20);

        g.drawString("TimeRemaining:", 350, 20);
        g.drawString(String.format("%02d", time), 490, 20);

        g.drawString("RemainingMine:",170,60);
        g.drawString(""+remainMine,300,60);

        g.drawLine(0, 0, 100, 0);
        g.drawLine(0,80,100,80);
    }
}
