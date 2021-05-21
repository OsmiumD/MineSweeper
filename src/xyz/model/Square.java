package xyz.model;

import java.io.Serializable;

public class Square implements Serializable {
    private final BoardLocation location;
    private boolean isOpened;//目前，opened意为“已结算”，无论一个格子是被左击还是右击，都会setOpened(true)
    private boolean isFlag;
    private boolean hasLandMine;
    private int numberOfLandMine;

    public Square(BoardLocation location) {
        this.location = location;
        isOpened = false;
        isFlag = false;
        hasLandMine = false;
        //numberOfLandMine = xxx;
        // 上一行的Initialize在Board.iniItem()中进行
    }

    public BoardLocation getLocation () {
        return location;
    }

    public boolean isOpened () {
        return isOpened;
    }

    public boolean hasLandMine () {
        return hasLandMine;
    }

    public int getNumberOfLandMine () {
        return numberOfLandMine;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public void setHasLandMine (boolean hasLandMine) {
        this.hasLandMine = hasLandMine;
    }

    public void setNumberOfLandMine (byte numberOfLandMine) {
        this.numberOfLandMine = numberOfLandMine;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public int getNum () {
        if (isFlag) return 11;//flag，方便写GUI
        if (!isOpened) return 10;//closed
        if (hasLandMine) return 9;//land mine
        return numberOfLandMine;
        // TODO: You should implement the method to give the number of the item stored in the grid
    }
    /*
    Each grid has five states: the first two include a grid that is not open or is marked;
    The last three are lattice is opened, if there are no mines around, do not show;
    The number of mines, if any;
    If it is a mine, draw a mine
     */
}
