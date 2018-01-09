package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.LocationRepository;
import ch.evel.warehouse.db.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/productInLocations")
public class ProductInLocationController extends PageController {
    private final LocationRepository locationRepository;
    private static final String PAGE_TITLE = "Materialverteilung Übersicht";
    private static final String PAGE_HOME = "productInLocations";
    private static final String PAGE_EDIT = "productInLocation";
    private Location editableLocation;

    @Autowired
    public ProductInLocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping("/")
    public String getLocation(ModelMap map) {
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Location getLocation(@PathVariable UUID uuid) {
        return locationRepository.findOne(uuid);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableLocation = locationRepository.findOne(UUID.fromString(id));
        map.addAttribute("location", editableLocation);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editProductInLocationSubmit(@Valid Location location, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else {
            return editLocation(map, editableLocation, location);
        }
    }

    private String editLocation(ModelMap map, Location oldLocation, Location newLocation) {
//        if (!oldLocation.getName().equals(newLocation.getName()) && locationRepository.existsByName(newLocation.getName())) {
//            map.addAttribute("errorUniqueName", "Name already exist");
//            return loadPage(map, PAGE_EDIT);
//        }
//        oldLocation.setName(newLocation.getName()
        locationRepository.save(oldLocation);
        editableLocation = null;

        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }
}
