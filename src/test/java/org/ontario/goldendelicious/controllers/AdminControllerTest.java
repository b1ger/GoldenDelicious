package org.ontario.goldendelicious.controllers;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AdminControllerTest {

    private AdminController adminController;
    private MockMvc mockMvc;

    @Mock
    private StaffServiceImpl staffService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.adminController = new AdminController(staffService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    public void methodShouldRenderIndexPage() throws Exception {

        mockMvc.perform(get("/admin/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/index"));
    }

    @Test
    public void outputListWithAllUsers() throws Exception {

        mockMvc.perform((get("/admin/users")))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/users"))
                .andExpect(model().attributeExists("usersList"));

        verify(staffService, times(1)).getStaffList();
    }
}
