package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.CategoryRepository;
import ch.evel.warehouse.db.model.Article;
import ch.evel.warehouse.db.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private static final String PAGE_TITLE = "Kategorien";
    private static final String PAGE_HOME = "categories";
    private static final String PAGE_EDIT = "category";
    private Category editableCategory;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String getCategory(ModelMap map) {
        return loadPage(map, PAGE_HOME);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Category getCategory(@PathVariable UUID uuid) {
        Category category = categoryRepository.findOne(uuid);
        System.out.println(category.getArticles().size());
        return ((Article) category.getArticles().toArray()[3]).getCategory();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Category category) {
        map.addAttribute("category", category);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableCategory = categoryRepository.findOne(UUID.fromString(id));
        map.addAttribute("category", editableCategory);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewCategorySubmit(@Valid Category category, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return loadPage(map, PAGE_EDIT);
        } else if (editableCategory == null) {
            return createCategory(map, category);
        } else {
            return editCategory(map, editableCategory, category);
        }
    }

    private String editCategory(ModelMap map, Category oldCategory, Category newCategory) {
        if (!oldCategory.getCode().equals(newCategory.getCode()) && categoryRepository.existsByCode(newCategory.getCode())) {
            map.addAttribute("errorUniqueCode", "Code already exist");
            return loadPage(map, PAGE_EDIT);
        } else if (!oldCategory.getName().equals(newCategory.getName()) && categoryRepository.existsByName(newCategory.getName())) {
            map.addAttribute("errorUniqueName", "Name already exist");
            return loadPage(map, PAGE_EDIT);
        }
        oldCategory.setCode(newCategory.getCode());
        oldCategory.setName(newCategory.getName());
        categoryRepository.save(oldCategory);
        editableCategory = null;

        return loadPage(map, PAGE_HOME);
    }

    private String createCategory(ModelMap map, Category category) {
        if (categoryRepository.existsByCode(category.getCode())) {
            map.addAttribute("errorUniqueCode", "Code must be unique");
            return loadPage(map, PAGE_EDIT);
        } else if (categoryRepository.existsByName(category.getName())) {
            map.addAttribute("errorUniqueName", "Name must be unique");
            return loadPage(map, PAGE_EDIT);
        }
        categoryRepository.save(category);
        return loadPage(map, PAGE_HOME);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            categoryRepository.delete(UUID.fromString(uuid));
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
