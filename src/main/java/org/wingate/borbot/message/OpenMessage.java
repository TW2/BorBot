package org.wingate.borbot.message;

import org.wingate.borbot.ui.Window;

import javax.swing.*;

public class OpenMessage extends AbstractMessage {

    public OpenMessage(String appPath) {
        if(appPath == null || appPath.isEmpty()){
            status = -1;
            updateStatus();
        }
        path = appPath;
        order = "Open";
    }
}
