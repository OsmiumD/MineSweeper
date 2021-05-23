package xyz.view;

import xyz.GameUtil;

import javax.swing.*;
import java.awt.*;


public class BGComponent extends JComponent {


    public BGComponent() {
        setSize(1024,1024);
        setLocation(0,0);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        painting(g);
    }

    private void painting(Graphics g) {
        final int GIRD_SIZE = 64;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                g.drawImage(GameUtil.getBg(), GIRD_SIZE * i, GIRD_SIZE * j, GIRD_SIZE, GIRD_SIZE, this);
            }
        }
    }
}
