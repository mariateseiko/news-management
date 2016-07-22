package by.epam.news.servlet.command;

public enum CommandName {
VIEW_LIST("VIEW_LIST");
// , VIEW_FILTERED_LIST, VIEW_NEWS, POST_COMMENT;
    private String commandName;
    CommandName(String commandName) {
        this.commandName = commandName;
    }
}
