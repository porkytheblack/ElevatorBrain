package com.donduke;

import  java.util.*;

public class passenger {
    public int currentFloor;
    public int toFloor;
    public int myNumber;
    public int amountOfTime = 0;
    public boolean aboard = false;
    public Timer timer;
    public ArrayList<Integer> currentlyActive;

    public passenger(int currentFloor, int toFloor, int myNumber){
        this.currentFloor = currentFloor;
        this.toFloor = toFloor;
        this.myNumber = myNumber;
    }
    public void startTravel(){
        aboard = true;
        timer = new Timer();
        timer.schedule(new taskRun(), 0, 1000);
    }
    public void stopTravel(){
            aboard = false;
    }

    public class taskRun extends TimerTask {
        public void run(){
            if(aboard){
                amountOfTime++;
            }else{
                timer.cancel();
            }
        }
    }
    public void setCurrentlyActive (ArrayList<Integer> data){
        currentlyActive = data;
    }
    public void amIActive(){
        for(int i =0; i < currentlyActive.size(); i++ ){
            if(currentlyActive.get(i) == myNumber){
                aboard = true;
            }
        }
    }
    public void notAboard(){
        aboard = false;
    }

}
