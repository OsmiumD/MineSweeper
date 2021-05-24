package xyz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player implements Comparable<Player>, Serializable {
    protected final byte id;
    protected int scoreCnt;//得分（标记正确）与失分（左键触雷）
    protected int turnoverCnt;//失误（左键触雷，标记错误）
    private byte avatar;
    protected List<BoardLocation> clickedLocations;
    protected HashMap<BoardLocation, Boolean> turnoverOrNot;

    public Player(byte id) {
        this.id = id;
        scoreCnt = 0;
        turnoverCnt = 0;
        clickedLocations = new ArrayList<>();
        turnoverOrNot = new HashMap<>();
    }

    public Player(byte id, int scoreCnt, int turnoverCnt) {
        this.id = id;
        this.scoreCnt = scoreCnt;
        this.turnoverCnt = scoreCnt;
        clickedLocations = new ArrayList<>();
        turnoverOrNot = new HashMap<>();
    }

    @Override
    public int compareTo(Player that) {
        //表现最好的player排最前（-1）
        if (this.scoreCnt > that.getScoreCnt()) return -1;
        if (this.scoreCnt < that.getScoreCnt()) return 1;
        if (this.turnoverCnt < that.getTurnoverCnt()) return -1;
        if (this.turnoverCnt > that.getTurnoverCnt()) return 1;
        return 0;
    }

    @Override
    public String toString() {
        return String.format("Player_%d", (id + 1));
    }

    public void goal(BoardLocation location) {
        scoreCnt++;
        turnoverOrNot.put(location, false);
        clickedLocations.add(location);
        System.out.printf("%s scored 1 point! Congrats!\n", toString());
    }
    public void unGoal() {
        scoreCnt--;
        turnoverOrNot.remove(clickedLocations.get(clickedLocations.size() - 1));
        clickedLocations.remove(clickedLocations.size() - 1);
    }

    public void lose(BoardLocation location) {
        scoreCnt--;
        turnoverCnt++;
        turnoverOrNot.put(location, true);
        clickedLocations.add(location);
        System.out.printf("%s made a turnover!\n", toString());
        System.out.printf("%s lost 1 point!\n", toString());
    }
    public void unLose() {
        scoreCnt++;
        turnoverCnt--;
        turnoverOrNot.remove(clickedLocations.get(clickedLocations.size() - 1));
        clickedLocations.remove(clickedLocations.size() - 1);
    }

    public void turnover(BoardLocation location) {
        turnoverCnt++;
        turnoverOrNot.put(location, true);
        clickedLocations.add(location);
        System.out.printf("%s made a turnover!\n", toString());
    }
    public void unTurnover() {
        turnoverCnt--;
        turnoverOrNot.remove(clickedLocations.get(clickedLocations.size() - 1));
        clickedLocations.remove(clickedLocations.size() - 1);
    }

    public void remake() {
        scoreCnt = 0;
        turnoverCnt = 0;
        clickedLocations.clear();
        turnoverOrNot.clear();
    }

    public byte getId() {
        return id;
    }

    public int getScoreCnt() {
        return scoreCnt;
    }

    public void setScoreCnt(int scoreCnt) {
        this.scoreCnt = scoreCnt;
    }

    public int getTurnoverCnt() {
        return turnoverCnt;
    }

    public void setTurnoverCnt(int turnoverCnt) {
        this.turnoverCnt = turnoverCnt;
    }

    public byte getAvatar() {
        return avatar;
    }

    public void setAvatar(byte avatar) {
        this.avatar = avatar;
    }

    public List<BoardLocation> getClickedLocations() {
        return clickedLocations;
    }

    public HashMap<BoardLocation, Boolean> getTurnoverOrNot() {
        return turnoverOrNot;
    }
}
