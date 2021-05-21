//用于两个JFrame之间的Back
package xyz.view.start;

import javax.swing.*;
public class FrameBackButton extends JButton{
    FrameBackButton(JFrame view1, JFrame view2){
        setText("Back");
        addActionListener(e -> {
            view1.setVisible(true);
            view2.dispose();
        });
    }
}
