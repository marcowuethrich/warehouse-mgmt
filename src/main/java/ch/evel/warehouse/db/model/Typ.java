package ch.evel.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "db_typ")
public class Typ extends EntityModel {

    @Column(length = 20, nullable = false, unique = true)
    @JsonView(DataTablesOutput.View.class)
    @Size(min = 2, max = 10)
    @NotNull(message = "Can't be Null")
    private String code;

    @Column(length = 40, nullable = false, unique = true)
    @JsonView(DataTablesOutput.View.class)
    @Size(min = 2, max = 40)
    @NotNull(message = "Can't be Null")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
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

    public String getCode() {
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

    public void setTypGroup(TypGroup typGroup) {
        this.typGroup = typGroup;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}


