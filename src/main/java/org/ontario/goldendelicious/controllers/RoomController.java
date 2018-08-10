package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.services.RoomService;
import org.ontario.goldendelicious.services.RoomServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class RoomController {

    private final String ROOM_ROOMFORM_URL = "room/roomform";
    private RoomService roomService;

    public RoomController(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/room/new")
    public String newAction(Model model) {
        RoomCommand room = new RoomCommand();
        model.addAttribute("room", room);

        return "room/roomform";
    }

    @PostMapping("/room/save")
    public String saveAction(@Valid @ModelAttribute("room") RoomCommand roomCommand,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return ROOM_ROOMFORM_URL;
        }

        RoomCommand savedCommand = roomService.saveRoomCommand(roomCommand);
        log.debug("Saved room id:" + savedCommand.getId());

        return "redirect:/index";
    }
}
