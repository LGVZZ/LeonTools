package LeonTools.ChatGUI;

import java.io.File;


public class CeckForExistingChatName {

    public boolean existsChatRoom(String roomName) {
        File folder = new File("C:/path/to/folder");

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null) return false;

        for (File f : files) {

            String fileName = f.getName().replace(".txt", "");

            if (fileName.equals(roomName)) {
                return true;
            }
        }

        return false;
    }

}
