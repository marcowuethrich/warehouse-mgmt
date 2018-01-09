package ch.evel.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;
import java.util.UUID;

public class ProductInLocation {

    private static final CurrencyUnit CURRENCY_UNIT = CurrencyUnit.CHF;

    @JsonView(DataTablesOutput.View.class)
    private UUID id;
    @JsonManagedReference
    private Location location;
    @JsonBackReference
    private List<Product> products;
    @JsonView(DataTablesOutput.View.class)
    private int amountDifferentArticle;
    @JsonView(DataTablesOutput.View.class)
    private int amountProduct;
    @JsonView(DataTablesOutput.View.class)
    private String fullRentPrice;
    @JsonView(DataTablesOutput.View.class)
    private String fullNewPrice;

    @JsonIgnore
    private Money rentPrice = Money.zero(CURRENCY_UNIT);
    @JsonIgnore
    private Money newPrice = Money.zero(CURRENCY_UNIT);


    public ProductInLocation() {
    }

    public ProductInLocation(UUID id, Location location, List<Product> products) {
        this.id = id;
        this.location = location;
        this.products = products;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getAmountDifferentArticle() {
        return this.amountDifferentArticle = products.size();
    }

    public int getAmountProduct() {
        return amountProduct = products.stream().mapToInt(Product::getAmount).sum();
    }

    public String getFullRentPrice() {
        return getRentPrice().toString();
    }

    public String getFullNewPrice() {
        return getNewPrice().toString();
    }

    private Money getRentPrice() {
        products.forEach(product -> {
            Money temp = Money.of(CURRENCY_UNIT, product.getArticle().getRentPriceAmount()).multipliedBy(product.getAmount());
            rentPrice = rentPrice.plus(temp);
        });
        return rentPrice;
    }

    private Money getNewPrice() {
        products.forEach(product -> {
            Money temp = Money.of(CURRENCY_UNIT, product.getArticle().getNewPriceAmount()).multipliedBy(product.getAmount());
            newPrice = newPrice.plus(temp);
        });
        return newPrice;
    }
}
