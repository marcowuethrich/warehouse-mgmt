package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.TypRestRepository;
import ch.evel.warehouse.db.model.Typ;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypRestController {

    @Autowired
    private TypRestRepository typRestRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/typs", method = RequestMethod.GET)
    public DataTablesOutput<Typ> getTyps(DataTablesInput input) {
        return typRestRepository.findAll(input);
    }
}
