package xyz.model;

import java.io.Serializable;
import java.util.Objects;

public class BoardLocation implements Serializable {
    private int row;
    private int column;

    public BoardLocation (int row, int column) {
        this.column = column;
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardLocation)) return false;
        BoardLocation that = (BoardLocation) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
