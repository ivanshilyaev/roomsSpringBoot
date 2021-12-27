package by.ivanshilyaev.rooms.utils;

import by.ivanshilyaev.rooms.controller.Controller;
import by.ivanshilyaev.rooms.entity.Lamp;
import by.ivanshilyaev.rooms.entity.Room;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/room/{roomId}", encoders = LampEncoder.class, decoders = LampDecoder.class)
public class RoomEndpoint {
    private Session session;
    private Long roomId;
    private static final Map<Long, Set<RoomEndpoint>> repository = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") Long roomId) throws IOException, EncodeException {
        this.session = session;
        this.roomId = roomId;
        if (repository.containsKey(roomId)) {
            repository.get(roomId).add(this);
        } else {
            Set<RoomEndpoint> roomEndpoints = new CopyOnWriteArraySet<>();
            roomEndpoints.add(this);
            repository.put(roomId, roomEndpoints);
        }
        Lamp currentLamp = new Lamp();
        Room currentRoom = Controller.roomService.findById(roomId).get();
        if (currentRoom.isLamp()) {
            currentLamp.setState("On");
        }
        else {
            currentLamp.setState("Off");
        }
        session.getBasicRemote().sendObject(currentLamp);
    }

    @OnMessage
    public void onMessage(Session session, Lamp lamp) throws IOException, EncodeException {
        Lamp response = new Lamp();
        if (lamp.getState().equals("On")) {
            response.setState("Off");
        } else {
            response.setState("On");
        }
        Room room = Controller.roomService.findById(roomId).get();
        room.setLamp(!room.isLamp());
        Controller.roomService.save(room);
        broadcast(response, roomId);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        repository.get(roomId).remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Lamp lamp, Long roomId) {
        Set<RoomEndpoint> roomEndpoints = repository.get(roomId);
        roomEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote()
                            .sendObject(lamp);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

