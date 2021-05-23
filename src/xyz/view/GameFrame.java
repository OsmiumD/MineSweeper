package xyz.view;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    JLayeredPane layer;

    public GameFrame() {
        setTitle("Mine clearance");
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        layer = new JLayeredPane();
        layer.setSize(2000,2000);
        layer.setLayout(null);
        super.add(layer);
    }

    public Component addBG(Component comp) {
        layer.add(comp, JLayeredPane.DEFAULT_LAYER);
        return comp;
    }

    @Override
    public Component add(Component comp) {
        layer.add(comp, JLayeredPane.MODAL_LAYER);
        return comp;
    }
}
