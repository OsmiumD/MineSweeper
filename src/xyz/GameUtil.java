package xyz;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameUtil {
    private static Image mask;
    private static Image empty;
    private static Image mine;
    private static Image flag;
    private static Image bg;
    private static Font font;

    private static int texture;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/FrozenNeutra.otf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        texture = 1;
        changeTexture();
    }

    public static void changeTexture() {
        String dict = "standard";
        texture = texture == 0 ? 1 : 0;
        if (texture == 0) {
            dict = "standard";
        }
        if (texture == 1) {
            dict = "mc";
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mask = toolkit.getImage("src/xyz/view/pic/" + dict + "/mask.png");
        empty = toolkit.getImage("src/xyz/view/pic/" + dict + "/empty.png");
        mine = toolkit.getImage("src/xyz/view/pic/" + dict + "/mine.png");
        flag = toolkit.getImage("src/xyz/view/pic/" + dict + "/flag.png");
        bg = toolkit.getImage("src/xyz/view/pic/" + dict + "/bg.png");
    }

    public static Image genItem(int i) {
        switch (i) {
            case 11:
                return flag;
            case 10:
                return mask;
            case 9:
                return mine;
            default:
                return empty;
        }
    }

    public static String currentTime() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return time.format(fmt);
    }

    public static void showMessage(String title, String content) {
        JFrame frame = new JFrame();
        JLabel label = new JLabel(content);
        frame.setSize(100, 40);
        frame.setTitle(title);
        frame.setLayout(new FlowLayout());
        frame.add(label);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static Font getFont() {
        return font;
    }

    public static int getTexture() {
        return texture;
    }

    public static Image getBg() {
        return bg;
    }
}

