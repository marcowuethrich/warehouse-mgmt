package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.CategoryRepository;
import ch.evel.warehouse.db.dao.GroupRepository;
import ch.evel.warehouse.db.model.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/groups")
public class GroupController {
    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;
    private static final String PAGE_TITLE = "Gruppen";
    private static final String PAGE_HOME = "groups";
    private static final String PAGE_EDIT = "group";
    private Groups editableGroup;

    @Autowired
    public GroupController(GroupRepository groupRepository, CategoryRepository categoryRepository) {
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String get(ModelMap map) {
        return loadPage(map, PAGE_HOME);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Groups getGroup(@PathVariable UUID uuid) {
        return groupRepository.findOne(uuid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Groups groups) {
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableGroup = groupRepository.findOne(UUID.fromString(id));
        map.addAttribute("groups", editableGroup);
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewGroupSubmit(@Valid Groups group, BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            initDropdownList(map);
            return loadPage(map, PAGE_EDIT);
        } else if (editableGroup == null) {
            return createGroup(map, group);
        } else {
            return editGroup(map, editableGroup, group);
        }
    }

    private String editGroup(ModelMap map, Groups oldGroup, Groups newGroup) {
        oldGroup.setCode(newGroup.getCode());
        oldGroup.setName(newGroup.getName());
        oldGroup.setCategory(newGroup.getCategory());
        groupRepository.save(oldGroup);
        editableGroup = null;

        return loadPage(map, PAGE_HOME);
    }

    private String createGroup(ModelMap map, Groups group) {
        groupRepository.save(group);
        return loadPage(map, PAGE_HOME);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            groupRepository.delete(UUID.fromString(uuid));
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
    }
}
