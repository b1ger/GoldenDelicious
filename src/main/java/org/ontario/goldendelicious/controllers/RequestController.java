package org.ontario.goldendelicious.controllers;

import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.services.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/view/{requestId}")
    public String viewAction(@PathVariable("requestId") String requestId, Model model) {
        RequestCommand requestModel = requestService.getById(Long.valueOf(requestId));
        model.addAttribute("requestModel", requestModel);

        return "request/view";
    }
}
