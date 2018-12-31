package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.ontario.goldendelicious.exceptions.ConvertionException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class RequestCommandToRequest implements Converter<RequestCommand, Request> {

    @Synchronized
    @Nullable
    @Override
    public Request convert(RequestCommand source) {
        if (source == null) {
            return null;
        }
        Request request = new Request();
        request.setId(source.getId());
        request.setFirstName(source.getFirstName());
        request.setLastName(source.getLastName());
        request.setPhone(source.getPhone());
        request.setEmail(source.getEmail());
        request.setDoctorId(source.getDoctorId());
        request.setServiceType(source.getServiceType());
        request.setStatus(source.getStatus());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date date = dateFormat.parse(source.getDate());
            request.setDate(date.getTime());
        } catch (ParseException e) {
            dateFormat = new SimpleDateFormat("d MMM y", Locale.ENGLISH);
            try {
                Date date = dateFormat.parse(source.getDate());
                request.setDate(date.getTime());
            } catch (ParseException e1) {
                throw new ConvertionException("Convertion RequestCommand to Request was failed: " + e.getMessage());
            }
        }
        request.setTime(source.getTime());
        request.setDescription(source.getDescription());

        return request;
    }
}
