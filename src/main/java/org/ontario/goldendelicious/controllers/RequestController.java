package org.ontario.goldendelicious.controllers;

import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.ontario.goldendelicious.services.RequestService;
import org.ontario.goldendelicious.services.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestService requestService;
    private StaffService staffService;

    public RequestController(RequestService requestService, StaffService staffService) {
        this.requestService = requestService;
        this.staffService = staffService;
    }

    @GetMapping("/view/{requestId}")
    public String viewAction(@PathVariable("requestId") String requestId, Model model) {
        RequestCommand requestModel = requestService.getById(Long.valueOf(requestId));
        StaffCommand doctor = staffService.findStaffCommandById(requestModel.getDoctorId());
        model.addAttribute("requestModel", requestModel);
        model.addAttribute("doctor", doctor);

        return "request/view";
    }

    @GetMapping("/{id}/{action}")
    public String processRequest(@PathVariable("id") String id, @PathVariable("action") String action) {
        RequestCommand command = requestService.getById(Long.valueOf(id));
        if (action.equals(RequestStatus.ACCEPTED)) {
            command.setStatus(RequestStatus.ACCEPTED);
        } else {
            command.setStatus(RequestStatus.DECLINED);
        }
        requestService.updateRequest(command);

        return "redirect:/index";
    }

    @GetMapping("/index")
    public String indexAction() {
        return "request/index";
    }
}
