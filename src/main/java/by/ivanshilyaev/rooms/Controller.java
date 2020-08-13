package by.ivanshilyaev.rooms;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    private RoomRepository roomRepository;

    @Autowired
    public Controller(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @RequestMapping(value = "/listOfAllRooms", method = RequestMethod.GET)
    public String listOfAllRooms(Model model) {
        List<Room> rooms = (List<Room>) roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "listOfAllRooms";
    }
}
