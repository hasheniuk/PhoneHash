package com.hash.phone.servlet;

import com.hash.phone.server.AsyncServlet;
import com.hash.phone.service.HashService;
import com.hash.phone.service.HashServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PhoneServlet extends AsyncServlet {
    private HashService hashService = new HashServiceImpl();

    @Override
    protected String content(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String hash = getHash(req, resp);
        String phone = hashService.findPhone(hash);
        if (phone.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        return phone;
    }

    private String getHash(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String path = req.getPathInfo();
        String phone = "phone/";
        int phoneIndex = path.indexOf(phone);
        if (phoneIndex < 0 || (phoneIndex + phone.length()) >= path.length()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return "";
        }
        return path.substring(phoneIndex + phone.length());
    }
}
