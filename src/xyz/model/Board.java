package xyz.model;

public class Board {
    private Square[][] grid;
    private int row;
    private int column;
    private int mineNum;
    private int remainderMineNum;//没翻开 且 没flag的雷
    private boolean gameEnded;//在GameController.judgeWinner()中用到

    public Board(int row, int col, int mineNum) {
        grid = new Square[row][col];
        this.column = col;
        this.row = row;
        this.mineNum = mineNum;
        remainderMineNum = mineNum;
        this.gameEnded = false;

        iniGrid();
        iniItem();
    }

    public void iniGrid () {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid[i][j] = new Square(new BoardLocation(i, j));
            }
        }
    }

    public void iniItem () {
        // TODO: This is only a demo implementation.
        grid[0][0].setNumberOfLandMine((byte) 3);
    }

    /**
     * 计算grid[i][j]附近雷的数量
     * @return 雷的数量
     */
    public byte calculateNum (int i, int j) {
        byte cnt = 0;
        for (int m = i-1; m <= i+1; m++) {
            for (int n = j-1; n <= j+1; n++) {
                if (m >= 0 && n >= 0 && m < row && n < column) {
                    if (grid[m][n].hasLandMine()) cnt++;
                }
            }
        }
        // 是否要去掉这一格？
        return cnt;
    }

    public Square getGridAt(BoardLocation location) {
        return grid[location.getRow()][location.getColumn()];
    }

    public int getNumAt(BoardLocation location) {
        return getGridAt(location).getNum();
    }

    public void openGrid(BoardLocation location) {
        getGridAt(location).setOpened(true);
    }

    public void flagGrid (BoardLocation location) {
        getGridAt(location).setFlag(true);
    }

    // click type == 1 means that is left click
    // click type == 2 means that is middle click
    // click type == 3 means that is right click
    public boolean isValidClick (BoardLocation location, int clickType) {
        // TODO: You should implement a method here to check whether it is a valid action
        switch (clickType) {
            case 1:
            case 2:
                if (!getGridAt(location).isOpened() && !getGridAt(location).isFlag()) return true;
                else return false;
            default:
                return true;
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public int getRemainderMineNum() {
        return remainderMineNum;
    }
    /**
     * 随机生成雷
     * 已完成：3.2 避免首发碰雷（应在第一次点击后调用此方法）
     * TODO: 3.1 避免过度密集
     * TODO: 3.3 透视雷的位置
     */
    private void randomLandMine() {
        //mineNum = 0;
        for (int i = 0; i < mineNum; i++) {
            //i仅起到计数的作用，即保证生成mineNum个雷
            int randomRow = (int) (Math.random() * row);
            int randomCol = (int) (Math.random() * column);
            /* 第二个判断条件：第一次点击不爆雷
               应在第一次点击后调用此方法
             */
            if (getGridAt(new BoardLocation(randomRow, randomCol)).hasLandMine() ||
                    getGridAt(new BoardLocation(randomRow, randomCol)).isOpened()) {
                i--;
            }else {
                getGridAt(new BoardLocation(randomRow, randomCol)).setHasLandMine(true);
            }
        }
    }
}
