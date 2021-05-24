package xyz.model;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
    protected final byte id;
    protected int scoreCnt;//得分（标记正确）与失分（左键触雷）
    protected int turnoverCnt;//失误（左键触雷，标记错误）
    private int avatar;

    public Player(byte id) {
        this.id = id;
        scoreCnt = 0;
        turnoverCnt = 0;
    }

    public Player(byte id, int scoreCnt, int turnoverCnt) {
        this.id = id;
        this.scoreCnt = scoreCnt;
        this.turnoverCnt = scoreCnt;
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

    public void goal() {
        scoreCnt++;
        System.out.printf("%s scored 1 point! Congrats!\n", toString());
    }

    public void lose() {
        scoreCnt--;
        turnover();
        System.out.printf("%s lost 1 point!\n", toString());
    }

    public void turnover() {
        turnoverCnt++;
        System.out.printf("%s made a turnover!\n", toString());
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

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
