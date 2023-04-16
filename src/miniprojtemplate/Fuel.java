package miniprojtemplate;

import javafx.scene.image.Image;

public class Fuel extends PowerUps {
    public final static Image FUEl_IMAGE = new Image("images/fuel.png",PowerUps.POWERUPS_WIDTH,PowerUps.POWERUPS_WIDTH,false,false); //fuel image
    public final static int POWER = 50; //the additional power that fuel has

    Fuel(int xPos, int yPos) { //constructor
        super(xPos, yPos); //calls thesuperclass' constructor
        this.loadImage(FUEl_IMAGE); //loads the fuel image
    }


    @Override
    protected void checkCollision(Ship ship) { //method that checks if the fuel collides with the ship
        if(this.collidesWith(ship)){ // if it collides with the ship
        	this.playPickUpSound();
            this.setVisible(false); //the powerup disappears
            ship.increaseStrength(); //the ship's strength will be increased
            //System.out.println(ship.getStrength());
        }else{ //if it does not collide with the ship
            if(this.isStartTimer() == false) { //if the timer has not been started yet
                this.timer =  new PowerUpTimer(this); //creates a timer
                this.timer.start(); //starts the timer
                this.setStartTimer(); //sets the start timer to true
            }
        }
    }

}