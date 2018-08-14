package org.ontario.goldendelicious.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ontario.goldendelicious.domain.Room;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.converters.RoomCommandToRoom;
import org.ontario.goldendelicious.converters.RoomToRoomCommand;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.repositories.RoomRepository;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RoomServiceImplTest {

    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomToRoomCommand roomToRoomCommand;

    @Mock
    private RoomCommandToRoom roomCommandToRoom;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.roomService = new RoomServiceImpl(roomRepository, roomToRoomCommand, roomCommandToRoom);
    }

    @Test
    public void getRooms() {

        Room room = new Room();
        HashSet<Room> roomsData = new HashSet<>();
        roomsData.add(room);

        when(roomService.getRooms()).thenReturn(roomsData);

        Set<Room> rooms = roomService.getRooms();

        assertEquals(rooms.size(), 1);
        verify(roomRepository, times(1)).findAll();
        verify(roomRepository, never()).findById(anyLong());
    }

    @Test
    public void findById() throws Exception {
        Room room = new Room();
        room.setId(1L);
        Optional<Room> roomOptional = Optional.of(room);

        when(roomRepository.findById(anyLong())).thenReturn(roomOptional);

        Room roomReturned = roomService.findById(1L);

        assertNotNull("Null room returned", roomReturned);
        verify(roomRepository, times(1)).findById(anyLong());
        verify(roomRepository, never()).findAll();
    }

    @Test(expected = NotFoundException.class)
    public void findByIdThrowNotFoundException() throws Exception {
        Room room = new Room();
        room.setId(5L);

        //when(roomRepository.findById(anyLong())).thenThrow(new NotFoundException("Room not found"));

        Room roomReturned = roomService.findById(3L);

        // should be BOOM!!!!
    }

    @Test
    public void findRoomCommandById() {
        Room room = new Room();
        room.setId(1L);
        Optional<Room> roomOptional = Optional.of(room);

        when(roomRepository.findById(anyLong())).thenReturn(roomOptional);

        RoomCommand roomCommand = new RoomCommand();
        roomCommand.setId(1L);

        when(roomToRoomCommand.convert(any())).thenReturn(roomCommand);

        RoomCommand roomCommandById = roomService.findRoomCommandById(1L);

        assertNotNull("Null room command returned", roomCommandById);
        verify(roomRepository, times(1)).findById(anyLong());
        verify(roomRepository, never()).findAll();
    }

    @Test
    public void saveRoomCommand() {
        RoomCommand command = new RoomCommand();
        command.setId(3L);
        command.setDescription("Description");
        Room room = new Room();
        room.setId(3L);
        room.setDescription("Description");

        when(roomRepository.save(any())).thenReturn(room);

        RoomCommand savedCommand = roomService.saveRoomCommand(command);

        verify(roomRepository, times(1)).save(any());
    }

    @Test
    public void deleteById() {

        // given
        Long idoDelete = 2L;

        // when
        // no 'when', since method has void return type

        roomService.deleteById(idoDelete);

        // then
        verify(roomRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void listAllRoomsTest() {
        List<Room> rooms = new ArrayList<>();
        Room room1 = new Room();
        room1.setId(1L);
        rooms.add(room1);

        Room room2 = new Room();
        room2.setId(2L);
        rooms.add(room2);

        when(roomRepository.findAllByOrderById()).thenReturn(rooms);

        List<Room> commands = roomService.listAllRooms();
        assertEquals(2, commands.size());
        verify(roomRepository, times(1)).findAllByOrderById();
    }
}