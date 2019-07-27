package me.aikin.concurrency.connector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Optional;

/**
 * @author aikin
 */
public class ServletProcessor {
    public void process(HttpRequest request, HttpResponse response) {
        Optional<String> optionalUri = Optional.ofNullable(request.getRequestURI());

        if (!optionalUri.isPresent()) {
            return;
        }
        String servletName = optionalUri.get().substring(optionalUri.get().lastIndexOf("/") + 1);
        try {
            Class<?> aClass = this.getClass().getClassLoader().loadClass("me.aikin.concurrency.servlet.".concat(servletName));
            Servlet servlet = (Servlet) aClass.newInstance();

            servlet.service(new HttpRequestFacade(request), new HttpResponseFacade(response));

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
