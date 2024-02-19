package ru.alexzmeu.java.onlineplaning.processors;

import com.google.gson.Gson;
import ru.alexzmeu.java.onlineplaning.HttpRequest;
import ru.alexzmeu.java.onlineplaning.objects.User;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AuthorizationRequestProcessor implements RequestProcessor {
    private Gson gson;
    public AuthorizationRequestProcessor() {
        gson = new Gson();

    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        User user = gson.fromJson(httpRequest.getBody(), User.class);
        int userId = user.authorizationUser();
        if (!(userId == 0)) {
            String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n{\n   \"id\":" + userId+"\n}";
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return;
        }
        String response = "HTTP/1.1 500 Server error\r\nContent-Type: application/json\r\n\r\n";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
