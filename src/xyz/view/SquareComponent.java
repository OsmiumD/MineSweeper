package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class SquareComponent extends JPanel {
    //一个方块
    private static Image grid = Toolkit.getDefaultToolkit().getImage("src/xyz/view/pic/emptygrid.png");
    private int size;
    private static Font font;
    private int num;

    static {
        try {
            font = Font.createFont( Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/FrozenNeutra.otf") );
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public SquareComponent (int size) {
        setLayout(new GridLayout(1, 1));
        setSize(size, size);
        font = font.deriveFont(Font.PLAIN, (float) (size/1.4));
        this.size = size;
    }

    public void setItem(int num){
        this.num=num;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        painting(g);
    }

    private void painting (Graphics g) {
        int spacing = (int) (getWidth() * 0.05);
        Image image = ItemUtil.genItem(num);
        if (image != null) {
            g.drawImage(image, spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing, this);
        }
        if(num > 0 && num < 9){
            g.setFont(font);
            g.drawString(""+num, (int)(size/2.9), (int)(size/1.5));
        }
    }
}
