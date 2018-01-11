package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.TypGroupRestRepository;
import ch.evel.warehouse.db.model.TypGroup;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypGroupRestController {

    @Autowired
    private TypGroupRestRepository typgroupRestRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/typgroups", method = RequestMethod.GET)
    public DataTablesOutput<TypGroup> getTypeGroups(DataTablesInput input) {
        return typgroupRestRepository.findAll(input);
    }
}
