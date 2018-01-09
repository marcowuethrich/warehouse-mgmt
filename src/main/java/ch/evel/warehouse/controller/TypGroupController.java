package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.TypGroupRepository;
import ch.evel.warehouse.db.model.Article;
import ch.evel.warehouse.db.model.TypGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/typgroups")
public class TypGroupController extends PageController {
    private final TypGroupRepository typgroupRepository;
    private static final String PAGE_TITLE = "Gruppen - Typ";
    private static final String PAGE_HOME = "typgroups";
    private static final String PAGE_EDIT = "typgroup";
    private TypGroup editableTypGroup;

    @Autowired
    public TypGroupController(TypGroupRepository typgroupRepository) {
        this.typgroupRepository = typgroupRepository;
    }

    @GetMapping("/")
    public String get(ModelMap map) {
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    TypGroup getTypGroup(@PathVariable UUID uuid) {
        TypGroup typgroup = typgroupRepository.findOne(uuid);
        System.out.println(typgroup.getArticles().size());
        return ((Article) typgroup.getArticles().toArray()[3]).getTypGroup();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, TypGroup typgroup) {
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableTypGroup = typgroupRepository.findOne(UUID.fromString(id));
        map.addAttribute("typGroup", editableTypGroup);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewTypGroupSubmit(@Valid TypGroup typGroup, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else if (editableTypGroup == null) {
            return createTypGroup(map, typGroup);
        } else {
            return editTypGroup(map, editableTypGroup, typGroup);
        }
    }

    private String editTypGroup(ModelMap map, TypGroup oldTypGroup, TypGroup newTypGroup) {
        if (!oldTypGroup.getCode().equals(newTypGroup.getCode()) && typgroupRepository.existsByCode(newTypGroup.getCode())) {
            map.addAttribute("errorUniqueCode", "Code already exist");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else if (!oldTypGroup.getName().equals(newTypGroup.getName()) && typgroupRepository.existsByName(newTypGroup.getName())) {
            map.addAttribute("errorUniqueName", "Name already exist");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        }
        oldTypGroup.setCode(newTypGroup.getCode());
        oldTypGroup.setName(newTypGroup.getName());
        typgroupRepository.save(oldTypGroup);
        editableTypGroup = null;

        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    private String createTypGroup(ModelMap map, TypGroup typgroup) {
        if (typgroupRepository.existsByCode(typgroup.getCode())) {
            map.addAttribute("errorUniqueCode", "Code must be unique");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else if (typgroupRepository.existsByName(typgroup.getName())) {
            map.addAttribute("errorUniqueName", "Name must be unique");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        }
        typgroupRepository.save(typgroup);
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            typgroupRepository.delete(UUID.fromString(uuid));
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        } catch (EmptyResultDataAccessException exception) {
            // TODO: 1/3/18 Send Msg to User
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        }
    }
}
