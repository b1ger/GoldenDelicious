package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.domain.enums.RequestStatus;
import org.ontario.goldendelicious.repositories.AuthorityRepository;
import org.ontario.goldendelicious.repositories.RequestRepository;
import org.ontario.goldendelicious.repositories.StaffRepository;
import org.ontario.goldendelicious.services.RequestServiceImpl;
import org.ontario.goldendelicious.services.StaffService;
import org.ontario.goldendelicious.services.StaffServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RequestControllerTest {

    private RequestController controller;
    private StaffServiceImpl staffService;
    private MockMvc mockMvc;
    private RequestServiceImpl requestService;

    @Mock
    private StaffRepository staffRepository;
    @Mock
    private AuthorityRepository authorityRepository;
    @Mock
    private RequestRepository requestRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        requestService = new RequestServiceImpl(requestRepository);
        staffService = new StaffServiceImpl(staffRepository, authorityRepository);
        this.controller = new RequestController(requestService, staffService);
        staffService = new StaffServiceImpl(staffRepository, authorityRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnRequestViewPage() throws Exception {
        // given
        Request request = new Request();
        request.setId(1L);
        request.setDate(1546984800000L);
        request.setDoctorId(1L);
        Staff doctor = new Staff();
        doctor.setId(1L);
        doctor.setBirthDate(646516800000L);
        Optional<Staff> optionalStaff = Optional.of(doctor);
        Optional<Request> optionalRequest = Optional.of(request);

        // when
        when(staffRepository.findById(anyLong())).thenReturn(optionalStaff);
        when(requestRepository.findById(anyLong())).thenReturn(optionalRequest);

        mockMvc.perform(get("/request/view/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("requestModel"))
                .andExpect(model().attributeExists("doctor"))
                .andExpect(view().name("request/view"));
    }

    @Test
    public void shouldAcceptRequest() throws Exception {

        // given
        RequestCommand command = new RequestCommand();
        command.setId(1L);
        command.setDate("9 Jan 2019");
        command.setStatus(RequestStatus.NEW);
        Request request = new Request();
        request.setId(1L);
        request.setDate(1546984800000L);
        request.setStatus(RequestStatus.ACCEPTED);
        Optional<Request> optionalRequest = Optional.of(request);

        // when
        when(requestRepository.findById(anyLong())).thenReturn(optionalRequest);
        when(requestRepository.save(any())).thenReturn(request);

        RequestCommand updated = requestService.updateRequest(command);

        // then
        mockMvc.perform(get("/request/1/accept"))
                .andExpect(status().is3xxRedirection());

        assertEquals(RequestStatus.ACCEPTED, updated.getStatus());
    }

    @Test
    public void shouldDeclineRequest() {
        assertTrue(false);
    }
}