package org.ontario.goldendelicious.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.repositories.ChairRepository;
import org.ontario.goldendelicious.services.ChairService;
import org.ontario.goldendelicious.services.ChairServiceImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

public class ChairControllerTest {

    private ChairController controller;
    @Mock
    private ChairServiceImpl chairService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new ChairController(chairService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void createAction() {
    }

    @Test
    public void saveAction() {
    }
}