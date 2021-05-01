package xyz.view;

import java.awt.*;

public class ItemUtil {
    private final static Image mask;

    private static final Image opened;

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mask = toolkit.getImage("src/xyz/view/pic/maskgrid.png");
        opened = toolkit.getImage("src/xyz/view/pic/empty.jpg");
    }

    public static Image genItem (int i) {
        if(i==0)return opened;
        if(i==10) return mask;
        return mask;
        // TODO: This is just a sample. You should implement the method here to provide the visible component according to the argument i

    }
}
