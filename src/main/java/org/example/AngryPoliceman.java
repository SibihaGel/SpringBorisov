package org.example;

@Transaction
public class AngryPoliceman implements Policeman{
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Сука! Я злой полисмен! Быстро все нахуй вышли!!!");
    }
}
