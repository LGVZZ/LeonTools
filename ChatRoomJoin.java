package LeonTools.ChatGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ChatRoomJoin implements ActionListener {
    public JFrame frame;
    public JPanel panel;
    public JScrollPane scrollPane;

    public ChatRoomJoin() {
        frame = new JFrame("LeonTools-JoinChat");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setResizable(false);
        frame.setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);

        scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(10, 10, 365, 440);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        frame.add(scrollPane);

        JoinChatRoomsButtonsAndShow();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public boolean JoinChatRoomsButtonsAndShow(){
        File folder = new File("Y:\\3BHIT\\LeonGraf\\Chat");
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt") && !name.endsWith(""));//you password ending
        if (files == null){
            return false;
        }
        int yOffset = 0;
        for (File f : files) {
            String fileName = f.getName().replace(".txt", "");
            addButton(fileName, yOffset);
            yOffset += 40;
        }

        panel.setPreferredSize(new Dimension(340, yOffset));

        panel.revalidate();
        panel.repaint();

        return true;
    }

    public void addButton(String Text, int yOffset){
        JButton button = new JButton("Join: " + Text);
        button.setBounds(0, yOffset, 340, 30);
        button.setFocusable(false);

        panel.add(button);

        button.addActionListener( this);
    }

    public JFrame getFrame() {
        return frame;
    }

    public static ChatRoomJoin start(){
        return new ChatRoomJoin();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton ){
            JButton clickedButton = (JButton) e.getSource();
            String buttonText = clickedButton.getText();
            String roomName = buttonText.replace("Join: ", "");

            try{
                File pwFile = new File("" + roomName + "");//your file path and password ending

                if(pwFile.exists()) {
                    new JoinWithPassword(roomName);
                    frame.dispose();
                } else {
                    GUIchat.startJoin(roomName, "");
                    frame.dispose();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Fehler beim finden/laden des Chat Rooms");
            }
        }
    }
}