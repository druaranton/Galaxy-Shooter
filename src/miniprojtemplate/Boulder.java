package miniprojtemplate;

import javafx.scene.image.Image;

public class Boulder extends Rock {
    public final static Image BOSS_IMAGE = new Image("images/boss.png",Boulder.BOSS_WIDTH,Boulder.BOSS_WIDTH,false,false); //image of the boss
    public final static Image BOSSR_IMAGE = new Image("images/bossR.png",Boulder.BOSS_WIDTH,Boulder.BOSS_WIDTH,false,false); //image of the boss
    private int health; //health of the boss
    public final static int BOSS_DAMAGE = 50; //damage of the boss
    public final static int BOSS_WIDTH=250; //width/size of the boss
    public final static int BOSS_HEALTH = 3000; //max health of the boss


    Boulder(int x, int y) { //constructor
        super(x, y);
        this.loadImage(BOSS_IMAGE); //load the image of boss
        this.health = Boulder.BOSS_HEALTH; //assigns the health of the boss

    }

    protected void checkCollision(Ship ship){ //method that checks if there's a collision that involves the boss
        for(int i = 0; i<ship.getBullets().size(); i++){ //for every bullet of the ship
            if(this.collidesWith(ship.getBullets().get(i))){ //this the boss collides with a bullet
            	this.playExplosionSound();
                ship.getBullets().get(i).setVisible(false); //the bullet disappears
                this.health -= ship.getStrength(); //reduces the health of the boss
                if(this.health <= 0){ //if the health of the boss is 0
                    this.isDead(); //the boss dies
                    ship.increaseScore(); //the ship earns a point
                    this.setVisible(false); //the boss disappears
                }
            }
        }
        if(this.collidesWith(ship)){ //if the boss collides with the ship
            if(ship.isBossImmune()==false){ //if the ship is not immune to the boss/outside the surface of the boss before it collides
            	this.playCollisionSound();
                 if(ship.isImmune() == false){ //if the ship is not immune(does not have a star powerup)
                     ship.reduceStrength(Boulder.BOSS_DAMAGE); //reduces the strength of the ship
                     ship.setBossImmune();
                 }
            }
        } else { //if the boss does not collides with the ship
            ship.resetBossImmune(); //resets the boss immunity
        }
    }
    @Override
    protected void setRotateImage() {
		if(this.moveRight == true) {
			this.loadImage(BOSSR_IMAGE);
		} else {
			this.loadImage(BOSS_IMAGE);
		}
	}
}