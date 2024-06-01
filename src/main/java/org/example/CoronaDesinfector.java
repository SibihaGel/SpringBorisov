package org.example;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;




//@Transaction
public class CoronaDesinfector {

    @InjectByTipe
    private Announcer announcer;
    @InjectByTipe
    private Policeman policeman;

    public CoronaDesinfector() {
    }

    @SneakyThrows
    public void start (Room room) {
        announcer.announce("Начинаем зезинфекцию, все вон!!");
        policeman.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("Попробуйте зайти обратно");
    }

    private void desinfect(Room room){
        System.out.println("Идет дезинфекция.... вирус повержен!!!");
    }
}
