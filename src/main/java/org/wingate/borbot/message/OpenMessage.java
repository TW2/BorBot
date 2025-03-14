package org.wingate.borbot.message;

import org.wingate.borbot.ui.Window;

import javax.swing.*;

public class OpenMessage extends AbstractMessage {

    public OpenMessage(String appName) {
        if(appName == null || appName.isEmpty()){
            status = -1;
            updateStatus();
        }
        name = "Open";
        order = appName + " opens";
//        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        w.setVisible(true);
    }
}
