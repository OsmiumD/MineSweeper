package xyz.view.start;

import xyz.GameUtil;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AvatarSelectFrame extends JFrame {
    ActionListener listener;

    AvatarSelectFrame() {
        setSize(160, 435);
        setLayout(null);
        setLocationRelativeTo(null);

        for (int i = 0; i < 4; i++) {
            Icon icon = new ImageIcon(GameUtil.getAvatar(i));
            JButton avatar = new JButton("" + i);
            avatar.setSize(190, 100);
            avatar.setLocation(0,  100 * i);
            avatar.setIcon(AvatarPanel.scaleIcon(icon, 200D/icon.getIconWidth()));
            add(avatar);

            avatar.addActionListener(e -> {
                listener.actionPerformed(e);
                this.dispose();
            });
        }
    }

    public void addActionListener(ActionListener listener) {
        this.listener = listener;
    }
}
