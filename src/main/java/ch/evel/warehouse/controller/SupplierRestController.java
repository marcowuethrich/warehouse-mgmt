package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.SupplierRestRepository;
import ch.evel.warehouse.db.model.Supplier;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupplierRestController {

    @Autowired
    private SupplierRestRepository supplierRestRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/suppliers", method = RequestMethod.GET)
    public DataTablesOutput<Supplier> getColors(DataTablesInput input) {
        return supplierRestRepository.findAll(input);
    }

}
