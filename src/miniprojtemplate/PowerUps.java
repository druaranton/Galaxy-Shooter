package miniprojtemplate;

import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PowerUps extends Sprite {
	protected boolean startTimer;
	protected PowerUpTimer timer;
	public final static String PICKUP_SOUND = "src/music/pickup.wav";
	public final static int POWERUPS_WIDTH=50; //width of the powerups

	PowerUps(int xPos, int yPos) { //constructor
		super(xPos, yPos); //calls superconstructor
		this.startTimer = false; //initialize startTimer to false

	}

	//method to check if the powerup collides with the ship
	//the subclasses of this class will override this method
	protected void checkCollision(Ship ship){
	}

	//method that will set the startTimer to true
	void setStartTimer(){
		this.startTimer = true;
	}

	//will return the value of startTimer
	boolean isStartTimer(){
		return this.startTimer;
	}

	void playPickUpSound() {
		Media media = new Media(Paths.get(PICKUP_SOUND).toUri().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}


}
