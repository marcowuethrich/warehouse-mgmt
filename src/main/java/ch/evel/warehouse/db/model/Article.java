package ch.evel.warehouse.db.model;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "db_article")
public class Article extends EntityModel {

    @Column(length = 40)
    private String code;

    @Column(length = 40)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "group_id")
    private Groups group;

    @ManyToOne()
    @JoinColumn(name = "typGroup_id")
    private TypGroup typGroup;

    @ManyToOne()
    @JoinColumn(name = "typ_id")
    private Typ typ;

    @ManyToOne()
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne()
    @JoinColumn(name = "length_id")
    private Length length;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products;

    @Column
    private BigDecimal newPriceAmount;

    @Column
    private String newPriceCurrency = CurrencyUnit.CHF.toString();

    @Column
    private BigDecimal rentPriceAmount;

    @Column
    private String rentPriceCurrency = CurrencyUnit.CHF.toString();

    private transient Money newPrice;
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

    String generateCode() {
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

    public void generateAndSetCode() {
        code = generateCode();
    }

    public Money getNewPrice() {
        if (newPrice == null) {
            newPrice = Money.of(CurrencyUnit.of(newPriceCurrency), newPriceAmount);
        }
        return newPrice;
    }

    public void setNewPrice(Money newPrice) {
        this.newPrice = newPrice;
    }

    public Money getRentPrice() {
        if (rentPrice == null) {
            rentPrice = Money.of(CurrencyUnit.of(rentPriceCurrency), rentPriceAmount);
        }
        return rentPrice;
    }

    public void setRentPrice(Money rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Money multipleRentPrice(int amount) {
        return rentPrice.multipliedBy(amount);
    }

    public String getColorCode(String size) {
        switch (size) {
            case "1.0":
            case "2.0":
                return "Violett";
            case "3.0":
            case "4.0":
                return "Braun";
            case "5.0":
                return "Grün";
            case "10.0":
                return "Rot";
            case "15.0":
                return "Grau";
            case "20.0":
                return "Blau";
            case "25.0":
                return "Gelb";
            case "30.0":
                return "Blau & Rot";
            case "35.0":
                return "Blau & Grau";
            case "40.0":
                return "Blau & Blau";
            case "45.0":
                return "Blau & Gelb";
            case "50.0":
                return "Weiss";
            case "60.0":
                return "Orange";
            case "70.0":
                return "Weiss & Blau";
            case "80.0":
                return "Orange & Blau";
            case "90.0":
                return "Orange & Blau & Rot";
            case "100.0":
                return "Rot & Rot";
        }
        return "-";
    }

    public String getIdByString() {
        return id.toString();
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