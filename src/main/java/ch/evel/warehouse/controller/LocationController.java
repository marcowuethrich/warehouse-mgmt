package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.LocationRepository;
import ch.evel.warehouse.db.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/locations")
public class LocationController extends PageController {
    private final LocationRepository locationRepository;
    private static final String PAGE_TITLE = "Standorte";
    private static final String PAGE_HOME = "locations";
    private static final String PAGE_EDIT = "location";
    private Location editableLocation;

    @Autowired
    public LocationController(LocationRepository locationRepository) {
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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Location location) {
        map.addAttribute("location", location);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableLocation = locationRepository.findOne(UUID.fromString(id));
        map.addAttribute("location", editableLocation);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewLocationSubmit(@Valid Location location, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else if (editableLocation == null) {
            return createLocation(map, location);
        } else {
            return editLocation(map, editableLocation, location);
        }
    }

    private String editLocation(ModelMap map, Location oldLocation, Location newLocation) {
        if (!oldLocation.getName().equals(newLocation.getName()) && locationRepository.existsByName(newLocation.getName())) {
            map.addAttribute("errorUniqueName", "Name already exist");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        }
        oldLocation.setName(newLocation.getName());
        locationRepository.save(oldLocation);
        editableLocation = null;

        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    private String createLocation(ModelMap map, Location location) {
        if (locationRepository.existsByName(location.getName())) {
            map.addAttribute("errorUniqueName", "Name must be unique");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        }
        locationRepository.save(location);
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            locationRepository.delete(UUID.fromString(uuid));
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        } catch (EmptyResultDataAccessException exception) {
            // TODO: 1/3/18 Send Msg to User
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        }
    }
}
