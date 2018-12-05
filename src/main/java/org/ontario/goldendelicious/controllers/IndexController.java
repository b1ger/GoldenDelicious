package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.services.RequestServiceImpl;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class IndexController {

    private StaffServiceImpl staffService;
    private RequestServiceImpl requestService;

    public IndexController(
            StaffServiceImpl staffService,
            RequestServiceImpl requestService
    ) {
        this.staffService = staffService;
        this.requestService = requestService;
    }

    @GetMapping({"", "/", "/index"})
    public String indexAction(Model model) {
        model.addAttribute("request", new RequestCommand());
        model.addAttribute("doctors", staffService.getDoctors());
        return "index";
    }

    /**
     * Take new request on service from exterior resource.
     *
     * @param request
     * @param bindingResult
     */
    @PostMapping("/index/request")
    public void saveNewRequest(@Valid @ModelAttribute("request") RequestCommand request,
                                BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            log.debug("Request has errors, and wasn't saved: " + request);
        }

        requestService.save(request);
    }
}
