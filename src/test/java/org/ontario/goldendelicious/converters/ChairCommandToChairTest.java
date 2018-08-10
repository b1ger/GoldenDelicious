package org.ontario.goldendelicious.converters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.domain.Chair;

import static org.junit.Assert.*;

public class ChairCommandToChairTest {

    private static final Long ID_VALUE = new Long(1L);
    private static final String DESCRIPTION = "description";
    ChairCommandToChair converter;

    @Before
    public void setUp() throws Exception {
        converter = new ChairCommandToChair();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new ChairCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        ChairCommand command = new ChairCommand();
        command.setId(ID_VALUE);
        command.setDescription(DESCRIPTION);

        //when
        Chair chair = converter.convert(command);

        //then
        assertEquals(ID_VALUE, chair.getId());
        assertEquals(DESCRIPTION, chair.getDescription());
    }
}