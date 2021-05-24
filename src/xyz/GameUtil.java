package xyz;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GameUtil {
    private static Image mask;
    private static Image empty;
    private static Image mine;
    private static Image flag;
    private static Image bg;
    private static Font font;
    private static ArrayList<Image> boom;
    private static final ArrayList<Image> avatar = new ArrayList<>();
    private static final String root = "src/xyz/view/pic/";
    private static final Toolkit toolkit = Toolkit.getDefaultToolkit();

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
        for (int i = 0; i < 4; i++) {
            avatar.add(toolkit.getImage(root + "/avatar/" + i + ".png"));
        }
    }

    public static void changeTexture() {
        boom = new ArrayList<>();
        String dict = "standard";
        int animationSize = 13;
        texture = texture == 0 ? 1 : 0;
        if (texture == 0) {
            dict = "standard";
        }
        if (texture == 1) {
            dict = "mc";
            animationSize = 0;
        }
        mask = toolkit.getImage(root + dict + "/mask.png");
        empty = toolkit.getImage(root + dict + "/empty.png");
        mine = toolkit.getImage(root + dict + "/mine.png");
        flag = toolkit.getImage(root + dict + "/flag.png");
        bg = toolkit.getImage(root + dict + "/bg.png");
        for (int i = 1; i <= animationSize; i++) {
            boom.add(toolkit.getImage(root + dict + "/boom/" + i + ".png"));
        }
    }

    public static Image genItem(int i) {
        if (i >= 100) {
            return boom.get(i - 100);
        }
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

    public static int getBoom() {
        return boom.size();
    }

    public static Image getAvatar(int i) {
        return avatar.get(i);
    }
}

