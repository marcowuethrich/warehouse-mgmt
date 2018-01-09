package ch.evel.warehouse.controller;

import org.springframework.ui.ModelMap;

public abstract class PageController {

    protected String loadPage(ModelMap map, String pageFileName, String pageTitle) {
        map.addAttribute("content", pageFileName);
        map.addAttribute("pageTitle", pageTitle);
        return "admin/home";
    }
}
