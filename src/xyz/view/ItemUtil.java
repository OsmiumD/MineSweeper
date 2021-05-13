package xyz.view;

import java.awt.*;

public class ItemUtil {
    private static final Image mask;
    private static final Image empty;

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mask = toolkit.getImage("src/xyz/view/pic/maskgrid.png");
        empty = toolkit.getImage("src/xyz/view/pic/empty.jpg");
    }

    public static Image genItem (int i) {
        switch (i) {
            case 10: return mask;
            default: return empty;
        }
    }
}
