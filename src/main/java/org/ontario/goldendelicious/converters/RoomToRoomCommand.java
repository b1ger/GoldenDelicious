package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.domain.Room;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RoomToRoomCommand implements Converter<Room, RoomCommand> {

    @Synchronized
    @Nullable
    @Override
    public RoomCommand convert(Room source) {
        if (source == null) {
            return null;
        }

        final RoomCommand command = new RoomCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());

        return command;
    }
}
