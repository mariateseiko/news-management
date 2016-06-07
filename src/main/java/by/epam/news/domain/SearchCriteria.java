package by.epam.news.domain;

import java.util.List;

/**
 * Represents a search criteria object, containing a list of authors and tags a news message must have in
 * order to match the criteria.
 */
public class SearchCriteria {
    private List<Long> tagsId;
    private List<Long> authorsId;

    public SearchCriteria() {}

    public SearchCriteria(List<Long> tagsId, List<Long> authorsId) {
        this.tagsId = tagsId;
        this.authorsId = authorsId;
    }

    public List<Long> getTagsId() {
        return tagsId;
    }

    public void setTagsId(List<Long> tagsId) {
        this.tagsId = tagsId;
    }

    public List<Long> getAuthorsId() {
        return authorsId;
    }

    public void setAuthorsId(List<Long> authorsId) {
        this.authorsId = authorsId;
    }
}
