package org.ontario.goldendelicious.services;

import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.domain.Chair;
import org.ontario.goldendelicious.exceptions.NotFoundException;

import java.util.Set;

public interface ChairService {

    Set<Chair> getChairs();
    Chair findById(Long id) throws NotFoundException;
    ChairCommand findChairCommandById(Long id);
    ChairCommand saveChairCommand(ChairCommand command);
    void deleteById(Long id);
}
