//用于两个Component之间的back
package xyz.view;

import javax.swing.*;

public class ComponentBackButton extends JButton {
    ComponentBackButton(JComponent component1, JComponent component2) {
        setSize(80,20);
        setLocation(0,0);
        setText("<--Back");
        addActionListener(e -> {
            component1.setVisible(true);
            component2.setVisible(false);
        });
    }
}
