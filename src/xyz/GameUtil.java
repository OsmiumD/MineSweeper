package xyz;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class GameUtil {
    private static Image mask;
    private static Image empty;
    private static Image mine;
    private static Image flag;
    private static Image bg;
    private static Image selected;
    private static Font font;
    private static ArrayList<Image> boom;
    private static final ArrayList<Image> avatar = new ArrayList<>();
    private static final String root = "xyz/";
    private static final String picRoot = root + "view/pic/";
    private static final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private static final ClassLoader cl = Thread.currentThread().getContextClassLoader();

    private static int texture;

    static {

        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    getResource(root + "view/Font/FrozenNeutra.otf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        texture = 1;
        changeTexture();
        for (int i = 0; i < 5; i++) {
            avatar.add(getImage(picRoot + "avatar/" + i + ".png"));
        }
    }

    public static void changeTexture() {
        boom = new ArrayList<>();
        String dict = "standard/";
        int animationSize = 13;
        texture = texture == 0 ? 1 : 0;
        if (texture == 0) {
            dict = "standard/";
        }
        if (texture == 1) {
            dict = "mc/";
            animationSize = 0;
        }
        mask = getImage(picRoot + dict + "mask.png");
        empty = getImage(picRoot + dict + "empty.png");
        mine = getImage(picRoot + dict + "mine.png");
        flag = getImage(picRoot + dict + "flag.png");
        selected = getImage(picRoot + dict + "selected.png");
        bg = getImage(picRoot + dict + "bg.png");

        for (int i = 1; i <= animationSize; i++) {
            boom.add(getImage(picRoot + dict + "boom/" + i + ".png"));
        }
    }

    public static Image genItem(int i) {
        if (i >= 100) {
            return boom.get(i - 100);
        }
        switch (i) {
            case 11:
                return flag;
            case 12:
                return selected;
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
        label.setSize(200,20);
        label.setLocation(80,20);
        frame.setSize(100, 80);
        frame.setTitle(title);
        frame.setLayout(null);
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

    public static String getRoot() {
        return root;
    }

    public static InputStream getResource(String path) {
        return Objects.requireNonNull(cl.getResourceAsStream(path));
    }

    public static Image getImage(String path) {
        URL url = cl.getResource(path);
        if(url==null)return null;
        return toolkit.getImage(url);
    }
}

