package org.ontario.goldendelicious.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.converters.ChairCommandToChair;
import org.ontario.goldendelicious.converters.ChairToChairCommand;
import org.ontario.goldendelicious.domain.Chair;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.repositories.ChairRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ChairServiceImplTest {

    private ChairServiceImpl chairService;

    @Mock
    private ChairRepository chairRepository;

    @Mock
    private ChairToChairCommand chairToChairCommand;

    @Mock
    private ChairCommandToChair chairCommandToChair;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.chairService = new ChairServiceImpl(chairRepository, chairToChairCommand, chairCommandToChair);
    }

    @Test
    public void getChairs() {

        Chair chair = new Chair();
        HashSet<Chair> chairsData = new HashSet<>();
        chairsData.add(chair);

        when(chairService.getChairs()).thenReturn(chairsData);

        Set<Chair> chairs = chairService.getChairs();

        assertEquals(chairs.size(), 1);
        verify(chairRepository, times(1)).findAll();
        verify(chairRepository, never()).findById(anyLong());
    }

    @Test
    public void findById() {
        Chair chair = new Chair();
        chair.setId(1L);
        Optional<Chair> chairOptional = Optional.of(chair);

        when(chairRepository.findById(anyLong())).thenReturn(chairOptional);

        Chair returnedChair = chairService.findById(1L);

        assertNotNull("Null chair returned", returnedChair);
        verify(chairRepository, times(1)).findById(anyLong());
        verify(chairRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void findByIdThrowNotFoundException() throws Exception {
        Chair chair = new Chair();
        chair.setId(2L);

        Chair chairReturned = chairService.findById(30L);

        // should be BOOM!!!!
    }

    @Test
    public void findChairCommandById() {
        Chair chair = new Chair();
        chair.setId(1L);
        Optional<Chair> chairOptional = Optional.of(chair);

        when(chairRepository.findById(anyLong())).thenReturn(chairOptional);

        ChairCommand command = new ChairCommand();
        command.setId(1L);

        when(chairToChairCommand.convert(any())).thenReturn(command);

        ChairCommand chairCommandById = chairService.findChairCommandById(1L);

        assertNotNull("Null room command returned", chairCommandById);
        verify(chairRepository, times(1)).findById(anyLong());
        verify(chairRepository, never()).findAll();
    }

    @Test
    public void saveChairCommand() {
        ChairCommand command = new ChairCommand();
        command.setId(3L);
        command.setDescription("Description");
        Chair chair = new Chair();
        chair.setId(3L);
        chair.setDescription("Description");

        when(chairRepository.save(any())).thenReturn(chair);

        ChairCommand savedCommand = chairService.saveChairCommand(command);

        verify(chairRepository, times(1)).save(any());
    }

    @Test
    public void deleteById() {
        Long idToDelete = 5L;
        chairService.deleteById(idToDelete);
        verify(chairRepository, times(1)).deleteById(anyLong());
    }
}