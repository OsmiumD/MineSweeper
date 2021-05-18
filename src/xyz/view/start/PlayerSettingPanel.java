package xyz.view.start;

import javax.swing.*;

public class PlayerSettingPanel extends JPanel {
    JComboBox<Integer> playerCount;
    JComboBox<Integer> stepCount;
    PlayerSettingPanel(){
        setSize(200,400);
        setLocation(200,0);

        JComboBox<Integer> playerCount = new JComboBox<>();
        for (int i = 2; i <= 4; i++) {
            playerCount.addItem(i);
        }

    }
}
