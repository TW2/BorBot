package org.wingate.borbot.message;

import java.awt.*;

public abstract class AbstractMessage implements Message {

    protected String name;
    protected String order;
    protected int port;
    protected Point clickLocation;
    protected int status;

    AbstractMessage(){
        name = "Unknown message";
        order = "Unknown order";
        port = 3200;
        clickLocation = null;
        status = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOrder() {
        return order;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        if(port < 0 || port > 65535) throw new PortException("Port is not allowed");
        this.port = port;
    }

    @Override
    public Point getClickLocation() {
        return clickLocation;
    }

    @Override
    public void setClickLocation(Point location) {
        clickLocation = location;
    }

    @Override
    public void updateStatus() {
        if(status < 0) System.exit(status);
    }
}
