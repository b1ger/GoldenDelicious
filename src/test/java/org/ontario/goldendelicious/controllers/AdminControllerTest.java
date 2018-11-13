package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.commands.UpdatableStaffCommand;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminControllerTest {

    private AdminController adminController;
    private MockMvc mockMvc;

    @Mock
    private StaffServiceImpl staffService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.adminController = new AdminController(staffService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).setControllerAdvice(new ExceptionHandlerController()).build();
    }

    @Test
    public void methodShouldRenderIndexPage() throws Exception {

        mockMvc.perform(get("/admin/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/index"));
    }

    @Test
    public void outputListWithAllUsers() throws Exception {

        mockMvc.perform((get("/admin/user/index")))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user/index"))
                .andExpect(model().attributeExists("usersList"));

        verify(staffService, times(1)).getStaffList();
    }

    @Test
    public void createNewUser() throws Exception {

        mockMvc.perform(get("/admin/user/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("admin/user/create"));
    }

    @Test
    public void shouldSaveNewUser() throws Exception {
        // given
        StaffCommand command = new StaffCommand();
        MockMultipartFile multipartFile =
                new MockMultipartFile("imageFile", "testing.txt", "text/plain", "Hello World from SpringBoot".getBytes());
        command.setId(1L);

        // when
        when(staffService.saveStaffCommand(any(), any())).thenReturn(command);

        // then
        mockMvc.perform(multipart("/admin/user/save").file(multipartFile)
                .param("id", "1")
                .param("firstName", "Test")
                .param("lastName", "Test")
                .param("lastTime", "Test")
                .param("birthDate", "5-10-90")
                .param("userName", "Test")
                .param("password", "testPass")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/user/index"));
    }

    @Test
    public void formShouldNotValidate() throws Exception {
        // given
        StaffCommand command = new StaffCommand();
        command.setId(2L);
        MockMultipartFile multipartFile =
                new MockMultipartFile("imageFile", "testing.txt", "text/plain", "Hello World from SpringBoot".getBytes());

        // when
        when(staffService.saveStaffCommand(any(), any())).thenReturn(command);

        // then
        mockMvc.perform(multipart("/admin/user/save").file(multipartFile)
                .param("id", "1")
        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("admin/user/create"));
    }

    @Test
    public void shouldReturnViewPageWithModel() throws Exception {
        // given
        StaffCommand user = new StaffCommand();
        user.setId(1L);

        // when
        when(staffService.findStaffCommandById(anyLong())).thenReturn(user);

        // then
        mockMvc.perform(get("/admin/user/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/user/view"))
                .andExpect(model().attributeExists("user"));

        verify(staffService, times(1)).findStaffCommandById(1L);
        verify(staffService, never()).deleteById(1L);
    }

    @Test
    public void deleteSelectedUser() throws Exception {

        mockMvc.perform(get("/admin/user/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/user/index"));

        verify(staffService, times(1)).deleteById(anyLong());
    }



    @Test
    public void shouldHandleBadRequest() throws Exception {

        mockMvc.perform(get("/admin/user/ff/view"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void shouldHandleNotFoundException() throws Exception {

        // when
        when(staffService.findStaffCommandById(anyLong())).thenThrow(NotFoundException.class);

        // then
        mockMvc.perform(get("/admin/user/1/view"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void getUpdateView() throws Exception {
        // given
        StaffCommand command = new StaffCommand();
        command.setId(1L);

        // when
        when(staffService.findStaffCommandById(anyLong())).thenReturn(command);

        // then
        mockMvc.perform(get("/admin/user/1/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("admin/user/update"));
    }

    @Test
    public void shouldUpdateUserModel() throws Exception {
        // given
        StaffCommand command = new StaffCommand();
        UpdatableStaffCommand updatable = mock(UpdatableStaffCommand.class);
        updatable.setId(1L);
        MockMultipartFile multipartFile =
                new MockMultipartFile("imageFile", "testing.txt", "text/plain", "Hello World from SpringBoot".getBytes());
        command.setId(1L);

        // when
        when(staffService.saveStaffCommand(any(), any())).thenReturn(command);

        // then
        mockMvc.perform(multipart("/admin/user/update").file(multipartFile)
                .param("id", "1")
                .param("firstName", "Test")
                .param("lastName", "Test")
                .param("userName", "Test")
                .param("birthDate", "5.10.1889")
                .param("type", "Test")
                .param("about", "Test")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/user/1/view"));
    }
}
