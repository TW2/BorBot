package org.wingate.borbot.ui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final int mainPort;
    private final int clickPort;

    public Window(int mainPort, int clickPort) throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainPort = mainPort;
        this.clickPort = clickPort;
    }

    public int getMainPort() {
        return mainPort;
    }

    public int getClickPort() {
        return clickPort;
    }
}
