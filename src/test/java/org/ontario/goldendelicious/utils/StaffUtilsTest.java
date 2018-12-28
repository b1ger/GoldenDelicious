package org.ontario.goldendelicious.utils;

import org.junit.Test;
import org.ontario.goldendelicious.domain.Request;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class StaffUtilsTest {

    @Test
    public void shouldReturnAvailableTime() {
        Request request = new Request();
        request.setId(1L);
        request.setDate(1544624325L);
        request.setTime("9:00");
        Request request2 = new Request();
        request2.setId(2L);
        request2.setTime("11:00");
        request2.setDate(1544624325L);;
        Set<Request> requestSet = new HashSet<>();
        requestSet.add(request);
        requestSet.add(request2);

        String[] time = StaffUtils.getAvailableTime(requestSet);

        assertNotNull(time);
        assertThat(new String[]{"9:00", "11:00"}, not(equalTo(time)));
        System.out.println(Arrays.toString(time));
    }
}