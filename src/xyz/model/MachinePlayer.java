package xyz.model;

import xyz.listener.GameListener;
import xyz.view.BoardComponent;

import java.util.Random;

public class MachinePlayer extends Player {
    private int clickType;
    private BoardLocation clickLocation;

    public MachinePlayer(byte id) {
        super(id);
    }

    GameListener listener;

    public MachinePlayer(byte id, int scoreCnt, int turnoverCnt) {
        super(id, scoreCnt, turnoverCnt);
    }

    public void move(Board model, BoardComponent view) {
        //if (model.isAllGridClicked()) return;
        clickType = (Math.random() > 0.5) ? 1 : 3;
        int randomRow, randomCol;
        do {
            randomRow = (int) (Math.random() * model.getRow());
            randomCol = (int) (Math.random() * model.getColumn());
            clickLocation = new BoardLocation(randomRow, randomCol);
        } while (!model.isValidClick(clickLocation, clickType));
        if (clickType == 1) {
            listener.onPlayerLeftClick(clickLocation, view.getGridAt(clickLocation));
        }
        if (clickType == 3) {
            listener.onPlayerRightClick(clickLocation, view.getGridAt(clickLocation));
        }
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
