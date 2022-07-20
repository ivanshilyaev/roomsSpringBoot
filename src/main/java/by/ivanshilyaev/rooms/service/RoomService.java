package by.ivanshilyaev.rooms.service;

import by.ivanshilyaev.rooms.entity.Room;
import by.ivanshilyaev.rooms.dao.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public void save(Room room) {
        roomRepository.save(room);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room getById(Long id) {
        return roomRepository.getById(id);
    }
}
