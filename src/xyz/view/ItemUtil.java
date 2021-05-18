package xyz.view;

import java.awt.*;

public class ItemUtil {
    private static final Image mask;
    private static final Image empty;
    private static final Image mine;
    private static final Image flag;

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mask = toolkit.getImage("src/xyz/view/pic/maskgrid.png");
        empty = toolkit.getImage("src/xyz/view/pic/empty.jpg");
        mine = toolkit.getImage("src/xyz/view/pic/mine.png");
        flag = toolkit.getImage("src/xyz/view/pic/flag.png");
    }

    public static Image genItem(int i) {
        switch (i) {
            case 10:
                return mask;
            case 9:
                return mine;
            default:
                return empty;
        }
    }
}
