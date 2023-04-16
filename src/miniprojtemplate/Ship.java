package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;



public class Ship extends Sprite{
	private String name; //name attribute
	private int strength; //strength attribute
	private boolean alive; //attribute to know it it is alive
	private int score; //score attribute
	private boolean isImmune; //is immune attribute
	private ImmuneTimer timer; //immune timer atrribute
	private boolean bossImmune; // boss immune attribute

	private ArrayList<Bullet> bullets; //arraylist for the bullets
	public final static Image SHIP_IMAGE = new Image("images/ship.png",Ship.SHIP_WIDTH,Ship.SHIP_HEIGHT,false,false); //ship image
	public final static int SHIP_WIDTH = 100; //size of the ship
	public final static int SHIP_HEIGHT = 90; //size of the ship

	Ship(String name, int x, int y){ //constructor
		super(x,y); //calls the superconstructor
		this.name = name; //initializes the name
		Random r = new Random(); //randomizer
		this.strength = r.nextInt(51)+100; //randomizes the strength of ship which would be 100 to 150
		this.alive = true; //sets the alive to true
		this.bullets = new ArrayList<Bullet>(); //initializes the bullets array list
		this.loadImage(Ship.SHIP_IMAGE); //loads trhe image of the ship
		this.isImmune = false; //sets the is immune to false
	}

	boolean isAlive(){ //getter
		if(this.alive) return true; //returns true if it is alive
		return false; //else false
	}

	String getName(){ //getter of the name of the ship
		return this.name;
	}

	private void die(){ //setter
    	this.alive = false; //sets the alive to flase
    }

	//method that will get the bullets 'shot' by the ship
	ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	//method called if spacebar is pressed
	void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x + this.width-20);
		int y = (int) (this.y + this.height/2.5);

		Bullet bullet = new Bullet(x,y); //Instantiate a new bullet
		bullets.add(bullet); //adds the bullet to the bullets arraylist of ship
		bullet.playBulletSound();
    }

	//method called if up/down/left/right arrow key is pressed.
	void move() { //method for the movement of the ship
		/*
		 *TODO: 		Only change the x and y position of the ship if the current x,y position
		 *				is within the gamestage width and height so that the ship won't exit the screen
		 */

		if(this.getX()+this.dx >= 0 && this.getX()+this.dx <= GameStage.WINDOW_WIDTH - this.width){
            this.x += this.dx;
        }
        if(this.getY()+this.dy >= GameTimer.STATUS_HEIGHT && this.getY() + this.dy <=GameStage.WINDOW_HEIGHT - this.width){
            this.y += this.dy;
        }


	}

	int getStrength(){ //getter
		return this.strength; //returnms the strength of ship
	}

	void reduceStrength(int damage){ //setter
		this.strength -= damage; //decreases the strength of the ship
		if(this.strength <= 0){ //if the strength of ship is less than or equal to 0
			this.die(); //ship dies
		}
	}

	int getScore(){ //getter
		return this.score; //returns the score
	}

	void increaseScore(){ //setter
		this.score+=1; //increments the score
	}

	void increaseStrength(){ //setter
		this.strength += Fuel.POWER; //increase the strength of the ship
	}

	void setImmune(){ //setter
		if(!this.isImmune()) { //if the ship is not immune
            this.isImmune = true; //the ship is immune
            this.timer =  new ImmuneTimer(this); //creates a timer
            this.timer.start(); //starts the timer
        }
	}

	boolean isImmune(){ //getter
		return this.isImmune; //returns the isImmune value
	}

	void resetImmune(){ //setter
		this.isImmune = false; //sets the is immune to false
	}

	void setBossImmune() { //setter
        this.bossImmune = true; //sets the boss immune to true
    }

    void resetBossImmune() { //setter
        this.bossImmune = false; //sets the boss immune to false
    }

    boolean isBossImmune() { //getter
        return this.bossImmune; //returns if the ship is immune to the boss
    }

}
