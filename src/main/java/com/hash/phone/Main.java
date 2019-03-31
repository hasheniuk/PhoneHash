package com.hash.phone;

import com.hash.phone.server.EmbeddedServer;
import com.hash.phone.servlet.HashServlet;
import com.hash.phone.servlet.PhoneServlet;

public class Main {

    public static void main(String[] args) {

        EmbeddedServer server = new EmbeddedServer();
        server.addServlet(HashServlet.class, "/hash/*");
        server.addServlet(PhoneServlet.class, "/phone/*");
        server.start();
    }
}
