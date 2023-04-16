package miniprojtemplate;

import java.nio.file.Paths;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Bullet extends Sprite {
	public final static int BULLET_SPEED = 20; //initial speed of the bullet
	public final static int BULLET_WIDTH = 20; //width of the bullet
	public final static String BULLET_SOUND = "src/music/bullet.wav";

	public final static Image BULLET_IMAGE = new Image("images/bullet.png",Bullet.BULLET_WIDTH,Bullet.BULLET_WIDTH,false,false);

	Bullet(int x, int y){
		super(x,y); //calls superconstructor
		this.loadImage(Bullet.BULLET_IMAGE); //load the image of the bullet
	}

	void playBulletSound() {
		Media media = new Media(Paths.get(BULLET_SOUND).toUri().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}


	//method that will move/change the x position of the bullet
	void move(){
		this.x += Bullet.BULLET_SPEED;
		//checker if the x position of the bullet is beyond the GameStage width;
		//if so, it will disappear
		if(this.getX() >= GameStage.WINDOW_WIDTH){
			this.setVisible(false);
		}
	}


}