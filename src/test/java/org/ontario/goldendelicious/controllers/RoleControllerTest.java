package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.controllers.admin.RoleController;
import org.ontario.goldendelicious.domain.Authority;
import org.ontario.goldendelicious.services.AuthorityService;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RoleControllerTest {

    private RoleController controller;
    private MockMvc mockMvc;

    @Mock
    private AuthorityService authorityService;

    @Mock
    private StaffServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new RoleController(authorityService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void showIndexRolePage() throws Exception {

        mockMvc.perform(get("/admin/role/index"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("roleList"))
                .andExpect(view().name("admin/role/index"));
    }

    @Test
    public void returnCreateForm() throws Exception {

        mockMvc.perform(get("/admin/role/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authority"))
                .andExpect(view().name("admin/role/create"));
    }

    @Test
    public void haveToSaveNewAuthority() throws Exception {
        Authority authority = new Authority("ROLE_TEST", "DESCRIPTION_TEST");

        when(authorityService.save(any())).thenReturn(authority);

        mockMvc.perform(post("/admin/role/save")
                .param("name", "ROLE_TEST")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/role/index"));
    }

    @Test
    public void failRoleSaving() throws Exception {
        Authority authority = new Authority("ROLE_TEST", "DESCRIPTION_TEST");

        when(authorityService.save(any())).thenReturn(authority);

        mockMvc.perform(post("/admin/role/save"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authority"))
                .andExpect(view().name("admin/role/create"));
    }

    @Test
    public void haveToAttachRoleToUser() throws Exception {
        // given
        StaffCommand user = new StaffCommand();
        user.setId(1L);
        Authority authority1 = new Authority("ROLE_TEST1", "ROLE_DESC1");
        authority1.setId(1L);
        Authority authority2 = new Authority("ROLE_TEST2", "ROLE_DESC2");
        authority2.setId(2L);
        user.getAuthorities().add(authority1);
        StaffCommand saved = new StaffCommand();
        saved.setId(1L);
        saved.getAuthorities().add(authority1);
        saved.getAuthorities().add(authority2);

        // when
        when(userService.findStaffCommandById(anyLong())).thenReturn(user);
        when(authorityService.getAuthorityById(anyLong())).thenReturn(authority2);
        when(userService.updateStaffCommand(any(), any())).thenReturn(saved);

        // then
        mockMvc.perform(post("/admin/role/attach/1/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("redirect:/admin/user/1/view"));

        assertThat(saved.getAuthorities(), hasItem(authority2));
    }

    @Test
    public void haveToDetachRoleFromUser() throws Exception {
        // given
        StaffCommand user = new StaffCommand();
        user.setId(1L);
        Authority authority1 = new Authority("ROLE_TEST1", "ROLE_DESC1");
        authority1.setId(1L);
        user.getAuthorities().add(authority1);
        StaffCommand saved = new StaffCommand();
        saved.setId(1L);
        saved.getAuthorities().add(authority1);

        // when
        when(userService.findStaffCommandById(anyLong())).thenReturn(user);
        when(userService.updateStaffCommand(any(), any())).thenReturn(saved);

        // then
        mockMvc.perform(post("/admin/role/detach/1/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("redirect:/admin/user/1/view"));
    }
}
