package org.ontario.goldendelicious.services;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.ChairCommand;
import org.ontario.goldendelicious.converters.ChairCommandToChair;
import org.ontario.goldendelicious.converters.ChairToChairCommand;
import org.ontario.goldendelicious.domain.Chair;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.repositories.ChairRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ChairServiceImpl implements ChairService {

    private ChairRepository chairRepository;
    private ChairToChairCommand chairToChairCommand;
    private ChairCommandToChair chairCommandToChair;

    public ChairServiceImpl(
            ChairRepository chairRepository,
            ChairToChairCommand chairToChairCommand,
            ChairCommandToChair chairCommandToChair
    ) {
        this.chairRepository = chairRepository;
        this.chairToChairCommand = chairToChairCommand;
        this.chairCommandToChair = chairCommandToChair;
    }

    @Override
    public Set<Chair> getChairs() {
        log.debug("ChairServiceImpl call findAll() method in ChairRepository");

        Set<Chair> chairs = new HashSet<>();
        chairRepository.findAll().iterator().forEachRemaining(chairs::add);

        return chairs;
    }

    @Override
    public Chair findById(Long id) throws NotFoundException {
        Optional<Chair> chairOptional = chairRepository.findById(id);

        if (! chairOptional.isPresent()) {
            throw new NotFoundException("Chair not found by 'id': " + id);
        }
        log.debug("Find chair by 'id': " + id);

        return chairOptional.get();
    }

    @Override
    public ChairCommand findChairCommandById(Long id) {
        return chairToChairCommand.convert(findById(id));
    }

    @Transactional
    @Override
    public ChairCommand saveChairCommand(ChairCommand command) {
        Chair detachedChair = chairCommandToChair.convert(command);

        Chair savedChair = chairRepository.save(detachedChair);
        log.debug("Saved Chair with id: " + savedChair.getId());

        return chairToChairCommand.convert(savedChair);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        chairRepository.deleteById(id);
        log.debug("Deleted Chair with id: " + id);
    }
}
