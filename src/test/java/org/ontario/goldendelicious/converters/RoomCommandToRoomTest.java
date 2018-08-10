package org.ontario.goldendelicious.converters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.domain.Room;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RoomCommandToRoomTest {

    private static final Long ID_VALUE = new Long(1L);
    private static final String DESCRIPTION = "description";
    private RoomCommandToRoom converter;

    @Before
    public void setUp() throws Exception {
        converter = new RoomCommandToRoom();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new RoomCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        RoomCommand command = new RoomCommand();
        command.setId(ID_VALUE);
        command.setDescription(DESCRIPTION);
        ChairCommand chairCommand = new ChairCommand();
        chairCommand.setId(1L);

        //when
        Room room = converter.convert(command);

        //then
        assertEquals(ID_VALUE, room.getId());
        assertEquals(DESCRIPTION, room.getDescription());
    }
}