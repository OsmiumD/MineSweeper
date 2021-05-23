//用于两个JFrame之间的Back
package xyz.view.start;

import xyz.listener.Stoppable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FrameBackButton extends JButton {
    private final List<Stoppable> stops=new ArrayList<>();

    FrameBackButton(JFrame view1, JFrame view2) {
        setText("Back");
        addActionListener(e -> {
            view1.setVisible(true);
            view2.dispose();
            for (Stoppable stop : stops) {
                stop.stop();
            }
        });
    }

    public void addStop(Stoppable stop) {
        stops.add(stop);
    }

    public void removeStop(Stoppable stop) {
        stops.remove(stop);
    }
}
