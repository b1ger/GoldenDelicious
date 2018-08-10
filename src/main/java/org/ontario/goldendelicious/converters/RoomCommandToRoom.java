package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.domain.Room;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RoomCommandToRoom implements Converter<RoomCommand, Room> {

    @Synchronized
    @Nullable
    @Override
    public Room convert(RoomCommand source) {
        if (source == null) {
            return null;
        }

        final Room room = new Room();
        room.setId(source.getId());
        room.setDescription(source.getDescription());

        return room;
    }
}
