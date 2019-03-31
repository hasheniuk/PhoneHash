package com.hash.phone.server;

import com.hash.phone.env.Environment;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

import javax.servlet.http.HttpServlet;

public class EmbeddedServer {

    private Server server;
    private ServletHandler servletHandler;

    public EmbeddedServer() {
        this.server = new Server(getThreadPool());
        this.servletHandler = new ServletHandler();

        int port = Environment.get().getPropertyAsInt("server.port", 8080);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        this.server.setConnectors(new Connector[] { connector });
    }

    private ThreadPool getThreadPool() {
        int maxThreads = Environment.get().getPropertyAsInt("server.max-threads", 1024);
        int minThreads = Environment.get().getPropertyAsInt("server.min-threads", 128);
        int idleTimeout = Environment.get().getPropertyAsInt("server.idle-timeout", 1000);

        return new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
    }

    public void addServlet(Class<? extends HttpServlet> servletClass, String mapping) {
        ServletHolder holder = this.servletHandler.addServletWithMapping(servletClass, mapping);
        holder.setAsyncSupported(true);
    }

    public void start() {
        this.server.setHandler(servletHandler);
        try {
            this.server.start();
            this.server.join();
        } catch (Exception e) {
            throw new EmbeddedServerException("Fail to start server", e);
        }
    }
}
