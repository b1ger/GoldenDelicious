package org.ontario.goldendelicious.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.commands.StaffCommand;
import org.ontario.goldendelicious.converters.StaffCommandToStaff;
import org.ontario.goldendelicious.converters.StaffToStaffCommand;
import org.ontario.goldendelicious.domain.Staff;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.repositories.StaffRepository;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StaffServiceImplTest {

    private StaffServiceImpl staffService;

    @Mock
    private StaffRepository repository;

    @Mock
    private StaffToStaffCommand staffToStaffCommand;

    @Mock
    private StaffCommandToStaff staffCommandToStaff;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        staffService = new StaffServiceImpl(repository, staffToStaffCommand, staffCommandToStaff);
    }

    @Test
    public void getStaffList() {
        // given
        Staff doctor = new Staff();
        List<Staff> doctorsData = new ArrayList<>();
        doctorsData.add(doctor);

        // when
        when(staffService.getStaffList()).thenReturn(doctorsData);
        List<Staff> doctors = staffService.getStaffList();

        // then
        assertEquals(1, doctors.size());
        verify(repository, times(1)).findAllByOrderById();
        verify(repository, never()).findById(anyLong());
    }

    @Test
    public void findById() {
        // given
        Staff staff = new Staff();
        staff.setId(1L);
        Optional<Staff> staffOptional = Optional.of(staff);

        // when
        when(repository.findById(anyLong())).thenReturn(staffOptional);
        Staff returnedStaff = staffService.findById(1L);

        // then
        assertNotNull("Null Staff returned", returnedStaff);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).findAll();
        verify(repository, never()).findAllByOrderById();
    }

    @Test
    public void findStaffCommandById() {
        // given
        Staff staff = new Staff();
        staff.setId(1L);
        Optional<Staff> staffOptional = Optional.of(staff);

        // when
        when(repository.findById(anyLong())).thenReturn(staffOptional);

        StaffCommand staffCommand = new StaffCommand();
        staffCommand.setId(1L);

        when(staffToStaffCommand.convert(any())).thenReturn(staffCommand);
        StaffCommand commandById = staffService.findStaffCommandById(5L);

        // then
        assertNotNull("Null Staff returned", commandById);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).findAll();
        verify(repository, never()).findAllByOrderById();
    }

    @Test
    public void saveStaffCommand() throws IOException {
        // given
        StaffCommand command = new StaffCommand();
        command.setId(1L);
        command.setUsername("username");
        command.setPassword("password");
        Staff staff = new Staff();
        staff.setId(1L);
        staff.setUsername("username");
        staff.setPassword("password");
        MockMultipartFile mockFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "GD".getBytes());

        // when
        when(repository.save(any())).thenReturn(staff);
       // when(staffService.saveStaffCommand(any(), any())).thenReturn(command);

        StaffCommand savedCommand = staffService.saveStaffCommand(command, mockFile);

        // given
//        assertNotNull("Null Staff returned", savedCommand);
//        assertEquals(Long.valueOf(1L), savedCommand.getId());
  //      assertEquals("username", savedCommand.getUsername());
        verify(repository, times(1)).save(any());
    }

    @Test
    public void deleteById() {
        // given
        Long idToDelete = 2L;

        staffService.deleteById(idToDelete);

        // then
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void findByIdThrowNotFoundException() throws Exception {
        Staff staff = new Staff();
        staff.setId(5L);

        //when(roomRepository.findById(anyLong())).thenThrow(new NotFoundException("Room not found"));

        Staff staffReturned = staffService.findById(3L);

        // should be BOOM!!!!
    }

    @Test
    public void getBytesFromFileTest() throws IOException {
        // given
        MockMultipartFile mockFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "GD".getBytes());

        // then
        assertEquals(mockFile.getBytes().length, staffService.getBytesFromFile(mockFile).length);
        assertNotNull(staffService.getBytesFromFile(mockFile));
    }
}