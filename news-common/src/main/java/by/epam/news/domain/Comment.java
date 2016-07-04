package by.epam.news.domain;

import java.sql.Timestamp;

/**
 * The comment entity represents a comment to a single news message. Contains a unique id, text of the comment,
 * date of creation and id of the news message.
 */
public class Comment {
    private Long id;
    private String commentText;
    private Timestamp creationDate;
    private Long newsId;

    public Comment() {}

    public Comment(Long newsId, String commentText) {
        this.commentText = commentText;
        this.newsId = newsId;
    }

    public Comment(Long id, String commentText, Timestamp creationDate, Long newsId) {
        this.id = id;
        this.commentText = commentText;
        this.creationDate = creationDate;
        this.newsId = newsId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (!commentText.equals(comment.commentText)) return false;
        if (!creationDate.equals(comment.creationDate)) return false;
        return !(newsId != null ? !newsId.equals(comment.newsId) : comment.newsId != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + commentText.hashCode();
        result = 31 * result + creationDate.hashCode();
        result = 31 * result + (newsId != null ? newsId.hashCode() : 0);
        return result;
    }
}
