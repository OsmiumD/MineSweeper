package xyz.model;

import java.io.Serializable;

public class Board implements Serializable {
    private Square[][] grid;
    private int row;
    private int column;
    private int mineNum;
    private int remainderMineNum;//没翻开(且没flag)的雷

    public Board(int row, int col, int mineNum) {
        grid = new Square[row][col];
        this.column = col;
        this.row = row;
        this.mineNum = mineNum;
        remainderMineNum = mineNum;

        iniGrid();
        //iniItem();
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
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid[i][j].setNumberOfLandMine(calculateNum(i,j));
                // 初始化参数，所以在Square的Constructor中不用写初始化
            }
        }
    }

    /**
     * 计算grid[i][j]附近雷的数量，算上自己
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
        if (getGridAt(location).hasLandMine()) {
            remainderMineNum--;
        }
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
            case 3:
                //if (!getGridAt(location).isOpened() && !getGridAt(location).isFlag()) return true;
                if (!getGridAt(location).isOpened()) return true;
                else return false;
            default:
                return true;
        }
    }

    /**
     * 随机生成雷
     * 已完成：3.1 避免过度密集
     * 已完成：3.2 避免首发碰雷（在第一次点击后调用此方法）
     * 已完成：3.3 透视雷的位置
     */
    public void randomLandMine(BoardLocation location) {
        //mineNum = 0;
        for (int i = 0; i < mineNum; i++) {
            //i仅起到计数的作用，即保证生成mineNum个雷
            int randomRow = (int) (Math.random() * row);
            int randomCol = (int) (Math.random() * column);
            /* 第二个判断条件：第一次点击不爆雷
               应在第一次点击后调用此方法
             */
            if (grid[randomRow][randomCol].hasLandMine() ||
                    grid[randomRow][randomCol].isOpened() ||
                    grid[randomRow][randomCol].isFlag() ||
                    (location.getRow() == randomRow && location.getColumn() == randomCol)) {
                i--;
            }else {
                grid[randomRow][randomCol].setHasLandMine(true);
                if (!isValidLandMine(randomRow, randomCol)) {
                    grid[randomRow][randomCol].setHasLandMine(false);
                    i--;
                }
            }
        }
    }

    private boolean isValidLandMine(int i, int j) {
        for (int m = i-1; m <= i+1 && 0 <= m && m < row; m++) {
            for (int n = j-1; n <= j+1 && 0 <= n && n <= column; n++) {
                if ((m == 0 || m == row-1) && (n == 0 || n == column-1)) {
                    if (calculateNum(m, n) == 4) return false;//角上
                }else if (m == 0 || m == row-1 || n == 0 || n == column-1) {
                    if (calculateNum(m, n) == 6) return false;//边上
                }else {
                    if (calculateNum(m, n) == 9) return false;//中间
                }
            }
        }
        return true;
    }

    public boolean isAllGridClicked() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (!grid[i][j].isOpened() && !grid[i][j].isFlag()) return false;
            }
        }
        return true;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getRemainderMineNum() {
        return remainderMineNum;
    }

    public Square[][] getGrid() {
        return grid;
    }
}
