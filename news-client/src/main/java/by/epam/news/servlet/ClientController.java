package by.epam.news.servlet;

import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;
import by.epam.news.servlet.command.CommandStorage;
import org.springframework.context.ApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class ClientController extends HttpServlet {
    private CommandStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");
        storage =  ac.getBean(CommandStorage.class, "commandStorage");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        try {
            Command command = storage.getCommand(req);
            page = command.execute(req);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } catch(CommandException e) {
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        try {
            Command command = storage.getCommand(req);
            page = command.execute(req);
            resp.sendRedirect(req.getContextPath() + page );
        } catch(CommandException e) {
            resp.sendError(500);
        }
    }

}
