package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.TypGroupRepository;
import ch.evel.warehouse.db.dao.TypRepository;
import ch.evel.warehouse.db.model.Typ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/typs")
public class TypController {
    private final TypRepository typRepository;
    private final TypGroupRepository typGroupRepository;
    private static final String PAGE_TITLE = "Typen";
    private static final String PAGE_HOME = "typs";
    private static final String PAGE_EDIT = "typ";
    private Typ editableTyp;

    @Autowired
    public TypController(TypRepository typRepository, TypGroupRepository typGroupRepository) {
        this.typRepository = typRepository;
        this.typGroupRepository = typGroupRepository;
    }

    @GetMapping("/")
    public String get(ModelMap map) {
        return loadPage(map, PAGE_HOME);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Typ getTyp(@PathVariable UUID uuid) {
        return typRepository.findOne(uuid);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Typ typ) {
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableTyp = typRepository.findOne(UUID.fromString(id));
        map.addAttribute("typ", editableTyp);
        initDropdownList(map);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewTypSubmit(@Valid Typ typ, BindingResult bindingResult, ModelMap map) {
        if (bindingResult.hasErrors()) {
            initDropdownList(map);
            return loadPage(map, PAGE_EDIT);
        } else if (editableTyp == null) {
            return createTyp(map, typ);
        } else {
            return editTyp(map, editableTyp, typ);
        }
    }

    private String editTyp(ModelMap map, Typ oldTyp, Typ newTyp) {
        oldTyp.setCode(newTyp.getCode());
        oldTyp.setName(newTyp.getName());
        oldTyp.setTypGroup(newTyp.getTypGroup());
        typRepository.save(oldTyp);
        editableTyp = null;

        return loadPage(map, PAGE_HOME);
    }

    private String createTyp(ModelMap map, Typ typ) {
        typRepository.save(typ);
        return loadPage(map, PAGE_HOME);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            typRepository.delete(UUID.fromString(uuid));
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
        map.addAttribute("typGroups", typGroupRepository.findAllByOrderByCodeAsc());
    }
}
