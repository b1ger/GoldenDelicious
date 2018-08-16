package org.ontario.goldendelicious.converters;

import lombok.Synchronized;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.domain.Chair;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ChairCommandToChair implements Converter<ChairCommand, Chair> {

    @Synchronized
    @Nullable
    @Override
    public Chair convert(ChairCommand source) {
        if (source == null) {
            return null;
        }

        final Chair chair = new Chair();
        chair.setId(source.getId());
        chair.setRoomId(source.getRoomId());
        chair.setDescription(source.getDescription());

        return chair;
    }
}
