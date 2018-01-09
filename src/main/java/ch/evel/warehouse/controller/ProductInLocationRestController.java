package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.LocationRestRepository;
import ch.evel.warehouse.db.dao.ProductRestRepository;
import ch.evel.warehouse.db.model.Location;
import ch.evel.warehouse.db.model.ProductInLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductInLocationRestController {

    @Autowired
    private LocationRestRepository locationRestRepository;
    @Autowired
    private ProductRestRepository productRestRepository;

    @RequestMapping(path = "/data/productInLocations", method = RequestMethod.GET)
    public Iterable<ProductInLocation> getLocationsInProduct() {
        Iterable<Location> locations = locationRestRepository.findAll();

        List<ProductInLocation> productInLocations = new ArrayList<>();

        locations.forEach(location -> productInLocations.add(new ProductInLocation(
                location.getId(),
                location,
                productRestRepository.findAllByLocation(location))));

        return productInLocations;
    }
}
