package org.ontario.goldendelicious.utils;

import org.ontario.goldendelicious.domain.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StaffUtils {

    private final static String[] ALL_TIME = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"};

    /**
     * Method checks available doctor's time on definite date,
     * that customer has chosen.
     *
     * @param set of requests with definite doctor with busy time.
     * @return array of available doctor's time.
     */
    public static String[] getAvailableTime(Set<Request> set) {
        Set<String> reqTime = set.stream().map(Request::getTime).collect(Collectors.toSet());
        List<String> list = new ArrayList<>();
        for (String s : ALL_TIME) {
            if (! reqTime.contains(s)) {
                list.add(s);
            }
        }
        return list.toArray(new String[0]);
    }
}
