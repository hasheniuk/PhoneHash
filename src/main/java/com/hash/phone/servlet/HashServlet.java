package com.hash.phone.servlet;

import com.hash.phone.server.AsyncServlet;
import com.hash.phone.service.HashService;
import com.hash.phone.service.HashServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HashServlet extends AsyncServlet {
    private HashService hashService = new HashServiceImpl();

    @Override
    protected String content(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pnone = getPhone(req, resp);
        return hashService.hash(pnone);
    }

    private String getPhone(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] path = req.getPathInfo().split("/");
        if (path.length != 3 || !path[2].matches("[0-9]+")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return path[2];
    }
}
