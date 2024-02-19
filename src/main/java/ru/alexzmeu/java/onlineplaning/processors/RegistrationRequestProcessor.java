package ru.alexzmeu.java.onlineplaning.processors;

import com.google.gson.Gson;
import ru.alexzmeu.java.onlineplaning.HttpRequest;
import ru.alexzmeu.java.onlineplaning.objects.User;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class RegistrationRequestProcessor implements RequestProcessor {
    private Gson gson;


    public RegistrationRequestProcessor() {
        gson = new Gson();

    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        User user = gson.fromJson(httpRequest.getBody(), User.class);
        if (user.userIsAlreadyExist()) {
            String response = "HTTP/1.1 400 User is already exists\r\nContent-Type: application/json\r\n\r\nUser is already exist";
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return;
        }

        if (user.registrationUser().equals("OK")){
            String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n{\n   \"success\":true\n}";
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return;
        }
        String response = "HTTP/1.1 500 Server error\r\nContent-Type: application/json\r\n\r\n";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
