package xyz.view;

import xyz.GameUtil;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ItemComponent extends JComponent {

    private static Font font;
    private int num;

    static {
        font = GameUtil.getFont();
        font = font.deriveFont(Font.PLAIN, 80);
    }

    public ItemComponent(int num) {
        this.num = num;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        painting(g);
    }

    private void painting(Graphics g) {

    }
}
