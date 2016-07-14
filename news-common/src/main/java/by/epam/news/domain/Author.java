package by.epam.news.domain;

import java.sql.Timestamp;

/**
 * The Author entity represents an author in the news management system. Contains a unique id and a name.
 * Optional field expired specifies datetime when the author was marked as an expired author.
 */
public class Author {
    private Long id;
    private String name;
    private Timestamp expired;

    public Author() {}

    public Author(Long id) {
        this.id = id;
    }

    public Author(Long id, String name, Timestamp expired) {
        this.expired = expired;
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getExpired() {
        return expired;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return id.equals(author.id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (expired != null ? expired.hashCode() : 0);
        return result;
    }
}
