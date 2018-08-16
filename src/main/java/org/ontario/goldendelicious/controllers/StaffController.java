package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.services.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Controller
public class StaffController {

    private StaffService staffService;
    private final String ROOM_ROOMFORM_URL = "/staff/staffform";

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/staff/new")
    public String newAction(Model model) {
        StaffCommand staffCommand = new StaffCommand();
        model.addAttribute("staff", staffCommand);

        return "staff/staffform";
    }

    @PostMapping("/staff/save")
    public String saveAction(@RequestParam("imagefile") MultipartFile file,
                             @Valid @ModelAttribute("staff") StaffCommand staffCommand,
                             BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return ROOM_ROOMFORM_URL;
        }

        StaffCommand savedCommand = staffService.saveStaffCommand(staffCommand, file);

        return "redirect:/staff/" + savedCommand.getId() + "/view";
    }

    @GetMapping("/staff/{id}/view")
    public String viewAction(@PathVariable String id, Model model) {
        StaffCommand command = staffService.findStaffCommandById(Long.valueOf(id));
        model.addAttribute("staff", command);

        return "staff/view";
    }
}
