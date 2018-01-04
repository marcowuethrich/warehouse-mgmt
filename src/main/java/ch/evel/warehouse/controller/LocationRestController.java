package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.LocationRestRepository;
import ch.evel.warehouse.db.model.Location;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationRestController {

    @Autowired
    private LocationRestRepository locationRestRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/locations", method = RequestMethod.GET)
    public DataTablesOutput<Location> getLocations(DataTablesInput input) {
        return locationRestRepository.findAll(input);
    }
}
