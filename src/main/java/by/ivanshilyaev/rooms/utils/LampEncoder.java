package by.ivanshilyaev.rooms.utils;

import by.ivanshilyaev.rooms.entity.Lamp;
import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class LampEncoder implements Encoder.Text<Lamp> {
    private static final Gson GSON = new Gson();

    @Override
    public String encode(Lamp lamp) throws EncodeException {
        return GSON.toJson(lamp);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
