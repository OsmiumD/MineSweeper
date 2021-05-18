package xyz.view.start;


public enum Difficulty {
    easy(9,9,10),
    normal(16,16,40),
    hard(30,16,99);

    private int row, col, mine;
    private Difficulty(int row, int col, int mine){
        this.row=row;
        this.col=col;
        this.mine=mine;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getMine() {
        return mine;
    }
}
