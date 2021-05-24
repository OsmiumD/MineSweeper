package xyz.view;

import java.awt.*;
import java.util.ArrayList;

public class AnimationRunnable implements Runnable {

    int animation;
    SquareComponent grid;

    public AnimationRunnable(int animation, SquareComponent grid) {
        this.animation = animation;
        this.grid = grid;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < animation; i++) {
                grid.setItem(i + 100);
                grid.repaint();
                Thread.sleep(60);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        grid.setItem(9);
        grid.repaint();
    }
}
