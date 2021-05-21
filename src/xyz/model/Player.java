package xyz.model;

public class Player implements Comparable<Player>{
    private final int id;
    private int scoreCnt;//得分（标记错误）与失分（左键触雷）
    private int turnoverCnt;//失误（左键触雷，标记错误）

    public Player(int id) {
        this.id = id;
        scoreCnt = 0;
        turnoverCnt = 0;
    }

    public Player(int id, int scoreCnt, int turnoverCnt) {
        this.id = id;
        this.scoreCnt = scoreCnt;
        this.turnoverCnt = scoreCnt;
    }

    @Override
    public int compareTo(Player that) {
        if (this.scoreCnt > that.getScoreCnt()) return 1;
        if (this.scoreCnt < that.getScoreCnt()) return -1;
        if (this.turnoverCnt < that.getTurnoverCnt()) return 1;
        if (this.turnoverCnt > that.getTurnoverCnt()) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return String.format("Player_%d_[%d][%d]", id, scoreCnt, turnoverCnt);
    }

    public void scores() {
        scoreCnt++;
        System.out.printf("\n%s scored 1 point! Congrats!", toString());
    }

    public void loses() {
       scoreCnt--;
        System.out.printf("\n%s lost 1 point!", toString());
        turnovers();
    }
    public void turnovers() {
        turnoverCnt++;
        System.out.printf("\n%s made a turnover!", toString());
    }

    public int getId() {
        return id;
    }

    public int getScoreCnt() {
        return scoreCnt;
    }

    public int getTurnoverCnt() {
        return turnoverCnt;
    }


}
