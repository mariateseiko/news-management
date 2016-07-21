package by.epam.news.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Represents a single news message. Contains a unique id, title, short and full texts along with creation and
 * modification dates.
 */
public class News {
    private Long id;

    @NotEmpty(message = "{field.not.empty}")
    @Size(max = 200, message = "{field.size.200}")
    private String title;

    @NotEmpty(message = "{field.not.empty}")
    @Size(max = 500, message = "{field.size.500}")
    private String shortText;

    @NotEmpty(message = "{field.not.empty}")
    @Size(max = 2000, message = "{field.size.2000}")
    private String fullText;

    private Date creationDate;
    private Date modificationDate;
    private Long nextId;
    private Long previousId;

    public News() {}

    public News(String title, String shortText, String fullText) {
        this.title = title;
        this.shortText = shortText;
        this.fullText = fullText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Long getNextId() {
        return nextId;
    }

    public void setNextId(Long nextId) {
        this.nextId = nextId;
    }

    public Long getPreviousId() {
        return previousId;
    }

    public void setPreviousId(Long previousId) {
        this.previousId = previousId;
    }
}
