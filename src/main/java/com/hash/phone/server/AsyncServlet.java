package com.hash.phone.server;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AsyncServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        AsyncContext async = req.startAsync();
        ServletOutputStream out = resp.getOutputStream();
        out.setWriteListener(new WriteListener() {

            @Override
            public void onWritePossible() throws IOException {
                out.write(content(req, resp).getBytes());
                resp.setStatus(200);
                async.complete();
            }

            @Override
            public void onError(Throwable t) {
                getServletContext().log(t.getMessage(), t);
                async.complete();
            }
        });
    }

    protected abstract String content(HttpServletRequest req,
                                      HttpServletResponse resp) throws IOException;
}
