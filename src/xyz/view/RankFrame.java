package xyz.view;


import xyz.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.SortedSet;

public class RankFrame extends JFrame {

    public RankFrame(Player[] players) {
        setTitle("Rank");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        RankComponent rankComponent = new RankComponent(players);
        add(rankComponent);
    }
}
