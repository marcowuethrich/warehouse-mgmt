package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.LocationRepository;
import ch.evel.warehouse.db.dao.ProductRepository;
import ch.evel.warehouse.db.dao.SupplierRepository;
import ch.evel.warehouse.db.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/products")
public class ProductController extends PageController {
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final SupplierRepository supplierRepository;

    private static final String PAGE_TITLE = "Produkte";
    private static final String PAGE_HOME = "products";
    private static final String PAGE_EDIT = "product";
    private Product editableProduct;

    @Autowired
    public ProductController(ProductRepository productRepository, LocationRepository locationRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/")
    public String get(ModelMap map) {
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Product getProduct(@PathVariable UUID uuid) {
        return productRepository.findOne(uuid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Product product) {
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableProduct = productRepository.findOne(UUID.fromString(id));
        map.addAttribute("product", editableProduct);
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewProductSubmit(@Valid Product product, BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            initDropdownList(map);

            if (product.getArticle() == null) {
                map.addAttribute("noArticleSelected", "Es wurde kein Artikel ausgewählt!!");
            }
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else if (editableProduct == null) {
            return createProduct(map, product);
        } else {
            return editProduct(map, editableProduct, product);
        }
    }

    private String editProduct(ModelMap map, Product oldProduct, Product newProduct) {
        oldProduct.setArticle(newProduct.getArticle());
        oldProduct.setSupplier(newProduct.getSupplier());
        oldProduct.setAmount(newProduct.getAmount());
        productRepository.save(oldProduct);
        editableProduct = null;

        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    private String createProduct(ModelMap map, Product product) {
        if (productRepository.existsProductByArticle(product.getArticle()) &&
                productRepository.existsProductByLocation(product.getLocation())) {
            map.addAttribute("productAlreadyExist", "Ein Produkt mit der Bezeichung \""
                    + product.getArticle().getName() + "\" und dem Lieferanten \"" + product.getSupplier().getName()
                    + "\" ist bereits in der Datenbank vorhanden!!");
            initDropdownList(map);
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        }
        productRepository.save(product);
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            productRepository.delete(UUID.fromString(uuid));
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        } catch (EmptyResultDataAccessException exception) {
            // TODO: 1/3/18 Send Msg to User
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        }
    }

    private void initDropdownList(ModelMap map) {
        map.addAttribute("suppliers", supplierRepository.findAllByOrderByNameAsc());
        map.addAttribute("locations", locationRepository.findAllByOrderByNameAsc());
    }

}
