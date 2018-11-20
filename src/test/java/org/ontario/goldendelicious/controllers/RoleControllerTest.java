package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.controllers.admin.RoleController;
import org.ontario.goldendelicious.services.AuthorityService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RoleControllerTest {

    private RoleController controller;
    private MockMvc mockMvc;

    @Mock
    private AuthorityService authorityService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RoleController(authorityService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void showIndexRolePage() throws Exception {

        mockMvc.perform(get("/admin/role/index"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("roleList"))
                .andExpect(view().name("admin/role/index"));
    }
}
