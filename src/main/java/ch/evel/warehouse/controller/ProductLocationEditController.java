package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.LocationRepository;
import ch.evel.warehouse.db.dao.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/productToLocationEdit")
public class ProductLocationEditController extends PageController {
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private static final String PAGE_TITLE = "Materialverteilung Bearbeitungsmodus";
    private static final String PAGE_HOME = "productLocationEdit";

    @Autowired
    public ProductLocationEditController(ProductRepository productRepository, LocationRepository locationRepository) {
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/")
    public String getProductList(ModelMap map) {
        loadDropDowns(map);
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        loadDropDowns(map);
        map.addAttribute("selectedLocationID", UUID.fromString(id));
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
//    public String edit(ModelMap map, @PathVariable String id) {
//        editableLocation = locationRepository.findOne(UUID.fromString(id));
//        map.addAttribute("location", editableLocation);
//        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
//    }
//
//    @RequestMapping(value = "/edit", method = RequestMethod.POST)
//    public String editProductInLocationSubmit(@Valid Location location, BindingResult bindingResult, ModelMap map) {
//
//        if (bindingResult.hasErrors()) {
//            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
//        } else {
//            return editLocation(map, editableLocation, location);
//        }
//    }
//
//    private String editLocation(ModelMap map, Location oldLocation, Location newLocation) {
//
//        locationRepository.save(oldLocation);
//        editableLocation = null;
//
//        return loadPage(map, PAGE_HOME, PAGE_TITLE);
//    }

    private void loadDropDowns(ModelMap map) {
        map.addAttribute("locations", locationRepository.findAllByOrderByNameAsc());
    }
}
