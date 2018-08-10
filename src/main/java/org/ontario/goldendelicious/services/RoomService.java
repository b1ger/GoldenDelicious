package org.ontario.goldendelicious.services;

import javassist.NotFoundException;
import org.ontario.goldendelicious.commands.RoomCommand;
import org.ontario.goldendelicious.domain.Room;

import java.util.Set;

public interface RoomService {

    Set<Room> getRooms();
    Room findById(Long id) throws NotFoundException;
    RoomCommand findRoomCommandById(Long id);
    RoomCommand saveRoomCommand(RoomCommand command);
    void deleteById(Long id);
    Set<RoomCommand> listAllRooms();
}
