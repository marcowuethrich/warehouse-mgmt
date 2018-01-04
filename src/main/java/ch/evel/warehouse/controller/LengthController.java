package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.LengthRepository;
import ch.evel.warehouse.db.model.Article;
import ch.evel.warehouse.db.model.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/lengths")
public class LengthController {
    private final LengthRepository lengthRepository;
    private static final String PAGE_TITLE = "Grössen";
    private static final String PAGE_HOME = "lengths";
    private static final String PAGE_EDIT = "length";
    private Length editableLength;

    @Autowired
    public LengthController(LengthRepository lengthRepository) {
        this.lengthRepository = lengthRepository;
    }

    @GetMapping("/")
    public String getLength(ModelMap map) {
        return loadPage(map, PAGE_HOME);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Length getLength(@PathVariable UUID uuid) {
        Length length = lengthRepository.findOne(uuid);
        System.out.println(length.getArticles().size());
        return ((Article) length.getArticles().toArray()[3]).getLength();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Length length) {
        map.addAttribute("length", length);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableLength = lengthRepository.findOne(UUID.fromString(id));
        map.addAttribute("length", editableLength);
        return loadPage(map, PAGE_EDIT);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewLengthSubmit(@Valid Length length, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return loadPage(map, PAGE_EDIT);
        } else if (editableLength == null) {
            return createLength(map, length);
        } else {
            return editLength(map, editableLength, length);
        }
    }

    private String editLength(ModelMap map, Length oldLength, Length newLength) {
        if (!oldLength.getCode().equals(newLength.getCode()) && lengthRepository.existsByCode(newLength.getCode())) {
            map.addAttribute("errorUniqueCode", "Code already exist");
            return loadPage(map, PAGE_EDIT);
        } else if ((oldLength.getSize() != newLength.getSize()) && lengthRepository.existsBySize(newLength.getSize())) {
            map.addAttribute("errorUniqueSize", "Name already exist");
            return loadPage(map, PAGE_EDIT);
        }
        oldLength.setCode(newLength.getCode());
        oldLength.setSize(newLength.getSize());
        lengthRepository.save(oldLength);
        editableLength = null;

        return loadPage(map, PAGE_HOME);
    }

    private String createLength(ModelMap map, Length length) {
        if (lengthRepository.existsByCode(length.getCode())) {
            map.addAttribute("errorUniqueCode", "Code must be unique");
            return loadPage(map, PAGE_EDIT);
        } else if (lengthRepository.existsBySize(length.getSize())) {
            map.addAttribute("errorUniqueName", "Name must be unique");
            return loadPage(map, PAGE_EDIT);
        }
        lengthRepository.save(length);
        return loadPage(map, PAGE_HOME);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            lengthRepository.delete(UUID.fromString(uuid));
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
