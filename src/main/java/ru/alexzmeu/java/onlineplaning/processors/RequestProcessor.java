package ru.alexzmeu.java.onlineplaning.processors;

import ru.alexzmeu.java.onlineplaning.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
    void execute(HttpRequest httpRequest, OutputStream output) throws IOException;
}
