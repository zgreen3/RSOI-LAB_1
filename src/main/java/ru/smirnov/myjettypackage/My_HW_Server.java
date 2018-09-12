package ru.smirnov.myjettypackage;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class My_HW_Server extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        String bufParam;
        int a, b;

        bufParam = request.getParameter("a");
        if (bufParam != null)
            a = Integer.parseInt(bufParam);
        else
            a = 0;

        bufParam = request.getParameter("b");
        if (bufParam != null)
            b = Integer.parseInt(bufParam);
        else
            b = 0;

        //тип ответа сервера и его кодировка:
        response.setContentType("text/html; charset=utf-8");

        //статус ответа сервера:
        response.setStatus(HttpServletResponse.SC_OK);

        //сервер непосредственно прописывает ответ:
        response.getWriter().println("<h1>a + b = " + String.valueOf((a + b)) + "</h1>");

        //информируем jetty, что текущий запрос теперь был обработан:
        baseRequest.setHandled(true);
    }

    public static void runServer(Server serverToRun) throws Exception {
        serverToRun.setHandler(new My_HW_Server());

        serverToRun.start();
        serverToRun.join();
    }

    public static void stopServer(Server serverToStop) throws Exception  {
        serverToStop.stop();
    }

    private static final int SERVER_PORT = 8081;

    public static void main(String[] args) throws Exception {
        My_HW_Server.runServer(new Server(SERVER_PORT));
    }
}
