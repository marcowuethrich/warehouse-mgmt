package ch.evel.warehouse.db.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import javax.jdo.annotations.Unique;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "db_article")
public class Article extends EntityModel {

    @Column(length = 20, unique = true)
    @JsonView(DataTablesOutput.View.class)
    @Unique
    private String code;

    @Column(length = 40, nullable = false, unique = true)
    @JsonView(DataTablesOutput.View.class)
    @Size(min = 2, max = 40)
    @Unique
    @NotNull(message = "Can't be Null")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
    @JoinColumn(name = "group_id")
    private Groups group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
    @JoinColumn(name = "typGroup_id")
    private TypGroup typGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
    @JoinColumn(name = "typ_id")
    private Typ typ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(DataTablesOutput.View.class)
    @NotNull(message = "Can't be Null")
    @JoinColumn(name = "length_id")
    private Length length;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Product> products;

    @Column(nullable = false)
    @NotNull(message = "Can't be Null")
    private BigDecimal newPriceAmount = new BigDecimal(0);

    @Column(length = 20, nullable = false, unique = true)
    @NotNull(message = "Can't be Null")
    private String newPriceCurrency = CurrencyUnit.CHF.toString();

    @Column(nullable = false)
    @NotNull(message = "Can't be Null")
    private BigDecimal rentPriceAmount = new BigDecimal(0);

    @Column(length = 20, nullable = false, unique = true)
    @NotNull(message = "Can't be Null")
    private String rentPriceCurrency = CurrencyUnit.CHF.toString();

    @JsonView(DataTablesOutput.View.class)
    private transient Money newPrice;

    @JsonView(DataTablesOutput.View.class)
    private transient Money rentPrice;

    public Article() {
    }

    public Article(String name, BigDecimal newPriceAmount, BigDecimal rentPriceAmount,
                   Category category, Groups group, TypGroup typGroup, Typ typ, Color color, Length length) {
        this.name = name;
        this.newPriceAmount = newPriceAmount;
        this.rentPriceAmount = rentPriceAmount;
        this.category = category;
        this.group = group;
        this.typGroup = typGroup;
        this.typ = typ;
        this.color = color;
        this.length = length;
        this.code = generateCode();
    }

    public Article(String code, String name, Category category, Groups group, TypGroup typGroup, Typ typ, Color color,
                   Length length, Set<Product> products, BigDecimal newPriceAmount,
                   BigDecimal rentPriceAmount) {
        this.name = name;
        this.code = code;
        this.category = category;
        this.group = group;
        this.typGroup = typGroup;
        this.typ = typ;
        this.color = color;
        this.length = length;
        this.products = products;
        this.newPriceAmount = newPriceAmount;
        this.newPriceCurrency = CurrencyUnit.CHF.toString();
        this.rentPriceAmount = rentPriceAmount;
        this.rentPriceCurrency = CurrencyUnit.CHF.toString();
    }

    public String generateCode() {
        String category = this.category == null ? "_" : this.category.getCode();
        String group = this.group == null ? "-" : this.group.getCode();
        String typGroup = this.typGroup == null ? "-" : this.typGroup.getCode();
        String typ = this.typ == null ? "-" : this.typ.getCode();

        return category + "-" + group + "-" + typGroup + "" + typ + "-" +
            color.getCode() + "-" + length.getCode();
    }

    public String generateName() {
        String group = this.group != null ? this.group.getName() : "";
        String type = typ != null ? typ.getName() : "";
        String col = color != null ? color.getCode() : "";
        String size = length == null ? "" : length.getSize() == 0.0 ? "" : length.getSize() + " m";
        String customName = this.name != null ? this.name.isEmpty() ? "" : " - " + this.name : "";
        return group + " " + type + " " + col + " " + size + customName;
    }


    public String getNewPrice() {
        if (newPrice == null) {
            newPrice = Money.of(CurrencyUnit.of(newPriceCurrency), newPriceAmount);
        }
        return newPrice.toString();
    }

    public void setNewPrice(Money newPrice) {
        this.newPrice = newPrice;
    }

    public String getRentPrice() {
        if (rentPrice == null) {
            rentPrice = Money.of(CurrencyUnit.of(rentPriceCurrency), rentPriceAmount);
        }
        return rentPrice.toString();
    }

    public void setRentPrice(Money rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Money multipleRentPrice(int amount) {
        return rentPrice.multipliedBy(amount);
    }

    public String getIdByString() {
        return id.toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = generateCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    public TypGroup getTypGroup() {
        return typGroup;
    }

    public void setTypGroup(TypGroup typGroup) {
        this.typGroup = typGroup;
    }

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }

    public BigDecimal getNewPriceAmount() {
        return newPriceAmount;
    }

    public void setNewPriceAmount(BigDecimal newPriceAmount) {
        this.newPriceAmount = newPriceAmount;
    }

    public String getNewPriceCurrency() {
        return newPriceCurrency;
    }

    public void setNewPriceCurrency(String newPriceCurrency) {
        this.newPriceCurrency = newPriceCurrency;
    }

    public BigDecimal getRentPriceAmount() {
        return rentPriceAmount;
    }

    public void setRentPriceAmount(BigDecimal rentPriceAmount) {
        this.rentPriceAmount = rentPriceAmount;
    }

    public String getRentPriceCurrency() {
        return rentPriceCurrency;
    }

    public void setRentPriceCurrency(String rentPriceCurrency) {
        this.rentPriceCurrency = rentPriceCurrency;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}