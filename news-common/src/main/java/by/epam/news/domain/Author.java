package by.epam.news.domain;

import java.sql.Timestamp;

/**
 * The Author entity represents an author in the news management system. Contains a unique id and a name.
 * Optional field expired specifies datetime when the author was marked as an expired author.
 */
public class Author {
    private Long id;
    private String Name;
    private Timestamp expired;

    public Author() {}

    public Author(Long id, String name, Timestamp expired) {
        this.expired = expired;
        this.id = id;
        Name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
        if (!(o instanceof Author)) return false;

        Author author = (Author) o;

        if (!id.equals(author.id)) return false;
        if (!Name.equals(author.Name)) return false;
        return !(expired != null ? !expired.equals(author.expired) : author.expired != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + Name.hashCode();
        result = 31 * result + (expired != null ? expired.hashCode() : 0);
        return result;
    }
}
