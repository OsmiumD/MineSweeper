package xyz.view.start;

import xyz.GameUtil;

import javax.swing.*;

public class AvatarButton extends JButton {
    private byte avatar=0;

    public byte getAvatar() {
        return avatar;
    }

    public void setAvatar(byte avatar) {
        this.avatar = avatar;
    }

    public void renewIcon(){
        Icon icon = new ImageIcon(GameUtil.getAvatar(avatar));
        setSize(100, 50);
        setIcon(AvatarPanel.scaleIcon(icon, 120D / icon.getIconWidth()));
    }
}
