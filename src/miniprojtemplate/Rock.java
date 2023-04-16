package miniprojtemplate;

import java.nio.file.Paths;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Rock extends Sprite {
	protected boolean alive;
	protected boolean moveRight;
	protected int speed;

	public final static String EXPLOSION_SOUND = "src/music/explode.wav";
	public final static String COLLISION_SOUND = "src/music/collision.wav";
	public static final int ROCK_DAMAGE = 30; //damage value of the rock
	public static final int MAX_ROCK_SPEED = 5; //speed of the rock
	public final static int ROCK_WIDTH=50; //width of the rock

	//image of the rock
	public final static Image ROCK_IMAGE = new Image("images/rock.png",Rock.ROCK_WIDTH,Rock.ROCK_WIDTH,false,false);
	public final static Image ROCKR_IMAGE = new Image("images/rockR.png",Rock.ROCK_WIDTH,Rock.ROCK_WIDTH,false,false);

	Rock(int x, int y){ //constructor
		super(x,y); //calls superconstructor
		this.alive = true; //initialize the alive value of the rock to true
		this.loadImage(Rock.ROCK_IMAGE); //load the image of the rock
		Random r = new Random(); //instantiate a randomizer
		this.speed = (r.nextInt(Rock.MAX_ROCK_SPEED) + 1); //set the speed of the rock to a random value between 1 to 5
		this.moveRight = r.nextBoolean();
	}

	//method that changes the x position of the rock
	void move(){
		this.setDX(speed); //call setDX method and pass speed as parameter
		//checker if the the rock's x position is still within the GameStage width
		if(this.moveRight == true && this.getX() < GameStage.WINDOW_WIDTH-this.width) {
			this.x += this.dx; //move right
		} else if (this.getX()+this.dx >= GameStage.WINDOW_WIDTH-this.width) { //checker if the x position of the rock is already beyond the gamestage width
			this.moveRight = false; //set move right value to false
			this.setRotateImage();
			this.x -= this.dx; //move left
		} else {
			if(this.moveRight == false && this.getX() > 0) { //if move right is false, the rock will move to the left
				this.x -= this.dx;
			} else if (this.getX() <= 0) { //if the X position is already less than or equal to 0
					this.moveRight = true; //set moveRight to true
					this.setRotateImage();
					this.x += this.dx; //move rocks to the right
			}
		}





		/*
		 * TODO: 				If moveRight is true and if the rock hasn't reached the right boundary yet,
		 *    						move the rock to the right by changing the x position of the rock depending on its speed
		 *    					else if it has reached the boundary, change the moveRight value / move to the left
		 * 					 Else, if moveRight is false and if the rock hasn't reached the left boundary yet,
		 * 	 						move the rock to the left by changing the x position of the rock depending on its speed.
		 * 						else if it has reached the boundary, change the moveRight value / move to the right
		 */
	}

	//getter
	boolean isAlive() {
		return this.alive;
	}

	void isDead(){
		this.alive = false;
	}

	protected void checkCollision(Ship ship){
		for(int i = 0; i<ship.getBullets().size(); i++){
			if(this.collidesWith(ship.getBullets().get(i))){
				this.playExplosionSound();
				ship.getBullets().get(i).setVisible(false);
				this.isDead();
				this.setVisible(false);
				ship.increaseScore();
			}
		}
		if(this.collidesWith(ship)){
			this.playCollisionSound();
			if(ship.isImmune() == false){
				ship.reduceStrength(Rock.ROCK_DAMAGE);
			}
			this.isDead();
			this.setVisible(false);

		}
	}

	protected void setRotateImage() {
		if(this.moveRight == true) {
			this.loadImage(ROCKR_IMAGE);
		} else {
			this.loadImage(ROCK_IMAGE);
		}

	}

	void playExplosionSound() {
		Media media = new Media(Paths.get(EXPLOSION_SOUND).toUri().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

	void playCollisionSound() {
		Media media = new Media(Paths.get(COLLISION_SOUND).toUri().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
}
