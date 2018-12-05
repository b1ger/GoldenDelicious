package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.domain.enums.ServiceType;
import org.ontario.goldendelicious.domain.enums.StaffType;
import org.ontario.goldendelicious.repositories.RequestRepository;
import org.ontario.goldendelicious.repositories.StaffRepository;
import org.ontario.goldendelicious.services.RequestServiceImpl;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IndexControllerTest {

    private IndexController controller;
    private MockMvc mockMvc;

    @Mock
    private StaffServiceImpl staffService;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private RequestServiceImpl requestService;

    @Mock
    private RequestRepository requestRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.controller = new IndexController(staffService, requestService);
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

    @Test
    public void shouldApplyRequestForm() throws Exception {
        Request request = new Request();
        request.setId(1L);

        mockMvc.perform(post("/index/request")
                .param("firstName", "FirstName")
                .param("lastName", "LastName")
                .param("phone", "093-429-45-89")
                .param("email", "example@mail.com")
                .param("serviceType", String.valueOf(ServiceType.SERVICE_1))
                .param("doctorId", "1458967")
                .param("date", "10 SEP 1998")
                .param("time", "23:59")
        )
                .andExpect(status().isOk());

        when(requestRepository.save(any())).thenReturn(request);

        verify(requestService, times(1)).save(any());
    }

    @Test
    public void shouldGetErrorsFromRequestForm() throws Exception {

        mockMvc.perform(post("/index/request")
                .param("firstName", "FirstName")
                .param("lastName", "LastName")
        )
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}