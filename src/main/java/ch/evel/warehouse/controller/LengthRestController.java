package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.LengthRestRepository;
import ch.evel.warehouse.db.model.Length;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LengthRestController {

    @Autowired
    private LengthRestRepository lengthRestRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/lengths", method = RequestMethod.GET)
    public DataTablesOutput<Length> getLengths(DataTablesInput input) {
        return lengthRestRepository.findAll(input);
    }
}
