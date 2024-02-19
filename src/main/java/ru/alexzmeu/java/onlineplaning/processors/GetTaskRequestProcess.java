package ru.alexzmeu.java.onlineplaning.processors;

import com.google.gson.Gson;
import ru.alexzmeu.java.onlineplaning.HttpRequest;
import ru.alexzmeu.java.onlineplaning.objects.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetTaskRequestProcess implements RequestProcessor{
    private Gson gson;
    public GetTaskRequestProcess() {
        gson = new Gson();

    }
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String taskId = httpRequest.getParameter("taskId");
        String userId = httpRequest.getParameter("userId");

        if (userId == null){
            String response = "HTTP/1.1 400 Bad-request\r\nContent-Type: application/json\r\n\r\n";
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return;
        }

        if(taskId == null){
            String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" +  gson.toJson(Task.getAllTasks(Integer.parseInt(userId)));
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return;
        }

        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" +  gson.toJson(Task.getTask(Integer.parseInt(userId),Integer.parseInt(taskId)));
        output.write(response.getBytes(StandardCharsets.UTF_8));


    }
}
