package org.wingate.borbot.message;

import java.awt.*;

public interface Message {
    String getName();
    void setName(String name);
    String getOrder();
    void setOrder(String order);
    int getPort();
    void setPort(int port);
    Point getClickLocation();
    void setClickLocation(Point location);
    void updateStatus();
}
