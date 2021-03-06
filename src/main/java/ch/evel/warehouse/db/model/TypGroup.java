package ch.evel.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.jdo.annotations.Unique;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "db_typegroup")
public class TypGroup extends EntityModel {

    @Column(length = 20, nullable = false, unique = true)
    @JsonView(DataTablesOutput.View.class)
    @Size(min = 2, max = 10)
    @Unique
    @NotNull(message = "Can't be Null")
    private String code;

    @Column(length = 40, nullable = false, unique = true)
    @JsonView(DataTablesOutput.View.class)
    @Size(min = 2, max = 40)
    @Unique
    @NotNull(message = "Can't be Null")
    private String name;

    @OneToMany(mappedBy = "typGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Typ> types = new LinkedList<>();

    @OneToMany(mappedBy = "typGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Article> articles;

    public TypGroup() {
    }

    public TypGroup(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public TypGroup(String code, String name, List<Typ> types, Set<Article> articles) {
        this.code = code;
        this.name = name;
        this.types = types;
        this.articles = articles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Typ> getTypes() {
        return types;
    }

    public void setTypes(List<Typ> types) {
        this.types = types;
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
