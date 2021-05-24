package xyz.view.start;

import xyz.GameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class AvatarPanel extends JPanel {
    byte playerNum;
    AvatarButton[] buttons;
    JCheckBox[] machines;
    Map<JCheckBox, AvatarButton> map = new HashMap<>();

    AvatarPanel() {
        setLayout(null);
        buttons = new AvatarButton[4];
        machines = new JCheckBox[4];
        for (int i = 0; i < 4; i++) {
            buttons[i] = new AvatarButton();
            buttons[i].setAvatar((byte) i);
            machines[i] = new JCheckBox("Machine");
            machines[i].setFont(GameUtil.getFont().deriveFont(Font.PLAIN, 15));
            machines[i].setSize(100, 20);
            map.put(machines[i], buttons[i]);
            AvatarButton avatar = buttons[i];
            avatar.addActionListener(e -> {
                AvatarSelectFrame avatarSelectFrame = new AvatarSelectFrame();
                avatarSelectFrame.setVisible(true);
                avatarSelectFrame.addActionListener(e1 -> {
                    avatar.setAvatar(Byte.parseByte(((JButton) e1.getSource()).getText()));
                    System.out.println(avatar.getAvatar());
                    avatar.renewIcon();
                });
            });
        }
        setSize(200, 400);
        setLocation(400, 0);
    }

    public void setPlayerNum(byte playerNum) {
        this.playerNum = playerNum;
        setContent();
    }

    public void setContent() {
        removeAll();
        for (int i = 0; i < playerNum; i++) {
            JLabel playerName = new JLabel("Player_" + (i + 1));
            playerName.setFont(GameUtil.getFont().deriveFont(Font.PLAIN, 20));
            playerName.setLocation(10, 80 * i);
            playerName.setSize(100, 20);
            add(playerName);

            JCheckBox machine = machines[i];
            machine.setLocation(90, 80 * i);
            machine.addActionListener(e -> {
                AvatarButton avatarButton = map.get(machine);
                if (machine.isSelected()) {
                    avatarButton.setAvatar((byte) 4);
                } else {
                    avatarButton.setAvatar((byte) 0);
                }
                avatarButton.renewIcon();
            });
            add(machine);

            AvatarButton avatar = buttons[i];
            avatar.setSize(100, 50);
            avatar.renewIcon();
            avatar.setLocation(10, 20 + 80 * i);
            add(avatar);
        }
    }

    public static Icon scaleIcon(Icon icon, double scale) {
        BufferedImage bi = new BufferedImage(
                (int) (scale * icon.getIconWidth()),
                (int) (scale * icon.getIconHeight()),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.scale(scale, scale);
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return new ImageIcon(bi);
    }

    public byte getAvatar(int i) {
        return buttons[i].getAvatar();
    }

    public boolean isMachine(int i) {
        return machines[i].isSelected();
    }
}
