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

    public LeonToolsStartGUI() {
        frame = new JFrame("LeonTools");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(null);
        frame.setResizable(false);

        panel = new JPanel();
        frame.add(panel);

        NewChatRoom = new JButton("Make new Chat room");
        NewChatRoom.setBounds(160,90, 180, 20);
        NewChatRoom.setFocusable(false);
        frame.add(NewChatRoom);
        NewChatRoom.addActionListener((ActionListener) this);

        JoinChatRoom = new JButton("Join Chat room");
        JoinChatRoom.setBounds(160,120, 180, 20);
        JoinChatRoom.setFocusable(false);
        frame.add(JoinChatRoom);

        frame.setVisible(true);
    }

    public static void main(String[] args)  {

        LeonToolsStartGUI leonToolsStartGUI = new LeonToolsStartGUI();
    }

    private ChatRoomFinalisation currentChatRoom = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == NewChatRoom) {

            // If we have a ChatRoom instance AND it is still open → block it
            if (currentChatRoom != null) {
                JFrame f = currentChatRoom.getFrame();
                if (f != null && f.isDisplayable()) {
                    JOptionPane.showMessageDialog(
                            f,
                            "There can't be more than one Chat Room! Please close the opened Chat Room first.",
                            "Too many Chat Rooms!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
            }


            currentChatRoom = ChatRoomFinalisation.start();


            currentChatRoom.getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    currentChatRoom = null;
                }
            });
        }
    }



}