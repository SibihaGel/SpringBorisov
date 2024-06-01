package org.example;

public class PolicemanImpl implements Policeman{
    @InjectByTipe
    Recomendator recomendator;


    @PostConstruct
    public void init(){
        System.out.println(recomendator.getClass());
    }


    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Я из полиции! Нужно всем выйти!");
    }
}
