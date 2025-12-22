package LeonTools.ChatGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LeonToolsStartGUI implements ActionListener {

    private static JFrame frame;
    private static JPanel panel;
    private static JButton NewChatRoom;
    private static JButton JoinChatRoom;

    private ChatRoomFinalisation currentChatRoom = null;
    private ChatRoomJoin currentChatRoomJoin = null;

    public LeonToolsStartGUI() {
        frame = new JFrame("LeonTools");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(null);
        frame.setResizable(false);

        panel = new JPanel();
        frame.add(panel);

        NewChatRoom = new JButton("Make new Chat room");
        NewChatRoom.setBounds(160, 90, 180, 20);
        NewChatRoom.setFocusable(false);
        frame.add(NewChatRoom);
        NewChatRoom.addActionListener(this);

        JoinChatRoom = new JButton("Join Chat room");
        JoinChatRoom.setBounds(160, 120, 180, 20);
        JoinChatRoom.setFocusable(false);
        JoinChatRoom.addActionListener(this);
        frame.add(JoinChatRoom);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LeonToolsStartGUI();
    }

    private boolean isAnythingOpen() {
        if (currentChatRoom != null && currentChatRoom.getFrame().isDisplayable()) return true;
        if (currentChatRoomJoin != null && currentChatRoomJoin.getFrame().isDisplayable()) return true;
        if (GUIchat.activeFrame != null && GUIchat.activeFrame.isDisplayable()) return true;

        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isAnythingOpen()) {
            JOptionPane.showMessageDialog(
                    frame,
                    "There is already an active window or Chat Room! Please close it first.",
                    "Action Blocked",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        if (e.getSource() == NewChatRoom) {
            currentChatRoom = ChatRoomFinalisation.start();
            currentChatRoom.getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    currentChatRoom = null;
                }
            });
        }

        if (e.getSource() == JoinChatRoom) {
            currentChatRoomJoin = ChatRoomJoin.start();
            currentChatRoomJoin.getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    currentChatRoomJoin = null;
                }
            });
        }
    }
}