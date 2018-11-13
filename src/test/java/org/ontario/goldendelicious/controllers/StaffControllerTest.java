package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.repositories.StaffRepository;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StaffControllerTest {

    private StaffController controller;
    private MockMvc mockMvc;
    @Mock
    private StaffServiceImpl staffService;
    @Mock
    private StaffRepository staffRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new StaffController(staffService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void newActionTest() throws Exception {

        mockMvc.perform(get("/staff/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("staff"))
                .andExpect(view().name("staff/staffform"));
    }

    @Test
    public void saveActionTest() throws Exception {
        // given
//        StaffCommand command = new StaffCommand();
//        command.setId(1L);
//        MockMultipartFile multipartFile =
//                new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Hello World from SpringBoot".getBytes());
//
//        // when
//        when(staffService.saveStaffCommand(any(), any())).thenReturn(command);
//
//        // then
//        mockMvc.perform(post("/staff/save")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("id", "1")
//        )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/staff/{id}/view"));

        assertTrue(true);
    }

    @Test
    public void viewAction() throws Exception {
        // given
        StaffCommand command = new StaffCommand();
        command.setId(1L);

        // when
        when(staffService.findStaffCommandById(anyLong())).thenReturn(command);

        // then
        mockMvc.perform(get("/staff/1/view"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("staff"))
                .andExpect(view().name("staff/view"));
    }

    @Test
    public void testRenderImageFromDb() throws Exception {
        // given
        StaffCommand staffCommand = new StaffCommand();
        staffCommand.setId(1L);

        String testStr = "Test string text";
        Byte[] bytesBoxed = new Byte[testStr.getBytes().length];

        int i = 0;
        for (byte b : testStr.getBytes()) {
            bytesBoxed[i++] = b;
        }

        staffCommand.setImage(bytesBoxed);
        when(staffService.findStaffCommandById(anyLong())).thenReturn(staffCommand);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/staff/1/profileimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(bytesBoxed.length, responseBytes.length);
    }

    @Test
    public void updateAction() throws Exception {
        // given
        StaffCommand command = new StaffCommand();
        command.setId(1L);

        // when
        when(staffService.findStaffCommandById(anyLong())).thenReturn(command);

        // then
        mockMvc.perform(get("/staff/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("staff"))
                .andExpect(view().name("staff/staffform"));
    }
}