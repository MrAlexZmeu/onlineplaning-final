package ru.alexzmeu.java.onlineplaning;

import ru.alexzmeu.java.onlineplaning.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> router;
    private RequestProcessor unknownRequestProcessor;

    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("POST /registration", new RegistrationRequestProcessor());
        this.router.put("POST /auth", new AuthorizationRequestProcessor());
        this.router.put("POST /newtask", new NewTaskRequestProcess());
        this.router.put("PUT /updatetask", new UpdateTaskRequestProcess());
        this.router.put("GET /task", new GetTaskRequestProcess());
        this.unknownRequestProcessor = new UnknownRequestProcessor();
    }

    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        if (!router.containsKey(httpRequest.getRoute())) {
            unknownRequestProcessor.execute(httpRequest, output);
            return;
        }
        router.get(httpRequest.getRoute()).execute(httpRequest, output);
    }
}
