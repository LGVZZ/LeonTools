package LeonTools.ChatGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatRoomFinalisation implements ActionListener {

    public JFrame frame;

    public JTextField nameOfChatRoom;
    public JPasswordField passwordForChatRoom;

    public JButton createButton;
    public JButton cancelButton;

    public String name;
    public String password;

    public ChatRoomFinalisation() {
        frame = new JFrame("Chat Room Creation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setResizable(false);

        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Chat Room name:");
        nameLabel.setBounds(10, 10, 120, 25);
        frame.add(nameLabel);

        nameOfChatRoom = new JTextField();
        nameOfChatRoom.setBounds(140, 10, 200, 25);
        frame.add(nameOfChatRoom);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(10, 45, 120, 25);
        frame.add(passLabel);

        passwordForChatRoom = new JPasswordField();
        passwordForChatRoom.setBounds(140, 45, 200, 25);
        frame.add(passwordForChatRoom);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(10, 90, 100, 30);
        frame.add(cancelButton);
        cancelButton.addActionListener(this);

        createButton = new JButton("Create");
        createButton.setBounds(120, 90, 100, 30);
        frame.add(createButton);
        createButton.addActionListener(this);

        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public static ChatRoomFinalisation start(){
        return new ChatRoomFinalisation();
    }

    public boolean CharakterLengthChecker(String Name, int MaxLength){
        if(Name.length() <= MaxLength){
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancelButton) {
            frame.dispose();
        }
        if(e.getSource() == createButton) {
            boolean hasPassword = false;
            name = nameOfChatRoom.getText();
            password = new String(passwordForChatRoom.getPassword());

            if(name.isEmpty() || CharakterLengthChecker(name, 20)) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid Chat Room name or a shorter name(Max characters for the Chat Room is 20 chars)!");
                return;
            }

            try {
                CeckForExistingChatName checker = new CeckForExistingChatName();
                if(checker.existsChatRoom(name)) {
                    JOptionPane.showMessageDialog(frame, "A Chat Room with the exact same name already exists!");
                    return;
                }

                if(!password.isEmpty()) {
                    if (CharakterLengthChecker(password, 10)) {
                        JOptionPane.showMessageDialog(frame, "Password must have a maximum of 10 chars!");
                        passwordForChatRoom.setText("");
                        return;
                    }

                    for (int i = 0; i < password.length(); i++) {
                        if (password.charAt(i) == '.' ||
                                password.charAt(i) == ',' ||
                                password.charAt(i) == ';' ||
                                password.charAt(i) == '!' ||
                                password.charAt(i) == '?' ||
                                password.charAt(i) == ' ') {
                            JOptionPane.showMessageDialog(frame, "Please do NOT use Characters like: . / , / ; / ! / ? / etc..", "New Password has not Wanted Characters", JOptionPane.WARNING_MESSAGE);
                            passwordForChatRoom.setText("");
                            return;
                        }
                    }
                    hasPassword = true;
                }

                frame.dispose();
                GUIchat.start(name, password, hasPassword);

            }catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}