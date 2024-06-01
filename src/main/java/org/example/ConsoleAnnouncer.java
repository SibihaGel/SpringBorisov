package org.example;


import java.lang.reflect.InvocationTargetException;

public class ConsoleAnnouncer implements Announcer{

    @InjectByTipe
    private Recomendator recomendator;

    public ConsoleAnnouncer() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    @Override
    public void announce(String message) {
        System.out.println(message);
        recomendator.recomend();

    }
}
