package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.ColorRepository;
import ch.evel.warehouse.db.dao.ColorRestRepository;
import ch.evel.warehouse.db.model.Color;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ColorRestController {

    @Autowired
    private ColorRestRepository colorRestRepository;

    @Autowired
    private ColorRepository colorRepository;

    @JsonView(DataTablesOutput.View.class)
    @RequestMapping(path = "/data/colors", method = RequestMethod.GET)
    public DataTablesOutput<Color> getColors(DataTablesInput input) {
        return colorRestRepository.findAll(input);
    }

    @RequestMapping(value = "/data/color/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") UUID uuid) {
        colorRepository.delete(uuid);
        return new ResponseEntity<Color>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/data/color/edit/", method = RequestMethod.DELETE)
    public ResponseEntity<?> edit(@RequestParam UUID uuid, @RequestParam String code, @RequestParam String name) {
        colorRepository.delete(uuid);
        return new ResponseEntity<Color>(HttpStatus.NO_CONTENT);
    }
}
