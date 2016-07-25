package by.epam.news.servlet.command;

public enum CommandName {
    VIEW_LIST("GET"), VIEW_FILTERED_LIST("GET"), VIEW_NEWS("GET"), POST_COMMENT("POST");
    private String method;
    CommandName(String method) {
        this.method = method;
    }
}
