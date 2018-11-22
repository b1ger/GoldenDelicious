package org.ontario.goldendelicious.controllers;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.domain.Room;
import org.ontario.goldendelicious.services.ChairService;
import org.ontario.goldendelicious.services.ChairServiceImpl;
import org.ontario.goldendelicious.services.RoomService;
import org.ontario.goldendelicious.services.RoomServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class ChairController {

    private ChairService chairService;
    private RoomService roomService;

    private final String CHAIR_CHAIRFORM_URL = "chair/chairform";

    public ChairController(ChairServiceImpl chairService, RoomServiceImpl roomService) {
        this.chairService = chairService;
        this.roomService = roomService;
    }

    @GetMapping("/chair/new")
    public String createAction(Model model) {
        ChairCommand chairCommand = new ChairCommand();
        List<Room> rooms = roomService.listAllRooms();
        System.out.println(rooms);
        model.addAttribute("roomList", rooms);
        model.addAttribute("chair", chairCommand);

        return "chair/chairform";
    }

    @PostMapping("/chair/save")
    public String saveAction(@Valid @ModelAttribute("chair") ChairCommand chairCommand,
                             BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return CHAIR_CHAIRFORM_URL;
        }

        ChairCommand savedCommand = chairService.saveChairCommand(chairCommand);
        log.debug("Saved chair id: " + savedCommand.getId());

        return "redirect:/index";
    }
}
