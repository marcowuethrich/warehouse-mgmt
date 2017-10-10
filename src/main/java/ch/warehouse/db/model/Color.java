package ch.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "db_color")
public class Color extends EntityModel {

    @Column(length = 20, nullable = false, unique = true)
    private String code;

    @Column(length = 40, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Article> articles;

    public Color() {
    }

    public Color(String code, String name, Set<Article> articles) {
        this.code = code;
        this.name = name;
        this.articles = articles;
    }

    public Color(String code, String name) {
        this.code = code;
        this.name = name;
    }

    String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public String getIdByString() {
        return id.toString();
    }
}
