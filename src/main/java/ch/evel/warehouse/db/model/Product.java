package ch.evel.warehouse.db.model;

import javax.persistence.*;

@Entity
@Table(name = "db_product")
public class Product extends EntityModel {

    @ManyToOne()
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne()
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne()
    @JoinColumn(name = "location_id")
    private Location location;

    @Column()
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
        return this.supplier.getCode() + "-" + this.article.generateCode();
    }

    int getAmount() {
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
