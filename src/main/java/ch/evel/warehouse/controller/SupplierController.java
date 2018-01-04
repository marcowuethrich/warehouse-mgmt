package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.SupplierRepository;
import ch.evel.warehouse.db.model.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/suppliers")
public class SupplierController {
    private final SupplierRepository supplierRepository;
    private static final String PAGE_TITLE = "Lieferant";
    private static final String PAGE_HOME = "suppliers";
    private static final String PAGE_EDIT = "supplier";
    private Supplier editableSupplier;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/")
    public String getSupplier(ModelMap map) {
        return loadPage(map, PAGE_HOME);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Supplier getSupplier(@PathVariable UUID uuid) {
        Supplier supplier = supplierRepository.findOne(uuid);
        return supplier;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Supplier supplier) {
        map.addAttribute("supplier", supplier);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableSupplier = supplierRepository.findOne(UUID.fromString(id));
        map.addAttribute("supplier", editableSupplier);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewSupplierSubmit(@Valid Supplier supplier, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return loadPage(map, PAGE_EDIT);
        } else if (editableSupplier == null) {
            return createSupplier(map, supplier);
        } else {
            return editSupplier(map, editableSupplier, supplier);
        }
    }

    private String editSupplier(ModelMap map, Supplier oldSupplier, Supplier newSupplier) {
        if (!oldSupplier.getCode().equals(newSupplier.getCode()) && supplierRepository.existsByCode(newSupplier.getCode())) {
            map.addAttribute("errorUniqueCode", "Code already exist");
            return loadPage(map, PAGE_EDIT);
        } else if (!oldSupplier.getName().equals(newSupplier.getName()) && supplierRepository.existsByName(newSupplier.getName())) {
            map.addAttribute("errorUniqueName", "Name already exist");
            return loadPage(map, PAGE_EDIT);
        }
        oldSupplier.setCode(newSupplier.getCode());
        oldSupplier.setName(newSupplier.getName());
        supplierRepository.save(oldSupplier);
        editableSupplier = null;

        return loadPage(map, PAGE_HOME);
    }

    private String createSupplier(ModelMap map, Supplier supplier) {
        if (supplierRepository.existsByCode(supplier.getCode())) {
            map.addAttribute("errorUniqueCode", "Code must be unique");
            return loadPage(map, PAGE_EDIT);
        } else if (supplierRepository.existsByName(supplier.getName())) {
            map.addAttribute("errorUniqueName", "Name must be unique");
            return loadPage(map, PAGE_EDIT);
        }
        supplierRepository.save(supplier);
        return loadPage(map, PAGE_HOME);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            supplierRepository.delete(UUID.fromString(uuid));
            return loadPage(map, PAGE_HOME);
        } catch (EmptyResultDataAccessException exception) {
            // TODO: 1/3/18 Send Msg to User
            return loadPage(map, PAGE_HOME);
        }
    }

    private String loadPage(ModelMap map, String page) {
        map.addAttribute("content", page);
        map.addAttribute("pageTitle", PAGE_TITLE);
        return "admin/home";
    }
}
