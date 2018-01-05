package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.ArticleRestRepository;
import ch.evel.warehouse.db.model.Article;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleRestController {

    @Autowired
    private ArticleRestRepository articleRestRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/articles", method = RequestMethod.GET)
    public DataTablesOutput<Article> getArticles(DataTablesInput input) {
        return articleRestRepository.findAll(input);
    }
}
