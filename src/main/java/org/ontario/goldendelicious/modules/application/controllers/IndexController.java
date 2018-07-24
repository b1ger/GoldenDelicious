package org.ontario.goldendelicious.modules.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"", "/", "/index"})
    public String indexAction() {

        return "loginform";
    }
}
