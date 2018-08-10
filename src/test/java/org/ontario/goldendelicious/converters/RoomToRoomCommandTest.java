package org.ontario.goldendelicious.converters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.domain.Chair;
import org.ontario.goldendelicious.domain.Room;

import static org.junit.Assert.*;

public class RoomToRoomCommandTest {

    private static final Long ID_VALUE = new Long(1L);
    private static final String DESCRIPTION = "description";
    private RoomToRoomCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RoomToRoomCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Room()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Room room = new Room();
        room.setId(ID_VALUE);
        room.setDescription(DESCRIPTION);
        Chair chair = new Chair();
        chair.setId(1L);

        //when
        RoomCommand command = converter.convert(room);

        //then
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}