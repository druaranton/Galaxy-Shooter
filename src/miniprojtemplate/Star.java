package miniprojtemplate;

import javafx.scene.image.Image;

public class Star extends PowerUps{

    public final static Image STAR_IMAGE = new Image("images/star.png",PowerUps.POWERUPS_WIDTH,PowerUps.POWERUPS_WIDTH,false,false); //image of the star

    Star(int xPos, int yPos) { //constructor
        super(xPos, yPos); //calles the superconstructor
        this.loadImage(STAR_IMAGE); //loads the star image

    }

    @Override
    protected void checkCollision(Ship ship) { //method for checking the collision
        if(this.collidesWith(ship)){ //if the star collids with the ship
        	this.playPickUpSound();
            ship.setImmune(); //makes the ship immune
            this.setVisible(false); //the star becomes invisible
        }else{ //if the star does not collide with the ship
            if(this.isStartTimer() == false) { //if the starttimer is false
                this.timer =  new PowerUpTimer(this); //creates a timer
                this.timer.start(); //starts the timer
                this.setStartTimer(); //sets the start timer to true
            }
        }
    }

}