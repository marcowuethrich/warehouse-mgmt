package ch.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "db_groups")
public class Groups extends EntityModel {

    private String code;

    @Column(length = 40, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Article> articles;

    public Groups() {
    }

    public Groups(String code, String name, Category category, Set<Article> articles) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.articles = articles;
    }

    public Groups(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Groups(String code, String name, Category category) {
        this.code = code;
        this.name = name;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category article) {
        this.category = article;
    }

    public String getIdByString() {
        return id.toString();
    }
}
