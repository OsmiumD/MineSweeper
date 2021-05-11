package xyz.view;

import java.awt.*;

public class ItemUtil {
    private static final Image mask;

    private static final Image empty;

    //private static final Image flag;

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mask = toolkit.getImage("src/xyz/view/pic/maskgrid.png");
        empty = toolkit.getImage("src/xyz/view/pic/empty.jpg");
        //flag = toolkit.getImage("");
    }

    public static Image genItem (int i) {
        if(i == 0) return empty;
        if(i == 10) return mask;
        return mask;
        // TODO: This is just a sample. You should implement the method here to provide the visible component according to the argument i

    }
}
