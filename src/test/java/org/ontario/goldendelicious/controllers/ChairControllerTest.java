package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.repositories.ChairRepository;
import org.ontario.goldendelicious.repositories.RoomRepository;
import org.ontario.goldendelicious.services.ChairServiceImpl;
import org.ontario.goldendelicious.services.RoomServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ChairControllerTest {

    private ChairController controller;
    @Mock
    private ChairServiceImpl chairService;
    @Mock
    private RoomServiceImpl roomService;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private ChairRepository chairRepository;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new ChairController(chairService, roomService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void createAction() throws Exception {

        when(roomRepository.findAll()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/chair/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("roomList"))
                .andExpect(model().attributeExists("chair"))
                .andExpect(view().name("chair/chairform"));

        verify(roomService, times(1)).listAllRooms();
    }

    @Test
    public void saveAction() throws Exception {
        ChairCommand command = new ChairCommand();
        command.setId(1L);
        command.setDescription("Some_description");
        command.setRoomId(2L);

        when(chairService.saveChairCommand(any())).thenReturn(command);

        mockMvc.perform(post("/chair/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("description", "Some_description")
                .param("roomId", "2")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }
}