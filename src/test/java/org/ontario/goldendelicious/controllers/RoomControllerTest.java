package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.services.RoomServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RoomControllerTest {

    private RoomController controller;
    private MockMvc mockMvc;

    @Mock
    private RoomServiceImpl roomService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.controller = new RoomController(roomService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void newAction() throws Exception {
        mockMvc.perform(get("/room/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("room"))
                .andExpect(view().name("room/roomform"));
    }

    @Test
    public void saveAction() throws Exception {

        // given
        RoomCommand command = new RoomCommand();
        command.setId(3L);
        command.setDescription("some_description");

        //when
        when(roomService.saveRoomCommand(any())).thenReturn(command);

        // then
        mockMvc.perform(post("/room/save")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "1")
                    .param("description", "some string")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }
}