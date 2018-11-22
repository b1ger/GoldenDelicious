package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    @GetMapping({"", "/", "/index"})
    public String indexAction() {

        return "index";
    }
}
