package by.epam.news.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * The Author entity represents an author in the news management system. Contains a unique id and a name.
 * Optional field expired specifies datetime when the author was marked as an expired author.
 */
public class Author {
    private Long id;

    @NotEmpty(message = "{field.not.empty}")
    @Size(max = 30, message = "{field.max.length.30}")
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

        if (id != null ? !id.equals(author.id) : author.id != null) return false;
        return !(expired != null ? !expired.equals(author.expired) : author.expired != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 37;
        result = 31 * result + (expired != null ? expired.hashCode() : 0);
        return result;
    }
}
