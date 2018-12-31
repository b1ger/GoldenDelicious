package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.RequestCommand;
import org.ontario.goldendelicious.domain.Request;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

@Component
public class RequestToRequestCommand implements Converter<Request, RequestCommand> {

    @Synchronized
    @Nullable
    @Override
    public RequestCommand convert(Request source) {
        if (source == null) {
            return null;
        }

        RequestCommand command = new RequestCommand();
        command.setId(source.getId());
        command.setFirstName(source.getFirstName());
        command.setLastName(source.getLastName());
        command.setPhone(source.getPhone());
        command.setEmail(source.getEmail());
        command.setDoctorId(source.getDoctorId());
        command.setServiceType(source.getServiceType());
        command.setStatus(source.getStatus());
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM y", Locale.ENGLISH);
        //Date date = Date.from(Instant.ofEpochSecond(source.getDate()));
        command.setDate(dateFormat.format(source.getDate()));
        command.setTime(source.getTime());
        command.setDescription(source.getDescription());

        return command;
    }
}
