package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.services.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class StaffController {

    private StaffService staffService;
    private final String STAFF_STAFFFORM_URL = "/staff/staffform";

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/staff/new")
    public String newAction(Model model) {
        StaffCommand staffCommand = new StaffCommand();
        model.addAttribute("staff", staffCommand);

        return STAFF_STAFFFORM_URL;
    }

    @PostMapping("/staff/save")
    public String saveAction(@RequestParam("imagefile") MultipartFile file,
                             @Valid @ModelAttribute("staff") StaffCommand staffCommand,
                             BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return STAFF_STAFFFORM_URL;
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

    @GetMapping("/staff/{id}/update")
    public String updateAction(@PathVariable String id, Model model) {
        StaffCommand command = staffService.findStaffCommandById(Long.valueOf(id));
        model.addAttribute("staff", command);

        return STAFF_STAFFFORM_URL;
    }

    @GetMapping("/staff/{id}/profileimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        StaffCommand staffCommand = staffService.findStaffCommandById(Long.valueOf(id));

        if (staffCommand.getImage() != null) {
            byte[] byteArray = new byte[staffCommand.getImage().length];
            int i = 0;

            for (Byte wrappedByte : staffCommand.getImage()){
                byteArray[i++] = wrappedByte;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
