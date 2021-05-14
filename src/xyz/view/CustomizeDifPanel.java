package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class CustomizeDifPanel extends JPanel {
    int row, col, mineNum;
    JTextField rowField, colField, mineField;
    static Font font;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/FrozenNeutra.otf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.PLAIN, 18);
    }

    CustomizeDifPanel() {
        setLayout(null);
        setSize(400, 400);

        JLabel rowLabel = new JLabel("Row(9-30):");
        rowLabel.setSize(100, 20);
        rowLabel.setFont(font);
        rowLabel.setLocation(50, 50);
        add(rowLabel);

        JLabel colLabel = new JLabel("Column(9-24):");
        colLabel.setSize(130, 20);
        colLabel.setFont(font);
        colLabel.setLocation(50, 110);
        add(colLabel);

        JLabel mineLabel = new JLabel("MineCount:");
        mineLabel.setSize(130, 20);
        mineLabel.setFont(font);
        mineLabel.setLocation(50, 170);
        add(mineLabel);

        rowField = new JTextField("9", 15);
        rowField.setSize(40, 20);
        rowField.setLocation(180, 50);
        add(rowField);

        colField = new JTextField("9", 15);
        colField.setSize(40, 20);
        colField.setLocation(180, 110);
        add(colField);

        mineField = new JTextField(15);
        mineField.setSize(40, 20);
        mineField.setLocation(180, 170);
        add(mineField);
    }

    public boolean isDataAvailable() {
        if (!isFieldDataAvailable(rowField, 30, 9) ||
                !isFieldDataAvailable(colField, 24, 9)) {
            showMessage("Error", "Input not Available");
            return false;
        }
        col = Integer.parseInt(colField.getText());
        row = Integer.parseInt(rowField.getText());
        int grid = row * col;
        if(!isFieldDataAvailable(mineField,grid/2,1)){
            showMessage("Error", "Input not Available");
            return false;
        }
        mineNum=Integer.parseInt(mineField.getText());
        return true;
    }

    public boolean isFieldDataAvailable(JTextField field, int boundMax, int boundMin) {
        String text = field.getText();
        if (!text.matches("\\d{1,2}")) return false;
        int num = Integer.parseInt(text);
        return num <= boundMax && num >= boundMin;
    }

    private void showMessage(String title, String content) {
        JFrame frame = new JFrame();
        JLabel label = new JLabel(content);
        frame.setLocationRelativeTo(null);
        frame.setTitle(title);
        frame.setLayout(new FlowLayout());
        frame.add(label);
        frame.setVisible(true);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getMineNum() {
        return mineNum;
    }
}
