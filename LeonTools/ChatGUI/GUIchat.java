package LeonTools.ChatGUI;

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

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class GUIchat implements ActionListener {

    private static JFrame frame;

    private static JPanel root;

    private static JTextArea Chat;

    private static JTextField UserText;
    private static JTextField NewName;

    private static JScrollPane ScrollPane;

    private static JButton SendButton;
    private static JButton ChangeNameButton;

    static int width = 600;
    static int height = 350;

    static String PCName;
    static String name;
    static String newName;

    private static final String filePath = "E:\\Schule\\it htl\\SEW\\javagui\\src\\LeonTools\\ChatGUI\\chat.txt";

    public GUIchat() {
        frame = new JFrame("Chat GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        Chat = new JTextArea();
        Chat.setEditable(false);
        Chat.setLineWrap(true);
        Chat.setWrapStyleWord(true);
        Chat.setFocusable(false);

        SendButton = new JButton("Send");
        SendButton.addActionListener(this);
        frame.getRootPane().setDefaultButton(SendButton);

        ChangeNameButton = new JButton();
        ChangeNameButton.setFont(ChangeNameButton.getFont().deriveFont(11f));
        ChangeNameButton.setText("Change Name to:");
        ChangeNameButton.addActionListener(this);

        UserText = new JTextField();

        NewName = new JTextField();
        limitTextField(NewName, 20);

        ScrollPane = new JScrollPane(Chat);
        ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        root = new JPanel(null);
        frame.setContentPane(root);

        root.add(ScrollPane);
        root.add(UserText);
        root.add(SendButton);
        root.add(NewName);
        root.add(ChangeNameButton);

        double whidthRatio = 0.9;
        double heightRatio = 0.7;

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int W = root.getWidth();
                int H = root.getHeight();
                int w = (int) (W * whidthRatio);
                int h = (int) (H * heightRatio);

                int chatWidth = (int) (W * whidthRatio);
                int chatHeight = (int) (H * heightRatio);

                int margin = 10;

                int UserTextHeight = 35;
                int NewNameHeight = 25;

                int SendButtonWidth = 100;

                int ChangeNameButtonWidth = 130;

                int UserTextY = chatHeight + margin;
                int UserTextWidth = chatWidth - SendButtonWidth - margin;

                int NewNameY = UserTextY + UserTextHeight + margin;
                int NewNameX = ChangeNameButtonWidth + margin;
                int NewNameWidth = chatWidth - ChangeNameButtonWidth - margin;

                UserText.setBounds(0, UserTextY, UserTextWidth, UserTextHeight);
                SendButton.setBounds(UserTextWidth + margin, UserTextY, SendButtonWidth, UserTextHeight);

                ChangeNameButton.setBounds(0, NewNameY, ChangeNameButtonWidth, NewNameHeight);
                NewName.setBounds(NewNameX, NewNameY, NewNameWidth, NewNameHeight);

                ScrollPane.setBounds(0, 0, w, h);
                root.revalidate();

            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        SwingUtilities.invokeLater(() -> UserText.requestFocusInWindow());

    }

    private void limitTextField(JTextField field, int maxChars) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                int currentLength = fb.getDocument().getLength();
                int overLimit = (currentLength - length + text.length()) - maxChars;

                if (overLimit <= 0) {
                    super.replace(fb, offset, length, text, attrs);
                    field.setBackground(Color.white);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "The new entered name is to long, please enter a name that has a maximum of 20 characters!",
                            "Name to Long!", JOptionPane.WARNING_MESSAGE);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                int currentLength = fb.getDocument().getLength();
                int overLimit = (currentLength + string.length()) - maxChars;

                if (overLimit <= 0) {
                    super.insertString(fb, offset, string, attr);
                    field.setBackground(Color.white);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "The new entered name is to long, please enter a name that has a maximum of 20 characters!",
                            "Name to Long!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void addline(String line) {
        Chat.append(line + "\n");
        Chat.setCaretPosition(Chat.getDocument().getLength());
    }

    public void startFileMonitoring() {

        GUIchat gui = this;

        File file = new File(filePath);
        Path path = file.toPath();
        Path dirPath = path.getParent();

        long filePointer = 0;
        int temp1 = 0;
        int temp2 = 0;

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            dirPath.register(watchService, ENTRY_MODIFY);

            while (true) {
                WatchKey key = watchService.take();

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == ENTRY_MODIFY) {
                        Path changed = (Path) event.context();

                        if (changed.equals(path.getFileName())) {
                            try (RandomAccessFile in = new RandomAccessFile(filePath, "r")) {
                                in.seek(filePointer);

                                long remain = in.length() - filePointer;
                                if (remain > 0 && remain <= Integer.MAX_VALUE) {
                                    byte[] bytes = new byte[(int) remain];
                                    in.readFully(bytes);
                                    String chunk = new String(bytes, StandardCharsets.UTF_8);
                                    String[] lines = chunk.split("\\r?\\n", -1);
                                    for (String line : lines) {
                                        if (line.isEmpty())
                                            continue;
                                        if (temp2 > 0) {
                                            gui.addline(line);
                                        } else {
                                            gui.addline(line);
                                            temp1++;
                                        }
                                    }
                                }
                                if (temp2 == 0) {
                                    temp2 = temp1;
                                }

                                filePointer = in.length();
                            } catch (IOException e) {
                                System.out.println("Fehler beim Lesen:");
                                e.printStackTrace();
                            }
                        }
                    }
                }
                key.reset();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /*
     * public static String name(){ String name = ""; try { Process process = new
     * ProcessBuilder("cmd.exe", "/c", "net use Z:") .redirectErrorStream(true)
     * .start(); BufferedReader reader = new BufferedReader(new
     * InputStreamReader(process.getInputStream(), "CP850")); String Text =
     * reader.lines().collect(Collectors.joining("\n")); process.waitFor(); int pos
     * = Text.indexOf("Remotename"); if(pos != -1){ String subString1 =
     * Text.substring(pos); int pos2 = Text.indexOf("\n", pos); if(pos2 != -1){
     * String subString2 = Text.substring(pos, pos2); int pos3 =
     * Text.indexOf("homes", pos); if(pos3 != -1){ pos3 += 6; name =
     * Text.substring(pos3, pos2); } } } } catch (Exception e) {
     * e.printStackTrace(); } PCName = name; return name; }
     */

    public static void writeToFile() throws FileNotFoundException {

        int spacecount = 0;
        boolean space = false;

        String text = UserText.getText();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                spacecount++;
            }
        }

        if (spacecount == text.length()) {
            space = true;
        }

        if (!text.isEmpty() && !space) {

            try (Writer writer = new OutputStreamWriter(new FileOutputStream(filePath, true), StandardCharsets.UTF_8)) {
                writer.write(name + ": " + text + "\n");
            } catch (IOException e) {
                System.out.println("Fehler beim Schreiben: " + e.getMessage());
            }
        } else {
            System.out.print("\033[F");
            System.out.print("\033[2K");
        }
    }

    public void RandomColor() {
        Random red = new Random();
        Random green = new Random();
        Random blue = new Random();

        int redColor = red.nextInt(219);
        int greenColor = green.nextInt(219);
        int blue1Color = blue.nextInt(219);

        redColor += 1;
        greenColor += 1;
        blue1Color += 1;
    }

    public static void checkForChatTXT(String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
    }

    public static void main(String[] args) throws IOException {
        name = "leon.graf"; // name();
        checkForChatTXT(filePath);
        GUIchat monitor = new GUIchat();
        monitor.startFileMonitoring();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean newNameHasSpecialChar = false;
        if (e.getSource() == SendButton) {
            try {
                writeToFile();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            UserText.setText("");
        }
        if (e.getSource() == ChangeNameButton) {
            newName = NewName.getText();
            NewName.setText("");

            char[] illegal = { '.', ',', ';', '!', '?', ' ' };
            for (char illegal_char : illegal)
                if (newName.indexOf(illegal_char) != -1) {
                    JOptionPane.showMessageDialog(frame, "Please do NOT use Characters like: . / , / ; / ! / ? / etc..",
                            "New Name has not Wanted Characters", JOptionPane.WARNING_MESSAGE);
                    newNameHasSpecialChar = true;
                    break;
                }

            if (newName.isEmpty())
                newNameHasSpecialChar = true;

            if (!newNameHasSpecialChar)
                name = newName;
            else
                newNameHasSpecialChar = false;

        }
    }
}
