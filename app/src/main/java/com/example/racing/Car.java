package com.example.racing;

public class Car {

    public Car(int initSpeed) {
        speed = initSpeed;
    }




    public void changeSpeed(int changeSpeed) {
        speed += changeSpeed;
    }
    public void resetSpeed(int changespeed) {speed = changespeed;}
    public void addWin() {
        wins++;
    }
    public void addLoss() {
        losses++;
    }


    public int getSpeed() {
        return speed;
    }
    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }

    private int wins = 0;
    private int losses = 0;
    private int speed;
}
