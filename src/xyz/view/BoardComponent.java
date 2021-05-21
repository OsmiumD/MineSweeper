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
                gridComponents[i][j] = new SquareComponent(gridSize);
                gridComponents[i][j].setLocation(i * gridSize, j * gridSize);
                add(gridComponents[i][j]);
            }
        }
    }

    public SquareComponent getGridAt(BoardLocation location) {
        return gridComponents[location.getRow()][location.getColumn()];
    }

    private BoardLocation getLocationByPosition(int x, int y) {
        return new BoardLocation(x / gridSize, y / gridSize);
    }

    public void setItemAt(BoardLocation location, int num) {
        getGridAt(location).setItem(num);
    }

    // 通过鼠标事件更改：界面 （其他在GameController中改）
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() != MouseEvent.MOUSE_PRESSED) return;
        switch (e.getButton()) {
            case MouseEvent.BUTTON1: {
                JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
                //get被点击的component
                BoardLocation location = getLocationByPosition(e.getX(), e.getY());
                for (GameListener listener : listenerList) {
                    listener.onPlayerLeftClick(location, (SquareComponent) clickedComponent);
                    //告诉GameController各个各个各个Listener：被点击的位置、component
                }
                break;
            }
            case MouseEvent.BUTTON2: {
                JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
                BoardLocation location = getLocationByPosition(e.getX(), e.getY());
                for (GameListener listener : listenerList) {
                    listener.onPlayerMidClick(location, (SquareComponent) clickedComponent);
                }
                break;
            }
            case MouseEvent.BUTTON3: {
                JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
                BoardLocation location = getLocationByPosition(e.getX(), e.getY());
                for (GameListener listener : listenerList) {
                    listener.onPlayerRightClick(location, (SquareComponent) clickedComponent);
                }
                break;
            }
        }
    }

    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

    public void unregisterListener(GameListener listener) {
        listenerList.remove(listener);
    }

}
