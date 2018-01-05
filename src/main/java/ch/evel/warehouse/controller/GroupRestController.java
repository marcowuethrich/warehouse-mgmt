package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.GroupRestRepository;
import ch.evel.warehouse.db.model.Groups;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupRestController {

    @Autowired
    private GroupRestRepository groupRestRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/groups", method = RequestMethod.GET)
    public DataTablesOutput<Groups> getGroups(DataTablesInput input) {
        return groupRestRepository.findAll(input);
    }
}
