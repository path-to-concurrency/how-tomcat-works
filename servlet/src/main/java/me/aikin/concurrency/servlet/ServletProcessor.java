package me.aikin.concurrency.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;

/**
 * @author aikin
 */
public class ServletProcessor {
    public void process(Request request, Response response) {
        Optional<String> optionalUri = request.getUri();

        if (!optionalUri.isPresent()) {
            return;
        }
        String servletName = optionalUri.get().substring(optionalUri.get().lastIndexOf("/") + 1);
        try {
            Class<?> aClass = this.getClass().getClassLoader().loadClass("me.aikin.concurrency.servlet.".concat(servletName));
            Servlet servlet = (Servlet) aClass.newInstance();

            servlet.service(request, response);

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
