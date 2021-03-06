package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.ColorRepository;
import ch.evel.warehouse.db.model.Article;
import ch.evel.warehouse.db.model.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/colors")
public class ColorController extends PageController {
    private final ColorRepository colorRepository;
    private static final String PAGE_TITLE = "Farben";
    private static final String PAGE_HOME = "colors";
    private static final String PAGE_EDIT = "color";
    private Color editableColor;

    @Autowired
    public ColorController(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @GetMapping("/")
    public String getColor(ModelMap map) {
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Color getColor(@PathVariable UUID uuid) {
        Color color = colorRepository.findOne(uuid);
        System.out.println(color.getArticles().size());
        return ((Article) color.getArticles().toArray()[3]).getColor();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(ModelMap map, Color color) {
        map.addAttribute("color", color);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String id) {
        editableColor = colorRepository.findOne(UUID.fromString(id));
        map.addAttribute("color", editableColor);
        return loadPage(map, PAGE_EDIT, PAGE_TITLE);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createNewColorSubmit(@Valid Color color, BindingResult bindingResult, ModelMap map) {

        if (bindingResult.hasErrors()) {
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else if (editableColor == null) {
            return createColor(map, color);
        } else {
            return editColor(map, editableColor, color);
        }
    }

    private String editColor(ModelMap map, Color oldColor, Color newColor) {
        if (!oldColor.getCode().equals(newColor.getCode()) && colorRepository.existsByCode(newColor.getCode())) {
            map.addAttribute("errorUniqueCode", "Code already exist");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else if (!oldColor.getName().equals(newColor.getName()) && colorRepository.existsByName(newColor.getName())) {
            map.addAttribute("errorUniqueName", "Name already exist");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        }
        oldColor.setCode(newColor.getCode());
        oldColor.setName(newColor.getName());
        colorRepository.save(oldColor);
        editableColor = null;

        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    private String createColor(ModelMap map, Color color) {
        if (colorRepository.existsByCode(color.getCode())) {
            map.addAttribute("errorUniqueCode", "Code must be unique");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        } else if (colorRepository.existsByName(color.getName())) {
            map.addAttribute("errorUniqueName", "Name must be unique");
            return loadPage(map, PAGE_EDIT, PAGE_TITLE);
        }
        colorRepository.save(color);
        return loadPage(map, PAGE_HOME, PAGE_TITLE);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(ModelMap map, @PathVariable("id") String uuid) {
        try {
            colorRepository.delete(UUID.fromString(uuid));
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        } catch (EmptyResultDataAccessException exception) {
            // TODO: 1/3/18 Send Msg to User
            return loadPage(map, PAGE_HOME, PAGE_TITLE);
        }
    }
}
