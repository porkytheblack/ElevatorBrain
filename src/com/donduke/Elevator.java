package com.donduke;

import java.util.*;

public class Elevator extends Thread {
    public int restrictedFloor;
    public int currentFloor;
    public ArrayList<Integer> activeRequests;
    public ArrayList<ArrayList<Integer>> pendingRequests;
    private int alighted = 0;
    public ArrayList<Integer> currentlyActive;
    public Map<Integer, passenger> passengers;
    public int haveRunFor;
    public int betweenFloors;
    public Timer timer;



    public Elevator(int restrictedFloor, int currentFloor){
        this.restrictedFloor = restrictedFloor;
        this.currentFloor = currentFloor;
        this.pendingRequests = new ArrayList<ArrayList<Integer>>();
        this.activeRequests = new ArrayList<Integer>() ;
        this.currentlyActive = new ArrayList<Integer>();
        this.passengers = new HashMap<Integer, passenger>();
    }

    public void run(){
        try{
            while(true){
                addPassenger();
                fillActiveRequests();
                moveToFloor();
                fillActiveRequests();
            }
        }catch(Exception e){
            System.out.println("Exception caught");
        }

    }

    //add the passenger to the passenger Map
    public void addPassenger(){
        Scanner reader = new Scanner(System.in);
        int currentFloor;
        int toFloor;
        System.out.println("Enter your current floor: ");
        currentFloor = reader.nextInt();
        System.out.println("Enter the floor you want to get to: ");
        toFloor = reader.nextInt();
        System.out.println("What is your unique identifier: ");
        int id = pendingRequests.size();
        pendingRequests.add(new ArrayList<Integer>(Arrays.asList(currentFloor, toFloor)));
        passengers.put(id, new passenger(currentFloor, toFloor, id));
        passengers.get(id).startTravel();
    }

    public void fillActiveRequests(){
        System.out.println("size of pending filler :" + pendingRequests.size());
        System.out.println("size of active filler :" + activeRequests.size());
        for(int i  = 0; i< pendingRequests.size(); i++){
            if(pendingRequests.get(i).get(0) == currentFloor && activeRequests.size() <10){
                currentlyActive.add(i);


                //lets test it out for the currently active user
                passengers.get(i).setCurrentlyActive(currentlyActive);




                activeRequests.add(pendingRequests.get(i).get(1));
                pendingRequests.remove(i);
                System.out.println("These are the currently active requests: "+ activeRequests.toString());
            }
        }

    }
//
    public void alight(){
        //convert to set removing all the duplicated values

        for(int i = 0; i< activeRequests.size(); i++){
            if(activeRequests.get(i) == currentFloor){
                System.out.println(" I alight on this floor called floor:"+activeRequests.get(i));
                activeRequests.remove(i);
                for(int j = 0; j < passengers.size(); j++ ){
                    if(passengers.get(j).toFloor == currentFloor){
                        passengers.get(j).stopTravel();
                    }
                }
            }
        }

    }
    public void moveToFloor(){
        if(activeRequests.size() == 0 && pendingRequests.size() != 0)
            currentFloor = pendingRequests.get(0).get(0);
            fillActiveRequests();



        System.out.println("We are now on floor : "+ currentFloor );
        int up =0;
        int down = 0;
        int toTop = 1;
        int toBottom = 1;
        int onCurrentFloor = 0;

        ArrayList <Integer> aboveElevator = new ArrayList<Integer>();
        ArrayList <Integer> belowElevator = new ArrayList<Integer>();
        Collections.sort(belowElevator, Collections.reverseOrder());
        Collections.sort(aboveElevator);
        if(aboveElevator.size() > 0){
            toTop = aboveElevator.get(0) - currentFloor;
        }
        if(belowElevator.size() > 0){
            toBottom = currentFloor - belowElevator.get(0);
        }




        for( int x = 0; x< activeRequests.size(); x++){
            if(activeRequests.get(x) > currentFloor){
                up++;
            }
            if(activeRequests.get(x) < currentFloor){
                down++;
            }
            if(activeRequests.get(x) == currentFloor){
                onCurrentFloor++;
            }
        }
        System.out.println("Going up are: " + up +"\n"+"Going down are : "+down+ "\n"+"The current floor is : "+currentFloor);

        if(onCurrentFloor != 0){
            System.out.println("Had someone on the current floor who needs to alight");
            alight();
        }else{
            //uppers
            if((up> down  && toTop<toBottom) || (up< down  && toTop<toBottom) || up > down || (up == down && toTop < toBottom ) ){ //|| toTop < toBottom
                for(int i = 0; i< activeRequests.size(); i++){
                    if(activeRequests.get(i) > currentFloor){
                        aboveElevator.add(activeRequests.get(i));
                    }
                }
                Collections.sort(aboveElevator);

                System.out.println("Above the elevator are the following floors: "+aboveElevator.toString());
                if(aboveElevator.size() != 0){
                    betweenFloors = aboveElevator.get(0) - currentFloor+1;
                        timer = new Timer();
                        TimerTask task = new helperUp(aboveElevator.get(0));
                        timer.schedule(task, betweenFloors*1000);
                }
            }

            ///downers
            if((down> up && toBottom < toTop) || (down< up && toBottom < toTop)   ||(down> up && toBottom > toTop)   || (down == up && toBottom < toTop) || (down == up && toBottom == toTop) || toTop == toBottom){ //|| toBottom < toTop || toBottom == toTop
                for(int i = 0; i< activeRequests.size(); i++){
                    if(activeRequests.get(i) < currentFloor){
                        belowElevator.add(activeRequests.get(i));
                    }
                }
                System.out.println("Bellow the elevator are the following floors : " + belowElevator.toString());
                Collections.sort(belowElevator, Collections.reverseOrder());
                if(belowElevator.size() != 0){
                    betweenFloors = currentFloor+1 - belowElevator.get(0);
                        timer = new Timer();
                        TimerTask task = new helperDown(belowElevator.get(0));
                        timer.schedule(task,0, betweenFloors*1000);
                }


            }
        }




    }

    public boolean runTest(){
        if(pendingRequests.size() >0){
            return true;
        }else{
            return false;
        }
    }
    public class helperUp extends TimerTask{
        public int n;
        public void run(){
                currentFloor = n;
                for(int i =0; i< currentlyActive.size(); i++){
                    if(passengers.get(currentlyActive.get(i)).toFloor == n){
                        passengers.get(currentlyActive.get(i)).stopTravel();
                    }
                }
                alight();

        }
        public helperUp(int n){
            this.n = n;
        }
    }
    public class helperDown extends TimerTask{
        public int n;
        public void run(){

            currentFloor = n;
            for(int i =0; i< currentlyActive.size(); i++){
                if(passengers.get(currentlyActive.get(i)).toFloor == n){
                    passengers.get(currentlyActive.get(i)).stopTravel();
                }
            }
            alight();

        }
        public helperDown(int n){
            this.n = n;
        }
    }

}
