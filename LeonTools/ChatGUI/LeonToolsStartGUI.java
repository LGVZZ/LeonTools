package LeonTools;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Random;
import java.util.stream.Collectors;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class LeonToolsStartGUI {

    private static JFrame frame;
    private static JPanel panel;
    private static JButton NewChatRoom;
    private static JButton JoinChatRoom;

    public LeonToolsStartGUI() {
        frame = new JFrame("LeonTools");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(null);
        frame.setResizable(false);

        panel = new JPanel();
        frame.add(panel);

        NewChatRoom = new JButton("Make new Chat room");
        NewChatRoom.setBounds(160,90, 180, 20);
        NewChatRoom.setFocusable(false);
        frame.add(NewChatRoom);

        JoinChatRoom = new JButton("Join Chat room");
        JoinChatRoom.setBounds(160,120, 180, 20);
        JoinChatRoom.setFocusable(false);
        frame.add(JoinChatRoom);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LeonToolsStartGUI leonToolsStartGUI = new LeonToolsStartGUI();
    }

}