package ru.alexzmeu.java.onlineplaning.objects;

public enum Status {
    NEW(1),
    EXECUTE(2),
    WAIT(3),
    DONE(4);

    private int statusId;


    Status(int id) {
        this.statusId = id;
    }

    public int getStatusId(){
        return statusId;
    }

}
