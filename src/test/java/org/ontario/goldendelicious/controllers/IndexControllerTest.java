package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.domain.enums.StaffType;
import org.ontario.goldendelicious.repositories.StaffRepository;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IndexControllerTest {

    private IndexController controller;
    private MockMvc mockMvc;

    @Mock
    private StaffServiceImpl staffService;

    @Mock
    private StaffRepository staffRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.controller = new IndexController(staffService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void indexAction() throws Exception {
        Staff doctor = new Staff();
        doctor.setId(1L);
        doctor.setType(StaffType.DOCTOR);
        List<Staff> doctors = new ArrayList<>();
        doctors.add(doctor);

        when(staffRepository.findByTypeOrderById(any())).thenReturn(doctors);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("request"))
                .andExpect(model().attributeExists("doctors"))
                .andExpect(view().name("index"));
    }
}