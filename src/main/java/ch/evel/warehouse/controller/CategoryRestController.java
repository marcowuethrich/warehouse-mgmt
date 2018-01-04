package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.CategoryRepository;
import ch.evel.warehouse.db.dao.CategoryRestRepository;
import ch.evel.warehouse.db.model.Category;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController {

    @Autowired
    private CategoryRestRepository categoryRestRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/categories", method = RequestMethod.GET)
    public DataTablesOutput<Category> getCategories(DataTablesInput input) {
        return categoryRestRepository.findAll(input);
    }

}
