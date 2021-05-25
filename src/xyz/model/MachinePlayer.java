package xyz.model;

import xyz.listener.GameListener;
import xyz.view.BoardComponent;

import java.util.Random;

public class MachinePlayer extends Player {
    private int clickType;
    private BoardLocation clickLocation;
    private final int SLEEP_TIME = 500;

    public MachinePlayer(byte id) {
        super(id);

    }

    GameListener listener;

    public synchronized void move(Board model, BoardComponent view) {
        //if (model.isAllGridClicked()) return;
        clickType = (Math.random() > 0.5) ? 1 : 3;
        int randomRow, randomCol;
        do {
            randomRow = (int) (Math.random() * model.getRow());
            randomCol = (int) (Math.random() * model.getColumn());
            clickLocation = new BoardLocation(randomRow, randomCol);
        } while (!model.isValidClick(clickLocation, clickType));
        new Thread(() -> {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listener.mouseEnter(clickLocation, view.getGridAt(clickLocation));
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (clickType == 1) {
                listener.onPlayerLeftClick(clickLocation, view.getGridAt(clickLocation));
            }
            if (clickType == 3) {
                listener.onPlayerRightClick(clickLocation, view.getGridAt(clickLocation));
            }
            listener.mouseExit(clickLocation, view.getGridAt(clickLocation));
        }).start();
    }

    public BoardLocation getClickLocation() {
        return clickLocation;
    }

    public int getClickType() {
        return clickType;
    }

    public void addListener(GameListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }
}
