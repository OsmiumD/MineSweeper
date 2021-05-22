package xyz;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameUtil {
    private static final Image mask;
    private static final Image empty;
    private static final Image mine;
    private static final Image flag;
    private static Font font;

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mask = toolkit.getImage("src/xyz/view/pic/maskgrid.png");
        empty = toolkit.getImage("src/xyz/view/pic/empty.jpg");
        mine = toolkit.getImage("src/xyz/view/pic/mine.png");
        flag = toolkit.getImage("src/xyz/view/pic/flag.png");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/FrozenNeutra.otf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        frame.setLocationRelativeTo(null);
        frame.setTitle(title);
        frame.setLayout(new FlowLayout());
        frame.add(label);
        frame.setVisible(true);
    }

    public static Font getFont() {
        return font;
    }
}

