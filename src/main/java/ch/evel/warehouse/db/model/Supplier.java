package ch.evel.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.jdo.annotations.Unique;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "db_supplier")
public class Supplier extends EntityModel {

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

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Product> products;

    public Supplier() {
    }

    public Supplier(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Supplier(String code, String name, Set<Product> products) {
        this.code = code;
        this.name = name;
        this.products = products;
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}
