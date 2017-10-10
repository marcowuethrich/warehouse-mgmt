package ch.warehouse.controller;

import ch.warehouse.db.model.Article;
import ch.warehouse.db.model.Color;
import ch.warehouse.db.model.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping(path = "/colors")
public class ColorController {
    private final ColorRepository colorRepository;

    @Autowired
    public ColorController(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @GetMapping("/{uuid}")
    public @ResponseBody
    Color getColor(@PathVariable UUID uuid) {
        Color color = colorRepository.findOne(uuid);
        System.out.println(color.getArticles().size());
        return ((Article) color.getArticles().toArray()[3]).getColor();
    }

    @GetMapping("/all")
    public @ResponseBody
    Iterable<Color> getAllColors() {
        return colorRepository.findAll();
    }

    @GetMapping("/add")
    public @ResponseBody
    String addColor(@RequestParam String code, @RequestParam String name) {
        Color color = new Color(code, name);
        colorRepository.save(color);
        return "Successful";
    }
}
