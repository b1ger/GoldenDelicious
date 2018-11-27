package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    private StaffServiceImpl staffService;

    public IndexController(StaffServiceImpl staffService) {
        this.staffService = staffService;
    }

    @GetMapping({"", "/", "/index"})
    public String indexAction(Model model) {
        model.addAttribute("request", new RequestCommand());
        model.addAttribute("doctors", staffService.getDoctors());
        return "index";
    }
}
