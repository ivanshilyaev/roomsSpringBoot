package by.ivanshilyaev.rooms;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.service.RoomService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    public static RoomService roomService;
    private Map<String, String> mapCountries = new TreeMap<>();
    private DatabaseReader reader;

    @Autowired
    public Controller(RoomService service) {
        roomService = service;
        getAllCountriesAndCodes();
        try {
            initDatabaseReader();
        } catch (IOException ignored) {
        }
    }

    private void getAllCountriesAndCodes() {
        String[] countryCodes = Locale.getISOCountries();
        mapCountries = new TreeMap<>();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            String code = locale.getCountry();
            String countryName = locale.getDisplayCountry();
            mapCountries.put(countryName, code);
        }
    }

    private void initDatabaseReader() throws IOException {
        File database = new File("src/main/resources/GeoLite2-Country.mmdb");
        reader = new DatabaseReader.Builder(database).build();
    }

    private String getUserCountryCode() {
        try (Scanner s = new java.util.Scanner(new URL("https://api.ipify.org").openStream(),
                "UTF-8").useDelimiter("\\A")) {
            String ip = s.next();
            InetAddress inetAddress = InetAddress.getByName(ip);
            CountryResponse countryResponse = reader.country(inetAddress);
            return countryResponse.getCountry().getIsoCode();
        } catch (IOException | GeoIp2Exception ignored) {
        }
        return "";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/createNewRoom")
    public String createNewRoom(Model model) {
        model.addAttribute("mapCountries", mapCountries);
        model.addAttribute("userCountry", getUserCountryCode());
        return "createNewRoom";
    }

    @PostMapping("/createNewRoomSubmit")
    public String createNewRoomSubmit(@RequestParam(name = "name") String name,
                                      @RequestParam(name = "country") String country) {
        Room room = new Room();
        room.setName(name);
        room.setCountry(mapCountries.get(country));
        room.setLamp(true);
        roomService.save(room);
        return "redirect:/listOfAllRooms";
    }

    @GetMapping("/listOfAllRooms")
    public String listOfAllRooms(Model model) {
        List<Room> rooms = roomService.findAll();
        model.addAttribute("rooms", rooms);
        return "listOfAllRooms";
    }

    @PostMapping("/room")
    public String room(@ModelAttribute("roomId") Long roomId, Model model) {
        Room room = roomService.findById(roomId).get();
        String userCountry = getUserCountryCode();
        if (!room.getCountry().equals(userCountry)) {
            return "redirect:/error403";
        }
        model.addAttribute("roomId", roomId);
        return "room";
    }

    @GetMapping("/")
    public String error() {
        return "error404";
    }
}
