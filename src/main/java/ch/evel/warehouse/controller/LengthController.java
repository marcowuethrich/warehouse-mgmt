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
    private Length editableLength;

    @Autowired
    public LengthController(LengthRepository lengthRepository) {
        this.lengthRepository = lengthRepository;
    }

    @GetMapping("/")
    public String getLength(ModelMap map) {
        return loadPage(map, "lengths");
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
        return loadPage(map, "length");
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableLength = lengthRepository.findOne(UUID.fromString(id));
        map.addAttribute("length", editableLength);
        return loadPage(map, "length");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewLengthSubmit(@Valid Length length, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return loadPage(map, "length");
        } else if (editableLength == null) {
            return createLength(map, length);
        } else {
            return editLength(map, editableLength, length);
        }
    }

    private String editLength(ModelMap map, Length oldLength, Length newLength) {
        if (!oldLength.getCode().equals(newLength.getCode()) && lengthRepository.existsByCode(newLength.getCode())) {
            map.addAttribute("errorUniqueCode", "Code already exist");
            return loadPage(map, "length");
        } else if ((oldLength.getSize() != newLength.getSize()) && lengthRepository.existsBySize(newLength.getSize())) {
            map.addAttribute("errorUniqueSize", "Name already exist");
            return loadPage(map, "length");
        }
        oldLength.setCode(newLength.getCode());
        oldLength.setSize(newLength.getSize());
        lengthRepository.save(oldLength);
        editableLength = null;

        return loadPage(map, "lengths");
    }

    private String createLength(ModelMap map, Length length) {
        if (lengthRepository.existsByCode(length.getCode())) {
            map.addAttribute("errorUniqueCode", "Code must be unique");
            return loadPage(map, "length");
        } else if (lengthRepository.existsBySize(length.getSize())) {
            map.addAttribute("errorUniqueName", "Name must be unique");
            return loadPage(map, "length");
        }
        lengthRepository.save(length);
        return loadPage(map, "lengths");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            lengthRepository.delete(UUID.fromString(uuid));
            return loadPage(map, "lengths");
        } catch (EmptyResultDataAccessException exception) {
            // TODO: 1/3/18 Send Msg to User
            return loadPage(map, "lengths");
        }
    }

    private String loadPage(ModelMap map, String page) {
        map.addAttribute("content", page);
        map.addAttribute("pageTitle", PAGE_TITLE);
        return "admin/home";
    }
}
