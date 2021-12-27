package by.ivanshilyaev.rooms.service;

import by.ivanshilyaev.rooms.entity.Room;
import by.ivanshilyaev.rooms.dao.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public void save(Room room) {
        roomRepository.save(room);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }
}
