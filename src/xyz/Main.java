package xyz;

import xyz.view.music.MusicPlayer;
import xyz.view.start.StartFrame;

import javax.swing.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartFrame startFrame = new StartFrame();
            startFrame.setVisible(true);
        });
    }
}
