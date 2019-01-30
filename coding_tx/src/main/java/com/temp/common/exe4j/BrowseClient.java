package com.temp.common.exe4j;

import com.sun.jndi.toolkit.url.Uri;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class BrowseClient {

    public static void main(String[] args) throws URISyntaxException, IOException {
        URI uri = new URI("http://localhost:7002/hqbpmn/index1.jsp");
        Desktop desktop = null;
        if(Desktop.isDesktopSupported()){
            desktop= Desktop.getDesktop();
        }
        if(desktop!=null){
            desktop.browse(uri);
        }
    }
}
