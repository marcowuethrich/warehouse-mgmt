package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Article;
import ch.evel.warehouse.db.model.Location;
import ch.evel.warehouse.db.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
    boolean existsProductByArticle(Article article);
    boolean existsProductByLocation(Location location);

    Product findProductByLocationAndArticle(Location location, Article article);
}
