package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.repositories.RequestRepository;
import org.ontario.goldendelicious.services.RequestServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RequestControllerTest {

    private RequestController controller;
    private MockMvc mockMvc;

    @Mock
    private RequestServiceImpl requestService;

    @Mock
    private RequestRepository requestRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.controller = new RequestController(requestService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldReturnRequestViewPage() throws Exception {
        // given
        RequestCommand request = new RequestCommand();
        request.setId(1L);

        // when
        when(requestService.getById(anyLong())).thenReturn(request);

        mockMvc.perform(get("/request/view/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("requestModel"))
                .andExpect(view().name("request/view"));
    }
}