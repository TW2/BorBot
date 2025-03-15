package org.wingate.borbot.io;

import org.wingate.borbot.ui.Window;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class XML {



    public static class Reader {

        private WindowHandler windowhandler;

        public Reader(String data) {
            try(ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))) {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                windowhandler = new WindowHandler();
                parser.parse(in, windowhandler);
            }catch(Exception _){
                windowhandler = null;
            }
        }

        public WindowHandler getWindowhandler() {
            return windowhandler;
        }

        public static class WindowHandler extends DefaultHandler {
            /*
            Structure:

            <app>
                <window name="" mainPort="" clickPort="">
                    <backgroundColor color="Hexadecimal ARGB" />
                    <measure x="" y="" width="" height="" />
                </window>
            </app>
            */

            private StringBuffer buffer;
            private Window window;

            public WindowHandler() {
                super();
                window = null;
            }

            public Window getWindow() {
                return window;
            }

            @Override
            public void startElement(String uri, String localName,
                                     String qName, Attributes attributes)
                    throws SAXException {

                buffer = new StringBuffer();

                switch(qName){
                    case "app" -> { /* Beginning */ }
                    case "window" -> {
                        String title = getFor(attributes, "name");
                        int mainPort = getIntValue(getFor(attributes, "mainPort"));
                        int clickPort = getIntValue(getFor(attributes, "clickPort"));
                        window = new Window(
                                mainPort == -1 ? 3020 : mainPort,
                                clickPort == -1 ? 3030 : clickPort
                        );
                        window.setTitle(title == null ? "Unknown application" : title);
                        System.out.println("T: "+title);
                    }
                    case "backgroundColor" -> {
                        String hexadecimalColor = getFor(attributes, "color");
                        if(hexadecimalColor != null){
                            window.setBackground(getHexadecimalARGB(hexadecimalColor));
                        }
                    }
                    case "measure" -> {
                        int x = getIntValue(getFor(attributes, "x"));
                        int y = getIntValue(getFor(attributes, "y"));
                        int w = getIntValue(getFor(attributes, "width"));
                        int h = getIntValue(getFor(attributes, "height"));
                        if(w < 0 || h < 0){
                            // TODO: Full screen exchange with 800x600
                            window.setSize(800, 600);
                        }else{
                            window.setSize(w, h);
                        }
                        if(x < 0 || y < 0){
                            window.setLocationRelativeTo(null);
                        }else{
                            window.setLocation(x, y);
                        }
                    }
                }
            }

            //détection fin de balise
            @Override
            public void endElement(String uri, String localName, String qName)
                    throws SAXException{

                switch(qName){
                    case "app" -> { /* End */ }
                    case "window", "backgroundColor", "measure" -> buffer = null;
                }

            }

            @Override
            public void characters(char[] ch,int start, int length)
                    throws SAXException{
                String reading = new String(ch,start,length);
                if(buffer != null) {
                    buffer.append(reading);
                }
            }
        }
    }

    public static class Writer {

    }

    public static Color getHexadecimalARGB(String argb){
        if(argb.startsWith("#")) argb = argb.substring(1);
        int a = Integer.parseInt(argb.substring(0, 2), 16);
        int r = Integer.parseInt(argb.substring(2, 4), 16);
        int g = Integer.parseInt(argb.substring(4, 6), 16);
        int b = Integer.parseInt(argb.substring(6), 16);
        return new Color(r, g, b, a);
    }

    public static int getIntValue(String attr){
        if(attr == null || attr.isEmpty()) return -1;
        return Integer.parseInt(attr);
    }

    public static String getFor(Attributes attrs, String name){
        int index = -1;
        for(int i=0; i<attrs.getLength(); i++){
            if(attrs.getLocalName(i).equals(name)){
                index = i;
                break;
            }
        }
        return index == -1 ? null : attrs.getValue(index);
    }
}
