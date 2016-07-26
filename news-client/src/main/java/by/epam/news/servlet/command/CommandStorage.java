package by.epam.news.servlet.command;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * A singleton for storing all possible system commands and providing them on demand.
 */
public class CommandStorage {
    private Map<CommandName, Command> commands;

    public void setCommands(Map<CommandName, Command> commands) {
        this.commands = commands;
    }

    /**
     * Returns a command for corresponding command parameter in a http-request
     * @param request http request from the servlet
     * @return corresponding action command or an empty command in case command is not specified
     * @throws CommandException if command parameter is invalid
     */
    public Command getCommand(HttpServletRequest request) throws CommandException {
        Command current = commands.get(CommandName.VIEW_LIST);
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandName commandName = CommandName.valueOf(action.toUpperCase());
            current = commands.get(commandName);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e);
        }
        return current;
    }
}
