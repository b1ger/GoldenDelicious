package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.domain.Chair;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ChairToChairCommand implements Converter<Chair, ChairCommand> {

    @Synchronized
    @Nullable
    @Override
    public ChairCommand convert(Chair source) {
        if (source == null) {
            return null;
        }

        final ChairCommand command = new ChairCommand();
        command.setId(source.getId());
        command.setRoomId(source.getRoomId());
        command.setDescription(source.getDescription());

        return command;
    }
}
