package ch.warehouse.db.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "db_category")
public class Category extends EntityModel {

    @Column(length = 20, nullable = false, unique = true)
    private String code;

    @Column(length = 40, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Article> articles;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Groups> groups;

    public Category() {
    }

    public Category(String code, String name, Set<Article> articles, Set<Groups> groups) {
        this.code = code;
        this.name = name;
        this.articles = articles;
        this.groups = groups;
    }

    public Category(String code, String name) {
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

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    public String getIdByString() {
        return id.toString();
    }
}
