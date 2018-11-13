package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class IndexController {

    @GetMapping({"", "/", "/index"})
    public String indexAction() {

        return "index";
    }
}
