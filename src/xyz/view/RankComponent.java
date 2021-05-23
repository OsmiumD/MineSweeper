package xyz.view;

import xyz.GameUtil;
import xyz.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.SortedSet;

public class RankComponent extends JComponent {
    private Player[] players;
    private static Font font = GameUtil.getFont().deriveFont(Font.PLAIN, 20);

    public RankComponent(Player[] players) {
        this.players = new Player[players.length];
        System.arraycopy(players, 0, this.players, 0, players.length);
        int i = 0;
        for (Player player : players) {
            this.players[i] = player;
            i++;
        }
        this.setSize(300, 600);
    }

    @Override
    public void paintComponent(Graphics g) {
        Arrays.sort(players);
        g.setFont(font.deriveFont(Font.PLAIN, 30));
        if (players[0].compareTo(players[1]) == 0) {
            g.drawString("Tie!NoWinner", 100, 50);
        } else {
            g.drawString(players[0] + "Wins!", 100, 50);
        }
        g.setFont(font.deriveFont(Font.BOLD, 30));
        g.drawString("Ranking-List", 100, 100);
        int y = 150;
        g.setFont(font);
        for (Player player : players) {
            g.drawString("Player:" + player, 100, y);
            y += 25;
            g.drawString("Score:" + player.getScoreCnt(), 80, y);
            g.drawString("Turnover:" + player.getTurnoverCnt(), 170, y);
            y += 40;
        }
    }
}
