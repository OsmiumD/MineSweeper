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
            case 0: return empty;
            case 10: return mask;
            default: return mask;
        }
        // TODO: This is just a sample. You should implement the method here to provide the visible component according to the argument i

    }
}
