package by.epam.news.domain;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Represents a search criteria object, containing a list of authors and tags a news message must have in
 * order to match the criteria.
 */
public class SearchCriteria {
    private List<Tag> tags;
    @NotEmpty
    private List<Author> authors;
    private Long page;
    private Long limit;

    public SearchCriteria() {}

    public SearchCriteria(List<Tag> tags, List<Author> authors) {
        this.tags = tags;
        this.authors = authors;
    }

    public SearchCriteria(List<Tag> tags, List<Author> authors, Long page, Long limit) {
        this.tags = tags;
        this.authors = authors;
        this.page = page;
        this.limit = limit;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
