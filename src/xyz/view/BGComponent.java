package xyz.view;

import xyz.GameUtil;

import javax.swing.*;
import java.awt.*;


public class BGComponent extends JComponent {


    public BGComponent() {
        setSize(2048, 2048);
        setLocation(0, 0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        painting(g);
    }

    private void painting(Graphics g) {
        final int GIRD_SIZE = 64;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                g.drawImage(GameUtil.getBg(), GIRD_SIZE * i, GIRD_SIZE * j, GIRD_SIZE, GIRD_SIZE, this);
            }
        }
    }
}
