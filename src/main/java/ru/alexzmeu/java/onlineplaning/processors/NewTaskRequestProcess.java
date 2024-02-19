package ru.alexzmeu.java.onlineplaning.processors;

import com.google.gson.Gson;
import ru.alexzmeu.java.onlineplaning.HttpRequest;
import ru.alexzmeu.java.onlineplaning.objects.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NewTaskRequestProcess  implements RequestProcessor{
    private Gson gson;
    public NewTaskRequestProcess() {
        gson = new Gson();

    }
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        Task task = gson.fromJson(httpRequest.getBody(), Task.class);
        int taskId = task.createTask();
        if (!(taskId == 0)) {
            String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n{\n   \"id\":" + taskId+"\n}";
            output.write(response.getBytes(StandardCharsets.UTF_8));
            return;
        }
        String response = "HTTP/1.1 500 Server error\r\nContent-Type: application/json\r\n\r\n";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
