package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.*;
import ch.evel.warehouse.db.model.Groups;
import ch.evel.warehouse.db.model.Product;
import ch.evel.warehouse.db.model.Typ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final GroupRepository groupRepository;
    private final TypGroupRepository typGroupRepository;
    private final TypRepository typRepository;
    private final LengthRepository lengthRepository;
    private final ColorRepository colorRepository;
    private static final String PAGE_TITLE = "Produkte";
    private static final String PAGE_HOME = "products";
    private static final String PAGE_EDIT = "product";
    private Product editableProduct;

    @Autowired
    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository,
                             GroupRepository groupRepository, TypGroupRepository typGroupRepository,
                             TypRepository typRepository, LengthRepository lengthRepository, ColorRepository colorRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.groupRepository = groupRepository;
        this.typGroupRepository = typGroupRepository;
        this.typRepository = typRepository;
        this.lengthRepository = lengthRepository;
        this.colorRepository = colorRepository;
    }

    @GetMapping("/")
    public String get(ModelMap map) {
        return loadPage(map, PAGE_HOME);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Product getProduct(@PathVariable UUID uuid) {
        return productRepository.findOne(uuid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Product product) {
        //initDropdownList(map);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableProduct = productRepository.findOne(UUID.fromString(id));
        map.addAttribute("product", editableProduct);
        //initDropdownList(map);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewProductSubmit(@Valid Product product, BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            // initDropdownList(map);
            return loadPage(map, PAGE_EDIT);
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

        return loadPage(map, PAGE_HOME);
    }

    private String createProduct(ModelMap map, Product product) {
//        if (product.getCode() == null) {
//            product.setCode(product.generateCode());
//        }
//        if (product.getGroup().getCategory().getId() != product.getCategory().getId()) {
//            map.addAttribute("errorFalseGroup", "Die \"Gruppe\" ist nicht in der \"Kategorie\" vorhanden!!");
//            initDropdownList(map);
//            return loadPage(map, PAGE_EDIT);
//        } else if (product.getTyp().getTypGroup().getId() != product.getTypGroup().getId()) {
//            map.addAttribute("errorFalseTyp", "Der \"Typ\" ist nicht in der \"Typ Gruppe\" vorhanden!!");
//            initDropdownList(map);
//            return loadPage(map, PAGE_EDIT);
//        }
        productRepository.save(product);
        return loadPage(map, PAGE_HOME);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            productRepository.delete(UUID.fromString(uuid));
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

    private void initDropdownList(ModelMap map) {
        map.addAttribute("categories", categoryRepository.findAllByOrderByCodeAsc());
        map.addAttribute("typGroups", typGroupRepository.findAllByOrderByCodeAsc());
        map.addAttribute("groups", getSortedGroups());
        map.addAttribute("typs", getSortedTyps());
        map.addAttribute("lengths", lengthRepository.findAllByOrderByCodeAsc());
        map.addAttribute("colors", colorRepository.findAllByOrderByCodeAsc());
    }

    private List<Groups> getSortedGroups() {
        List<Groups> groups = new ArrayList<>();
        groupRepository.findAll().forEach(groups::add);
        groups.sort(Comparator.comparing(groups1 -> groups1.getCategory().getCode()));
        return groups;
    }

    private List<Typ> getSortedTyps() {
        List<Typ> typs = new ArrayList<>();
        typRepository.findAll().forEach(typs::add);
        typs.sort(Comparator.comparing(typ -> typ.getTypGroup().getCode()));
        return typs;
    }
}
