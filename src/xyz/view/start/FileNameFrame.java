package xyz.view.start;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FileNameFrame extends JFrame {
    private File file;
    private ActionListener el;

    public FileNameFrame() {
        File dictionary=new File(System.getenv("APPDATA")+"\\MineSweeperJavaA");
        if(!dictionary.exists()){
            System.out.println(dictionary.mkdirs());
        }
        setSize(500, 400);
        setLocationRelativeTo(null);
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("MineSweeper save File (*.msv)", "msv");
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(dictionary);
        add(chooser, BorderLayout.CENTER);
        chooser.addActionListener(e -> {
            if (e.getActionCommand().equals("CancelSelection")) {
                dispose();
            }
            if (e.getActionCommand().equals("ApproveSelection")) {
                file = chooser.getSelectedFile();
            }
            el.actionPerformed(e);
        });
    }


    public File getFile() {
        return file;
    }

    public void addActionListener(ActionListener el){
        this.el=el;
    }
}
