package by.epam.news.domain;

import java.io.Serializable;
import java.util.List;

public class NewsDTO implements Serializable {
    private News news;
    private Author author;
    private List<Comment> comments;
    private List<Tag> tags;

    public NewsDTO() {}

    public NewsDTO(News news, Author author, List<Tag> tags) {
        this.news = news;
        this.author = author;
        this.tags = tags;
    }

    public NewsDTO(News news, Author author, List<Comment> comments, List<Tag> tags) {
        this.news = news;
        this.author = author;
        this.comments = comments;
        this.tags = tags;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
