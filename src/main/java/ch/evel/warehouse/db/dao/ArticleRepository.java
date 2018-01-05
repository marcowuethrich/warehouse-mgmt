package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArticleRepository extends CrudRepository<Article, UUID> {
    boolean existsByName(String name);

    boolean existsByCode(String code);
}
