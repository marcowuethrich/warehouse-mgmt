package ch.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "db_typ")
public class Typ extends EntityModel {

    private String code;

    @Column(length = 40, nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typGroup_id")
    private TypGroup typGroup;

    @OneToMany(mappedBy = "typ", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Article> articles;

    public Typ() {
    }

    public Typ(String code, String name, TypGroup typGroup, Set<Article> articles) {
        this.code = code;
        this.name = name;
        this.typGroup = typGroup;
        this.articles = articles;
    }

    public Typ(String code, String name, TypGroup typGroup) {
        this.code = code;
        this.name = name;
        this.typGroup = typGroup;
    }

    public Typ(String code, String name) {
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

    public TypGroup getTypGroup() {
        return typGroup;
    }

    public void setTypGroup(TypGroup article) {
        this.typGroup = article;
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


