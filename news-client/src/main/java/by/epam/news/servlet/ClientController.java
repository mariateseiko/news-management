package by.epam.news.servlet;

import by.epam.news.servlet.command.Command;
import by.epam.news.servlet.command.CommandException;
import by.epam.news.servlet.command.CommandStorage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
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

            if (page != null) {
                if (req.getMethod().equalsIgnoreCase("get")) {
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page+".tiles");
                    dispatcher.forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + page + ".tiles");
                }
            } else {
                page = req.getHeader("Referer");
                resp.sendRedirect(page);
            }
        } catch(CommandException e) {
//            LOG.error("Command execution failed", e);
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req, resp);
    }
}
