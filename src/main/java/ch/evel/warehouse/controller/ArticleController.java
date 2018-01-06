package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.*;
import ch.evel.warehouse.db.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/articles")
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final GroupRepository groupRepository;
    private final TypGroupRepository typGroupRepository;
    private final TypRepository typRepository;
    private final LengthRepository lengthRepository;
    private final ColorRepository colorRepository;
    private static final String PAGE_TITLE = "Artikel";
    private static final String PAGE_HOME = "articles";
    private static final String PAGE_EDIT = "article";
    private Article editableArticle;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, CategoryRepository categoryRepository,
                             GroupRepository groupRepository, TypGroupRepository typGroupRepository,
                             TypRepository typRepository, LengthRepository lengthRepository, ColorRepository colorRepository) {
        this.articleRepository = articleRepository;
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
    Article getArticle(@PathVariable UUID uuid) {
        return articleRepository.findOne(uuid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Article article) {
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableArticle = articleRepository.findOne(UUID.fromString(id));
        map.addAttribute("articles", editableArticle);
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewArticleSubmit(@Valid Article article, BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            initDropdownList(map);
            return loadPage(map, PAGE_EDIT);
        } else if (editableArticle == null) {
            return createArticle(map, article);
        } else {
            return editArticle(map, editableArticle, article);
        }
    }

    private String editArticle(ModelMap map, Article oldArticle, Article newArticle) {
        oldArticle.setCode(newArticle.getCode());
        oldArticle.setName(newArticle.getName());
        oldArticle.setGroup(newArticle.getGroup());
        oldArticle.setTypGroup(newArticle.getTypGroup());
        oldArticle.setTyp(newArticle.getTyp());
        oldArticle.setLength(newArticle.getLength());
        articleRepository.save(oldArticle);
        editableArticle = null;

        return loadPage(map, PAGE_HOME);
    }

    private String createArticle(ModelMap map, Article article) {
        if (article.getCode() == null) {
            article.setCode(article.generateCode());
        }
        if (!article.getCategory().getCode().equals(article.getGroup().getCode())) {
            map.addAttribute("errorFalseGroup", "Die \"Gruppe\" ist nicht in der \"Kategorie\" vorhanden!!");
            initDropdownList(map);
            return loadPage(map, PAGE_EDIT);
        } else if (!article.getTypGroup().getCode().equals(article.getTyp().getCode())) {
            map.addAttribute("errorFalseTyp", "Der \"Typ\" ist nicht in der \"Typ Gruppe\" vorhanden!!");
            initDropdownList(map);
            return loadPage(map, PAGE_EDIT);
        }
        articleRepository.save(article);
        return loadPage(map, PAGE_HOME);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            articleRepository.delete(UUID.fromString(uuid));
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
        map.addAttribute("groups", groupRepository.findAllByOrderByCodeAsc());
        map.addAttribute("typGroups", typGroupRepository.findAllByOrderByCodeAsc());
        map.addAttribute("typs", typRepository.findAllByOrderByCodeAsc());
        map.addAttribute("lengths", lengthRepository.findAllByOrderByCodeAsc());
        map.addAttribute("colors", colorRepository.findAllByOrderByCodeAsc());
    }
}
