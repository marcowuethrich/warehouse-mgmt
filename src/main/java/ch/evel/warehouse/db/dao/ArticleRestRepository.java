package ch.evel.warehouse.db.dao;

import ch.evel.warehouse.db.model.Article;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.UUID;

public interface ArticleRestRepository extends DataTablesRepository<Article, UUID> {
}
