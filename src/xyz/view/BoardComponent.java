package xyz.view;

import xyz.listener.GameListener;
import xyz.model.BoardLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.util.*;
import java.util.List;

public class BoardComponent extends JComponent {
    private final List<GameListener> listenerList = new ArrayList<>();
    private final SquareComponent[][] gridComponents;//每个格子是一个实例
    private final int row;
    private final int col;
    private final int gridSize;

    public BoardComponent(int row, int col, int rowLength, int colLength) {
        enableEvents(MouseEvent.MOUSE_EVENT_MASK);//可以监听鼠标事件
        setLayout(null);
        setSize(rowLength, colLength);
        this.row = row;
        this.col = col;
        this.gridSize = colLength / col;
        gridComponents = new SquareComponent[row][col];
        initialGridComponent();
    }

    // 通过SquareComponent.location对方块的位置信息读取、更改
    private void initialGridComponent() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                gridComponents[i][j] = new SquareComponent(gridSize, i, j);
                gridComponents[i][j].setLocation(i * gridSize, j * gridSize);
                add(gridComponents[i][j]);
            }
        }
    }

    public SquareComponent getGridAt(BoardLocation location) {
        return gridComponents[location.getRow()][location.getColumn()];
    }

    public void setItemAt(BoardLocation location, int num) {
        getGridAt(location).setItem(num);
    }

    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

    public void unregisterListener(GameListener listener) {
        listenerList.remove(listener);
    }

    public void registerGridListener(GameListener listener) {
        for (SquareComponent[] grids: gridComponents) {
            for (SquareComponent grid: grids) {
                grid.registerListener(listener);
            }
        }
    }

    public void unregisterGridListener(GameListener listener) {
        for (SquareComponent[] grids: gridComponents) {
            for (SquareComponent grid: grids) {
                grid.unregisterListener(listener);
            }
        }
    }
}
