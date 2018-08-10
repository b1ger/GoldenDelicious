package org.ontario.goldendelicious.controllers;

import org.ontario.goldendelicious.repositories.ChairRepository;
import org.ontario.goldendelicious.services.ChairService;
import org.ontario.goldendelicious.services.ChairServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChairController {

    private ChairService chairService;

    public ChairController(ChairServiceImpl chairService) {
        this.chairService = chairService;
    }

    @GetMapping("/chair/new")
    public String createAction() {
        return null;
    }

    @PostMapping("/chair/save")
    public String saveAction() {
        return null;
    }
}
