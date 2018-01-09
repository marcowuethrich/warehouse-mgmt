package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.*;
import ch.evel.warehouse.db.model.Article;
import ch.evel.warehouse.db.model.Groups;
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
@RequestMapping(path = "/admin/articles")
public class ArticleController extends PageController {
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
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Article getArticle(@PathVariable UUID uuid) {
        return articleRepository.findOne(uuid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Article article) {
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableArticle = articleRepository.findOne(UUID.fromString(id));
        map.addAttribute("article", editableArticle);
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewArticleSubmit(@Valid Article article, BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            initDropdownList(map);
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
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

        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    private String createArticle(ModelMap map, Article article) {
        if (article.getCode() == null) {
            article.setCode(article.generateCode());
        }
        if (article.getGroup().getCategory().getId() != article.getCategory().getId()) {
            map.addAttribute("errorFalseGroup", "Die \"Gruppe\" ist nicht in der \"Kategorie\" vorhanden!!");
            initDropdownList(map);
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else if (article.getTyp().getTypGroup().getId() != article.getTypGroup().getId()) {
            map.addAttribute("errorFalseTyp", "Der \"Typ\" ist nicht in der \"Typ Gruppe\" vorhanden!!");
            initDropdownList(map);
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        }
        articleRepository.save(article);
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            articleRepository.delete(UUID.fromString(uuid));
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        } catch (EmptyResultDataAccessException exception) {
            // TODO: 1/3/18 Send Msg to User
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        }
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
