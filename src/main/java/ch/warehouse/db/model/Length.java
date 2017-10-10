package ch.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "db_length")
public class Length extends EntityModel {

    @Column(length = 20, nullable = false, unique = true)
    private String code;

    @Column(length = 40, nullable = false, unique = true)
    private double size;

    @OneToMany(mappedBy = "length", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Article> articles;

    public Length() {
    }

    public Length(String code, double size, Set<Article> articles) {
        this.code = code;
        this.size = size;
        this.articles = articles;
    }

    public Length(String code, double size) {
        this.code = code;
        this.size = size;
    }

    String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    double getSize() {
        return size;
    }

    public void setSize(double name) {
        this.size = name;
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
