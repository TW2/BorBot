package org.wingate.borbot.io;

public enum Protocol {
    None("None"),
    Window("Window");

    final String name;

    Protocol(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
