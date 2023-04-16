package miniprojtemplate;

public class PowerUpTimer extends Thread{
    private PowerUps powerup; //powerup attribute
    private int time; //time

    public final static int UP_TIME = 5; //5 seconds for the visibility of the powerup

    PowerUpTimer(PowerUps powerup){ //constructor
        this.powerup = powerup; //initializes the powerup
        this.time = PowerUpTimer.UP_TIME; //initialiozes the time
    }

    private void countDown(){ //method for the countdown
        while(this.time!=0){ //while time is not yet 0
            try{ //try
                Thread.sleep(1000); //sleep
                this.time--; //decrements the time
            }catch(InterruptedException e){ //catch
                System.out.println(e.getMessage());
            }
        }
        this.powerup.setVisible(false); //makes the power-up invisible
    }

    public void run(){ //runs the countdown
        this.countDown();
    }


}