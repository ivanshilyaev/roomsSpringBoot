package by.ivanshilyaev.rooms.utils;

import by.ivanshilyaev.rooms.entity.Lamp;
import com.google.gson.Gson;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class LampDecoder implements Decoder.Text<Lamp> {
    private static final Gson GSON = new Gson();

    @Override
    public Lamp decode(String s) {
        return GSON.fromJson(s, Lamp.class);
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
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
