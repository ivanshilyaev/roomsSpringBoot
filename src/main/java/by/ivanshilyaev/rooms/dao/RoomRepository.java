package by.ivanshilyaev.rooms.dao;

import by.ivanshilyaev.rooms.bean.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByName(String name);
}
