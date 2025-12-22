package LeonTools.ChatGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JoinWithPassword implements ActionListener {

    private JFrame frame;
    private JPasswordField passwordField;
    private JButton joinButton;
    private JButton backButton;
    private JLabel label;

    private String roomName;
    private String correctPassword;

    public JoinWithPassword(String roomName) {
        this.roomName = roomName;

        try {
            String path = "" + roomName + "";//your file path and password ending
            correctPassword = new String(Files.readAllBytes(Paths.get(path))).trim();
        } catch (IOException e) {
            correctPassword = "";
        }

        frame = new JFrame("Enter Password");
        frame.setSize(400, 220);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);

        label = new JLabel("Please enter the Room Password for " + roomName + ":");
        label.setBounds(50, 20, 300, 25);
        frame.add(label);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 50, 285, 30);
        frame.add(passwordField);

        backButton = new JButton("Back");
        backButton.setBounds(50, 100, 120, 30);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        frame.add(backButton);

        joinButton = new JButton("Join");
        joinButton.setBounds(215, 100, 120, 30);
        joinButton.setFocusable(false);
        joinButton.addActionListener(this);
        frame.add(joinButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton){
            frame.dispose();
            ChatRoomJoin.start();
        }
        else if(e.getSource() == joinButton){
            String inputPassword = new String(passwordField.getPassword());
            if(inputPassword.equals(correctPassword)){
                try {
                    GUIchat.startJoin(roomName, inputPassword);
                    frame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong Password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        }
    }
}