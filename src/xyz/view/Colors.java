package xyz.view;
import java.awt.Color;
public enum Colors {
    NUMBERS(new Color[]{new Color(0,0,255),
            new Color(0,128,0),
            new Color(255,0,0),
            new Color(0,0,128),
            new Color(128,0,0),
            new Color(0,128,128),
            new Color(0,0,0),
            new Color(128,128,128)

    })
    ;

    Color[] colorList;
    private Colors(Color[] color) {
        colorList = color;
    }
    public Color[] getColors() {
        return colorList;
    }
}
