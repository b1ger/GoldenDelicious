package org.ontario.goldendelicious.converters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.domain.Chair;

import static org.junit.Assert.*;

public class ChairToChairCommandTest {

    private static final Long ID_VALUE = new Long(1L);
    private static final String DESCRIPTION = "description";
    ChairToChairCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new ChairToChairCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Chair()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Chair chair = new Chair();
        chair.setId(ID_VALUE);
        chair.setDescription(DESCRIPTION);

        //when
        ChairCommand command = converter.convert(chair);

        //then
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION, command.getDescription());
    }
}