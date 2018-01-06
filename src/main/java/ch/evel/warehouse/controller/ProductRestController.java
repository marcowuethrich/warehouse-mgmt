package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.ProductRestRepository;
import ch.evel.warehouse.db.model.Product;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    @Autowired
    private ProductRestRepository productRestRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/products", method = RequestMethod.GET)
    public DataTablesOutput<Product> getProducts(DataTablesInput input) {
        return productRestRepository.findAll(input);
    }
}
