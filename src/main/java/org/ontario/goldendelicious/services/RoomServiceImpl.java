package org.ontario.goldendelicious.services;

import lombok.extern.slf4j.Slf4j;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.converters.RoomCommandToRoom;
import org.ontario.goldendelicious.converters.RoomToRoomCommand;
import org.ontario.goldendelicious.domain.Room;
import org.ontario.goldendelicious.exceptions.NotFoundException;
import org.ontario.goldendelicious.repositories.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomToRoomCommand roomToRoomCommand;
    private final RoomCommandToRoom roomCommandToRoom;

    public RoomServiceImpl(
            RoomRepository roomRepository,
            RoomToRoomCommand roomToRoomCommandConverter,
            RoomCommandToRoom roomCommandToRoom
    ) {
        this.roomRepository = roomRepository;
        this.roomToRoomCommand = roomToRoomCommandConverter;
        this.roomCommandToRoom = roomCommandToRoom;
    }

    @Override
    public Set<Room> getRooms() {
        log.debug("RoomServiceImpl call findAll() method in RoomRepository");

        Set<Room> roomSet = new HashSet<>();
        roomRepository.findAll().iterator().forEachRemaining(roomSet::add);

        return  roomSet;
    }

    @Override
    public Room findById(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);

        if (! roomOptional.isPresent()) {
            throw new NotFoundException("Room not found for 'id' value: " + id);
        }

        return roomOptional.get();
    }

    @Override
    public RoomCommand findRoomCommandById(Long id) {
        return roomToRoomCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public RoomCommand saveRoomCommand(RoomCommand command) {
        Room detachedRoom = roomCommandToRoom.convert(command);

        Room savedRoom = roomRepository.save(detachedRoom);
        log.debug("Saved Room with id: " + savedRoom.getId());

        return roomToRoomCommand.convert(savedRoom);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
        log.debug("Deleted Room with id: " + id);
    }

    @Override
    public List<Room> listAllRooms() {
        return roomRepository.findAllByOrderById();
    }
}
