package miniprojtemplate;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{
    private GameStage gamestage;
    private GraphicsContext gc;
    private Scene theScene;
    private Ship myShip;
    private ArrayList<Rock> rocks;
    private ArrayList<PowerUps> powerups;
    private long startSpawn;
    private long endGame;
    private long launchBoss;
    private long launchPower;



    public final static int STATUS_HEIGHT = 20; //height of the status bar
    public static final int MAX_NUM_ROCKS = 7; //num of rocks spawned at the start
    public static final int MIN_NUM_ROCKS = 3; //num of rocks spawned every 5 seconds
    private final static double SPAWN_BOSS = 30; //time stamp when the boss shall be spawned
    private final static double POWER_INTERVAL = 10; //interval time for the powerups to appear
    private final static double SPAWN_DELAY = 5; //interval time for the rocks to be spawned
    private final static double GAME_TIME = 60; //the total game time


    GameTimer(GraphicsContext gc, Scene theScene, GameStage gs){
        this.gamestage = gs;
        this.gc = gc;
        this.theScene = theScene;
        this.myShip = new Ship("PH Agila",150,250);
        //instantiate the ArrayList of Rock
        this.rocks = new ArrayList<Rock>();
        this.startSpawn = System.nanoTime();
        this.endGame = System.nanoTime();
        this.launchBoss = System.nanoTime();
        this.launchPower = System.nanoTime();
        this.powerups = new ArrayList<PowerUps>();

        //call the spawnRocks method
        this.spawnRocks(GameTimer.MAX_NUM_ROCKS);
        //call method to handle mouse click event
        this.handleKeyPressEvent();
    }

    @Override
    public void handle(long currentNanoTime) {
        this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
        double spawnElapsedTime = (currentNanoTime - this.startSpawn) / 1000000000.0;
        double gameTimer = (currentNanoTime - this.endGame) / 1000000000.0;
        double bossCountdown = (currentNanoTime-this.launchBoss) / 1000000000.0;
        double spawnPower = (currentNanoTime - this.launchPower) / 1000000000.0;


        this.showStatus(gameTimer); //call showStatus method; pass gameTimer as parameter
        this.myShip.move(); //call move method
        this.moveBullets(); //call moveBullets
        this.moveRocks(); //call moveRocks
        this.checkPower(); //call checkPower
        this.myShip.render(gc); //render the ship
        this.renderBullets(); //call renderBullets
        this.renderRocks(); //call renderRocks
        this.renderPowerUps(); // call renderPowerUps

        if(spawnElapsedTime >= GameTimer.SPAWN_DELAY) { //spawn rocks every 5 seconds
            this.spawnRocks(GameTimer.MIN_NUM_ROCKS);
            this.startSpawn = System.nanoTime();
        }

        if(bossCountdown >= GameTimer.SPAWN_BOSS){ //spawn boss when the time reaches 30 seconds
        	this.spawnBoss();
        	this.launchBoss = System.nanoTime();
        }

        if(spawnPower >= GameTimer.POWER_INTERVAL){ //spawn powerups every 10 seconds
        	this.spawnPowerUps();
        	this.launchPower = System.nanoTime();
        }

        //checker if the ship is already not alive (strength reaches 0)
        if(!this.myShip.isAlive()) {
            this.stop(); //call stop
            this.gamestage.flashGameOver(this.myShip.isAlive(), this.myShip.getScore()); //flash the corresponding gameover stage
        }

        //check if the game runtime reaches the GAME_TIME
        if(gameTimer >= GameTimer.GAME_TIME){
        	this.stop(); //call stop
        	this.gamestage.flashGameOver(this.myShip.isAlive(), this.myShip.getScore()); //flash the corresponding gameover stage
        }



    }

    //method that will render/draw the rocks to the canvas
    private void renderRocks() {
        for (Rock r : this.rocks){
            r.render(this.gc);
        }
    }

  //method that will render/draw the bullets to the canvas
    private void renderBullets() {
        //iterate through the bullets of the ship and render them into the canvas
        for(Bullet b: this.myShip.getBullets()){
            b.render(gc);
        }
    }

    //method that will render the power ups to the canvas
    private void renderPowerUps(){
    	//iterate through the powerups and render them into the canvas
    	for(PowerUps p: this.powerups){
    		p.render(gc);
    	}
    }

    //method that will spawn/instantiate three rocks at a random x,y location
    private void spawnRocks(int rockNum){
        Random r = new Random();
        for(int i=0;i<rockNum;i++){
            int x = GameStage.WINDOW_WIDTH; //starting x position of the rocks should be at the right end of the window
            int y = r.nextInt(GameStage.WINDOW_HEIGHT-GameTimer.STATUS_HEIGHT)+(GameTimer.STATUS_HEIGHT); //randomizer for the y position; should start below the status bar

            //instantiate a new rock
            Rock rock = new Rock(x,y); //add a new object rock to the rocks arraylist
            if((x+(int)rock.width) >= GameStage.WINDOW_WIDTH){
                x-= rock.width;
            }
            rock = new Rock(x,(int)rock.adjustY(y));
            this.rocks.add(rock); //add the rock into the rock arrayist
        }

    }

    //method that will spawn/instantiate boss rock
    private void spawnBoss(){
    	Random r = new Random();

            int x = GameStage.WINDOW_WIDTH;
            int y = r.nextInt(GameStage.WINDOW_HEIGHT-GameTimer.STATUS_HEIGHT)+(GameTimer.STATUS_HEIGHT);

            //instantiate the boulder (boss)
            Boulder boss = new Boulder(x,y);
            if((boss.height + y) >= GameStage.WINDOW_HEIGHT){
                y-= boss.height;
            }
            boss = new Boulder(x,(int)boss.adjustX(y));
            this.rocks.add(boss); //add the boss into the arraylist of rocks

    }

    //method that will spawn the powerups
    private void spawnPowerUps(){

    	Random r = new Random();
    	int pType = r.nextInt(2); //randomizer for the powerup type that will appear

    	int x = r.nextInt(GameStage.WINDOW_WIDTH); //randomizer for x position
    	int y = r.nextInt(GameStage.WINDOW_HEIGHT-GameTimer.STATUS_HEIGHT)+(GameTimer.STATUS_HEIGHT); //randomizer for y position

        if(pType == 0){
        	Fuel powerType = new Fuel(x,y); //instantiate a fuel
        	powerType = new Fuel((int)powerType.adjustX(x),(int)powerType.adjustY(y));
        	this.powerups.add(powerType); //add to powerup arraylist
        }else if(pType == 1){
        	Star powerType = new Star(x,y); //instantiate a star
        	powerType = new Star ((int)powerType.adjustX(x),(int)powerType.adjustY(y));
        	this.powerups.add(powerType);//add to the powerup arraylsit
        }

    }

    //method that will move the bullets shot by a ship
    private void moveBullets(){
        //create a local arraylist of Bullets for the bullets 'shot' by the ship
        ArrayList<Bullet> bList = this.myShip.getBullets();

        //Loop through the bullet list and check whether a bullet is still visible.
        for(int i = 0; i < bList.size(); i++){
            Bullet b = bList.get(i);
            //checker if the bullet is visible; if it is visible, call move
            if(b.isVisible()){
                b.move();
            }else{ //if the bullet is not visible, remove from the bullet from the bullet arraylist
                bList.remove(i);
            }
        }
    }

    //method that will move the rocks
    private void moveRocks(){
        //Loop through the rocks arraylist
        for(int i = 0; i < this.rocks.size(); i++){
            Rock r = this.rocks.get(i);
            //checker if the rock is alive
            if(r.isAlive()){ //if alive, call move method
                r.move();
                r.checkCollision(this.myShip); //check if the rock collides with myShip
            }else{ //if not alive, remove the rock from the rock arraylist
                rocks.remove(i);
            }
        }
    }

    //method to check if the powerup is collected
    private void checkPower(){
    	for(int i = 0; i<this.powerups.size(); i++){
    		PowerUps p = this.powerups.get(i);
    		//checker if the powerup is visible
    		if(p.isVisible()){ //if visible, check if it collides with myShip by calling checkCollision method
    			p.checkCollision(this.myShip);
    		}else{ //if not visible, remove from powerups arraylist
    			powerups.remove(i);
    		}
    	}
    }

    //method that will listen and handle the key press events
    private void handleKeyPressEvent() {
        this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent e){
                KeyCode code = e.getCode();
                moveMyShip(code); //call moveMyShip method and pass the keycode as parameter
            }
        });

        this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
                    public void handle(KeyEvent e){
                        KeyCode code = e.getCode();
                        stopMyShip(code); //call stopMyship method and pass the keycode as parameter
                    }
                });
    }

    //method that will move the ship depending on the key pressed
    private void moveMyShip(KeyCode ke) {
        if(ke==KeyCode.UP) this.myShip.setDY(-10); //move up by 10 if the up key is pressed

        if(ke==KeyCode.LEFT) this.myShip.setDX(-10); //move left when the left key is pressed

        if(ke==KeyCode.DOWN) this.myShip.setDY(10); //move down when the down key is pressed

        if(ke==KeyCode.RIGHT) this.myShip.setDX(10); //move right when the down key is pressed

        if(ke==KeyCode.SPACE) this.myShip.shoot(); //call shoot method when space key is pressed

        System.out.println(ke+" key pressed.");
       }


    //method that will stop the ship's movement; set the ship's DX and DY to 0
    private void stopMyShip(KeyCode ke){
        this.myShip.setDX(0);
        this.myShip.setDY(0);
    }

   //showStatus method for the status bar
    private void showStatus(double gametimer){

    	//this.gc.drawImage(imgView, 0, 0);
        Font theFont = Font.font("Showcard Gothic",FontWeight.BOLD,20); //set the font type, style and size
        this.gc.setFont(theFont);
        this.gc.setFill(Color.WHITE); //set the text color
        //text for the runtime of the game
        this.gc.fillText("Time: "+String.valueOf((int)(60-gametimer + 1) + " secs"), 10, GameTimer.STATUS_HEIGHT);
        //text for the score of the ship
        this.gc.fillText("Score: "+String.valueOf(this.myShip.getScore()), 355, GameTimer.STATUS_HEIGHT);
        //text for the strength of the ship
        this.gc.fillText("Strength: "+String.valueOf(this.myShip.getStrength()), 653, GameTimer.STATUS_HEIGHT);
    }



}