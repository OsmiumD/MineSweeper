package xyz.view;

import xyz.GameUtil;
import xyz.listener.GameListener;
import xyz.model.BoardLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SquareComponent extends JPanel {
    //一个方块
    private final int size;
    private static Font font;
    private int num;
    private final BoardLocation location;
    private final List<GameListener> listenerList = new ArrayList<>();


    static {
        font = GameUtil.getFont();
    }

    public SquareComponent(int size, int row, int col) {
        enableEvents(MouseEvent.MOUSE_EVENT_MASK);//可以监听鼠标事件
        setLayout(new GridLayout(1, 1));
        setSize(size, size);
        font = font.deriveFont(Font.BOLD, (float) (size / 1.4));
        this.size = size;
        location = new BoardLocation(row, col);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_ENTERED) {
            for (GameListener listener : listenerList) {
                listener.mouseEnter(location, this);
            }
        }
        if (e.getID() == MouseEvent.MOUSE_EXITED) {
            for (GameListener listener : listenerList) {
                listener.mouseExit(location, this);
            }
        }
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            switch (e.getButton()) {
                case MouseEvent.BUTTON1: {
                    //get被点击的component
                    for (GameListener listener : listenerList) {
                        listener.onPlayerLeftClick(location, this);
                        //告诉GameController各个各个各个Listener：被点击的位置、component
                    }
                    break;
                }
                case MouseEvent.BUTTON2: {
                    for (GameListener listener : listenerList) {
                        listener.onPlayerMidClick(location, this);
                    }
                    break;
                }
                case MouseEvent.BUTTON3: {
                    JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
                    for (GameListener listener : listenerList) {
                        listener.onPlayerRightClick(location, this);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        painting(g);
    }

    private void painting(Graphics g) {
        int spacing = (int) (getWidth() * 0.05);
        Image image = GameUtil.genItem(num);
        if (image != null) {
            g.drawImage(image, spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing, this);
        }
        if (num > 0 && num < 9) {
            g.setFont(font);
            g.setColor(Colors.NUMBERS.getColors()[num - 1]);
            g.drawString("" + num, (int) (size / 2.9), (int) (size / 1.5));
        }
    }

    public void setItem(int num) {
        this.num = num;
    }

    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

    public void unregisterListener(GameListener listener) {
        listenerList.remove(listener);
    }

}
