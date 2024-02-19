package ru.alexzmeu.java.onlineplaning.objects;

import ru.alexzmeu.java.onlineplaning.database.DataBaseUserProcesses;

public class User {
    private String login;
    private String password;


    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean userIsAlreadyExist() {
        return new DataBaseUserProcesses().isLoginAlreadyExist(login);
    }

    public String registrationUser() {
        return new DataBaseUserProcesses().createNewUser(login, password);
    }

    public int authorizationUser() {
        String result = new DataBaseUserProcesses().getIdUserByLoginAndPassword(login, password);
        if (result.equals("Failed")) {
            return 0;
        }
        return Integer.parseInt(result);
    }
}
