package com.donduke;

import java.util.*;

public class Main {



    public static void main(String[] args) {
	// write your code here
        String d = "d";


        int x =100;
        int y =2;
        ArrayList<ArrayList<Integer>>requests = new ArrayList<ArrayList<Integer>>();


//        for(int i = 0; i<200; i++){
//            int randomNumber = rand.nextInt(upperbound);
//            int randomNumberTwo = rand.nextInt(upperbound);
//            if(randomNumber != randomNumberTwo && requests.size() < 100){
//                requests.add(new ArrayList<Integer>(Arrays.asList(randomNumber, randomNumberTwo)));
//            }
//        }
        System.out.println("+++++++++++++++++++++++++++++++++++Get In The Elevator+++++++++++++++++++++++++++++");


        Elevator elevatorOne = new Elevator(10, 5);
        elevatorOne.start();


//        while(elevatorOne.pendingRequests.size() != 0){
//            elevatorOne.alight();
//            elevatorOne.moveToFloor();
//            elevatorOne.alight();
//            elevatorOne.fillActiveRequests();
//            int n = 100-elevatorOne.pendingRequests.size();
//            if(n == 100){
//                System.out.println(elevatorOne.pendingRequests.toString());
//            }
//            System.out.println("_____________________________________________Elevator Session: "+n+" is done "+"________________________________________________" );
//        }










    }
}
