package ch.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "db_location")
public class Location extends EntityModel {

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Product> products = new HashSet<>(0);

    @Column(unique = true)
    private String name;

    public Location() {
    }

    public Location(String name) {
        this.name = name;
    }

    public Location(Set<Product> products, String name) {
        this.products = products;
        this.name = name;
    }

    public int getFullAmount() {
        return products.stream().mapToInt(Product::getAmount).sum();
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
