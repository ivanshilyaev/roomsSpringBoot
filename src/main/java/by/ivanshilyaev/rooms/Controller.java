package by.ivanshilyaev.rooms;

import by.ivanshilyaev.rooms.bean.Room;
import by.ivanshilyaev.rooms.dao.RoomRepository;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
    private RoomRepository roomRepository;

    @Autowired
    public Controller(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/createNewRoom", method = RequestMethod.GET)
    public String createNewRoom(Model model) {
        String[] countryCodes = Locale.getISOCountries();
        Map<String, String> mapCountries = new TreeMap<>();
        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            String code = locale.getCountry();
            String countryName = locale.getDisplayCountry();
            mapCountries.put(countryName, code);
        }
        model.addAttribute("mapCountries", mapCountries);
        try (Scanner s = new java.util.Scanner(new URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
            String ip = s.next();
            File database = new File("src/main/resources/GeoLite2-Country.mmdb");
            DatabaseReader reader = new DatabaseReader.Builder(database).build();
            InetAddress inetAddress = InetAddress.getByName(ip);
            CountryResponse countryResponse = reader.country(inetAddress);
            String userCountry = countryResponse.getCountry().getName();
            model.addAttribute("userCountry", userCountry);
        } catch (IOException | GeoIp2Exception e) {
        }
        return "createNewRoom";
    }

    @RequestMapping(value = "/createNewRoomSubmit", method = RequestMethod.POST)
    public String createNewRoomSubmit() {
        return "redirect:/listOfAllRooms";
    }

    @RequestMapping(value = "/listOfAllRooms", method = RequestMethod.GET)
    public String listOfAllRooms(Model model) {
        List<Room> rooms = (List<Room>) roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "listOfAllRooms";
    }
}
