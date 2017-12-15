package ch.evel.warehouse.controller;

import ch.evel.warehouse.db.dao.ColorRepository;
import ch.evel.warehouse.db.dao.UserRepository;
import ch.evel.warehouse.db.dao.UserRolesRepository;
import ch.evel.warehouse.db.model.Article;
import ch.evel.warehouse.db.model.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping(path = "/admin/color")
public class ColorController {
    private final ColorRepository colorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    public ColorController(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @GetMapping("/")
    public String getColor(ModelMap map) {
        map.addAttribute("content", "color");
        map.addAttribute("pageTitle", "Farben");
        return "admin/home";
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Color getColor(@PathVariable UUID uuid) {
        Color color = colorRepository.findOne(uuid);
        System.out.println(color.getArticles().size());
        return ((Article) color.getArticles().toArray()[3]).getColor();
    }

    @GetMapping("/add")
    public @ResponseBody
    String addColor(@RequestParam String code, @RequestParam String name) {
        Color color = new Color(code, name);
        colorRepository.save(color);
        return "Successful";
    }
}
