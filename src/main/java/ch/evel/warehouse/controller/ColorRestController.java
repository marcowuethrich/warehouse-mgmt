package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.ColorRestRepository;
import ch.evel.warehouse.db.model.Color;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ColorRestController {

    @Autowired
    private ColorRestRepository colorRestRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/colors", method = RequestMethod.GET)
    public DataTablesOutput<Color> getColors(DataTablesInput input) {
        return colorRestRepository.findAll(input);
    }
}
