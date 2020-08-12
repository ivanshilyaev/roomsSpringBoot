package by.ivanshilyaev.rooms;

import by.ivanshilyaev.rooms.dao.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Controller implements CommandLineRunner {
    @Autowired
    private RoomRepository roomRepository;

    public static void main(String[] args) {
        SpringApplication.run(Controller.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(roomRepository.findAll());
    }
}
