package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.TypRepository;
import ch.evel.warehouse.db.model.Article;
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
    private static final String PAGE_TITLE = "Typen";
    private static final String PAGE_HOME = "typs";
    private static final String PAGE_EDIT = "typ";
    private Typ editableTyp;

    @Autowired
    public TypController(TypRepository typRepository) {
        this.typRepository = typRepository;
    }

    @GetMapping("/")
    public String get(ModelMap map) {
        return loadPage(map, PAGE_HOME);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Typ getTyp(@PathVariable UUID uuid) {
        Typ typ = typRepository.findOne(uuid);
        System.out.println(typ.getArticles().size());
        return ((Article) typ.getArticles().toArray()[3]).getTyp();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Typ typ) {
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableTyp = typRepository.findOne(UUID.fromString(id));
        map.addAttribute("typ", editableTyp);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewTypSubmit(@Valid Typ typ, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return loadPage(map, PAGE_EDIT);
        } else if (editableTyp == null) {
            return createTyp(map, typ);
        } else {
            return editTyp(map, editableTyp, typ);
        }
    }

    private String editTyp(ModelMap map, Typ oldTyp, Typ newTyp) {
        if (!oldTyp.getCode().equals(newTyp.getCode()) && typRepository.existsByCode(newTyp.getCode())) {
            map.addAttribute("errorUniqueCode", "Code already exist");
            return loadPage(map, PAGE_EDIT);
        } else if (!oldTyp.getName().equals(newTyp.getName()) && typRepository.existsByName(newTyp.getName())) {
            map.addAttribute("errorUniqueName", "Name already exist");
            return loadPage(map, PAGE_EDIT);
        }
        oldTyp.setCode(newTyp.getCode());
        oldTyp.setName(newTyp.getName());
        typRepository.save(oldTyp);
        editableTyp = null;

        return loadPage(map, PAGE_HOME);
    }

    private String createTyp(ModelMap map, Typ typ) {
        if (typRepository.existsByCode(typ.getCode())) {
            map.addAttribute("errorUniqueCode", "Code must be unique");
            return loadPage(map, PAGE_EDIT);
        } else if (typRepository.existsByName(typ.getName())) {
            map.addAttribute("errorUniqueName", "Name must be unique");
            return loadPage(map, PAGE_EDIT);
        }
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
}
