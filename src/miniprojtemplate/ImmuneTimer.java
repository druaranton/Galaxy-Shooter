package miniprojtemplate;

public class ImmuneTimer extends Thread {
    private Ship ship; //ship attribute
    private int time; //int time
    private final static int IMMUNE_TIME = 3; //3 seconds immune time

    ImmuneTimer(Ship ship){ //constructor
        this.ship = ship; //initializes the ship
        this.time = ImmuneTimer.IMMUNE_TIME; //initializes the time
    }

    private void countDown(){ //method for the countdown of timer
        while(this.time!=0){ //while the time is not yet 0
            try{ //try
                Thread.sleep(1000); //sleep
                this.time--; //decrements time
            }catch(InterruptedException e){ //catch
                System.out.println(e.getMessage());
            }
        }
        this.ship.resetImmune(); //resets the immunity of the ship
    }

    public void run(){ //run the countdown
        this.countDown();
    }
}