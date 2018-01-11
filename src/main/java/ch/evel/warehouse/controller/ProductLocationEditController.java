package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.LocationRepository;
import ch.evel.warehouse.db.dao.ProductRepository;
import ch.evel.warehouse.db.model.Location;
import ch.evel.warehouse.db.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @RequestMapping(value = "/change/{productId}/{newLocationName}/{editAmount}", method = RequestMethod.GET)
    public ResponseEntity change(@PathVariable String productId,
                                 @PathVariable String newLocationName, @PathVariable String editAmount) {
        int amount = Integer.parseInt(editAmount);
        try {
            Product product = productRepository.findOne(UUID.fromString(productId));
            Location newLocation = locationRepository.findByName(newLocationName);
            List<Product> productInNewLoc = newLocation.getProducts().stream()
                    .filter(prod -> prod.getArticle().getId().equals(product.getArticle().getId()))
                    .collect(Collectors.toList());


            if (product.getAmount() == amount) {
                if (productInNewLoc.isEmpty()) {
                    product.setLocation(newLocation);
                    productRepository.save(product);
                } else {
                    productInNewLoc.get(0).setAmount(productInNewLoc.get(0).getAmount() + amount);
                    productRepository.save(productInNewLoc);
                    productRepository.delete(product);
                }
            } else {
                if (productInNewLoc.isEmpty()) {
                    productRepository.save(copyProductWithAmount(product, newLocation, amount));
                    if (product.getAmount() - amount == 0) {
                        productRepository.delete(product);
                    } else {
                        product.setAmount(product.getAmount() - amount);
                        productRepository.save(product);
                    }
                } else {
                    productInNewLoc.get(0).setAmount(productInNewLoc.get(0).getAmount() + amount);
                    product.setAmount(product.getAmount() - amount);
                    productRepository.save(productInNewLoc.get(0));
                    productRepository.save(product);
                }
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/reload/{error}")
    public String getProductList(ModelMap map, @PathVariable String error) {
        if (Boolean.valueOf(error)) {
            map.addAttribute("msgSuccess", "Die Änderungen konnten erfolgreich übernommen werden");
        } else {
            map.addAttribute("msgError", "Die Änderungen konnten nicht vollständig übernommen werden!");
        }
        loadDropDowns(map);
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    private Product copyProductWithAmount(Product product, Location location, int amount) {
        return new Product(product.getSupplier(), product.getArticle(), location, amount);
    }

    private void loadDropDowns(ModelMap map) {
        map.addAttribute("locations", locationRepository.findAllByOrderByNameAsc());
    }
}
