package by.ivanshilyaev.rooms.dao;

import by.ivanshilyaev.rooms.bean.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomRepository extends CrudRepository<Room, Long> {
    List<Room> findByName(String name);
}
