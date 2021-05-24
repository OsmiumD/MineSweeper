package xyz.model;

public class MachinePlayer extends Player{
    private int clickType;
    private BoardLocation clickLocation;
    public MachinePlayer(byte id) {
        super(id);
    }

    public MachinePlayer(byte id, int scoreCnt, int turnoverCnt) {
        super(id, scoreCnt, turnoverCnt);
    }

    public void move(Board model) {
        //if (model.isAllGridClicked()) return;
        clickType = (Math.random() > 0.5) ? 1 : 3;
        int randomRow,randomCol;
        while (true) {
            randomRow = (int) (Math.random() * model.getRow());
            randomCol = (int) (Math.random() * model.getColumn());
            clickLocation = new BoardLocation(randomRow, randomCol);
            if (model.isValidClick(clickLocation, clickType)) {
                break;
            }
        }
    }

    public BoardLocation getClickLocation() {
        return clickLocation;
    }

    public int getClickType() {
        return clickType;
    }
}
