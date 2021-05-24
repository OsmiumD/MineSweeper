package xyz.view.start;

import xyz.GameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PlayerSettingPanel extends JPanel {
    JComboBox<Byte> playerCount;
    JComboBox<Byte> stepCount;
    JCheckBox sequenceOpen;
    static Font font;
    ActionListener playerListener;

    static {
        font = GameUtil.getFont();
        font = font.deriveFont(Font.PLAIN, 16);
    }

    PlayerSettingPanel() {
        setSize(200, 400);
        setLocation(200, 0);
        setLayout(null);

        JLabel labelPlayerCount = new JLabel("PlayerCount");
        labelPlayerCount.setSize(100, 20);
        labelPlayerCount.setFont(font);
        labelPlayerCount.setLocation(50, 30);
        add(labelPlayerCount);

        playerCount = new JComboBox<>();
        playerCount.setSize(40, 20);
        playerCount.setLocation(80, 50);
        for (byte i = 2; i <= 4; i++) {
            playerCount.addItem(i);
        }
        add(playerCount);

        JLabel labelStep = new JLabel("Step");
        labelStep.setSize(60, 20);
        labelStep.setFont(font);
        labelStep.setLocation(70, 80);
        add(labelStep);

        stepCount = new JComboBox<>();
        stepCount.setSize(40, 20);
        stepCount.setLocation(80, 100);
        for (byte i = 1; i <= 5; i++) {
            stepCount.addItem(i);
        }
        add(stepCount);

        sequenceOpen = new JCheckBox("SequenceOpen");
        sequenceOpen.setFont(font);
        sequenceOpen.setSize(130, 20);
        sequenceOpen.setLocation(35, 130);
        add(sequenceOpen);

        playerCount.addActionListener(e -> {
            playerListener.actionPerformed(e);
        });
    }

    public byte getPlayerCount() {
        return (Byte) playerCount.getSelectedItem();
    }

    public byte getStep() {
        return (Byte) stepCount.getSelectedItem();
    }

    public boolean isSequenceOpen() {
        return sequenceOpen.isSelected();
    }

    public void addPlayerListener(ActionListener listener) {
        this.playerListener = listener;
    }
}
