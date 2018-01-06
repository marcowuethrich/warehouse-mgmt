package ch.evel.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "db_product")
public class Product extends EntityModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(length = 40, nullable = false, unique = true)
    @JsonView(DataTablesOutput.View.class)
    private int amount;

    public Product() {
    }

    public Product(Article article, Supplier supplier, int amount) {
        this.article = article;
        this.supplier = supplier;
        this.amount = amount;
    }

    public Product(Supplier supplier, Article article, Location locations, int amount) {
        this.supplier = supplier;
        this.article = article;
        this.location = locations;
        this.amount = amount;
    }

    public Product(Product product, int amount) {
        this.supplier = product.supplier;
        this.article = product.article;
        this.location = product.location;
        this.amount = amount;
    }

    public String getCode() {
        return this.supplier.getCode() + "-" + this.article.getCode();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location locations) {
        this.location = locations;
    }
}
